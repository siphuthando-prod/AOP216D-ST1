package za.aop.st1.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import za.ac.aop.api.CallDetail;
import za.ac.aop.api.CallDetailManager; //JAR required

/**
 *
 * @author LANGARM
 */
public class ST1GUI extends JFrame{
    private JPanel tab1, tab2;
    private final JTabbedPane tabbedPane;
    
    // TAB 1 Thingz
    private JTextField tfSentence;
    private JButton btnReverse, btnMapWordLength;
    private JLabel lblReversedSentence, lblMapWordLength;
    
    // TAB 2 Thingz
    private JTextArea taContent;
    
    private JComboBox<String> cmbExtensions;
    private JButton btnCountLongerCalls, btnAddRecord, btnCountDayCalls;
    private JTextField tfPhone, tfProvider;

    private CallDetailManager cdm; //JAR required

    public ST1GUI() {
        setTitle("Semester Test 1 - AOP/AOR216D");
        setSize(550, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");

        JMenuItem openItem = new JMenuItem("Open File");
        openItem.addActionListener(new MenuItemOpenClickListener());
        JMenuItem saveItem = new JMenuItem("Save Report");
        saveItem.addActionListener(new MenuItemSaveClickListener());
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));

        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
        
        tabbedPane = new JTabbedPane();
        
        Question1Tab(); //initializing tab1 in here
        
        Question2Tab(); //initializing tab2 in here

        // Add tabs
        tabbedPane.addTab("Q1", tab1);
        tabbedPane.addTab("Q2", tab2);

        add(tabbedPane);
        
