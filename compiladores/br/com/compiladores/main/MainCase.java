package br.com.compiladores.main;

import br.com.compiladores.exceptions.IsiComecoException;
import br.com.compiladores.exceptions.IsiFimException;
import br.com.compiladores.exceptions.IsiLexicalException;
import br.com.compiladores.exceptions.IsiSyntaxException;
import br.com.compiladores.lexico.IsiScanner;
import br.com.compiladores.parser.IsiParser;

public class MainCase {
    public static void main(String[] args) {
        try {
            IsiScanner sc = new IsiScanner("input.isi");
            IsiParser pa = new IsiParser(sc);
            pa.S();   
        } catch (IsiLexicalException ex) {
            System.out.println("Lexical ERROR " + ex.getMessage());

        } catch (IsiSyntaxException ex) {
            System.out.println("Sintatico ERROR " + ex.getMessage());

        } catch (IsiComecoException ex) {
            System.out.println("Inicio ERROR " + ex.getMessage());

        }catch (IsiFimException ex) {
            System.out.println("Final ERROR " + ex.getMessage());

        } 
    }
}