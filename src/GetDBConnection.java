import java.sql.*;
public class GetDBConnection {
    public static Connection connectDB(String DBName,String id,String p){
        Connection con=null;
        String url ="jdbc:mysql://localhost:3306/";
        url +=DBName;
        url +="?useSSL=true";
        try{
            Class.forName("com.mysql.jbbc.Driver");
        }
        catch(Exception e){}
        try{
            con = DriverManager.getConnection(url,id,p);
        }
        catch(SQLException e){}
        return con;
    }
}
