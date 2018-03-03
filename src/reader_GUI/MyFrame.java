package reader_GUI;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class MyFrame extends JFrame{

    MyFrame(){
        init();
        setBounds(300,400,700,600);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    void init(){
        setTitle("Reader 花Q");
        final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        //创建借书 选件卡
        RenderBook renderBook = new RenderBook();
        tabbedPane.addTab("借书",renderBook);

        //创建还书 选件卡
        ReturnBook returnBook = new ReturnBook();
        tabbedPane.addTab("还书",returnBook);

        //创建通信 选项卡
        Communication communication = new Communication();
        tabbedPane.addTab("通信",communication);

        add(tabbedPane);

    }
}
