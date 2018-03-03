package UserGUI.ManagerUI;


import RegisterUser.PrivilegeDivision.PrivilegeDivision;
import SQLQuery.Base.SQLBase;
import UserRelated.Manager;
import UserRelated.Root;
import UserRelated.user;
import UserRelated.userInformation;
import jdk.nashorn.internal.scripts.JO;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class ManagerUI {
    public ManagerUI(String userID,String password,boolean is_supper_manager){
        new ManagerFrame(userID,password,is_supper_manager);
    }
    public static void main(String [] args){
        new ManagerUI("","",true);
    }
}

class ManagerFrame extends JFrame {
    boolean is_supper_manager;
    Manager manager;
    //user information
    static int USER_COL = 7;
    static String[] USER_COL_ITEM = new String[] {"userID","isAdmin","userName","userSex","userStatus","userRentCount","hostName"};
    //book information
    static int BOOK_COL = 7;
    static String[] BOOK_COL_ITEM = new String[] {"bookID","bookName","author","classification","press","entryDate","status"};
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
    JButton delUserBtn;
    JButton queryUserBtn;
    JButton banUserBtn;
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
    public ManagerFrame(String userID,String password,boolean is_supper_manager) {
        this.is_supper_manager = is_supper_manager;
        manager = new Manager(userID,password);
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
           initManagerPanel();
           tabPane.add("manager management", managerPanel);
       }
       initMessagePanel();
       tabPane.add("Send message",messagePanel);
       add(tabPane);
       validate();
    }
    void initUserPanel(){
        delUserBtn = new JButton("del");
        queryUserBtn = new JButton("query");
        banUserBtn = new JButton("ban");
        QueryUserListener queryListener = new QueryUserListener(this);
        queryUserBtn.addMouseListener(queryListener);
        OperatorUserListener operatorUserListener = new OperatorUserListener(this);
        delUserBtn.addMouseListener(operatorUserListener);
        banUserBtn.addMouseListener(operatorUserListener);
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
        userQueryResult = manager.queryUser();
        userResult = new JTable(userQueryResult,USER_COL_ITEM);
        userResultPanel = new JScrollPane(userResult);
        userOperatorPanel = new JPanel();
        GridLayout operatorHelper = new GridLayout(3,1);
        userOperatorPanel.setLayout(operatorHelper);
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
        QueryBookListener queryBookListener = new QueryBookListener(this);
        queryBookBtn.addMouseListener(queryBookListener);
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
    void initManagerPanel(){
        addManagerBtn = new JButton("add");
        delManagerBtn = new JButton("cancel");
        queryManagerBtn = new JButton("query");
        QueryManagerListener queryManagerListener = new QueryManagerListener(this);
        OperatorManagerListener operatorManagerListener = new OperatorManagerListener(this);
        addManagerBtn.addMouseListener(operatorManagerListener);
        delManagerBtn.addMouseListener(operatorManagerListener);
        queryManagerBtn.addMouseListener(queryManagerListener);
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
        clearManagerResult();
    }
    void initMessagePanel(){
        messagePanel = new JPanel();

        GridBagLayout layout = new GridBagLayout();
        messagePanel.setLayout(layout);
        sendTargetLable = new JLabel("send to");
        sendTargetLable.setHorizontalAlignment(JLabel.CENTER);
        messageLable = new JLabel("Message");
        messageLable.setHorizontalAlignment(JLabel.CENTER);
        sendTarget = new JTextField(10);
        message = new JTextArea(10,10);
        messageHelper = new JScrollPane(message);
        sendBtn = new JButton("send");
        sendBtn.setSize(1,2);

        JPanel blank1 = new JPanel();
        blank1.setSize(100,100);
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

        messagePanel.add(messageHelper);
        s.weightx = 3;
        s.weighty = 2;
        s.gridwidth = 0;
        layout.setConstraints(messageHelper,s);
    }
    void clearUserResult(){
        manager.userId = null;
        manager.userHostName = null;
        manager.userRentCount = null;
        manager.userStatus = null;
        manager.userName = null;
        manager.isAdmin = null;
        manager.userSex = null;
        userResult = new JTable(manager.queryUser(),USER_COL_ITEM);
        validate();
    }
    void clearBookResult(){
        manager.queryBookID = null;
        manager.queryBookName = null;
        manager.queryAuthor= null;
        manager.queryPressName = null;
        manager.queryClassification = null;
        manager.queryEntyrDate = null;
        manager.queryStatus = null;
        bookResult = new JTable(manager.queryBook(),BOOK_COL_ITEM);
        validate();
    }
    void clearManagerResult(){
        manager.userId = null;
        manager.userHostName = null;
        manager.userRentCount = null;
        manager.userStatus = null;
        manager.userName = null;
        manager.isAdmin = "1";
        manager.userSex = null;
        userResult = new JTable(manager.queryUser(),USER_COL_ITEM);
        validate();
    }
}

class QueryUserListener implements MouseListener{
    ManagerFrame workArea;
    public QueryUserListener(ManagerFrame myFrame){
        this.workArea= myFrame;
    }
    class Information{
        ArrayList<String> dataArray;
        public Information(String...information){
            dataArray = new ArrayList<String>();
            for(String info:information){

                dataArray.add(info);
            }
        }

