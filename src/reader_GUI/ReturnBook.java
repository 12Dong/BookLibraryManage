package reader_GUI;

import UserRelated.user;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReturnBook extends JPanel {
    JLabel label = null;
    JButton returnBtn,resetBtn;
    JScrollPane jScrollPane;
    JTable jTable ;

    private class Information{
        ArrayList<String> dataArray=new ArrayList<String>();
        public Information(String...information){
            for(String info:information){
                dataArray.add(info);
            }
        }

        @Override
        public String toString() {
            String output =null;
            for(String info:dataArray){
                output += info+"   ";
            }
            output+='\n';
            return output;
        }
    }
    class TableModel extends AbstractTableModel {
        private static final long serialVersionUID = 1L;
        // 保存一个User的列表
        private List<Information> informationArray = new ArrayList<Information>();
        final String columName[]={"借书者Id","书籍Id","书名","结束日期","还书日期"};

        public void setInformationArray(List<Information> informationArray) {
            this.informationArray = informationArray;
            this.fireTableDataChanged();
        }

//        public String getColumnName(int col) {
//            return columName[col];
//        }

        @Override
        public String getColumnName(int column) {return columName[column];}

        @Override
        public int getRowCount() {
            return informationArray.size();
        }

        @Override
        public int getColumnCount() {
            return informationArray.get(0).dataArray.size();
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Information info = informationArray.get(rowIndex);
            return info.dataArray.get(columnIndex);
        }
    }
    ReturnBook(){
        init();
    }

    void Tableinit(){


    }
    void init(){
        label = new JLabel("还书");
        GridBagLayout layout = new GridBagLayout();
        setLayout(layout);
        GridBagConstraints s = new GridBagConstraints();
        s.gridx = 1;
        s.gridy = 0;
        s.gridwidth = 0;
        s.gridheight = 1;
        add(label);
        layout.setConstraints(label,s);




        ArrayList<Information> infoArray = new ArrayList<Information>();
        user reader = new user();
        reader.userId="1";
        reader.GetDBConnection("booklibrarymanager","root","HanDong85");
        String[][] table = reader.queryRenderInformation();
        int col=0;
        for(String[] array:table){
            infoArray.add(new Information(array));
        }
        final TableModel tableModel= new TableModel();

        tableModel.setInformationArray(infoArray);

        jTable=new JTable(tableModel);
        TableColumnModel cm = jTable.getColumnModel();
        //得到第i个列对象
        TableColumn column = cm.getColumn(3);
        column.setPreferredWidth(100);
        column = cm.getColumn(4);
        column.setPreferredWidth(100);


        jScrollPane = new JScrollPane(jTable);
        add(jScrollPane);
        s.gridy = 3;
        layout.setConstraints(jScrollPane,s);
        returnBtn = new JButton("还书");
        returnBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = jTable.getSelectedRow();
                if(row==-1){
                    JOptionPane.showMessageDialog(new JPanel(),"未选择书籍");
                    return;

                }
                String bookId = (String)jTable.getValueAt(row,1);
                if(bookId==null){
                    JOptionPane.showMessageDialog(new JPanel(),"未选择书籍");
                    return;

                }
                if(reader.returnBook(bookId)){
                    infoArray.clear();
                    reader.GetDBConnection("booklibrarymanager","root","HanDong85");
                    String[][] table = reader.queryRenderInformation();
                    for(String[] array:table){
                        infoArray.add(new Information(array));
                    }
                    tableModel.setInformationArray(infoArray);
                    JOptionPane.showMessageDialog(new JPanel(),"还书成功");
                }else{
                    JOptionPane.showMessageDialog(new JPanel(),"还书失败");
                }
            }
        });
        add(returnBtn);
        s.gridx = 1;
        s.gridy = 4;
        s.gridwidth = 3;
        s.gridheight=1;
        layout.setConstraints(returnBtn,s);
        resetBtn = new JButton("刷新");
        resetBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reader.GetDBConnection("booklibrarymanager","root","HanDong85");
                reader.setUserId("1");
                infoArray.clear();
                String[][] table = reader.queryRenderInformation();
                if(table!=null){
                    for(String[] array:table){
                        infoArray.add(new Information(array));
                        System.out.println(Arrays.toString(array));
                    }
                }
                else{
                    infoArray.add(new Information("等待传参","等待传参","等待传参","等待传参","等待传参"));

                }
                tableModel.setInformationArray(infoArray);
            }
        });
        add(resetBtn);
        s.gridx=7;
        s.gridy=4;
        layout.setConstraints(resetBtn,s);


    }
}
