package UserRelated;

import java.sql.*;
import java.util.Arrays;

import RegisterUser.MailCreator.MailCreator;
import RegisterUser.PrivilegeDivision.PrivilegeDivision;
import SQLQuery.Base.*;
import SQLQuery.Connect.GetDBConnection;

public class Root extends Manager{
    public static boolean transmit(userInformation UserInformation, boolean check){
        //未实现manage注册
        String sql = "create user '?'@'?' identified by '?';";
        PreparedStatement preSQL;
        Connection helper = GetDBConnection.connectDB("booklibrarymanager","root","");
        try{
            preSQL = helper.prepareStatement(sql);
            preSQL.setString(1,UserInformation.hostName);
            preSQL.setString(2,"%");
            preSQL.setString(3,UserInformation.password);
            int ok = preSQL.executeUpdate();
            GetDBConnection.closeCon(helper);
            if(ok == 0)
                return false;
        }
        catch (SQLException e){
            return false;
        }
        if(check){
            user register = new user();
            register.GetDBConnection("booklibrarymanager","host","HanDong85");
            String nextId;
            try{
                Statement statement = register.con.createStatement();
                nextId = getNextId("userinformation");
                String SQLCommand = "insert into userinformation values (\'"+nextId+"\',\'1\',"+
                        "\'"+UserInformation.name+"\',\'"+UserInformation.sex+"\',\'1\',\'0\',\'"+UserInformation.hostName
                        +"\')";
                System.out.println(SQLCommand);
                statement.executeUpdate(SQLCommand);
            }catch(SQLException e){
                e.printStackTrace();
                return false;
            }
            MailCreator.createMail(nextId);
            PrivilegeDivision.managerPrivilegeDivision(UserInformation);
        }
        //未实现root分配权限
        else{
            user register = new user();
            register.GetDBConnection("booklibrarymanager","host","HanDong85");
            String nextId;
            try{
                Statement statement = register.con.createStatement();
                nextId = getNextId("userinformation");
                String SQLCommand = "insert into userinformation values (\'"+nextId+"\',\'0\',"+
                        "\'"+UserInformation.name+"\',\'"+UserInformation.sex+"\',\'1\',\'0\',\'"+UserInformation.hostName
                        +"\')";
                System.out.println(SQLCommand);
                statement.executeUpdate(SQLCommand);
            }catch(SQLException e){
                e.printStackTrace();
                return false;
            }
            MailCreator.createMail(nextId);
            PrivilegeDivision.readerPrivilegeDivision(UserInformation);
        }
        return true;
    }
    //libName 仅仅包含 authorinformation bookinformation classification pressinformation  userinformation  开放
    public static String getNextId(String libName){
        if(libName=="authorinformation" || libName=="bookinformation" || libName=="classificationinformation" ||
                libName=="pressinformation" || libName=="userinformation") {
        }
        else return null;
        String nextId = null;
        SQLBase root = new SQLBase();
        root.GetDBConnection("booklibrarymanager","root","HanDong85");
        String SQLCommand = "select * from " + libName;
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
