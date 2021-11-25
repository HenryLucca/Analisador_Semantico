package br.com.compiladores.lexico;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import br.com.compiladores.exceptions.IsiLexicalException;

public class IsiScanner {
    private char[] content;
    private int estado;
    private int pos;
    private int linha;
    private int coluna;

    public IsiScanner(String filename) {
        try {
            linha = 1;
            coluna = 0;
            String txtConteudo;
            txtConteudo = new String(Files.readAllBytes(Paths.get(filename)), StandardCharsets.UTF_8);
            System.out.println("---DEBUG---");
            System.out.println(txtConteudo);
            System.out.println("-----------");
            content = txtConteudo.toCharArray();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Token nextToken() {
        char currentChar;
        Token token;

        String term = "";
        if (isEOF()) {
            return null;
        }
        estado = 0;
        while (true) {
            currentChar = nextChar();
            coluna++;

            switch (estado) {
            case 0:
                if (isSpace(currentChar)) {
                    estado = 0;
                } else if (isDigit(currentChar)) {
                    term += currentChar;
                    estado = 1;
                } else if (isChar(currentChar)) {
                    term += currentChar;
                    estado = 5;
                } else if (isOperator(currentChar)) {
                    term += currentChar;
                    token = new Token();
                    token.setType(2);
                    token.setText(term);
                    token.setLinha(linha);
                    token.setColuna(coluna-term.length());
                    return token;
                } else if (isSpecial(currentChar)) {
                    term += currentChar;
                    estado = 9;
                } else if (isAr(currentChar)) {
                    term += currentChar;
                    estado = 10;
                } else if (isEndOfFile(currentChar)) {
                    term += currentChar;
                    estado = 11;
                } else if (isEndOfLine(currentChar)) {
                    term += currentChar;
                    estado = 12;
                } else if (isStartofLine(currentChar)) {
                    term += currentChar;
                    estado = 13;
                } else if (isCommentary(currentChar)) {
                    term += currentChar;
                    estado = 16;
                }

                else {
                    throw new IsiLexicalException("Simbolo desconhecido");
                }
                break;

            case 1: // tratamento de numero
                if (isDigit(currentChar)) {
                    term += currentChar;
                    estado = 1;
                } else if (currentChar == '.') {
                    term += currentChar;
                    estado = 2;
                } else {
                    back();
                    estado = 3;
                }
                break;

            case 2:// tratamento de double
                if (isDigit(currentChar)) {
                    term += currentChar;
                    estado = 2;
                } else {
                    estado = 4;
                }
                break;

            case 3: // retornando numero inteiro
                back();
                token = new Token();
                token.setText(term);
                token.setType(1);
                token.setLinha(linha);
                token.setColuna(coluna-term.length());
                return token;

            case 4: // retornando numero float
                back();
                token = new Token();
                token.setText(term);
                token.setType(9);
                token.setLinha(linha);
                token.setColuna(coluna - term.length());
                return token;
            case 5: // tratando de string
                if (isChar(currentChar) || isDigit(currentChar)) {
                    term += currentChar;
                    estado = 5;
                } else if (term.equals("bola")) {
                    back();
                    estado = 14;
                } else if (term.equals("ednaldopereira")) {
                    back();
                    estado = 15;
                } else {
                    back();
                    estado = 6;
                }
                break;
            case 6:// retornando string e tratando palavras reservadas
                
                back();
                token = new Token();
                if (term.equals("main") || term.equals("if") || term.equals("else") || term.equals("while")
                        || term.equals("do") || term.equals("for") || term.equals("int") || term.equals("float")
                        || term.equals("char")) {
                    token.setText(term);
                    token.setType(8);
                    token.setLinha(linha);
                    token.setColuna(coluna-term.length());
                    return token;
                } else {
                    token.setText(term);
                    token.setType(0);
                    token.setLinha(linha);
                    token.setColuna(coluna-term.length());
                    return token;
                }

            case 7: // tratando do operando seguido de = ou passando pro 8 pra finalizar
                if (currentChar == '=') {
                    term += currentChar;
                    token = new Token();
                    token.setText(term);
                    token.setType(2);
                    token.setLinha(linha);
                    token.setColuna(coluna-term.length());
                    return token;

                } else {
                    back();
                    estado = 8;
                }
                break;

            case 8: // estado final para retornar operando
                back();
                token = new Token();
                token.setText(term);
                token.setType(2);
                token.setLinha(linha);
                token.setColuna(coluna-term.length());
                return token;

            case 9: // termo especial ()
                back();
                token = new Token();
                token.setText(term);
                token.setType(7);
                token.setLinha(linha);
                token.setColuna(coluna-term.length());
                return token;

            case 10: // Aritimetico
                back();
                token = new Token();
                token.setText(term);
                token.setType(5);
                token.setLinha(linha);
                token.setColuna(coluna-term.length());
                return token;

            case 11: // final
                token = new Token();

                token.setText(term);
                token.setType(99);
                token.setLinha(linha);
                token.setColuna(coluna-term.length());
                return token;

            case 12: // fim da linha
                token = new Token();
                token.setText(term);
                token.setType(6);
                token.setLinha(linha);
                token.setColuna(coluna-term.length());
                return token;

            case 13: // começo da linha
                back();
                token = new Token();
                token.setText(term);
                token.setType(15);
                token.setLinha(linha);
                token.setColuna(coluna-term.length());
                return token;

            case 14: // EasterEgg
                token = new Token();
                back();
                if (term.equals("bola")) {
                    token.setText(term);
                    token.setType(98);
                    token.setLinha(linha);
                    token.setColuna(coluna-term.length());
                    return token;
                } else {
                    token.setText(term);
                    token.setType(0);
                    token.setLinha(linha);
                    token.setColuna(coluna-term.length());
                    return token;

                }
            case 15: // EDNALDO PEREIRA
                token = new Token();
                back();
                if (term.equals("ednaldopereira")) {
                    token.setText(term);
                    token.setType(14);
                    token.setLinha(linha);
                    token.setColuna(coluna-term.length());
                    return token;
                } else {
                    token.setText(term);
                    token.setType(0);
                    token.setLinha(linha);
                    token.setColuna(coluna-term.length());

                    return token;

                }
            case 16: // comentario

                token = new Token();
                back();
                if (term.equals("^")) {
                    token.setText(term);
                    token.setType(100);
                    token.setLinha(linha);
                    token.setColuna(coluna-term.length());
                    return token;
                } else {
                    token.setText(term);
                    token.setType(0);
                    token.setLinha(linha);
                    token.setColuna(coluna-term.length());
                    return token;
                }
            }
        }

    }

    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }


    private boolean isChar(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

    private boolean isOperator(char c) {
        return c == '>' || c == '<' || c == '!' || c == '=';
    }

    private boolean isSpecial(char c) {
        return c == '(' || c == ')' || c == '{' || c == '}' || c == ',' || c == ';';
    }

    private boolean isSpace(char c) {
        if (c == '\n' || c == '\r') {
            linha++;
            coluna = 0;
        }
        return c == ' ' || c == '\t' || c == '\n' || c == '\r';
    }

    private boolean isEndOfFile(char c) {
        return c == '#';
    }

    private boolean isEndOfLine(char c) {
        return c == '%';
    }

    private boolean isStartofLine(char c) {
        return c == '㋡';
    }

    private boolean isCommentary(char c) {
        return c == '^';
    }

    private boolean isAr(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    private char nextChar() {
        return content[pos++];
    }

    private boolean isEOF() {
        return pos == content.length;
    }

    private void back() {
        pos = pos - 1;
        coluna--;

    }

}
