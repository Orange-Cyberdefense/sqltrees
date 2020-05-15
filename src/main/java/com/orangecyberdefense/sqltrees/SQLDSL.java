package com.orangecyberdefense.sqltrees;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public final class SQLDSL {
    // Reserved keywords
    private static String[] keywords = {
	"select", "from", "where", "having", "group", "by",
	"and", "or"
    };
    static { Arrays.sort(keywords); } // lets us binarySearch the array.

    // private as instantiating it makes no sense
    private SQLDSL() { return; }
    
    public static AST num(long n) {
	return new AST(Long.valueOf(n).toString(), null, 0);
    }

    public static AST str(String s) {
	return new StrLit(s);
    }
    
    private static Pattern allowedIdent =
	Pattern.compile("^[a-zA-Z][a-zA-Z0-9]*$");

    public static AST id(String s) {
	if (!allowedIdent.matcher(s).matches()
	    || Arrays.binarySearch(keywords, s.toLowerCase()) >= 0) {
	    throw new IllegalArgumentException("Invalid ident: " + s);
	}
	return new AST(s, null, 0);
    }

    public static AST star = new AST("*", null, 0);

    public static AST eq(AST e0, AST e1) {
	return new OperatorAST("=", new AST[]{ e0, e1 }, 100, null);
    }

    public static AST sum(AST... exprs) {
	return new OperatorAST("+", exprs, 50, null);
    }
			
    public static AST tuple(AST... elements) {
	return new OperatorAST(",", elements, 200, null);
    }

    public static AST select(AST... elements) {
	return new AST("select", elements, 1000);
    }

    public static AST from(AST e) {
	return new AST("from", new AST[]{ e }, 201);
    }

    public static AST where(AST c) {
	return new AST("where", new AST[]{ c }, 201);
    }

    public static AST trueExpr = eq(num(1), num(1));
    public static AST falseExpr = eq(num(0), num(1));

    public static AST andArray(AST[] exprs) {
	return new OperatorAST("and", exprs, 150, trueExpr);
    }

    public static AST andList(List<AST> exprs) {
	return andArray(exprs.toArray(new AST[0]));
    }
    
    public static AST and(AST... exprs) {
	return andArray(exprs);
    }

    public static AST orArray(AST[] exprs) {
	return new OperatorAST("or", exprs, 150, falseExpr);
    }

    public static AST orList(List<AST> exprs) {
	return orArray(exprs.toArray(new AST[0]));
    }

    public static AST or(AST... exprs) {
	return orArray(exprs);
    }
}
