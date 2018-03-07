import UserRelated.Root;
import UserRelated.user;
import UserRelated.userInformation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ex {
    public static void main(String[] argv){
        java.util.Date rendDate = new java.util.Date ();
        java.sql.Date RendDate = new java.sql.Date(rendDate.getTime());
        System.out.println(RendDate);
//        System.out.println(UserRelated.Root.getNextId("userinformation"));
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
//        SQLQuery.Base.SQLBase sqlBase = new SQLQuery.Base.SQLBase();
//        sqlBase.SQLQuery.Connect.GetDBConnection("example","host","HanDong85");
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
//          UserRelated.user reader =  new UserRelated.user();
//          reader.SQLQuery.Connect.GetDBConnection("booklibrarymanager","host","HanDong85");
//          reader.setUserHost("liming");
//          reader.getUserId();
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
//        user reader = new user();
//        reader.GetDBConnection("booklibrarymanager","host","HanDong85");
//        System.out.println(reader.checkRend("01"));
//        userInformation UserInformation = new userInformation();
//        UserInformation.setHostName("wangxiaoming");
//        UserInformation.setName("王小明");
//        UserInformation.setSex("男");
//        UserInformation.setPassword("123456");
//        if(UserInformation==null) {
//            System.out.println("NullPointerException");
//            return ;
//        }
//        Root.transmit(UserInformation,false);
        user reader = new user();
        reader.GetDBConnection("booklibrarymanager","root","HanDong85");
        try{
            PreparedStatement pstmt = reader.con.prepareStatement("select * from 01message");
//            pstmt.setString(1,"01");
//            pstmt.executeUpdate();
//            pstmt = reader.con.prepareStatement("insert into pressinformation (?,?)");
//            pstmt.setString(1,"03");
//            pstmt.setString(2,"随便");
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                System.out.println(rs.getString("sendMessage"));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

    }
}
