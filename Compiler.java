import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Stack;


public class Compiler {
static Syntactic us;
private static javax.swing.JFrame analysisFrame;
private static javax.swing.JTable at;
private static void setup_anaframe(){
        analysisFrame.setVisible(true);
        
        Object[] result = new Object[us.terminals.size()+1];
        result[0] = "";
        System.arraycopy(us.terminals.toArray(), 0, result, 1, us.terminals.size());
        Object[] columnNames = result;
        
        String[][] anat = new String[us.RS.size()][us.terminals.size()+1];
        for(int i=0;i<anat.length;i++){
            anat[i][0] = us.LS.get(i);
            System.arraycopy(us.analysis_table[i], 0, anat[i], 1, us.analysis_table[i].length);
        }
        DefaultTableModel model = new DefaultTableModel(anat, columnNames);
        at.setModel(model);
    }

    

     public static void main(String[] args) {
        // Create and set up the window
        JFrame frame = new JFrame("Compiler");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 500);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        frame.setLayout(new BoxLayout (frame.getContentPane(),BoxLayout.Y_AXIS));
        // Create and set up the panel
        JPanel panel = new JPanel();
        frame.add(panel, BorderLayout.CENTER);
        JLabel textInputLabel = new JLabel("Write the text");
        panel.add(textInputLabel);
        JTextArea textArea = new JTextArea(7, 30);
        panel.add(textArea,BorderLayout.WEST);
        JScrollPane scrollPane = new JScrollPane(textArea,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        panel.add(scrollPane);
        
        JPanel buttonPanel = new JPanel();
       

        JButton fileChooserButton = new JButton("Choose File");
        panel.add(fileChooserButton,BoxLayout.Y_AXIS);

// Label and Button for file selection
        JLabel fileChooserLabel = new JLabel("Or choose a file:");
        panel.add(fileChooserLabel,BoxLayout.Y_AXIS);


        JButton submitButton = new JButton("Analyze Text lexically");
        buttonPanel.add(submitButton);
        panel.add(buttonPanel);
        
       
        // Create and set up the panel for the Grammar
        JPanel panel2 = new JPanel();
        frame.add(panel2, BorderLayout.CENTER);
        JLabel textInputLabel2 = new JLabel("Write the grammar");
        panel2.add(textInputLabel2);
        JTextArea textArea2 = new JTextArea(7, 30);

       JScrollPane scrollPane2 = new JScrollPane(textArea2,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        panel2.add(scrollPane2);
        
        JPanel buttonPanel2 = new JPanel();
       
        

        JButton fileChooserButton2 = new JButton("Choose File");
       
        panel2.add(fileChooserButton2,BoxLayout.Y_AXIS);

// Label and Button for file selection
        JLabel fileChooserLabel2 = new JLabel("Or choose a file:");
        panel2.add(fileChooserLabel2,BoxLayout.Y_AXIS);


        JButton testGrammar=new JButton("Test the grammar");
        panel2.add(testGrammar);

        JButton FirstAndFollow= new JButton("First And Follow Table");
        FirstAndFollow.setEnabled(false);
        panel2.add(FirstAndFollow);
        
        JButton submitButton2 = new JButton("Analyze Text syntactically");
        submitButton2.setEnabled(false);
        buttonPanel2.add(submitButton2);
        panel2.add(buttonPanel2);

        // Action listener for file chooser button
        fileChooserButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(frame);

                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    try {
                        String fileContent = new String(Files.readAllBytes(Paths.get(selectedFile.getAbsolutePath())));
                        textArea.setText(fileContent); // Set the text area with the file content
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        });
        
        fileChooserButton2.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(frame);

                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    try {
                        String fileContent = new String(Files.readAllBytes(Paths.get(selectedFile.getAbsolutePath())));
                        textArea2.setText(fileContent); // Set the text area with the file content
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        });

        // Add action listener to the button
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = textArea.getText();

