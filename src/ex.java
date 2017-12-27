import java.sql.*;
public class ex {
    public static void main(String[] argv){
//        SQLBase sqlBase = new SQLBase();
//        sqlBase.GetDBConnection("example","host","HanDong85");
//        if(sqlBase.con==null){
//            System.out.println("Connection is error");
//            return ;
//        }
//        sqlBase.query("select * from student");
//        String[][] table = sqlBase.table;
//        if(table==null){
//            System.out.println("It's error");
//            return ;
//        }
//        int rows = table.length;
//        int cols = table[0].length;
//        for(int i=0;i<rows;i++){
//            for(int j=0;j<cols;j++){
//                System.out.print(table[i][j]+'\t');
//            }
//            System.out.println("");
//        }
//        sqlBase.closeConnection();
          user reader =  new user();
          reader.GetDBConnection("booklibrarymanager","host","HanDong85");
          reader.setQueryBookName("高等");
          if(!reader.checkConnection()){
              return;
          }
          System.out.println(reader.makeQuerySQLCommand());
          reader.query(reader.makeQuerySQLCommand());
        String table[][] = reader.table;
          if(table==null){
            System.out.println("It's error");
            return ;
        }
        int rows = table.length;
        int cols = table[0].length;
        for(int i=0;i<rows;i++){
            for(int j=0;j<cols;j++){
                System.out.print(table[i][j]+'\t');
            }
            System.out.println("");
        }

    }
}
