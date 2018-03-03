package reader_GUI;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.ArrayList;

public class ReturnBook extends JPanel {
    JLabel label = null;
    JButton returnBtn,resetBtn;
    JScrollPane jScrollPane;
    JTable jTable ;
    class Information{
        List<String> dataList = new ArrayList<String>();
        
    }
    class  tableModel extends AbstractTableModel{
        private List<>
    }
    ReturnBook(){
        init();
    }

    void init(){
        label = new JLabel("还书");
        GridBagLayout layout = new GridBagLayout();
        setLayout(layout);
        GridBagConstraints s = new GridBagConstraints();
        s.gridx = 1;
        s.gridy = 0;
        s.gridwidth = 1;
        s.gridheight = 1;
        add(label);
        layout.setConstraints(label,s);


    }
}