        // Check if the text area is empty
        if (text.trim().isEmpty()) {
            // Show error message
            JOptionPane.showMessageDialog(frame, "Please enter text or choose a file for analysis.", 
                                          "Error", JOptionPane.ERROR_MESSAGE);
        } else {

        try {
            Lexical lexicalAnalyzer = new Lexical(text,true);
            ArrayList<Lexical> lexemes = new ArrayList<>();

           lexicalAnalyzer.processFile(lexemes);

           // Create a new frame for displaying the results
           JFrame lexicalResultFrame = new JFrame("Lexical Analysis Results");
           lexicalResultFrame.setSize(500, 300);

           // Create a text area for the results
           JTextArea resultTextArea = new JTextArea();
           resultTextArea.setEditable(false); // Make the text area non-editable

           // Add a scroll pane to the text area
           JScrollPane resultScrollPane = new JScrollPane(resultTextArea);
           lexicalResultFrame.add(resultScrollPane);

           // Append the results to the text area
           for (Lexical lexeme : lexemes) {
               resultTextArea.append("Type: " + lexeme.type + ", Value: '" + lexeme.value +
                                     "', At Line: " + lexeme.line + ", Column: " + lexeme.column + "\n");
           }

           // Show the result frame
           lexicalResultFrame.setVisible(true);
           
        } catch (Exception ex) {
            ex.printStackTrace();
        }}
    }

    
});



// the first and follow table : 
        JFrame firstFollowFrame = new JFrame("First and Follow table");
        firstFollowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        firstFollowFrame.setSize(500, 350);
        firstFollowFrame.setLocationRelativeTo(null);
        JList<String> nts = new JList<>();
        JList<String> first=new JList<>();
        JList<String> follow=new JList<>();

        JScrollPane jScrollPaneNTS = new JScrollPane();
        JScrollPane jScrollPaneFIRST = new JScrollPane();
        JScrollPane jScrollPaneFOLLOW = new JScrollPane();

        nts.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPaneNTS.setViewportView(nts);
        jScrollPaneNTS.setBounds(10, 50, 130, 200); // x, y, width, height
        firstFollowFrame.getContentPane().add(jScrollPaneNTS);


        follow.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPaneFOLLOW.setViewportView(follow);
        jScrollPaneFOLLOW.setBounds(330, 50, 135, 200);
        firstFollowFrame.getContentPane().add(jScrollPaneFOLLOW);

        first.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPaneFIRST.setViewportView(first);
        jScrollPaneFIRST.setBounds(166, 50, 135, 200);
        firstFollowFrame.getContentPane().add(jScrollPaneFIRST);


        JPanel names = new JPanel();
        names.setBounds(10, 20,500,15);
        JLabel labelNTS = new JLabel("NT's");
        labelNTS.setBounds(50, 30, 50, 15); // x, y, width, height
        names.add(labelNTS);
        
        JLabel labelFIRST = new JLabel("FIRST");
        labelFIRST.setBounds(200, 30, 50, 15); // x = original x of "NT's" + width of "NT's" + 50px spacing
        names.add(labelFIRST);
        
        JLabel LabelFOLLOW = new JLabel("FOLLOW");
        // x = x of "FIRST" + width of "FIRST" + 50px spacing
        LabelFOLLOW.setBounds(350, 30, 60, 15); // Adjust width of "FOLLOW" as needed
        names.add(LabelFOLLOW);
        firstFollowFrame.add(names);
        // the analysis table button

        JButton analyzeBtnTable = new JButton("Predictive Table");
        firstFollowFrame.getContentPane().add(analyzeBtnTable,BorderLayout.SOUTH);
        

        JTable at= new JTable();
        JScrollPane jScrollPane5 = new JScrollPane();
        
        at.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        at.setEnabled(false);
        at.setMinimumSize(new java.awt.Dimension(300, 72));
        jScrollPane5.setViewportView(at);

       
