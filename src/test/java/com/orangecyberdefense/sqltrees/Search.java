package com.orangecyberdefense.sqltrees;

import java.sql.*;
// import org.sqlite.JDBC;

import static com.orangecyberdefense.sqltrees.SQLDSL.*;

public abstract class Search {
    String songtitle = "I'm a loser";
    String author = "Lennon";
    String[] fields = { "songtitle", "author" };
    String[] values = { songtitle, author };
    AST tree = select(star, from(id("songs")),
		     where(and(eq(id("yearofsong"), num(1964)),
			       and(eq(id("songtitle"), str(songtitle)),
				   eq(id("author"), str(author))))));
    String expected_tpl =
	"select * from songs where yearofsong = 1964 and ( songtitle = ? and author = ? ) ";
    String[] expected_args = { songtitle, author };

    String dburl = "jdbc:sqlite:test.db";
    Connection con = null;

    public void init() throws SQLException {
        con = DriverManager.getConnection(dburl);
    }

    abstract ResultSet search(int y, String[] fields, String[] values) throws SQLException;

}
