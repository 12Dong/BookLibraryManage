package RegisterUser.PrivilegeDivision;

import UserRelated.userInformation;
import SQLQuery.Connect.GetDBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PrivilegeDivision {
    public static void managerPrivilegeDivision(userInformation information) {
        Connection helper = GetDBConnection.connectDB("booklibrarymanager", "root", "HanDong85");
        cancelAllPrivilege(helper,information);
        refreshPrivilege(helper);
        setPrivilege(helper, "*.*", "ALL", information);
        refreshPrivilege(helper);
        updateisAdmin(helper,1,information);
        GetDBConnection.closeCon(helper);
    }
    public static void readerPrivilegeDivision(userInformation information){
        Connection helper = GetDBConnection.connectDB("booklibrarymanager","root","HanDong85");
        //set Privilege
        cancelAllPrivilege(helper,information);
        refreshPrivilege(helper);
        setPrivilege(helper,"authorinformation,bookinformation,pressinformation,classificationinformation,rootinfomation",
                "select",information);
        String sql = "select userId from userinformation where host = ?;";
        String userId = null;
        PreparedStatement preSQL;
        try{
            preSQL = helper.prepareStatement(sql);
            preSQL.setString(1,information.hostName);
            ResultSet rs = preSQL.executeQuery();
            rs.beforeFirst();
            if(rs.next())
               userId = rs.getString(1);
            GetDBConnection.closeCon(helper);
        }
        catch (SQLException e){
            GetDBConnection.closeCon(helper);
        }
        setPrivilege(helper,userId + "message","select,delete,insert",information);
        setPrivilege(helper,"rendinformation","*",information);
        refreshPrivilege(helper);
        GetDBConnection.closeCon(helper);
    }
    static void setPrivilege(Connection con,String table,String privilege,userInformation information){
        Connection c = GetDBConnection.connectDB("booklibrarymanager","root","HanDong85");
        String str = "GRANT ? ON ? TO ?@'%';";
        PreparedStatement preSql;
        try{
            preSql = c.prepareStatement(str);
            preSql.setString(1,privilege);
            preSql.setString(2,table);
            preSql.setString(3,information.hostName);
            preSql.execute();
            GetDBConnection.closeCon(c);
        }
        catch (SQLException e){
            GetDBConnection.closeCon(c);
            System.out.println("Privilege Division Error!");
        }
    }
    static void refreshPrivilege(Connection con){
       String str = "flush privileges;";
       Connection c = GetDBConnection.connectDB("booklibrarymanager","root","HanDong85");
       try {
           PreparedStatement sql = c.prepareStatement(str);
           GetDBConnection.closeCon(c);
           sql.execute();
       }
       catch (SQLException e){
           GetDBConnection.closeCon(c);
           System.out.println("Refresh Privileges Error!");
       }
    }
    static void cancelAllPrivilege(Connection con,userInformation information){
        String sql = "REVOKE ALL ON *.* ?@'%';";
        Connection c = GetDBConnection.connectDB("booklibrarymanager","root","HanDong85");
        PreparedStatement preSQL;
        try{
            preSQL = c.prepareStatement(sql);
            preSQL.setString(1,information.hostName);
            preSQL.execute();
            GetDBConnection.closeCon(c);
        }
        catch (SQLException e){
            GetDBConnection.closeCon(c);
            System.out.println("Privilege Division Error!");
        }
        updateisAdmin(con,0,information);
    }
    static void updateisAdmin(Connection con,int status,userInformation information){
        Connection c = GetDBConnection.connectDB("booklibrarymanager","root","HanDong85");
        String sql = "update userinformation set isAdmin = ? where host = ?;";
        PreparedStatement preSql;
        try{
            preSql = c.prepareStatement(sql);
            preSql.setInt(1,status);
            preSql.setString(2,information.hostName);
            preSql.executeUpdate();
            GetDBConnection.closeCon(c);
        }
        catch (SQLException e){
            e.printStackTrace();
            GetDBConnection.closeCon(c);
        }
    }
}
