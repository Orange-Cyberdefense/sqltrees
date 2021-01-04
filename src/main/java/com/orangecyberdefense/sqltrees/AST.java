package com.orangecyberdefense.sqltrees;

import java.util.Arrays;
import java.sql.Connection;
import java.sql.ResultSet;

public class AST {
    protected String lp = "(";
    protected String rp = ")";
    protected final String label;
    protected final int parenLevel;

    private final AST[] children;

    // Constructors
    AST(String label, AST[] a, int plevel) {
	this.parenLevel = plevel;
	this.label = label;
	this.children = (a == null) ? new AST[0] : a;
    }

    // Accessors
    public int getChildrenCount() {
	return this.children.length;
    }
    
    public AST getChild(int i) {
	return this.children[i];
    }

    void writeTo(CompiledStatement b) {
	b.addToken(this.label);
	int n = getChildrenCount();
	for (int i = 0; i < n; i++) {
	    getChild(i).cpwriteTo(b, parenLevel);
	}
    }

    // parenthesis-surrounded write
    void pwriteTo(CompiledStatement b) {
	b.addToken(this.lp); writeTo(b); b.addToken(this.rp);
    }

    // conditional parenthesis-surrounded write
    // p is the precedence of the surrounding operator
    void cpwriteTo(CompiledStatement b, int p) {
	// if we bind tighter (lower precedence level) than the
	// surrounding operator, there is no need to parenthesize
	if (parenLevel < p) { writeTo(b); } else { pwriteTo(b); }
    }

    public CompiledStatement compile() {
	CompiledStatement b = new CompiledStatement();
	writeTo(b);
	return b;
    }
    
    public ResultSet execute(Connection con) throws CompiledStatementError {
	return compile().run(con);
    }

}
