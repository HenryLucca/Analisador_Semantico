package br.com.compiladores.lexico;

public class Token {
    public static final int TK_IDENTIFIER = 0;
    public static final int TK_NUMBER = 1;
    public static final int TK_OPERATOR = 2;
    public static final int TK_PONTUACTION = 3;
    public static final int TK_ASSIGN = 4;
    public static final int TK_ARIT = 5;
    public static final int TK_OP = 6;
    public static final int TK_SPECIAL = 7;
    public static final int TK_RESERVED = 8;
    public static final int TK_FLOAT = 9;
    public static final int TK_EDNALDO = 14;
    public static final int TK_INICIO = 15;
    public static final int TK_EASTEREGG = 98;
    public static final int TK_END = 99;

    public static final String TK_TEXT[] = { "IDENTIFIER", "NUMBER", "OPERATOR", "PONTUACTION", "ASSIGN", "ARIT", "OP",
            "FLOAT", "SPECIAL", "RESERVED", "EDNALDO", "INICIO", "EASTEREGG", "END" };

    private int type;
    private String text;
    private int linha;
    private int coluna;

    public Token(int type, String text) {
        super();
        this.type = type;
        this.text = text;
    }

    public int getColuna() {
        return coluna;
    }

    public void setColuna(int coluna) {
        this.coluna = coluna;
    }

    public int getLinha() {
        return linha;
    }

    public void setLinha(int linha) {
        this.linha = linha;
    }

    public Token() {
        super();
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {

        switch (type) {
        case 0:
            return "Token [type= STRING" + ", text= " + text + "]";
        case 1:
            return "Token [type= INTEIRO" + ", text= " + text + "]";
        case 2:
            return "Token [type= OPERANDO" + ", text= " + text + "]";
        case 5:
            return "Token [type= ARITIMETICO" + ", text= " + text + "]";
        case 6:
            return "Token [type= FIM DA LINHA" + ", text= " + text + "]";
        case 7:
            return "Token [type= TERMO ESPECIAL" + ", text= " + text + "]";
        case 8:
            return "Token [type= PALAVRA RESERVADA" + ", text= " + text + "]";
        case 9:
            return "Token [type= DOUBLE" + ", text= " + text + "]";
        case 14:
            return "Token [type= VOCÊ NÃO VALE NADA, VOCÊ VALE TUDO \n VOCÊ TOPA QUALQUER PARADA, POIS VOCÊ QUER SER TUDO \n"
                    + ", text= " + text + "]";
        case 15:
            return "Token [type= COMEÇO DA LINHA" + ", text= " + text + "]";
        case 98:
            return "Token [type= FIFA 22 :) " + ", text= " + text + "]";
        case 99:
            return "Token [type= ENCERRA PROGRAMA" + ", text= " + text + "]";
        case 100:
            return "Token [type= COMENTARIO" + ", text= " + text + "]";

        }
        return text;
    }

}
