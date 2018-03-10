package GUI.login_GUI.reader_GUI;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class User {
    private String id;
        private String name;
    public User() {
            }
    public User(String id, String name) {
            this.id = id;
            this.name = name;
            }
    public String getId() {
            return id;
            }
    public void setId(String id) {
            this.id = id;
            }
    public String getName() {
            return name;
            }
    public void setName(String name) {
            this.name = name;
            }
}
class UserTableModel extends AbstractTableModel {
    private static final long serialVersionUID = 1L;
    // 保存一个User的列表
    private List<User> users = new ArrayList<User>();
    // 设置User列表, 同时通知JTabel数据对象更改, 重绘界面
    public void setUsers(List<User> users) {
            this.users = users;
            this.fireTableDataChanged();// 同时通知JTabel数据对象更改, 重绘界面
            }
    public int getColumnCount() {
            return 2;
            }
    public int getRowCount() {
            return users.size();
            }
    // 从list中拿出rowIndex行columnIndex列显示的值
    public Object getValueAt(int rowIndex, int columnIndex) {
            User user = users.get(rowIndex);
            if (columnIndex == 0) {
            return user.getId();
            } else {
            return user.getName();
            }
            }
            }
public class userjtable {
    public static void main(String args[]) {
        JFrame frame = new JFrame();
        frame.setBounds(100, 100, 500, 375);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTable table = new JTable();
        final UserTableModel userTableModel = new UserTableModel();
        userTableModel.setUsers(Arrays.asList(new User("1", "fuxueliang")));
        table.setModel(userTableModel);

        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add("Center", table);

        JButton button = new JButton("Change Model");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String current = String.valueOf(System.currentTimeMillis());
                // 注意:这里修改了UserTableModel的list, 这个list可以是你从数据库上查出来的
                userTableModel.setUsers(Arrays.asList(new User("2", current)));
            }
        });
        frame.getContentPane().add("South", button);
        frame.setVisible(true);
    }
}