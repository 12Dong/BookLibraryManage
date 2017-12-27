import java.sql.*;
public class SQLBase {
    Connection con;

    //建立connection  连接数据库
    Connection  GetDataBaseConnection(String libName,String hostname,String password){
        Connection con = null;
        String url ="jdbc:mysql://localhost:3306/";
        url +=libName;
        url +="?useSSL=true";
        try{
            Class.forName("com.mysql.jbbc.Driver");
        }
        catch(Exception e){}
        try{
            con = DriverManager.getConnection(url,hostname,password);
        }
        catch(SQLException e){}
        return con;
    }
    //关闭数据库 确保先建立连接后 再关闭连接
    void closeConnection(){
        try{
            con.close();
            System.out.println("DataBase closed successfully");
        }
        catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Closing was failed.Something wrong happened");
        }
    }

    //执行查询操作
    void query(String sqlCommand){
        if(con==null){
            System.out.println("Connection is not prepared");
            return ;
        }

    }
}
