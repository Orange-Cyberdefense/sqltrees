package com.orangecyberdefense.sqltrees; 

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CriterionTest extends Search {
    // BEGIN_ARTICLE
    String computeCriterion(String[] fields, String[] values) {
        StringBuffer b = new StringBuffer();
        for (int i = 0; i < Math.min(values.length, fields.length); i++) {
            if (values[i] != null) {
                b.append(" AND " + fields[i] + " = '" + values[i] + "'");
            }
        }
        return b.toString();
    }
    
    PreparedStatement buildStatement(int y, String[] fields,
                                     String[] values)
	throws SQLException {
        String crit = computeCriterion(fields, values);
        String s = "SELECT * FROM songs WHERE yearofsong = ?" + crit;
        PreparedStatement ps = con.prepareStatement(s);
        ps.setInt(1, y);
        return ps;
    }
    
    ResultSet search(int y, String[] fields, String[] values)
	throws SQLException {
        return buildStatement(y, fields, values).executeQuery();
    }
    // END_ARTICLE

    String preBuildStatement(int y, String[] fields,
                                     String[] values)
	throws SQLException {
        String crit = computeCriterion(fields, values);
        String s = "SELECT * FROM songs WHERE yearofsong = ?" + crit;
        return s;
    }

    @Test
    public void testPreBuildStatement()
	throws SQLException {
	String[] fields = { "songtitle" };
	String[] values = { "I'm a loser" };
	String s = preBuildStatement(42, fields, values);
	assertEquals(s, "SELECT * FROM songs WHERE yearofsong = ? AND songtitle = 'I'm a loser'");
    }

}
