package com.orangecyberdefense.sqltrees; 

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;

import static com.orangecyberdefense.sqltrees.SQLDSL.*;


public class SecureCriterionTest extends Search {
    // BEGIN_ARTICLE
    AST computeCriterion(String[] fields, String[] values) {
        ArrayList<AST> la = new ArrayList<AST>();
        for (int i = 0; i < Math.min(values.length, fields.length); i++) {
            if (values[i] != null) {
                la.add(eq(id(fields[i]), str(values[i])));
            }
        }
        return andList(la);
    }
    
    AST buildStatement(int y, String[] fields, String[] values) {
        return select(star,
                      from(id("songs")),
                      where(and(eq(id("yearofsong"), num(y)),
                                computeCriterion(fields, values))));
    }
    
    ResultSet search(int y, String[] fields, String[] values)
	throws SQLException {
        return buildStatement(y, fields, values).execute(con);
    }
    // END_ARTICLE
    
    CompiledStatement preSearch(int y, String[] fields, String[] values) {
        return buildStatement(y, fields, values).compile();
    }

    @Test
    public void testPreSearch() {
	CompiledStatement cs0 = tree.compile();
	CompiledStatement cs1 = preSearch(1964, fields, values);
	assertEquals(cs0.getTpl(), cs1.getTpl());
	assertArrayEquals(cs0.getArgs(), cs1.getArgs());
    }

    @Test
    public void testCompile() {
	CompiledStatement cs = tree.compile();
	assertEquals(expected_tpl, cs.getTpl());
	assertArrayEquals(expected_args, cs.getArgs());
    }
}
