import java.sql.*;
import java.util.Date;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.text.ParseException;
public class ex {
    public static void main(String[] argv){
//        Date d = new Date();
//        System.out.println(d);
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Calendar returnDate = Calendar.getInstance();
//        returnDate.setTime(d);
//        String dateNowStr = sdf.format(d);
//        System.out.println("格式化后的日期：" + dateNowStr);
//        returnDate.add(Calendar.DAY_OF_MONTH,30);
//        Date returnDatedd = returnDate.getTime();
//        String reStr = sdf.format(returnDatedd);
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
          reader.setUserHost("liming");
          reader.getUserId();
//          reader.rendBook("01");
//          System.out.println(reader.userName+"   "+reader.userId);
//          reader.setQueryBookName("高等");
//          if(!reader.checkConnection()){
//              return;
//          }
//          System.out.println(reader.makeQuerySQLCommand());
//          reader.query(reader.makeGetMessageSQLCommand());
//        String table[][] = reader.table;
//          if(table==null){
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
        reader.sendMessageSQLCommand("吃了");
    }
}
