import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class Compiler {
    static Syntactic us;
    public static ArrayList<Lexical> lexemes = new ArrayList<>();

    public static void main(String[] args) {
        
        // Create and set up the window
        JFrame frame = new JFrame("Compiler");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 500);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        // Create and set up the panel
        JPanel panel = new JPanel();
        frame.add(panel, BorderLayout.CENTER);
        JLabel textInputLabel = new JLabel("Write the text");
        panel.add(textInputLabel);
        JTextArea textArea = new JTextArea(7, 30);
        panel.add(textArea, BorderLayout.WEST);
        JScrollPane scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        panel.add(scrollPane);

        JPanel buttonPanel = new JPanel();

        JButton fileChooserButton = new JButton("Choose File");
        panel.add(fileChooserButton, BoxLayout.Y_AXIS);

        // Label and Button for file selection
        JLabel fileChooserLabel = new JLabel("Or choose a file:");
        panel.add(fileChooserLabel, BoxLayout.Y_AXIS);

        JButton submitButton = new JButton("Analyze Text lexically");
        buttonPanel.add(submitButton);
        panel.add(buttonPanel);

        // Create and set up the panel for the Grammar
        JPanel panel2 = new JPanel();
        frame.add(panel2, BorderLayout.CENTER);
        JLabel textInputLabel2 = new JLabel("Write the grammar");
        panel2.add(textInputLabel2);
        JTextArea textArea2 = new JTextArea(7, 30);

        JScrollPane scrollPane2 = new JScrollPane(textArea2, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        panel2.add(scrollPane2);

        JPanel buttonPanel2 = new JPanel();

        JButton fileChooserButton2 = new JButton("Choose File");

        panel2.add(fileChooserButton2, BoxLayout.Y_AXIS);

        // Label and Button for file selection
        JLabel fileChooserLabel2 = new JLabel("Or choose a file:");
        panel2.add(fileChooserLabel2, BoxLayout.Y_AXIS);

        JButton testGrammar = new JButton("Test the grammar");
        panel2.add(testGrammar);

        JButton FirstAndFollow = new JButton("First And Follow Table");
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
                        Lexical lexicalAnalyzer = new Lexical(text, true);
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
                            resultTextArea.append("Value: '" + lexeme.value + "', Type: '" + lexeme.type +
                                    "', At Line: " + lexeme.line + ", Column: " + lexeme.column + "\n");
                        }

                        // Show the result frame
                        lexicalResultFrame.setVisible(true);
                        lexicalResultFrame.setLocationRelativeTo(null);

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }

        });

        // the first and follow table :
        JFrame firstFollowFrame = new JFrame("First and Follow table");

        firstFollowFrame.setSize(500, 350);
        firstFollowFrame.setLocationRelativeTo(null);
        JList<String> nts = new JList<>();
        JList<String> first = new JList<>();
        JList<String> follow = new JList<>();

        JScrollPane jScrollPaneNTS = new JScrollPane();
        JScrollPane jScrollPaneFIRST = new JScrollPane();
        JScrollPane jScrollPaneFOLLOW = new JScrollPane();

        nts.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };

            public int getSize() {
                return strings.length;
            }

            public String getElementAt(int i) {
                return strings[i];
            }
        });
        jScrollPaneNTS.setViewportView(nts);
        jScrollPaneNTS.setBounds(10, 50, 130, 200); // x, y, width, height
        firstFollowFrame.getContentPane().add(jScrollPaneNTS);

        follow.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };

            public int getSize() {
                return strings.length;
            }

            public String getElementAt(int i) {
                return strings[i];
            }
        });
        jScrollPaneFOLLOW.setViewportView(follow);
        jScrollPaneFOLLOW.setBounds(330, 50, 135, 200);
        firstFollowFrame.getContentPane().add(jScrollPaneFOLLOW);

        first.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };

            public int getSize() {
                return strings.length;
            }

            public String getElementAt(int i) {
                return strings[i];
            }
        });
        jScrollPaneFIRST.setViewportView(first);
        jScrollPaneFIRST.setBounds(166, 50, 135, 200);
        firstFollowFrame.getContentPane().add(jScrollPaneFIRST);

        JPanel names = new JPanel();
        names.setBounds(10, 20, 500, 15);
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
        firstFollowFrame.getContentPane().add(analyzeBtnTable, BorderLayout.SOUTH);
        // END


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
                    switch ((us = new Syntactic()).setup(text)) {
                        case 1:
                            JOptionPane.showMessageDialog(frame, "Grammar Correct", "success",
                                    JOptionPane.INFORMATION_MESSAGE);
                            FirstAndFollow.setEnabled(true);

                            DefaultListModel<String> nts_model = new DefaultListModel<>();
                            for (String l : us.LS) {
                                nts_model.addElement(l);
                            }
                            DefaultListModel<String> first_model = new DefaultListModel<>();
                            for (ArrayList<String> l : us.first) {
                                String _tmp = "";
                                for (String s : l) {
                                    _tmp += s + " | ";
                                }
                                first_model.addElement(_tmp.substring(0, _tmp.length() - 3));
                            }
                            DefaultListModel<String> follow_model = new DefaultListModel<>();
                            for (ArrayList<String> l : us.follow) {
                                String _tmp = "";
                                for (String s : l) {
                                    _tmp += s + " | ";
                                }
                                follow_model.addElement(_tmp.substring(0, _tmp.length() - 3));
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
                            JOptionPane.showMessageDialog(firstFollowFrame, "Error Grammar incorrect", "File Error!!",
                                    JOptionPane.ERROR_MESSAGE);
                            break;
                        case -2:
                            JOptionPane.showMessageDialog(firstFollowFrame, "Error cannot calculate FIRST",
                                    "Syntax Error!!", JOptionPane.ERROR_MESSAGE);
                            break;
                        case -3:
                            JOptionPane.showMessageDialog(firstFollowFrame, "Error cannot calculate FOLLOW",
                                    "Syntax Error!!", JOptionPane.ERROR_MESSAGE);
                            break;
                    }
                }
            }
        });

        // first and follow table end
        analyzeBtnTable.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setup_anaframe();
            }
        });

        //parse Tree

        submitButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              
             
       // Parse Tree frame
       JFrame frame3 = new JFrame("Parse Tree");
       frame3.setSize(900, 400);
       frame3.setLocationRelativeTo(null);

       // Creating components
        JList<String> input = new JList<>();
        JList<String> stack = new JList<>();
        JList<String> action = new JList<>();
        JList<String> rule = new JList<>();
        JLabel inputL = new JLabel("Input");
        JLabel stackL = new JLabel("Stack");
        JLabel ruleL = new JLabel("Rule");
        JLabel actionL = new JLabel("Action");
       

        // Main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Labels panel
        JPanel labelsPanel = new JPanel(new GridLayout(1, 4)); // 1 row, 4 columns
        labelsPanel.add(inputL);
        labelsPanel.add(stackL);
        labelsPanel.add(actionL);
        labelsPanel.add(ruleL);

        // Lists panel
        JPanel listsPanel = new JPanel(new GridLayout(1, 4)); // 1 row, 4 columns
        listsPanel.add(new JScrollPane(input)); // Wrap JLists in JScrollPane for scrollability
        listsPanel.add(new JScrollPane(stack));
        listsPanel.add(new JScrollPane(action));
        listsPanel.add(new JScrollPane(rule));

        // Add panels to main panel
        mainPanel.add(labelsPanel, BorderLayout.NORTH);
        mainPanel.add(listsPanel, BorderLayout.CENTER);

        // Add main panel to the frame
        frame3.add(mainPanel);

        // Set frame size 
        frame3.setExtendedState(JFrame.MAXIMIZED_BOTH);
       // Show the frame
       frame3.setVisible(true);

       action.removeAll();
       rule.removeAll();
       stack.removeAll();
       input.removeAll();
       Stack<String> p1 = new Stack<String>();
       Stack<String> p2 = new Stack<String>();
       Stack<String> p3 = new Stack<String>();
       Stack<String> p5 = new Stack<String>();

       // *******************************************part 1***********************************************//

       p1.push("#");//in stack # is always the first
       p2.push("#");
       String py = "";

       String ar1[] = new String[100];
       String ar2[] = new String[100];

        String text = textArea.getText();//to get text and lexemes 
        Lexical lexicalAnalyzer = new Lexical(text, true);
           ArrayList<Lexical> lexemes = new ArrayList<>();
           lexicalAnalyzer.processFile(lexemes);

       

       for (int ccc = 0; ccc < ar2.length; ccc++) {
           ar2[ccc] = "";
       }
        

       for (int jc = lexemes.size() - 1; jc >= 0; jc--) {
           p1.push(lexemes.get(jc).getValue()); //get value
       }

       int rrr = 0;
       String ar4[] = new String[50];
       String[] LSstring = new String[us.LS.size()]; // split the left side alone
       
       if (rrr == 0)
           p2.push(us.LS.toArray(LSstring)[0]);

       String mm1 = "";
       p5.addAll(p2);

       p3.addAll(p1);
       String[] terminalsS = us.terminals.toArray(new String[0]) ;// to convert the arrayList to a string[]
       System.out.println(Arrays.toString(terminalsS));
       DefaultListModel<String> modelRule;
       // Check if the model of 'rule' is a DefaultListModel and get it. Otherwise,
       // create a new one.
       if (rule.getModel() instanceof DefaultListModel) {
           modelRule = (DefaultListModel<String>) rule.getModel();
       } else {
           modelRule = new DefaultListModel<>();
           rule.setModel(modelRule);
       }

       // Add an element to the model
       modelRule.addElement(us.analysis_table[us.getIndex(us.LS.toArray(LSstring), p5.peek().toString())]
            [us.getIndex(terminalsS, p3.peek().toString())]);
       mm1 = us.analysis_table[us.getIndex(us.LS.toArray(LSstring), p5.peek().toString())]
            [us.getIndex(terminalsS,p3.peek().toString())];
       int jkk = 0;
       int jk = 0;
       String fy = "";
       while (!p5.empty()) {
           ar2[jkk] = p5.peek().toString();
           p5.pop();
           jkk++;
       }
       int lss = ar2.length - 1;
       while (lss >= 0) {
           if (ar2[lss] != "")
               py = py + ar2[lss] + " ";
           lss--;
       }

       DefaultListModel<String> modelInput;

       // Check if the model of 'rule' is a DefaultListModel and get it. Otherwise,
       // create a new one.
       if (input.getModel() instanceof DefaultListModel) {
           modelInput = (DefaultListModel<String>) input.getModel();
       } else {
           modelInput = new DefaultListModel<>();
           input.setModel(modelInput);
       }
       
       DefaultListModel<String> modelStack;
       if (stack.getModel() instanceof DefaultListModel) {
           modelStack = (DefaultListModel<String>) stack.getModel();
       } else {
           modelStack = new DefaultListModel<>();
           stack.setModel(modelStack);
       }

       // Add an element to the model
       modelStack.addElement(py.toString()); //from input to stack 
       while (!p3.empty()) {
           ar1[jk] = p3.peek().toString();
           fy = fy + ar1[jk] + " ";
           p3.pop();
           jk++;
       }

       DefaultListModel<String> modelAction;

       // Check if the model of 'rule' is a DefaultListModel and get it. Otherwise,
       // create a new one.
       if (action.getModel() instanceof DefaultListModel) {
           modelAction = (DefaultListModel<String>) action.getModel();
       } else {
           modelAction = new DefaultListModel<>();
           action.setModel(modelAction);
       }

       

       modelAction.addElement("Production");
       modelInput.addElement(fy); 
       int ee = 0;
       int kk = 0;

       // *******************************************part 2***********************************************//

       String o3 = "";
       String o1 = "";
       String o2 = "";
       int flag = 0;
       
       while (!p1.empty()) {
           flag = 0;

           if (Syntactic.checking(terminalsS, p1.peek().toString())
                   && Syntactic.checking(terminalsS, p2.peek().toString())
                   && !GET_TYPE_OF_ArrayList(lexemes, p1.peek().toString()).equals(("identifier"))
                   && (!p1.peek().equals(p2.peek()))) {
               JOptionPane.showMessageDialog(null, "Problem Syntactic :  Error");
               modelAction.addElement("ERROR");
               modelRule.addElement("ERROR");
               break;

           }
           if (GET_TYPE_OF_ArrayList(lexemes, p1.peek().toString()).equals(("identifier"))
                   && Syntactic.checking(terminalsS, p2.peek().toString())
                   && !p2.peek().equals("Identifier")) {
               JOptionPane.showMessageDialog(null, "Problem Syntactic :  Error");
               modelAction.addElement("ERROR");
               modelRule.addElement("ERROR");
               break;
           }

           if ((p1.peek().equals("#") && !p2.peek().equals("#"))
                   || (p2.peek().equals("#") && !p1.peek().equals("#"))) {

               if (Syntactic.checking(terminalsS, p1.peek().toString()) && Syntactic.checking(terminalsS, p2.peek().toString())) {
                   JOptionPane.showMessageDialog(null, "Problem Syntactic :  Error");
                   modelAction.addElement("ERROR");
                   modelRule.addElement("ERROR");
                   break;
               }
         }
           if (ee == 0) {
               int uu = mm1.indexOf("->");
               System.out.println("uu" + uu);
               mm1 = mm1.replace(mm1.substring(0, uu + 1), "");
               mm1 = mm1.replaceFirst(" ", "");
               System.out.println("mm1++++" + mm1);
               if (mm1.equals("~")) {
                   System.out.println("epsilon");
                   mm1 = "";

               }

               ar4 = mm1.split("\\.");
               for (int kkj = 0; kkj < ar4.length; kkj++) {
                   System.out.println("tableau    ->>>>>>>" + ar4[kkj] + " ");
               }
               // mettre le tableau (split) dans la pile
               int lss1 = ar4.length - 1;
               p2.pop();
               System.out.println("p2.peek   " + p2.peek());
               while (lss1 >= 0) {
                   if (ar4[lss1] != "") {

                       p2.push(ar4[lss1]);

                   }
                   lss1--;
               }

               System.out.println("is empty ===" + p3.empty());
               System.out.println("is empty is s  ===" + p5.empty());

               p5.addAll(p2);
               p3.addAll(p1);
           }
           System.out.println("p5.peek " + p5.peek());
           System.out.println("pile abcd ==" + p2.toString());
           System.out.println("pile ==" + p5.toString());

           jk = 0;

           jkk = 0;
           py = "";
           fy = "";

           // for matching cases

           o1 = p5.peek().toString();
           o2 = p3.peek().toString();
           if (p5.peek().equals(p3.peek()) || (!Syntactic.checking(us.LS.toArray(LSstring), o2)
                   && !Syntactic.checking(terminalsS, o2) && o1.equals("Identifier"))) {

               System.out.println(" before py +++" + py);
               ee = 1;
               for (int yu = 0; yu < ar2.length; yu++) {
                   if (ar2[yu] != "")
                       System.out.println("Yu " + ar2[yu]);
               }

               while (!p5.empty()) {

                   ar2[jkk] = p5.peek().toString();

                   p5.pop();
                   jkk++;
               }

               lss = ar2.length - 1;
               while (lss >= 0) {
                   if (ar2[lss] != "") {
                       py = py + ar2[lss] + " ";
                    System.out.println(" py = py + ar2[lss] + \" \";"+py);
                   }

                   lss--;
               }
               System.out.println("+py " + py);
               while (!p3.empty()) {
                   ar1[jk] = p3.peek().toString();
                   fy = fy + ar1[jk] + " ";
                   p3.pop();
                   jk++;
                   System.out.println("loop fy" + fy);
               }

               modelStack.addElement(py.toString());
               modelInput.addElement(fy.toString());
               p1.pop();
               p2.pop();

               p5.addAll(p2);
               p3.addAll(p1);

               modelAction.addElement("Matching");
               modelRule.addElement("----");

               for (int ccc = 0; ccc < ar2.length; ccc++) {
                   ar2[ccc] = "";
               }

               if (p1.empty() && p2.empty()) {
                   modelStack.addElement("");
                   modelInput.addElement("");
                   modelRule.addElement("");
                   modelAction.addElement("");
                   JOptionPane.showMessageDialog(frame3, "Accepted", "Action",JOptionPane.INFORMATION_MESSAGE);
               }

           }

           else if (!o1.equals(o2)) {

               ee = 0;

               if (!Syntactic.checking(terminalsS, p2.peek().toString())) {
                   for (int op = 0; op < lexemes.size(); op++) {

                       if (lexemes.get(op).getValue().equals(p3.peek().toString())) {
                           if (lexemes.get(op).getType().equals("Identifier")) {

                               o3 = lexemes.get(op).getType();
                               flag = 1;
                               break;
                           }
                       }
                   }
                   System.out.println("O3 = " + o3);

               }
               if (flag == 1) { 
                
                    int index1 = us.getIndex(LSstring, p5.peek().toString());
                   int index2 = us.getIndex(terminalsS, o3);

                   mm1 = us.analysis_table[index1][index2];
                   modelRule.addElement(us.analysis_table[index1][index2]);
                   
                   
                   
               } else {
                  System.out.println(us.LS.toArray(LSstring).length); 
                  System.out.println(terminalsS.length); 
                       
                  int index1 = us.getIndex(LSstring, p5.peek().toString());
                  int index2 = us.getIndex(terminalsS, p3.peek().toString());
                  
                  // Check if indices are valid
                  if (index1 != -1 && index2 != -1) {
                      mm1 = us.analysis_table[index1][index2];
                  } 

                  int i1 = us.getIndex(LSstring, p5.peek().toString());
                  int i2 = us.getIndex(terminalsS, p3.peek().toString());
                  
                  // Check if indices are valid
                  if (i1 != -1 && i2 != -1) {
                      mm1 = us.analysis_table[index1][index2];
                      modelRule.addElement(us.analysis_table[i1][i2]);
                  } 
                  System.out.println("THIS IS P3"+p3.peek().toString());
                 
               }

               while (!p5.empty()) {
                   ar2[jkk] = p5.peek().toString();

                   p5.pop();
                   jkk++;
               }
               lss = ar2.length - 1;
               while (lss >= 0) {
                   if (ar2[lss] != "")
                       py = py + ar2[lss] + " ";
                   lss--;
               }
             
               modelStack.addElement(py.toString()); 
               while (!p3.empty()) {
                   ar1[jk] = p3.peek().toString();
                   fy = fy + ar1[jk] + " ";
                   p3.pop();
                   jk++;
               }
               modelInput.addElement(fy);
               if (mm1.equals("") || mm1.equals(null)) {

                   JOptionPane.showMessageDialog(null, "Problem writing :  Error");
                   modelAction.addElement("ERROR");
                   modelRule.addElement("ERROR");
                   break;
               }
               modelAction.addElement("Production");

               for (int yu = 0; yu < ar2.length; yu++) {
                   ar2[yu] = "";

               }
           }

           kk++;
       }
            
        }
        });

        // Display the window
        frame.setVisible(true);

    }

    public static String GET_TYPE_OF_ArrayList(ArrayList<Lexical> lexemes, String str) {
        for (int op = 0; op < lexemes.size(); op++) {
            if (str.equals(lexemes.get(op).getType())) {
                return str;

            }

        }
        return "";
    }

    private static void setup_anaframe() {
        // Check if the necessary data is available and properly initialized
        if (us.terminals == null || us.RS == null || us.LS == null || us.analysis_table == null) {
            System.out.println("terminals or something else is null");
            return;
        }
        System.out.println(us.terminals);

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
        JFrame analysisFrame = new JFrame("Analysis Table");

        // Assuming 'at' is your JTable
        JTable at = new JTable(model);
        
        // Add the table to a JScrollPane
        JScrollPane jScrollPane = new JScrollPane(at);

        // Add the JScrollPane to the frame's content pane
        analysisFrame.getContentPane().add(jScrollPane);
        // Configure and display the analysis frame
        analysisFrame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Adjusts size to fit contents
        analysisFrame.setLocationRelativeTo(null); // Center on screen

        analysisFrame.setVisible(true);

    }
}