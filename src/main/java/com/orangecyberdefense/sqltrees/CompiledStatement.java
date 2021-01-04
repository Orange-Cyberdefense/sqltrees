package com.orangecyberdefense.sqltrees;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class CompiledStatement {
    private final StringBuffer tpl = new StringBuffer();
    private final ArrayList<String> args = new ArrayList<String>();

    public String getTpl() {
       return tpl.toString();
    }

    public String[] getArgs() {
        return args.toArray(new String[0]);
    }

    void addStringParam(String s) {
	tpl.append("? ");
	args.add(s);
    }

    void addToken(String repr) {
	tpl.append(repr);
	tpl.append(" ");
    }

    PreparedStatement getPreparedStatement(Connection con)
      throws SQLException {
        PreparedStatement stmt = con.prepareStatement(tpl.toString());
        for(int i = 0; i < args.size(); i++) {
            stmt.setString(i+1, // numbering starts at 1
                           args.get(i));
        }
        return stmt;
    }

    public ResultSet run(Connection con) throws CompiledStatementError {
        try {
            return getPreparedStatement(con).executeQuery();
        } catch (SQLException e) {
            throw new CompiledStatementError();
        }
    }
}