        @Override
        public String toString() {
            String output=null;
            int cnt =0;
            for(String data:dataArray){
                output+=cnt+data+"  ";
                cnt++;
            }
            return output;
        }
    }
    class UserTableModel extends AbstractTableModel {
        private static final long serialVersionUID = 1L;
        // 保存一个User的列表
        private ArrayList<Information> informationArray = new ArrayList<Information>();
        // 设置User列表, 同时通知JTabel数据对象更改, 重绘界面
        public void setList(ArrayList<Information> informationArray) {
            this.informationArray = informationArray;
            int cnt = 0;
            for(Information info:informationArray){
                System.out.println(info);
            }
            System.out.println(getColumnCount()+" "+getRowCount());
            this.fireTableDataChanged();// 同时通知JTabel数据对象更改, 重绘界面
        }
        public int getColumnCount() {
            return informationArray.get(0).dataArray.size();
        }
        public int getRowCount() {
            return informationArray.size();
        }
        // 从list中拿出rowIndex行columnIndex列显示的值
        public Object getValueAt(int rowIndex, int columnIndex) {
            Information info =informationArray.get(rowIndex);
            return info.dataArray.get(columnIndex);
        }
    }
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        workArea.manager.userId = workArea.userQueryText[0].getText();
        workArea.manager.isAdmin = workArea.userQueryText[1].getText();
        workArea.manager.userName = workArea.userQueryText[2].getText();
        workArea.manager.userSex = workArea.userQueryText[3].getText();
        workArea.manager.userStatus = workArea.userQueryText[4].getText();
        workArea.manager.userRentCount = workArea.userQueryText[5].getText();
        workArea.manager.userHostName = workArea.userQueryText[6].getText();
        String[][] data = workArea.manager.queryUser();
        ArrayList<Information> tempArray = new ArrayList<>();
        for(int i = 0;i < data.length;i ++){
            Information tempInfo = new Information(data[i]);
            tempArray.add(tempInfo);
        }
        UserTableModel tempModel = new UserTableModel();
        tempModel.setList(tempArray);
        workArea.userResult.setModel(tempModel);
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}
class OperatorUserListener implements MouseListener{
    ManagerFrame workArea;
    public OperatorUserListener(ManagerFrame workArea) {
        this.workArea = workArea;
    }
    void solveDel() {
        int cur_row = workArea.userResult.getSelectedRow();
        if(cur_row == -1) {
            JOptionPane.showMessageDialog(workArea, "you should choose one user!");
            return;
        }
        workArea.manager.removeInformation("user",(String)workArea.userResult.getValueAt(cur_row,0));
        workArea.clearUserResult();
    }
    void solveBan() {
        int cur_row = workArea.userResult.getSelectedRow();
        if(cur_row == -1){
            JOptionPane.showMessageDialog(workArea,"you should choose one user!");
            return;
        }
        String wait_set;
        if("1".equals((String)workArea.userResult.getValueAt(cur_row,4)))
            wait_set = "0";
        else
            wait_set = "1";
        workArea.manager.setUserStatus((String)workArea.userResult.getValueAt(cur_row,0),wait_set);
        workArea.clearUserResult();
    }
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        JButton curObj = (JButton)mouseEvent.getSource();
        if("del".equals(curObj.getText()))
            solveDel();
        else if("ban".equals(curObj.getText()))
            solveBan();
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}
class QueryManagerListener implements MouseListener{
    ManagerFrame workArea;
    public QueryManagerListener(ManagerFrame myFrame){
        this.workArea= myFrame;
    }
    class Information{
        ArrayList<String> dataArray;
        public Information(String...information){
            dataArray = new ArrayList<String>();
            for(String info:information){

                dataArray.add(info);
            }
        }

