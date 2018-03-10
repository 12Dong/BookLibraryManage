package login_GUI;

import javax.swing.*;
import java.awt.*;

public class login extends JFrame{
    JLabel username,password;
    JTextField inputName;
    JPasswordField inputPassword;
    JButton loginbtn;
    JRadioButton box1,box2;
    public login(){
        init();
        setBounds(300,400,500,500);
        setVisible(true);
    }
    public void init(){
        GridBagLayout layout = new GridBagLayout();
        setLayout(layout);
        username = new JLabel("用户名");
        GridBagConstraints s = new GridBagConstraints();
        add(username);
        s.gridx=0;
        s.gridy=0;
        s.gridwidth=1;
        s.gridheight=1;
        layout.setConstraints(username,s);
        inputName = new JTextField(10);
        add(inputName);
        s.gridx = 2;
        s.gridwidth=0;
        layout.setConstraints(inputName,s);


        password = new JLabel("密码");
        add(password);
        s.gridx=0;
        s.gridy=1;
        s.gridwidth=1;
        layout.setConstraints(password,s);
        inputPassword = new JPasswordField(10);
        add(inputPassword);
        s.gridx=2;
        s.gridwidth=0;
        layout.setConstraints(inputPassword,s);

        loginbtn = new JButton("登陆");
        add(loginbtn);
        s.gridy=3;
        s.gridx=4;
        s.gridwidth=1;
        layout.setConstraints(loginbtn,s);

        box1 = new JRadioButton("管理员");
        box2 = new JRadioButton("读者");
        ButtonGroup group= new ButtonGroup();
        group.add(box1);
        group.add(box2);
        add(box1);
        add(box2);
//        s.weighty=2;
//        s.weightx=0;
//        s.gridwidth=1;
//        layout.setConstraints(box1,s);
//        s.weightx=1;
//        s.gridwidth=0;
//        layout.setConstraints(box2,s);

    }
}
