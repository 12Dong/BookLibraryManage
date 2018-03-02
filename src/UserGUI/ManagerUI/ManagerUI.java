package UserGUI.ManagerUI;


import javax.swing.*;
import java.awt.*;

public class ManagerUI {
    public ManagerUI(boolean is_supper_manager){
        new ManagerFrame(is_supper_manager);
    }
    public static void main(String [] args){
        new ManagerUI(true);
    }
}

class ManagerFrame extends JFrame {
    boolean is_supper_manager;
    //user information
    static int USER_COL = 3;
    static String[] USER_COL_ITEM = new String[] {"name","sex","age"};
    //book information
    static int BOOK_COL = 3;
    static String[] BOOK_COL_ITEM = new String[] {"name","library","fuck"};
    // user managemet
    JSplitPane userPanel;
    JSplitPane userTempPanel;
    JScrollPane userResultPanel;
    JSplitPane userSettingPanel;
    JPanel userOptionPanel;
    JPanel userTextPanel;
    JPanel userOperatorPanel;
    JTable userResult;
    String[][] userQueryResult;
    JLabel[] userOptionLable;
    JTextField[] userQueryText;
    // book management
    JSplitPane bookPanel;
    JSplitPane bookTempPanel;
    JScrollPane bookResultPanel;
    JSplitPane bookSettingPanel;
    JPanel bookOptionPanel;
    JPanel bookTextPanel;
    JPanel bookOperatorPanel;
    JTable bookResult;
    String[][] bookQueryResult;
    JLabel[] bookOptionLable;
    JTextField[] bookQueryText;
    //manager management
    JSplitPane managerPanel;
    JSplitPane managerTempPanel;
    JScrollPane managerResultPanel;
    JSplitPane managerSettingPanel;
    JPanel managerOptionPanel;
    JPanel managerTextPanel;
    JPanel managerOperatorPanel;
    JTable managerResult;
    String[][] managerQueryResult;
    JLabel[] managerOptionLable;
    JTextField[] managerQueryText;
    //send message
    JPanel messagePanel;
    JLabel sendTargetLable;
    JLabel messageLable;
    JTextField sendTarget;
    JScrollPane messageHelper;
    JTextArea message;
    JButton sendBtn;
    //user operator
    JButton addUserBtn;
    JButton delUserBtn;
    JButton queryUserBtn;
    JButton banUserBtn;
    JButton sendMessageBtn;
    //book operator
    JButton addBookBtn;
    JButton delBookBtn;
    JButton queryBookBtn;
    JButton addDetailBtn;
    JButton updateBookBtn;
    //manager operator
    JButton addManagerBtn;
    JButton delManagerBtn;
    JButton queryManagerBtn;
    public ManagerFrame(boolean is_supper_manager) {
        this.is_supper_manager = is_supper_manager;
        this.init();
        setBounds(10,10,700,400);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        userSettingPanel.setDividerLocation(150);
        userTempPanel.setDividerLocation(300);
        userPanel.setDividerLocation(150);
        bookSettingPanel.setDividerLocation(150);
        bookTempPanel.setDividerLocation(300);
        bookPanel.setDividerLocation(150);
        managerSettingPanel.setDividerLocation(150);
        managerTempPanel.setDividerLocation(300);
        managerPanel.setDividerLocation(150);
        validate();
    }
    void init() {
       JTabbedPane tabPane = new JTabbedPane();
       initUserPanel();
       initBookPanel();
       tabPane.add("user management",userPanel);
       tabPane.add("book management",bookPanel);
       if(is_supper_manager == true) {
           initManagerPanl();
           tabPane.add("manager management", managerPanel);
       }
       initMessagePanel();
       tabPane.add("Send message",messagePanel);
       add(tabPane);
       validate();
    }
    void initUserPanel(){
        addUserBtn = new JButton("add");
        delUserBtn = new JButton("del");
        queryUserBtn = new JButton("query");
        banUserBtn = new JButton("ban");
        userOptionPanel = new JPanel();
        userTextPanel = new JPanel();
        GridLayout optionHelper = new GridLayout(USER_COL,1);
        GridLayout textHelper = new GridLayout(USER_COL,1);
        userOptionLable = new JLabel[USER_COL];
        userQueryText = new JTextField[USER_COL];
        for(int i = 0;i < USER_COL;i ++){
            userOptionLable[i] = new JLabel(USER_COL_ITEM[i]);
            userQueryText[i] = new JTextField();
            userOptionLable[i].setHorizontalAlignment(JLabel.CENTER);
            userOptionPanel.add(userOptionLable[i]);
            userTextPanel.add(userQueryText[i]);
        }
        userOptionPanel.setLayout(optionHelper);
        userTextPanel.setLayout(textHelper);
        userSettingPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,userOptionPanel,userTextPanel);
        userQueryResult = new String[1][USER_COL];
        userResult = new JTable(userQueryResult,USER_COL_ITEM);
        userResultPanel = new JScrollPane(userResult);
        userOperatorPanel = new JPanel();
        GridLayout operatorHelper = new GridLayout(4,1);
        userOperatorPanel.setLayout(operatorHelper);
        userOperatorPanel.add(addUserBtn);
        userOperatorPanel.add(delUserBtn);
        userOperatorPanel.add(queryUserBtn);
        userOperatorPanel.add(banUserBtn);
        userTempPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,userSettingPanel,userOperatorPanel);
        userPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT,userTempPanel,userResultPanel);
        //userTempPanel.setDividerSize(0);
        //userPanel.setDividerSize(0);
    }
    void initBookPanel(){
        addBookBtn = new JButton("add book");
        delBookBtn = new JButton("del");
        queryBookBtn = new JButton("query");
        addDetailBtn = new JButton("add deltail");
        updateBookBtn = new JButton("update status");
        bookOptionPanel = new JPanel();
        bookTextPanel = new JPanel();
        GridLayout optionHelper = new GridLayout(BOOK_COL,1);
        GridLayout textHelper = new GridLayout(BOOK_COL,1);
        bookOptionLable = new JLabel[BOOK_COL];
        bookQueryText = new JTextField[BOOK_COL];
        bookOptionPanel.setLayout(optionHelper);
        bookTextPanel.setLayout(textHelper);
        for(int i = 0;i < BOOK_COL;i ++){
            bookOptionLable[i] = new JLabel(BOOK_COL_ITEM[i]);
            bookQueryText[i] = new JTextField();
            bookOptionLable[i].setHorizontalAlignment(JLabel.CENTER);
            bookOptionPanel.add(bookOptionLable[i]);
            bookTextPanel.add(bookQueryText[i]);
        }
        bookSettingPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,bookOptionPanel,bookTextPanel);
        bookQueryResult = new String[1][BOOK_COL];
        bookResult = new JTable(bookQueryResult,BOOK_COL_ITEM);
        bookResultPanel = new JScrollPane(bookResult);
        GridLayout operatorHelper = new GridLayout(5,1);
        bookOperatorPanel = new JPanel();
        bookOperatorPanel.setLayout(operatorHelper);
        bookOperatorPanel.add(addBookBtn);
        bookOperatorPanel.add(delBookBtn);
        bookOperatorPanel.add(queryBookBtn);
        bookOperatorPanel.add(addDetailBtn);
        bookOperatorPanel.add(updateBookBtn);
        bookTempPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,bookSettingPanel,bookOperatorPanel);
        bookPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT,bookTempPanel,bookResultPanel);
    }
    void initManagerPanl(){
        addManagerBtn = new JButton("add");
        delManagerBtn = new JButton("del");
        queryManagerBtn = new JButton("query");
        managerOptionPanel = new JPanel();
        managerTextPanel = new JPanel();
        GridLayout optionHelper = new GridLayout(USER_COL,1);
        GridLayout textHelper = new GridLayout(USER_COL,1);
        managerOptionLable = new JLabel[USER_COL];
        managerQueryText = new JTextField[USER_COL];
        managerOptionPanel.setLayout(optionHelper);
        managerTextPanel.setLayout(textHelper);
        for(int i = 0;i < USER_COL;i ++){
            managerOptionLable[i] = new JLabel(USER_COL_ITEM[i]);
            managerQueryText[i] = new JTextField();
            managerOptionLable[i].setHorizontalAlignment(JLabel.CENTER);
            managerOptionPanel.add(managerOptionLable[i]);
            managerTextPanel.add(managerQueryText[i]);
        }
        managerSettingPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,managerOptionPanel,managerTextPanel);
        managerQueryResult = new String[1][USER_COL];
        managerResult = new JTable(managerQueryResult,USER_COL_ITEM);
        managerResultPanel = new JScrollPane(managerResult);
        GridLayout operatorHelper = new GridLayout(3,1);
        managerOperatorPanel = new JPanel();
        managerOperatorPanel.setLayout(operatorHelper);
        managerOperatorPanel.add(addManagerBtn);
        managerOperatorPanel.add(delManagerBtn);
        managerOperatorPanel.add(queryManagerBtn);
        managerTempPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,managerSettingPanel,managerOperatorPanel);
        managerPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT,managerTempPanel,managerResultPanel);
    }
    void initMessagePanel(){
        messagePanel = new JPanel();

        GridBagLayout layout = new GridBagLayout();
        messagePanel.setLayout(layout);
        Box box1 = Box.createHorizontalBox();
        sendTargetLable = new JLabel("send to");
        sendTargetLable.setHorizontalAlignment(JLabel.CENTER);
        messageLable = new JLabel("Message");
        messageLable.setHorizontalAlignment(JLabel.CENTER);
        sendTarget = new JTextField(10);
        message = new JTextArea(10,10);
        JLabel blank= new JLabel();
        messageHelper = new JScrollPane(message);
        sendBtn = new JButton("send");
        sendBtn.setSize(1,2);
//        box1.add(sendTargetLable);
//        box1.add(sendTarget);
//        box1.add(sendBtn);
//        box1.setAlignmentX(Box.LEFT_ALIGNMENT);
//        Box box2 = Box.createHorizontalBox();
//        box2.add(messageLable);
//        box2.add(messageHelper);
//        box2.setAlignmentX(Box.LEFT_ALIGNMENT);
//
//        messagePanel.add(box1);
//        messagePanel.add(box2);
        JPanel blank1 = new JPanel();
        blank1.setSize(100,100);
        //blank1.setBackground(Color.YELLOW);
        sendTargetLable.setFont(new java.awt.Font("Dialog",1,25));
        blank1.add(sendTargetLable);
        messagePanel.add(blank1);
        GridBagConstraints s = new GridBagConstraints();
        s.fill=GridBagConstraints.BOTH;
        s.weightx = 0;
        s.weighty = 0;
        s.gridwidth = 2;
        s.gridheight=1;
        layout.setConstraints(blank1,s);
        sendTargetLable.setBackground(Color.red);

//        messagePanel.add(sendTargetLable);

//        s.weightx = 1;
//        s.weighty = 0;
//        s.gridwidth = 1;
//        s.gridheight = 2;
//        layout.setConstraints(sendTargetLable,s);

//        JPanel jPanel = new JPanel();
//        messagePanel.add(jPanel);
//        s.weightx = 2;
//        s.gridwidth = 1;
//        layout.setConstraints(jPanel,s);

        messagePanel.add(sendTarget);
        s.weightx = 3;
        s.gridwidth = 2;
        layout.setConstraints(sendTarget,s);

        messagePanel.add(sendBtn);
        s.weightx = 5;
        s.weighty = 0;
        s.gridwidth = 0;
        layout.setConstraints(sendBtn,s);

        JPanel blank2 = new JPanel();
        //blank2.setBackground(Color.ORANGE);
        blank2.setSize(100,100);
        messageLable.setFont(new java.awt.Font("Dialog",1,25));
        messageLable.setVerticalAlignment(JLabel.CENTER);
        blank2.setAlignmentY(JPanel.CENTER_ALIGNMENT);
        blank2.add(messageLable);
        messagePanel.add(blank2);
        s.weightx=0;
        s.weighty=3;
        s.gridwidth=2;
        layout.setConstraints(blank2,s);
//        messagePanel.add(messageLable);
//        s.weightx = 1;
//        s.weighty = 3;
//        s.gridwidth = 2;
//        layout.setConstraints(messageLable,s);

        messagePanel.add(messageHelper);
        s.weightx = 3;
        s.weighty = 2;
        s.gridwidth = 0;
        layout.setConstraints(messageHelper,s);
        //
//        s.weightx = 0;
//        s.weighty = 3;
//        s.gridwidth = 10;
//        s.gridheight = 10;
//        layout.setConstraints(box2,s);
    }
}


