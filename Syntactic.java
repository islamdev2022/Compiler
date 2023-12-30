import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import javax.swing.JOptionPane;

public class Syntactic {

 ArrayList<String> LS = new ArrayList<String>();
    ArrayList<String[]> RS = new ArrayList<String[]>();
    
    ArrayList<String> terminals = new ArrayList<String>();
      public String parse(String input) {
        Stack<String> stack = new Stack<>();
        stack.push("#");  // End symbol
        stack.push(LS.get(0));  // Assuming the start symbol is the first element in LS

        Lexical lexicalAnalyzer = new Lexical(input,true);
            ArrayList<Lexical> lexemes = new ArrayList<>();

           lexicalAnalyzer.processFile(lexemes);

       // Lexical token = Tokens.getToken(input);  // Get the first token
for (Lexical lexeme : lexemes){
    System.out.println(lexeme.value);
    while (!stack.empty()) {
            String stackTop = stack.peek();
            System.out.println(terminals);
            System.out.println(stackTop);
            if (terminals.contains(stackTop)) {

                
                if (stackTop.equals(lexeme.value)) {  // token.value gives the terminal
                    stack.pop();
                      // Move to the next token
                    if (lexeme == null) break;  // End of input
                } else {
                    JOptionPane.showMessageDialog(null, "Error: Unexpected symbol '" + lexeme.type + "'");
                    return "Error";
                }
            } else {
                int rowIndex = LS.indexOf(stackTop);
                int colIndex = terminals.indexOf(lexeme.value);
                System.out.println("row ="+ rowIndex + "col = "+colIndex);
                System.out.println(lexeme.value);
                System.out.println(lexeme.type);
                if (rowIndex < 0 || colIndex < 0) {
                    JOptionPane.showMessageDialog(null, "Error: Invalid syntax");
                    return "Error";
                }

                stack.pop();
                String production = analysis_table[rowIndex][colIndex];
                if (!production.equals("~")) {  //  ~ represents epsilon
                    String[] symbols = production.split(" ");
                    for (int i = symbols.length - 1; i >= 0; i--) {
                        stack.push(symbols[i]);
                    }
                }
                System.out.println(stack);
            }
        }

        if (!stack.empty() || lexeme != null) {
            JOptionPane.showMessageDialog(null, "Error: Incomplete parsing");
            return "Error";
        }
}
        

        return "Success";
    }





     
    String[][] analysis_table;
    ArrayList<String>[] first;
    ArrayList<String>[] follow;
    
    boolean isCalculated = false;
    
    
    public int setup(String fileContent){
        String[] G;
        if((G = readFile(fileContent)) == null)return -1; //Error Grammar incorrect", "File Error!!
        
        for(int i=1;i<G.length;i++){
            String[] lrSides = G[i].split("->");
            LS.add(lrSides[0]);
            
            RS.add(new String[lrSides[1].split("\\|").length]);
            
            for(int j=0;j<RS.get(i-1).length;j++){
                RS.get(i-1)[j] = lrSides[1].split("\\|")[j];
            }
        }
        
        first = new ArrayList[RS.size()];
        for(int i=0;i<LS.size();i++){
            first[i] = new ArrayList<String>();
            if(!FIRST(LS.get(i), i,0))first[i].add("~");
        }
        
        if(first.length == 0)return -2; //Error cannot calculate FIRST
        
        follow = new ArrayList[RS.size()];
        for(int i=0;i<RS.size();i++){
            follow[i] = new ArrayList<String>();
            FOLLOW(LS.get(i), i);
        }
        
        if(follow.length == 0)return -3; //Error cannot calculate FOLLOW
        
        analysis_table = new String[RS.size()][terminals.size()];
        AnalysisTable();
        isCalculated = true;
        return 1; //SUCCESS

    }
    private static final int SOME_MAX_DEPTH = 20;
    private Set<String> alreadyProcessed = new HashSet<>();
    public Object analysisTable;
    
    private boolean FIRST(String r, int index, int depth) {
        // Termination condition to avoid infinite recursion
        if (depth > SOME_MAX_DEPTH || alreadyProcessed.contains(r)) {
            return false;
        }
        alreadyProcessed.add(r);

        boolean ep_test = true;
        int i, k = 0;
        for (i = 0; i < LS.size(); i++) {
            if (r.equals(LS.get(i))) break;
        }
        // Check if 'i' is valid to avoid out-of-bounds access
        if (i >= LS.size()) {
            return false;
        }

        for (int j = 0; j < RS.get(i).length; j++) {
            String[] s;
            k = 0;
            s = RS.get(i)[j].split("\\.");
            if (s[k].equals("~")) {
                ep_test = false;
                continue;
            } else {
                if (isNoneTerminal(s[k])) {
                    ep_test = ep_test && FIRST(s[k], index, depth + 1);

                    while (!ep_test && k + 1 < s.length) {
                        k++;
                        ep_test = ep_test || FIRST(s[k], index, depth + 1);
                    }
                } else {
                    if (!first[index].contains(s[k])) first[index].add(s[k]);
                }
            }
        }
        alreadyProcessed.remove(r); // Allow re-processing in future calls
        return ep_test;
    }
    
