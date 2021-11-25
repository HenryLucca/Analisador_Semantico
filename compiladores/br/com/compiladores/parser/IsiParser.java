package br.com.compiladores.parser;

import br.com.compiladores.exceptions.IsiComecoException;

import br.com.compiladores.exceptions.IsiSyntaxException;
import br.com.compiladores.lexico.IsiScanner;
import br.com.compiladores.lexico.Token;

public class IsiParser {

    private IsiScanner scanner;
    private Token token;
    private String variaveis[] = new String[100];
    private String[][] tipos = new String[100][100];
    private int contador = 0;
    private int contM1 = 0;
    private int contM2 = 0;
    private boolean encontrei = false;

    public IsiParser(IsiScanner scanner) {
        this.scanner = scanner;
    }

    public void S() {
        token = scanner.nextToken();
        if (token.getType() != 15) { // token.TK_INICIO
            throw new IsiComecoException("㋡ Era Esperado! Encontramos " + Token.TK_TEXT[token.getType()] + " ("
                    + token.getText() + ") Na linha: " + token.getLinha() + ", Na coluna: " + token.getColuna());
        }

        token = scanner.nextToken();
        if (!token.getText().equals("main")) {
            throw new IsiComecoException("main Era Esperado! Encontramos " + Token.TK_TEXT[token.getType()] + " ("
                    + token.getText() + ") Na linha: " + token.getLinha() + ", Na coluna: " + token.getColuna());
        }

        token = scanner.nextToken();
        if (!token.getText().equals("(")) {
            throw new IsiComecoException("Abrir parenteses Era Esperado! Encontramos " + Token.TK_TEXT[token.getType()]
                    + " (" + token.getText() + ") Na linha: " + token.getLinha() + ", Na coluna: " + token.getColuna());
        }

        token = scanner.nextToken();
        if (!token.getText().equals(")")) {
            throw new IsiComecoException("Fechar parenteses Era Esperado! Encontramos " + Token.TK_TEXT[token.getType()]
                    + " (" + token.getText() + ") Na linha: " + token.getLinha() + ", Na coluna: " + token.getColuna());
        }

        B();
        token = scanner.nextToken();
        if (token.getType() != Token.TK_END) {
            throw new IsiComecoException("# Era Esperado! Encontramos " + Token.TK_TEXT[token.getType()] + " ("
                    + token.getText() + ") Na linha: " + token.getLinha() + ", Na coluna: " + token.getColuna());
        } else {
            procuraTipo();
            System.out.println("NUNCA FOMOS TAO GRANDES");
        }
    }

    public void B() {
        token = scanner.nextToken();
        if (!token.getText().equals("{")) {
            throw new IsiComecoException("'{' Era Esperado! Encontramos " + Token.TK_TEXT[token.getType()] + " ("
                    + token.getText() + ") Na linha: " + token.getLinha() + ", Na coluna: " + token.getColuna());
        }
        token = scanner.nextToken();
        V();
        if (!token.getText().equals("}")) {
            throw new IsiComecoException("'}'Era Esperado! Encontramos " + Token.TK_TEXT[token.getType()] + " ("
                    + token.getText() + ") Na linha: " + token.getLinha() + ", Na coluna: " + token.getColuna());
        }
    }

    public void E() {
        token = scanner.nextToken();
        if (token.getType() != 15) { // token.TK_INICIO
            throw new IsiComecoException("㋡ Era Esperado! Encontramos " + Token.TK_TEXT[token.getType()] + " ("
                    + token.getText() + ") Na linha: " + token.getLinha() + ", Na coluna: " + token.getColuna());
        }

        T();
        if (!token.getText().equals("}")) {
            El();
        }

    }

    public void V() {
        token = scanner.nextToken();
        if (token.getType() != 15) { // token.TK_INICIO
            throw new IsiComecoException("㋡ Era Esperado! Encontramos " + Token.TK_TEXT[token.getType()] + " ("
                    + token.getText() + ") Na linha: " + token.getLinha() + ", Na coluna: " + token.getColuna());
        }

        token = scanner.nextToken();
        if (token.getType() != Token.TK_RESERVED) {
            throw new IsiComecoException("Declaração Era Esperado! Encontramos " + Token.TK_TEXT[token.getType()] + " ("
                    + token.getText() + ") Na linha: " + token.getLinha() + ", Na coluna: " + token.getColuna());
        } else {
            tipos[contM1][contM2] = token.getText(); // armazena o tipo das variaveis
            contM2++;
            T1();
        }
    }

