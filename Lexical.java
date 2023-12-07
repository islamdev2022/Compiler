import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Lexical {
    private FileInputStream fileInputStream;
    private static String fileContent;
    
    int line;
    int column;
    public String type,value;
    //Constructors 
    public static void LexicalT(String filename) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                contentBuilder.append(line).append("\n"); // Append each line and a newline character
            }
        }
       fileContent = contentBuilder.toString();
        
    }

    public Lexical (String type,String value){
        this.type=type;
        this.value=value;
    }

    public Lexical(String text, boolean isText) {
        if (isText) {
            this.fileContent = text;
        }
    }

    public Lexical() {
    }

    public void processFile(ArrayList<Lexical> u) {
        getLexemes(u, this.fileContent);
    }

    //Functions
    static boolean isLetter(char c){
        return ((c>='a' && c<='z') || (c>='A' && c<='Z'));
    }
    static boolean isPunctuation(char c) {
        return c == ';' || c == ',' || c == ':';
    }
    static boolean isLeftParenthesis(char c){
        return c=='(';  
    }
    static boolean isRightParenthesis(char c){
        return c==')';
    }

    public void getLexemes(ArrayList<Lexical> u, String s) {
        int i = 0, iBegin;
        int currentLine = 1; // Starting from line 1
        int currentColumn = 1; // Starting from column 1
        while (i < s.length()) {
            if (s.charAt(i) == '\n') { // Handle newline
                currentLine++;
                currentColumn = 1;
                i++;
                continue;
            }
            while (i < s.length() && s.charAt(i) == ' ') { // Skip spaces and update column
                currentColumn++;
                i++;
            }
            iBegin = i;
            while (i < s.length() && !isPunctuation(s.charAt(i)) && !isLeftParenthesis(s.charAt(i)) && 
                   !isRightParenthesis(s.charAt(i)) && s.charAt(i) != ' ' && s.charAt(i) != '\n') {
                i++;
            }
            if (i != iBegin) {
                Lexical newToken = Tokens.getToken(s.substring(iBegin, i));
                newToken.line = currentLine;
                newToken.column = currentColumn;
                u.add(newToken);
                currentColumn += (i - iBegin);
            }
            if (i < s.length() && !s.substring(i, i + 1).trim().isEmpty()) {
                Lexical newChar = Tokens.getToken(s.substring(i, i + 1));
                newChar.line = currentLine;
                newChar.column = currentColumn;
                u.add(newChar);
                currentColumn++;
            }
            if (i < s.length() && s.charAt(i) == ' ') {
                currentColumn++;
            }
            i++;
        }
    }
    
    public FileInputStream getFileInputStream() {
        return fileInputStream;
    }
    public void readNextChar() throws IOException {
        fileInputStream.read();
    }

}

