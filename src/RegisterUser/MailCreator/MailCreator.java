package RegisterUser.MailCreator;

import SQLQuery.Connect.GetDBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MailCreator {
    static public boolean createMail(String userId){
        String sql = "create table ? (sender varchar(20),sendtime datetime,sendmessage varchar(1000));";
        PreparedStatement preSQL;
        try{
            Connection helper = GetDBConnection.connectDB("booklibrarymanager","root","");
            preSQL = helper.prepareStatement(sql);
            preSQL.setString(1,"booklibrarymanager." + userId + "message");
            int ok = preSQL.executeUpdate();
            GetDBConnection.closeCon(helper);
            if(ok == 1)
                return true;
            else
                return false;
        }
        catch (SQLException e){
            return false;
        }
    }
}
