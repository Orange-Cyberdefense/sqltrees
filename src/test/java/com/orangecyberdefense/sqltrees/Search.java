package com.orangecyberdefense.sqltrees;

import java.sql.*;
// import org.sqlite.JDBC;

import static com.orangecyberdefense.sqltrees.SQLDSL.*;

public abstract class Search {
    String name = "Scarlett";
    String surname = "O'Hara";
    String[] fields = { "name", "surname" };
    String[] values = { name, surname };
    AST tree = select(star, from(id("events")),
		     where(and(eq(id("year"), num(1936)),
			       and(eq(id("name"), str(name)),
				   eq(id("surname"), str(surname))))));
    String expected_tpl = "select * from events where year = 1936 and ( name = ? and surname = ? ) ";
    String[] expected_args = { name, surname };

    String dburl = "jdbc:sqlite:test.db";
    Connection con = null;

    public void init() throws SQLException {
        con = DriverManager.getConnection(dburl);
    }

    abstract ResultSet search(int y, String[] fields, String[] values) throws SQLException;

    // public void test() throws Exception {
    //     String[] fields = { "name", "note" };
    //     String[] values = { "Smith", "1=1 ' UNION SELECT * FROM events -- " };
    //     init();
    //     ResultSet rs = search(2020, fields, values);
    //     System.out.println("table events queried");
    //     while (rs.next()) {
    //         int uid = rs.getInt("uid");
    //         String name = rs.getString("name");
    //         int year = rs.getInt("year");
    //         String note = rs.getString("note");
    //         System.out.printf("%s:%d:%s:%s\n", uid, year, name, note);
    //     }
    // }
}
