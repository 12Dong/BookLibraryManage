package GUI.login_GUI.reader_GUI;

import javax.swing.*;

public class ReaderFrame extends JFrame{

    private String userId;
    public void setUserId(String userId){
        this.userId = userId;
    }
    public ReaderFrame(String userId){
        init(userId);
        setBounds(300,400,700,600);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    void init(String userId){
        setUserId(userId);
        setTitle("Reader 花Q");
        final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        //创建借书 选件卡
        RenderBook renderBook = new RenderBook(userId);
        tabbedPane.addTab("借书",renderBook);

        //创建还书 选件卡
        ReturnBook returnBook = new ReturnBook(userId);
        tabbedPane.addTab("还书",returnBook);

        //创建通信 选项卡
        Communication communication = new Communication(userId);
        tabbedPane.addTab("通信",communication);

        add(tabbedPane);

    }
}