JFrame analysisFrame = new JFrame();
javax.swing.GroupLayout anaframeLayout = new javax.swing.GroupLayout(analysisFrame.getContentPane());
       analysisFrame.getContentPane().setLayout(anaframeLayout);
        anaframeLayout.setHorizontalGroup(
            anaframeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        anaframeLayout.setVerticalGroup(
            anaframeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );

//END
        testGrammar.addActionListener(new ActionListener() {
            @Override
        public void actionPerformed(ActionEvent e) {
            String text = textArea2.getText();

        // Check if the text area is empty
        if (text.trim().isEmpty()) {
            // Show error message
            JOptionPane.showMessageDialog(frame, "Please enter a grammar for the syntactic analysis.", 
                                          "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            switch((us = new Syntactic()).setup(text)){
                case 1:
                    
                    DefaultListModel<String> nts_model = new DefaultListModel<>();
                    for(String l:us.LS){
                        nts_model.addElement(l);
                    }
                    DefaultListModel<String> first_model = new DefaultListModel<>();
                    for(ArrayList<String> l:us.first){
                        String _tmp = "";
                        for(String s:l){
                            _tmp+=s+" | ";
                        }
                        first_model.addElement(_tmp.substring(0, _tmp.length()-3));
                    }
                    DefaultListModel<String> follow_model = new DefaultListModel<>();
                    for(ArrayList<String> l:us.follow){
                        String _tmp = "";
                        for(String s:l){
                            _tmp+=s+" | ";
                        }
                        follow_model.addElement(_tmp.substring(0, _tmp.length()-3));
                    }
                    
                    
                    JOptionPane.showMessageDialog(frame,"grammar correct","success", JOptionPane.INFORMATION_MESSAGE);
                    FirstAndFollow.setEnabled(true);
                    FirstAndFollow.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {       
                                nts.setModel(nts_model);
                    first.setModel(first_model);
                    follow.setModel(follow_model);
                    firstFollowFrame.setVisible(true);
                    
                        }
                        });
                    submitButton2.setEnabled(true); // Enable the analyze button

                    break;
                case -1:
                    JOptionPane.showMessageDialog(firstFollowFrame, "Error Grammar incorrect", "File Error!!", JOptionPane.ERROR_MESSAGE);
                    break;
                case -2:
                    JOptionPane.showMessageDialog(firstFollowFrame, "Error cannot calculate FIRST", "Syntax Error!!", JOptionPane.ERROR_MESSAGE);
                    break;
                case -3:
                    JOptionPane.showMessageDialog(firstFollowFrame, "Error cannot calculate FOLLOW", "Syntax Error!!", JOptionPane.ERROR_MESSAGE);
                    break;
            }
        }  
        }
        });
        analyzeBtnTable.addActionListener(new ActionListener() {
             @Override
            public void actionPerformed(ActionEvent e) { 
                at.setEnabled(true);
                setup_anaframe();
            }
        });

        submitButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { 
                
                String text = textArea.getText().replace(";\n", ";");
                ul = new ArrayList<>();
                stack.clear();
                stack.push(us.LS.get(0));
                
                if(text.length()==0){
                    return;
                }
                
                 Lexical lexicalAnalyzer = new Lexical(text,true);
            ArrayList<Lexical> lexemes = new ArrayList<>();

           lexicalAnalyzer.processFile(lexemes);
           for (Lexical lexeme : lexemes){
                System.out.println(lexeme.type +" "+ lexeme.value);
           }
                

                if(!isSyntaxicallyCorrect(lexemes)){System.out.println("Erreur Syntaxique");JOptionPane.showMessageDialog(frame, "Syntaxic Error", "Syntaxic Error", JOptionPane.ERROR_MESSAGE);return;}
                JOptionPane.showMessageDialog(frame, "Correct", "Correct", JOptionPane.NO_OPTION);
                System.out.println("Correct");
    }
});

    
// Display the window
    frame.setVisible(true);
    }
            static Stack stack = new Stack();
static ArrayList<Lexical> ul;

static boolean isSyntaxicallyCorrect(ArrayList<Lexical> ul){
        String at_result;
        for (Lexical u : ul) {
            String v;
            if(u.type.equals("Identifier")) v = u.type;
            else v = u.value;
            
            String top_stack;
            while(!(top_stack=(String) stack.pop()).equals(v)){
            

                int i = us.Tindex(v);
                int j;
                if((j = us.NTindex(top_stack))==-1)return false;
                
                if((at_result = us.analysis_table[j][i]) == null) return false;
                else if(at_result.equals("~")){
                    if(stack.empty()) return false;
                    continue;
                }

                for (int k = at_result.split("\\.").length-1; k >= 0; k--) {
                    stack.push(at_result.split("\\.")[k]);
                }
            }
        }
        while(!stack.empty()){
            int j, i = us.Tindex("#");
            if((j = us.NTindex((String) stack.pop()))==-1)return false;
            if((at_result= us.analysis_table[j][i]) == null)return false;
            if(!at_result.equals("~"))return false;
        }
        return true;
    }

}