package com.ciencias.core;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class ParserTest extends TestCase {

    public static Test suite(){
        return new TestSuite(ParserTest.class);
    }

    public void testParser(){
        assertTrue(Parser.hasPrecedence('*', '+'));
        assertTrue(Parser.hasPrecedence('^', '+'));
        assertTrue(Parser.hasPrecedence('^', '*'));
        assertTrue(Parser.hasPrecedence('^', '/'));
        assertTrue(Parser.hasPrecedence('/', '-'));
    }
}
