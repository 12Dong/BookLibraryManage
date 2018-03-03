package reader_GUI;

import javax.swing.*;

public class ReturnBook extends JPanel {
    JLabel label = null;
    ReturnBook(){
        init();
    }

    void init(){
        label = new JLabel("借书");
    }
}