    public void T1() {
        token = scanner.nextToken();
        if (token.getType() != Token.TK_END) {
            if (token.getType() != Token.TK_IDENTIFIER && token.getType() != Token.TK_OPERATOR
                    && token.getType() != Token.TK_FLOAT && token.getType() != Token.TK_NUMBER
                    && token.getType() != Token.TK_OP) {
                throw new IsiSyntaxException("Um Identificador ou um Numero era esperado!, Encontramos "
                        + Token.TK_TEXT[token.getType()] + " (" + token.getText() + ") Na linha: " + token.getLinha()
                        + ", Na coluna: " + token.getColuna());
            }
        }
        for (int i = 0; i < contador; i++) {
            if (token.getText().equals(variaveis[i])) {
                throw new IsiSyntaxException("Variavel com nome repetido!, Encontramos "
                        + Token.TK_TEXT[token.getType()] + " (" + token.getText() + ") Na linha: " + token.getLinha()
                        + ", Na coluna: " + token.getColuna());
            }
        }

        variaveis[contador] = token.getText(); // armazena o nome das variaveis para futuras comparações
        contador++;

        tipos[contM1][contM2] = token.getText(); // armazena o nome das variaveis para futuras comparações
        contM1++;
        token = scanner.nextToken();
        if (token.getType() == Token.TK_OP) {
            E();
        }
    }

    public void El() {
        token = scanner.nextToken();
        if (!token.getText().equals("}")) {

            if (token != null) {
                OP();
                T();
                El();
            }
        } else {

        }
    }

    public void T() {

        try {
            token = scanner.nextToken();
        } catch (Exception ex) {
            throw new IsiComecoException("# Era Esperado! Encontramos " + Token.TK_TEXT[token.getType()] + " ("
                    + token.getText() + ") Na linha: " + token.getLinha() + ", Na coluna: " + token.getColuna());
        }
        if (token.getType() == Token.TK_RESERVED) {
            T1();

        } else if (!token.getText().equals("}")) {
            if (token.getType() == 15) {
                token = scanner.nextToken();
            }
            if (token.getType() != Token.TK_IDENTIFIER && token.getType() != Token.TK_OPERATOR
                    && token.getType() != Token.TK_FLOAT && token.getType() != Token.TK_NUMBER
                    && token.getType() != Token.TK_OP) {
                throw new IsiSyntaxException("Um Identificador ou um Numero era esperado!, Encontramos "
                        + Token.TK_TEXT[token.getType()] + " (" + token.getText() + ") Na linha: " + token.getLinha()
                        + ", Na coluna: " + token.getColuna());
            }
            for (int i = 0; i < contador; i++) {
                if (token.getText().equals(variaveis[i])) {
                    encontrei = true;
                }
            }
            if (token.getType() == Token.TK_NUMBER) {

            } else {
                if (encontrei) {
                    encontrei = false;
                } else {
                    throw new IsiSyntaxException("Variavel não declarada, Encontramos " + Token.TK_TEXT[token.getType()]
                            + " (" + token.getText() + ") Na linha: " + token.getLinha() + ", Na coluna: "
                            + token.getColuna());
                }

            }

        }
    }

    public void OP() {
        if (token.getType() != Token.TK_ARIT && token.getType() != Token.TK_OPERATOR
                && token.getType() != Token.TK_OP) {
            throw new IsiSyntaxException("Esperavamos um operador, found " + Token.TK_TEXT[token.getType()] + " ("
                    + token.getText() + ")  Na Linha: " + token.getLinha() + ", Na coluna: " + token.getColuna());
        } else {

        }
    }

    public void procuraTipo() {
        for (int i = 0; i > tipos.length; i++) {
            for (int j = 0; j > tipos.length; j++) {
                System.out.println("chamar chama");
                System.out.println(tipos[i][j]);

            }
        }
    }
}