package com.orangecyberdefense.sqltrees;

class OperatorAST extends AST {
    protected final AST neutral;

    OperatorAST(String s, AST[] exprs, int plevel, AST neutral) {
	super(s, exprs, plevel);
	this.neutral = neutral;
    }

    void writeTo(CompiledStatement b) {
	int n = getChildrenCount();
	if (n == 0) {
	    this.neutral.writeTo(b);
	} else {
	    getChild(0).cpwriteTo(b, parenLevel);
	    for (int i = 1; i < n; i++) {
		b.addToken(this.label);
		getChild(i).cpwriteTo(b, parenLevel);
	    }
	}
    }
}
