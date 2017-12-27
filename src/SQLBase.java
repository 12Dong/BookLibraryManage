import java.sql.*;

public class SQLBase {
    Connection con;
    String[][] table;
    //建立connection  连接数据库
    void   GetDBConnection(String libName,String hostname,String password){
        con = null;
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

    //执行查询操作 返回二维数组 包含字段名 和 数据
    void query(String sqlCommand){
        if(con!=null){
            ResultSet rs ;
            ResultSetMetaData metaData;
            try {
                Statement statement = con.createStatement();
                rs = statement.executeQuery(sqlCommand);
                metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();
                rs.last();
                int recordAmount = rs.getRow();
                table = new String[recordAmount+1][columnCount];
                for(int i=1;i<=columnCount;i++){
                    table[0][i-1]=metaData.getColumnName(i);
                }
                int i=1;
                rs.beforeFirst();
                while(rs.next()){
                    for(int j=1;j<=columnCount;j++){
                        table[i][j-1]=rs.getString(j);
                        System.out.print(table[i][j-1]+"\t");
                    }
                    i++;
                }
            }
            catch(SQLException e){
                e.printStackTrace();
            }
        }else
        System.out.println("Connection is not prepared");
    }
}
