package com.orangecyberdefense.sqltrees;

import java.util.Arrays;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

class StrLit extends AST {
    StrLit(String label) {
	super(label, null, 0);
    }

    void writeTo(CompiledStatement b) {
	b.addStringParam(this.label);
    }
}
