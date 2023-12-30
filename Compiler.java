import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
import java.util.Vector;


public class Compiler {
static Syntactic us;

static Stack stack = new Stack();
static ArrayList<Lexical> ul;
    

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
                    JOptionPane.showMessageDialog(frame,"grammar correct","success", JOptionPane.INFORMATION_MESSAGE);
                    FirstAndFollow.setEnabled(true);


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
                setup_anaframe();
            }
        });

        JTextArea resultArea = new JTextArea(10, 30);
        resultArea.setEditable(false);

        // Add components to the panel or frame
        // Adjust this based on your existing layout and design
        JPanel inputPanel = new JPanel();
        inputPanel.add(new JScrollPane(resultArea));


         JFrame frame3 = new JFrame("syn");
         frame3.setSize(600, 400);
         frame3.setLocationRelativeTo(null);
        
        frame3.add(inputPanel, BorderLayout.CENTER);

        submitButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = textArea.getText().replace(";\n", ";");;
                String grammar = textArea2.getText();

                // Assuming 'us' is your Syntactic instance and it's properly initialized
                // You might need to modify how you handle the grammar setup and parsing
                (us = new Syntactic()).setup(grammar);
                String result = us.parse(text);
                resultArea.setText(result);
                frame3.setVisible(true);
            }
});

    
// Display the window
    frame.setVisible(true);
   
    }
            
    private static void setup_anaframe() {
        // Check if the necessary data is available and properly initialized
        if (us.terminals == null || us.RS == null || us.LS == null || us.analysis_table == null) {
            System.out.println("terminals or something else is null");
            return;
        }
    
        // Prepare column names for the table model
        Object[] result = new Object[us.terminals.size() + 1];
        result[0] = "";
        if (us.terminals.size() > 0) {
            System.arraycopy(us.terminals.toArray(), 0, result, 1, us.terminals.size());
            System.out.println(Arrays.toString(result));
        }
        Object[] columnNames = result;
    
        // Prepare data for the table model
        String[][] anat = new String[us.RS.size()][us.terminals.size() + 1];
        for (int i = 0; i < us.LS.size(); i++) {
            anat[i][0] = us.LS.get(i).toString();
    
            for (int j = 0; j < us.terminals.size(); j++) {
                if (us.analysis_table[i] != null && j < us.analysis_table[i].length && us.analysis_table[i][j] != null) {
                    anat[i][j + 1] = us.analysis_table[i][j];
                } else {
                    anat[i][j + 1] = "-"; // Replace null with "-" 
                }
            }
        }
    
        // Debugging output
        for (int k = 0; k < us.analysis_table.length; k++) {
            System.out.println("analysis_table[" + k + "]: " + Arrays.toString(anat[k]));
        }
    
        // Create and set the table model
        DefaultTableModel model = new DefaultTableModel(anat, columnNames);

        // Debugging: Print out the contents of the table model
for (int i = 0; i < model.getRowCount(); i++) {
    for (int j = 0; j < model.getColumnCount(); j++) {
        System.out.print(model.getValueAt(i, j) + "\t");
    }
    System.out.println(); // New line at the end of a row
}
JFrame analysisFrame=new JFrame("ana table"); 
              
        // Assuming 'at' is your JTable
        JTable at = new JTable(model);
    
        // Add the table to a JScrollPane
        JScrollPane jScrollPane = new JScrollPane(at);
    
        // Add the JScrollPane to the frame's content pane
        analysisFrame.getContentPane().add(jScrollPane);
        // Configure and display the analysis frame
        analysisFrame.pack(); // Adjusts size to fit contents
        analysisFrame.setLocationRelativeTo(null); // Center on screen
        
        analysisFrame.setVisible(true);

    }
    


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
                
                if(us.analysis_table[j][i] == null) {   
                   System.out.println("erreur "); 
                   }
                else if(us.analysis_table[j][i].equals("~")){
                    if(stack.empty()) return false;
                    continue;
                }

                for (int k = us.analysis_table[j][i].split("\\.").length-1; k >= 0; k--) {
                    stack.push(us.analysis_table[j][i].split("\\.")[k]);
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