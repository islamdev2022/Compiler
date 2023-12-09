import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class Syntactic {
 ArrayList<String> LS = new ArrayList<String>();
    ArrayList<String[]> RS = new ArrayList<String[]>();
    
    ArrayList<String> terminals = new ArrayList<String>();
     
    String[][] analysis_table;
    ArrayList<String>[] first;
    ArrayList<String>[] follow;
    
    boolean isCalculated = false;
    
    
    public int setup(String fileContent){
        String[] G;
        if((G = readFile(fileContent)) == null)return -1;
        
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
        
        if(first.length == 0)return -2;
        
        follow = new ArrayList[RS.size()];
        for(int i=0;i<RS.size();i++){
            follow[i] = new ArrayList<String>();
            FOLLOW(LS.get(i), i);
        }
        
        if(follow.length == 0)return -3;
        
        analysis_table = new String[RS.size()][terminals.size()];
        AnalysisTable();
        isCalculated = true;
        return 1;

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
        if (r.equals(LS.get(0)) && !follow[index].contains("#")) {
            follow[index].add("#");
        }
    
        for (int i = 0; i < RS.size(); i++) {
            for (int j = 0; j < RS.get(i).length; j++) {
                String[] s = RS.get(i)[j].split("\\.");
                for (int k = 0; k < s.length; k++) {
                    if (s[k].equals(r)) {
                        if (k + 1 < s.length) {
                            int nindex = NTindex(s[k + 1]);
                            if (nindex != -1) {
                                boolean ep_test = false;
                                for (String item : first[nindex]) {
                                    if (item.equals("~")) {
                                        ep_test = true;
                                    } else if (!follow[index].contains(item)) {
                                        follow[index].add(item);
                                    }
                                }
                                if (ep_test) {
                                    for (String item : follow[nindex]) {
                                        if (!follow[index].contains(item)) {
                                            follow[index].add(item);
                                        }
                                    }
                                }
                            } else if (!follow[index].contains(s[k + 1])) {
                                follow[index].add(s[k + 1]);
                            }
                        } else if (i < index) {
                            for (String item : follow[i]) {
                                if (!follow[index].contains(item)) {
                                    follow[index].add(item);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    private void AnalysisTable(){
        for (int i = 0; i < RS.size(); i++) {
            for (int j = 0; j < RS.get(i).length; j++) {
                ArrayList<String> _tmp = new ArrayList<String>();
                String[] s = RS.get(i)[j].split("\\.");
                for (int k = 0; k < s.length; k++) {
                    int index = NTindex(s[k]);
                    boolean ep_test=false;
                    if(index != -1){
                        
                        for (String fst : first[index]) 
                            if(!(ep_test=ep_test || fst.equals("~")))
                                if(!_tmp.contains(fst))_tmp.add(fst);
                        }
                        else{
                            if(!(ep_test=s[k].equals("~")))if(!_tmp.contains(s[k]))_tmp.add(s[k]);
                        }
                    if(ep_test)
                        for (String flw : follow[i]) 
                            if(!_tmp.contains(flw))_tmp.add(flw);
                    break;
                }
                for (String t : _tmp) {
                    for (int k = 0; k < terminals.size(); k++) {
                        if(t.equals(terminals.get(k))){
                            analysis_table[i][k] = RS.get(i)[j];
                            break;
                        }
                    }
                }
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
