package reader_GUI;

import UserRelated.user;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Communication extends JPanel {
    JLabel label = null;
    JTextArea jTextArea = null;
    JScrollPane jScrollPane = null;
    JButton sendBtn,reset;
    Communication(){
        init();
    }
    void init(){
        label = new JLabel("只能发给系统管理员");
        GridBagLayout layout = new GridBagLayout();
        setLayout(layout);
        add(label);
        GridBagConstraints s = new GridBagConstraints();
//        s.gridx = 0;
//        s.gridy = 0;
        s.gridwidth = 0;
        layout.setConstraints(label,s);

        jTextArea = new JTextArea(10,20);
        jScrollPane = new JScrollPane(jTextArea);
        add(jScrollPane);
//        s.weighty=100;
        s.gridwidth=0;
        layout.setConstraints(jScrollPane,s);

        sendBtn = new JButton("发送");
        sendBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                user reader = new user();
                reader.userId="1";
                String message = jTextArea.getText();
                if(message.trim().equals("")){
                    JOptionPane.showMessageDialog(new JPanel(),"信息不能为空");
                    return ;
                }
                reader.sendMessageSQLCommand(message);
                jTextArea.setText(null);
            }
        });
        add(sendBtn);
//        s.weighty=12;
        s.gridwidth=3;
        layout.setConstraints(sendBtn,s);

        reset = new JButton("重置");
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jTextArea.setText(null);
            }
        });
        add(reset);
        s.gridwidth=0;
        layout.setConstraints(reset,s);
    }
}