        @Override
        public String toString() {
            String output = null;
            int cnt = 0;
            for (String data : dataArray) {
                output += cnt + data + "  ";
                cnt++;
            }
            return output;
        }
    }
    class UserTableModel extends AbstractTableModel {
        private static final long serialVersionUID = 1L;
        // 保存一个User的列表
        private ArrayList<Information> informationArray = new ArrayList<Information>();
        // 设置User列表, 同时通知JTabel数据对象更改, 重绘界面
        public void setList(ArrayList<Information> informationArray) {
            this.informationArray = informationArray;
            int cnt = 0;
            for(Information info:informationArray){
                System.out.println(info);
            }
            System.out.println(getColumnCount()+" "+getRowCount());
            this.fireTableDataChanged();// 同时通知JTabel数据对象更改, 重绘界面
        }
        public int getColumnCount() {
            return informationArray.get(0).dataArray.size();
        }
        public int getRowCount() {
            return informationArray.size();
        }
        // 从list中拿出rowIndex行columnIndex列显示的值
        public Object getValueAt(int rowIndex, int columnIndex) {
            Information info =informationArray.get(rowIndex);
            return info.dataArray.get(columnIndex);
        }
    }
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        workArea.manager.userId = workArea.managerQueryText[0].getText();
        workArea.manager.isAdmin = workArea.managerQueryText[1].getText();
        workArea.manager.userName = workArea.managerQueryText[2].getText();
        workArea.manager.userSex = workArea.managerQueryText[3].getText();
        workArea.manager.userStatus = workArea.managerQueryText[4].getText();
        workArea.manager.userRentCount = workArea.managerQueryText[5].getText();
        workArea.manager.userHostName = workArea.managerQueryText[6].getText();
        String[][] data = workArea.manager.queryUser();
        ArrayList<Information> tempArray = new ArrayList<>();
        for(int i = 0;i < data.length;i ++){
            Information tempInfo = new Information(data[i]);
            tempArray.add(tempInfo);
        }
        UserTableModel tempModel = new UserTableModel();
        tempModel.setList(tempArray);
        workArea.managerResult.setModel(tempModel);
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}
class OperatorManagerListener implements MouseListener{
    ManagerFrame workArea;
    public OperatorManagerListener(ManagerFrame workArea) {
        this.workArea = workArea;
    }
    void solveAdd(){
        int curRow = workArea.managerResult.getSelectedRow();
        if(curRow == -1){
            JOptionPane.showMessageDialog(workArea,"you should choose a manager!");
            return;
        }
        String tarString = (String)workArea.managerResult.getValueAt(curRow,1);
        if("1".equals(tarString)){
            JOptionPane.showMessageDialog(workArea,"Already an admin!");
            return;
        }
        userInformation information = new userInformation();
        information.setHostName((String)workArea.managerResult.getValueAt(curRow,6));
        information.setName((String)workArea.managerResult.getValueAt(curRow,2));
        PrivilegeDivision.managerPrivilegeDivision(information);
    }
    void solveCancel(){
        int curRow = workArea.managerResult.getSelectedRow();
        if(curRow == -1){
            JOptionPane.showMessageDialog(workArea,"you should choose a user!");
            return;
        }
        String tarString = (String)workArea.managerResult.getValueAt(curRow,1);
        if("0".equals(tarString)){
            JOptionPane.showMessageDialog(workArea,"is not an admin!");
            return;
        }
        userInformation information = new userInformation();
        information.setHostName((String)workArea.managerResult.getValueAt(curRow,6));
        information.setName((String)workArea.managerResult.getValueAt(curRow,2));
        PrivilegeDivision.readerPrivilegeDivision(information);
    }
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        JButton curObj = (JButton)mouseEvent.getSource();
        if("add".equals(curObj.getText())){
            solveAdd();
        }
        else if("cancel".equals(curObj.getText())){
            solveCancel();
        }
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}
class QueryBookListener implements MouseListener{
    ManagerFrame workArea;
    public QueryBookListener(ManagerFrame myFrame){
        this.workArea= myFrame;
    }
    class Information{
        ArrayList<String> dataArray;
        public Information(String...information){
            dataArray = new ArrayList<String>();
            for(String info:information){

                dataArray.add(info);
            }
        }

        @Override
        public String toString() {
            String output=null;
            int cnt =0;
            for(String data:dataArray){
                output+=cnt+data+"  ";
                cnt++;
            }
            return output;
        }
    }
    class UserTableModel extends AbstractTableModel {
        private static final long serialVersionUID = 1L;
        // 保存一个User的列表
        private ArrayList<Information> informationArray = new ArrayList<Information>();
        // 设置User列表, 同时通知JTabel数据对象更改, 重绘界面
        public void setList(ArrayList<Information> informationArray) {
            this.informationArray = informationArray;
            int cnt = 0;
            for(Information info:informationArray){
                System.out.println(info);
            }
            System.out.println(getColumnCount()+" "+getRowCount());
            this.fireTableDataChanged();// 同时通知JTabel数据对象更改, 重绘界面
        }
        public int getColumnCount() {
            return informationArray.get(0).dataArray.size();
        }
        public int getRowCount() {
            return informationArray.size();
        }
        // 从list中拿出rowIndex行columnIndex列显示的值
        public Object getValueAt(int rowIndex, int columnIndex) {
            Information info =informationArray.get(rowIndex);
            return info.dataArray.get(columnIndex);
        }
    }
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        workArea.manager.queryBookID = workArea.bookQueryText[0].getText();
        workArea.manager.queryBookName = workArea.bookQueryText[1].getText();
        workArea.manager.queryAuthor = workArea.bookQueryText[2].getText();
        workArea.manager.queryClassification = workArea.bookQueryText[3].getText();
        workArea.manager.queryPress = workArea.bookQueryText[4].getText();
        workArea.manager.queryEntyrDate = workArea.bookQueryText[5].getText();
        workArea.manager.queryStatus = workArea.bookQueryText[6].getText();
        String[][] data = workArea.manager.queryBook();
        ArrayList<Information> tempArray = new ArrayList<>();
        for(int i = 0;i < data.length;i ++){
            Information tempInfo = new Information(data[i]);
            tempArray.add(tempInfo);
        }
        UserTableModel tempModel = new UserTableModel();
        tempModel.setList(tempArray);
        workArea.bookResult.setModel(tempModel);
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}

