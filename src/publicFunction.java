import java.sql.*;
import java.util.Arrays;
public class publicFunction{
    public static void transmit(userInformation UserInformation,boolean check){
        //未实现manage注册
        if(check){

        }
        //未实现root分配权限
        else{
            user register = new user();
            register.GetDBConnection("booklibrarymanager","host","HanDong85");
            try{
                Statement statement = register.con.createStatement();
                String nextId = getNextId("userinformation");
                String SQLCommand = "insert into userinformation values (\'"+nextId+"\',\'0\',"+
                        "\'"+UserInformation.name+"\',\'"+UserInformation.sex+"\',\'1\',\'0\',\'"+UserInformation.hostName
                        +"\')";
                System.out.println(SQLCommand);
                statement.executeUpdate(SQLCommand);
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }
    //libName 仅仅包含 authorinformation bookinformation classification pressinformation  userinformation  开放
    public static String getNextId(String libName){
        if(libName=="authorinformation" || libName=="bookinformation" || libName=="classification" ||
                libName=="pressinformation" || libName=="userinformation") {
        }
        else return null;
        String nextId = null;
        SQLBase root = new SQLBase();
        root.GetDBConnection("booklibrarymanager","host","HanDong85");
        String SQLCommand = "select * from " + libName;
        System.out.println(SQLCommand);
        root.query(SQLCommand);
        if(root.table==null){
            System.out.println("table is null");
            return null;
        }
        int rows  = root.table.length;
        String ID[] = new String[rows];
        for(int i=0;i<rows;i++) ID[i]=root.table[i][0];
        ID[0]="0";
        Arrays.sort(ID);
        int i;
        for(i=1;i<rows;i++){
            int curNum = Integer.parseInt(ID[i].trim());
            int lastNum = 0;
            if(lastNum!=1) lastNum = Integer.parseInt(ID[i-1]);
            if(curNum!=lastNum+1){
                nextId = String.valueOf(lastNum+1);
                root.closeConnection();
                return nextId;
            }
        }
        if(i==rows){
            root.closeConnection();
            return String.valueOf(rows);
        }
        root.closeConnection();
        return nextId;
    }
}
