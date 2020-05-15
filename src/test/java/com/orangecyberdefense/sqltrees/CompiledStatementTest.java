package com.orangecyberdefense.sqltrees;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class CompiledStatementTest {
  @Test
  public void testAddStringParam() {
      CompiledStatement s = new CompiledStatement();
      s.addStringParam("hello");
      assertEquals(s.getTpl(), "? ");
  }
}
