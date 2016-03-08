
package net.stgtech.chemistryHelper.controller;


public class Parser {
    private String equation;
    private int pos = -1, c;

    private void eatChar() {
        c = (++pos < equation.length()) ? equation.charAt(pos) : -1;
    }

    private void eatSpace() {
        while (Character.isWhitespace(c)) eatChar();
    }

    public double parse(String equation) {
        this.equation = equation;
        eatChar();
        double v = parseExpression();
        if (c != -1) throw new RuntimeException("Unexpected: " + (char)c);
        return v;
    }

    private double parseExpression() {
        double v = parseTerm();
        for (;;) {
            eatSpace();
            switch (c) {
                case '+':
                    // addition
                    eatChar();
                    v += parseTerm();
                    break;
                case '-':
                    // subtraction
                    eatChar();
                    v -= parseTerm();
                    break;
                default:
                    return v;
            }
        }
    }

    private double parseTerm() {
        double v = parseFactor();
        for (;;) {
            eatSpace();
            switch (c) {
                case '/':
                    // division
                    eatChar();
                    v /= parseFactor();
                    break;
                case '*':
                case '(':
                    // multiplication
                    if (c == '*') eatChar();
                    v *= parseFactor();
                    break;
                default:
                    return v;
            }
        }
    }

    private double parseFactor() {
        double v;
        boolean negate = false;
        eatSpace();
        if (c == '+' || c == '-') { // unary plus & minus
            negate = c == '-';
            eatChar();
            eatSpace();
        }
        if (c == '(') { // brackets
            eatChar();
            v = parseExpression();
            if (c == ')') eatChar();
        } else { 
            int startIndex = this.pos;
            while ((c >= '0' && c <= '9') || c == '.') eatChar();
            if (pos == startIndex) throw new RuntimeException("Unexpected: " + (char)c);
            v = Double.parseDouble(equation.substring(startIndex, pos));
        }

        eatSpace();
        if (c == '^') {
            eatChar();
            v = Math.pow(v, parseFactor());
        }
        if (negate) v = -v;
        
        return v;
        
    }
}