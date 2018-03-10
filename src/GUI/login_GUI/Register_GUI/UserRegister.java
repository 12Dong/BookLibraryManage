package GUI.login_GUI.Register_GUI;

import UserRelated.Root;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import UserRelated.*;
import static javax.swing.BoxLayout.Y_AXIS;

public class UserRegister extends JFrame {
    JLabel hostJlabel,passwordJlabel,nameJlabel,sexJlabel;
    JPasswordField inputPassword;
    JTextField inputHost,inputName;
    JButton submit;
    JRadioButton maleBox,femaleBox;
    ButtonGroup group = new ButtonGroup();
    public UserRegister(){
        init();
        setBounds(300,400,500,600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

    }
    public void init(){
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints s = new GridBagConstraints();
        setLayout(layout);
        JPanel all = new JPanel();
        hostJlabel = new JLabel("用户名");
        passwordJlabel = new JLabel("密  码");
        nameJlabel = new JLabel("姓  名");
        sexJlabel = new JLabel("性  别");
        inputHost = new JTextField(10);
        inputPassword = new JPasswordField(10);
        inputName = new JTextField(10);
        all.setLayout(new BoxLayout(all,Y_AXIS));
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel,BoxLayout.X_AXIS));
        jPanel.add(hostJlabel);
        jPanel.add(inputHost);
        all.add(jPanel);
        jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel,BoxLayout.X_AXIS));
        jPanel.add(passwordJlabel);
        jPanel.add(inputPassword);
        all.add(jPanel);
        jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel,BoxLayout.X_AXIS));
        jPanel.add(nameJlabel);
        jPanel.add(inputName);
        all.add(jPanel);
        jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel,BoxLayout.X_AXIS));
        jPanel.add(sexJlabel);
        maleBox = new JRadioButton("男");
        femaleBox = new JRadioButton("女");
        group.add(maleBox);
        group.add(femaleBox);
        jPanel.add(maleBox);
        jPanel.add(femaleBox);
        all.add(jPanel);

        jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel,BoxLayout.X_AXIS));
        submit = new JButton("提交");
        jPanel.add(submit);
        submit.addMouseListener(new SubmitMouseListener(this));
        all.add(jPanel);

        add(all);

        s.weightx=0;
        s.weighty=0;
        layout.setConstraints(all,s);
    }
}

class SubmitMouseListener implements MouseListener{
    UserRegister workArea;
    public SubmitMouseListener(UserRegister workArea) {
        this.workArea = workArea;
    }
    private boolean judgeString(String s){
        if(s.trim().length() != s.length())
            return false;
        return true;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        String host = workArea.inputHost.getText();
        String passWord = workArea.inputPassword.getText();
        String name = workArea.inputName.getText();

        String sex = "";
        if(workArea.maleBox.isSelected()) sex="男";
        else if(workArea.femaleBox.isSelected()) sex="女";
        else sex="";
        String wrongMsg = "";
        boolean wrong = false;
        if(!judgeString(host)){
            wrong = true;
            wrongMsg += "host ";
        }
        if(!judgeString(passWord)){
            wrong = true;
            wrongMsg += "password ";
        }
        if(!judgeString(name)){
            wrong = true;
            wrongMsg += "name ";
        }
        if(!judgeString(sex)){
            wrong = true;
            wrongMsg += "sex ";
        }
        if(wrong){
            wrongMsg += "can't with blank";
            JOptionPane.showMessageDialog(workArea,wrongMsg);
            return;
        }
        userInformation user = new userInformation();
        user.setHostName(host);
        user.setPassword(passWord);
        user.setName(name);
        user.setSex(sex);
        if(Root.transmit(user,false)) {
            JOptionPane.showMessageDialog(workArea, "register successfully");
            workArea.dispose();
        }
        else JOptionPane.showMessageDialog(workArea,"register fail");
        return;
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