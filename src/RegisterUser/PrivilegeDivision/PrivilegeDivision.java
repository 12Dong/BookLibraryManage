package RegisterUser.PrivilegeDivision;

import UserRelated.userInformation;
import SQLQuery.Connect.GetDBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
