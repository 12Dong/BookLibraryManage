package SQLQuery.Connect;

import java.sql.*;
public class GetDBConnection {
    public static Connection connectDB(String DBName,String id,String p){
        Connection con=null;
        String url ="jdbc:mysql://211.159.219.126:3306/";
        url +=DBName;
        url +="?useSSL=true";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Successfully Driver");
        }
        catch(Exception e){System.out.println("Fail Driver");}
        try{
            con = DriverManager.getConnection(url,id,p);
        }
        catch(SQLException e){}
        return con;
    }
    public static void closeCon(Connection con){
        if(con == null)
            return;
        try{
            con.close();
            System.out.println("close Connection");
        }
        catch (SQLException e){
            System.out.println("Close Error!");
        }
    }
}