        setVisible(true);
    }
    
    private void Question1Tab(){
        tab1 = new JPanel(new BorderLayout());
        JPanel upper = new JPanel(new GridLayout(4,1,0,0));
        upper.setBorder(javax.swing.BorderFactory.createTitledBorder("Question 1 - sentence manipulation"));
        
        tfSentence = new JTextField("A good programmer, is a lazy programmer!");
        tfSentence.setHorizontalAlignment(JTextField.CENTER);
        tfSentence.setCaretPosition(0);
        tfSentence.setEditable(false);
        tfSentence.setFont(new Font("Comic Sans", Font.BOLD, 15));
        
        JPanel mPnl = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        btnReverse = new JButton("Reverse sentence");
        btnReverse.setBackground(Color.decode("#ccc5f8"));
        btnReverse.addActionListener(new BtnReverseClickListener());
        btnMapWordLength = new JButton("Word-Length Mapper");
        btnMapWordLength.setBackground(Color.decode("#ccc44f"));
        btnMapWordLength.addActionListener(new BtnMapWordLengthClickListener());
        
        mPnl.add(btnMapWordLength); mPnl.add(btnReverse); 
        
        lblReversedSentence = new JLabel("Display reversed sentence here");
        lblReversedSentence.setFont(new Font("Times New Roman", Font.BOLD, 15));
        lblReversedSentence.setHorizontalAlignment(JLabel.CENTER);
        
        lblMapWordLength = new JLabel("Display word-length here");
        lblMapWordLength.setFont(new Font("Times New Roman", Font.BOLD, 15));
        lblMapWordLength.setHorizontalAlignment(JLabel.CENTER);
        
        upper.add(tfSentence);
        upper.add(mPnl);
        upper.add(lblMapWordLength);
        upper.add(lblReversedSentence);
       
        JButton btnNextQuestion = new JButton("Go to next question!");
        btnNextQuestion.addActionListener((e) ->{ tabbedPane.setSelectedIndex(1); });
        
        tab1.add(upper, BorderLayout.NORTH);
        tab1.add(btnNextQuestion, BorderLayout.SOUTH);
    }

    //TOTAL [10]
    private class BtnMapWordLengthClickListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            //Write code here
            String sent = tfSentence.getText().trim();
            
            String[] words = sent.split("[\\s+,!]+");
            
            String output = "";
            
            for (String word : words) {
                output += word + word.length() + " "; 
            }
            
            lblMapWordLength.setForeground(Color.magenta);
            lblMapWordLength.setText(output);
        }
        
    }
    
    //TOTAL [5]
    private class BtnReverseClickListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            //Write code here
            String sent = tfSentence.getText().trim();
            
            String output = "";
            
            for (int k = sent.length() - 1; k > 0; k--) {
                output += sent.charAt(k);
            }
            
            lblReversedSentence.setText(output);
            
        }
    }
    
    private void Question2Tab() {
        tab2 = new JPanel(new BorderLayout());
        taContent = new JTextArea(10, 30);
        taContent.setFont(new Font("Arial", Font.BOLD, 12));
        taContent.setEditable(false);
        taContent.setTabSize(3);
        JScrollPane scrollPane = new JScrollPane(taContent);
        
        JPanel topPnl = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPnl.setBorder(BorderFactory.createTitledBorder("New Service Detail"));
        
        cmbExtensions = new JComboBox(
        new String[]{"200", "202", "205", "206", "207", "208", "210", "211",
            "212", "213", "214", "216", "218", "219", "220"});
        
        tfPhone = new JTextField(8);
        tfProvider = new JTextField(8);
        
        btnAddRecord = new JButton("Add Record");
        btnAddRecord.setEnabled(false);
        btnAddRecord.addActionListener(new BtnAddRecordClickListener());
        
        topPnl.add(new JLabel("Ext")); topPnl.add(cmbExtensions);
        topPnl.add(new JLabel("Number")); topPnl.add(tfPhone);
        topPnl.add(new JLabel("Provider")); topPnl.add(tfProvider);
        topPnl.add(btnAddRecord);
        
        JPanel bottomPnl = new JPanel();
        bottomPnl.setBorder(BorderFactory.createTitledBorder("Functionality Processing"));
        
        btnCountLongerCalls = new JButton("Count Calls > 2 Mins");
        btnCountLongerCalls.addActionListener(new BtnCountLongCallsClickListener());
        btnCountLongerCalls.setEnabled(false);
        
        btnCountDayCalls = new JButton("Count Day with Most Calls");
        btnCountDayCalls.addActionListener(new BtnCountMostDayCallsClickListener());
        btnCountDayCalls.setEnabled(false);
        
        bottomPnl.add(btnCountLongerCalls);
        bottomPnl.add(btnCountDayCalls);
        
        tab2.add(topPnl, BorderLayout.NORTH);
        tab2.add(scrollPane, BorderLayout.CENTER);
        tab2.add(bottomPnl, BorderLayout.SOUTH);
        
        cdm = new CallDetailManager();
    }
    
    // TOTAL [11]
    private class MenuItemOpenClickListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            //Write code here
            JFileChooser jc = new JFileChooser("Documents");
            
            int value = jc.showOpenDialog(null);
            
            if(value != JFileChooser.APPROVE_OPTION){
                System.out.println("Cancelled");
                return;
            }
            
            try{
                File file = jc.getSelectedFile();
                
                BufferedReader br = new BufferedReader(new FileReader(file));                
                String line = br.readLine();
                
                while(line != null){
                    String[] fields = line.split(",");
                    
                    LocalDate date = LocalDate.parse(fields[0], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    LocalTime startTime = LocalTime.parse(fields[1], DateTimeFormatter.ofPattern("HH:mm:ss"));
                    LocalTime endTime = LocalTime.parse(fields[2], DateTimeFormatter.ofPattern("HH:mm:ss"));
                    long ext = Long.parseLong(fields[3]);
                    String phone = fields[4];
                    String provider = fields[5];
                    
                    CallDetail call = new CallDetail(date, startTime, endTime, ext, phone, provider);
                    
                    cdm.addCallDetail(call);
                    
                    line = br.readLine();
                    
                }
                
                taContent.setTabSize(5);
                taContent.setText("");
                for (CallDetail call : cdm.getCalls()) {
                    taContent.append(call.getCallDate() + "\t" + call.getStartTime() + "\t" + call.getEndTime() +
                            "\t" + call.getExtension() + "\t" + call.getPhoneNumber() + "\t" 
                            + call.getNetworkProvider() + "\n");
                }
                
                btnAddRecord.setEnabled(true);
                btnCountDayCalls.setEnabled(true);
                btnCountLongerCalls.setEnabled(true);
               
                
            } catch(FileNotFoundException ex){
                System.out.println("File error: " + ex.getMessage());
            } catch(IOException ex){
                System.out.println("I/O error: " + ex.getMessage());
            }
            
        }
    }
    
    // TOTAL [14]
    private class BtnAddRecordClickListener implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            //Write code here
            String ext = (String)cmbExtensions.getSelectedItem();
            long extension = Long.parseLong(ext);
            
            String phone = tfPhone.getText().trim();
            if(phone.length() == 10){
                String provider = tfProvider.getText().trim();
                if("MTN".equals(provider) || "Vodacom".equals(provider) || 
                        "CellC".equals(provider) || "Telkom".equals(provider)){
                    LocalDate date = LocalDate.now();
                    LocalTime startTime = LocalTime.now();
                    LocalTime endTime = startTime.plusMinutes(3).plusSeconds(24);  
                    
                    CallDetail cd = new CallDetail(date, startTime, endTime, extension, phone, provider);
                    cdm.addCallDetail(cd);
                    
                    taContent.setText("");
                    taContent.setTabSize(5);
                    for (CallDetail call : cdm.getCalls()) {
                        taContent.append(call.getCallDate() + "\t" + call.getStartTime() + "\t" + call.getEndTime() +
                                "\t" + call.getExtension() + "\t" + call.getPhoneNumber() + "\t" 
                                + call.getNetworkProvider() + "\n");
                    }
                    
                    tfPhone.setText("");
                    tfProvider.setText("");
                    tfSentence.setText("");
                    cmbExtensions.setSelectedIndex(0);
                    
                    JOptionPane.showMessageDialog(rootPane, "Record added");
                    
                } else{
                    JOptionPane.showMessageDialog(null, "Provider invalid.");
                }
            } else{
                JOptionPane.showMessageDialog(null, "Phone number length invalid.");
            }             
            
        }
    }
    
    // TOTAL [6]
    private class BtnCountLongCallsClickListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            //Write code here
            int count = 0;
            
            for (CallDetail call : cdm.getCalls()) {
                int duration = call.getEndTime().toSecondOfDay() - call.getStartTime().toSecondOfDay();
                if(duration >= 120){
                    count++;
                }
            }
            
            JOptionPane.showMessageDialog(rootPane, count + " calls were longer than 2 minutes");
        }
    }
    
    // TOTAL [11]
    private class BtnCountMostDayCallsClickListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            //Write code here
            
            
        }
    }
    
    // TOTAL [8]
    private class MenuItemSaveClickListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            //Write code here
            
            
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ST1GUI());
    }
}