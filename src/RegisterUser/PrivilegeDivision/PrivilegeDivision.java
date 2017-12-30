package RegisterUser.PrivilegeDivision;

import UserRelated.userInformation;
import SQLQuery.Connect.GetDBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PrivilegeDivision {
    public static void managerPrivilegeDivision(userInformation information) {
        Connection helper = GetDBConnection.connectDB("booklibrarymanager", "root", "");
        setPrivilege(helper, "*.*", "ALL", information);
        refreshPrivilege(helper);
        GetDBConnection.closeCon(helper);
    }
    public static void readerPrivilegeDivision(userInformation information){
        Connection helper = GetDBConnection.connectDB("booklibrarymanager","root","");
        //set Privilege
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
        }
        catch (SQLException e){

        }
        setPrivilege(helper,userId + "message","select,delete,insert",information);
        setPrivilege(helper,"rendinformation","*",information);
        refreshPrivilege(helper);
        GetDBConnection.closeCon(helper);
    }
    static void setPrivilege(Connection con,String table,String privilege,userInformation information){
        String str = "GRANT ? ON ? TO '?'@'%';";
        PreparedStatement preSql;
        try{
            preSql = con.prepareStatement(str);
            preSql.setString(1,privilege);
            preSql.setString(2,table);
            preSql.setString(3,information.hostName);
            preSql.execute();
        }
        catch (SQLException e){
            System.out.println("Privilege Division Error!");
        }
    }
    static void refreshPrivilege(Connection con){
       String str = "flush privileges;";
       try {
           PreparedStatement sql = con.prepareStatement(str);
           sql.execute();
       }
       catch (SQLException e){
           System.out.println("Refresh Privileges Error!");
       }
    }
}
