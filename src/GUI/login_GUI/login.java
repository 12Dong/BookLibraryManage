package GUI.login_GUI;

import GUI.login_GUI.Register_GUI.UserRegister;
import SQLQuery.Connect.GetDBConnection;
import GUI.login_GUI.ManagerUI.ManagerUI;
import UserRelated.Manager;
import GUI.login_GUI.reader_GUI.ReaderFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;

public class login extends JFrame{
    JLabel username,password;
    JTextField inputName;
    JPasswordField inputPassword;
    JButton loginbtn,register;
    JRadioButton box1,box2;
    ButtonGroup group;
    public login(){
        init();
        setBounds(300,400,500,500);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    public void init(){
        JPanel all = new JPanel();
        all.setLayout(new BoxLayout(all,BoxLayout.Y_AXIS));
        GridBagLayout layout = new GridBagLayout();
        setLayout(layout);
        username = new JLabel("用户名");
        GridBagConstraints s = new GridBagConstraints();
        inputName = new JTextField(10);
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel,BoxLayout.X_AXIS));
        jPanel.add(username);
        jPanel.add(inputName);
        all.add(jPanel);

        password = new JLabel("密码");
        inputPassword = new JPasswordField(10);
        jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel,BoxLayout.X_AXIS));
        jPanel.add(password);
        jPanel.add(inputPassword);
        all.add(jPanel);

        jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel,BoxLayout.X_AXIS));
        box1 = new JRadioButton("管理员");
        box2 = new JRadioButton("读者");
        group= new ButtonGroup();
        group.add(box1);
        group.add(box2);
        jPanel.add(box1);
        jPanel.add(box2);
        all.add(jPanel);

        jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel,BoxLayout.X_AXIS));
        register = new JButton("注册");
        loginbtn = new JButton("登陆");
        loginbtn.addMouseListener(new LoginMouseListener(this));
        register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UserRegister();
            }
        });
        jPanel.add(register);
        jPanel.add(loginbtn);

        all.add(jPanel);
        add(all);
        s.weightx=0;
        s.weighty=0;
        layout.setConstraints(all,s);


    }
}

class LoginMouseListener implements MouseListener{
    login workArea;
    String hostName;
    String passWord;
    public LoginMouseListener(login workArea){
        this.workArea = workArea;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        hostName = workArea.inputName.getText().trim();
        passWord = workArea.inputPassword.getText().trim();
        Connection con = GetDBConnection.connectDB("booklibrarymanager",hostName,passWord);
        if(con == null) {
            JOptionPane.showMessageDialog(workArea, "login fail");
            return;
        }
        GetDBConnection.closeCon(con);
        Manager help = new Manager();
        help.userHostName = hostName;
        boolean isSupper;
        String [][] res;
        DefaultButtonModel buttonModel = (DefaultButtonModel)workArea.box1.getModel();
        if(buttonModel.getGroup().isSelected(buttonModel)){
            help.isAdmin = "1";
            res = help.queryUser();
            System.out.println(res == null);
            if(res != null){
                if(res[0][4].equals("0")){
                    JOptionPane.showMessageDialog(workArea,"Your accout is frozened");
                    return ;
                }
                if("root".equals(hostName))
                    isSupper = true;
                else
                    isSupper = false;
                new ManagerUI(hostName,passWord,isSupper);
                workArea.dispose();
                return;
            }
            else JOptionPane.showMessageDialog(workArea,"login fail");
            return;
        }
        buttonModel = (DefaultButtonModel)workArea.box2.getModel();
        if(buttonModel.getGroup().isSelected(buttonModel)) {
            help.isAdmin = "";
            help.userHostName = hostName;
            res = help.queryUser();
            if(res != null){
                new ReaderFrame(res[0][0]);
            }
            else JOptionPane.showMessageDialog(workArea,"login fail");
            return;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