    private void FOLLOW(String r, int index) {
        if (r.equals(LS.get(0))) {
            if (follow[index] == null) {
                follow[index] = new ArrayList<>(); // Initialize the ArrayList if it's null
            }
            if (!follow[index].contains("#")) {
                follow[index].add("#");
            }
        }
        for (int i = 0; i < RS.size(); i++) {
            for (int j = 0; j < RS.get(i).length; j++) {
                
                System.out.println("RS.get(" + i + ")[" + j + "]: " + RS.get(i)[j]);

                if (RS.get(i) != null && j < RS.get(i).length) {
                  
                     String[] s = RS.get(i)[j].split("\\.");
                    for (int k = 0; k < s.length; k++) {
                    if (s[k].equals(r)) {
                        if (k + 1 < s.length) {
                            int nindex = NTindex(s[++k]);
                            if (nindex != -1) {
                                boolean ep_test = false;
    
                                if (follow[index] == null) {
                                    follow[index] = new ArrayList<>(); // Initialize the ArrayList if it's null
                                }
    
                                for (String item : first[nindex]) {
                                    if (!(ep_test = ep_test || item.equals("~"))) {
                                        if (!follow[index].contains(item)) {
                                            follow[index].add(item);
                                        }
                                    }
                                }
    
                                if (!ep_test) continue;
    
                                if (follow[nindex] == null) {
                                    follow[nindex] = new ArrayList<>(); // Initialize the ArrayList if it's null
                                }
    
                                for (String item : follow[nindex]) {
                                    if (!follow[index].contains(item)) {
                                        follow[index].add(item);
                                    }
                                }
                            } else if (!follow[index].contains(s[k])) {
                                if (follow[index] == null) {
                                    follow[index] = new ArrayList<>(); // Initialize the ArrayList if it's null
                                }
                                follow[index].add(s[k]);
                            }
                        } else {
                            if (i < index) {
                                if (follow[i] != null) { // Check if the ArrayList is null
                                    for (String item : follow[i]) {
                                        if (!follow[index].contains(item)) {
                                            if (follow[index] == null) {
                                                follow[index] = new ArrayList<>(); // Initialize the ArrayList if it's null
                                            }
                                            follow[index].add(item);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }// Rest of your code...
                } else {
                    System.out.println("null");
                        }                
                
            }
        }
    }
    
    
    private void AnalysisTable() {
        for (int i = 0; i < RS.size(); i++) {
            for (int j = 0; j < RS.get(i).length; j++) {
                ArrayList<String> tempSet = new ArrayList<>();
                String[] symbols = RS.get(i)[j].split("\\.");
                boolean epsilonPresent = false;
    
                for (String symbol : symbols) {
                    if (isNonTerminal(symbol)) {
                        int index = NTindex(symbol);
                        for (String firstElem : first[index]) {
                            if ("~".equals(firstElem)) {
                                epsilonPresent = true;
                            } else if (!tempSet.contains(firstElem)) {
                                tempSet.add(firstElem);
                            }
                        }
                    } else {
                        if (!"~".equals(symbol) && !tempSet.contains(symbol)) {
                            tempSet.add(symbol);
                        } else {
                            epsilonPresent = true;
                        }
                    }
    
                    if (epsilonPresent) {
                        for (String followElem : follow[i]) {
                            if (!tempSet.contains(followElem)) {
                                tempSet.add(followElem);
                            }
                        }
                        break; // Break after processing follow set
                    }
                }
    
                updateAnalysisTable(i, tempSet, RS.get(i)[j]);
            }
        }
    }
    
  
    
    private void updateAnalysisTable(int rowIndex, ArrayList<String> symbols, String production) {
        for (String symbol : symbols) {
            int columnIndex = terminals.indexOf(symbol);
            if (columnIndex != -1) {
                analysis_table[rowIndex][columnIndex] = production;
            }
        }
    }
    
    
    boolean isNoneTerminal(String s){
        for (String LS1 : LS)
            if (s.equals(LS1))return true;
        return false;
    }
    
    int NTindex(String s){
        for (int i = 0; i < LS.size(); i++)
            if(s.equals(LS.get(i)))return i;
        
        return -1;
    }
    private boolean isNonTerminal(String symbol) {
        return NTindex(symbol) != -1;
    }
    
    int Tindex(String s){
        for (int i = 0; i < terminals.size(); i++)
            if(terminals.get(i).equals(s))return i;
        return -1;
    }
    
    private String[] readFile(String fileContent){
       
        try {
            
            fileContent = fileContent.replace("\n", "");
            String[] t_g = fileContent.split(";end;");//for end line
            if(t_g.length==1)return null;
            
            String[] ts = t_g[0].split("\\{")[1].split("}")[0].split(","); // for terminals
            for(String t:ts)terminals.add(t);
            terminals.add("#");
            return t_g;
        } catch (Exception e) {
            return null;
        }
    }


}