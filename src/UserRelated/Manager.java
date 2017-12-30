package UserRelated;


import SQLQuery.Connect.*;

import javax.management.MalformedObjectNameException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Manager extends user{
    public Manager(){}
    public Manager(String userID,String passWord){
        con = GetDBConnection.connectDB("booklibrarymanager",userID,passWord);
    }
    static int AUTHOR_INFORMATION = 0;
    static int CLASSIFICATION_INFORMATION = 1;
    static int PRESS_INFORMATION = 2;
    static String CAN_USE = "1";
    static String LENT = "2";
    static String CANT_USE  = "3";
    static String ERROR_TIP = "****FUCK*****";
    static String USER_CLOSE = "0";
    static String USER_ALIVE = "1";
    static String [] informationTable;
    static {
        informationTable = new String [3];
        informationTable[0] = "authorinformation";
        informationTable[1] = "classificationinformation";
        informationTable[2] = "pressinformation";
    }
    Connection selfcon;
    //-1 not exist else return id
    String queryInformation(String tableName,String queryname){
       Connection con = GetDBConnection.connectDB("booklibrarymanager","root","");
       String sql = "select ? from ? where ? = ?;";
       PreparedStatement preSQL;
       try{
           preSQL = con.prepareStatement(sql);
           preSQL.setString(1,tableName + "ID");
           preSQL.setString(2,tableName);
           preSQL.setString(3,tableName + "Name");
           preSQL.setString(4,queryname);
           ResultSet rs = preSQL.executeQuery();
           rs.beforeFirst();
           if(rs.next()){
              String ans = rs.getString(1);
              GetDBConnection.closeCon(con);
              return ans;
           }
           else{
              GetDBConnection.closeCon(con);
              return ERROR_TIP;
           }
       }
       catch (SQLException e){
           return null;
       }
    }
    //add press || classification || author
    public boolean addNewInformation(int tableID,String needAdd){
        if(tableID > 2)
            return false;
        String tableName = informationTable[tableID];
        if(needAdd == null)
            return false;
        Connection con = GetDBConnection.connectDB("booklibrarymanager","root","");
        String tempID = queryInformation(tableName,needAdd);
        if(!ERROR_TIP.equals(tempID))
            return false;
        String next = Root.getNextId(tableName);
        String sql = "insert into ? values(?,?);";
        PreparedStatement preSQL ;
        try{
           preSQL = con.prepareStatement(sql);
           preSQL.setString(1,tableName);
           preSQL.setString(2,next);
           preSQL.setString(3,needAdd);
           int ok  = preSQL.executeUpdate();
           GetDBConnection.closeCon(con);
           if(ok == 1)
               return true;
           else return false;
        }
        catch (SQLException e){
           return false;
        }
    }
    public boolean removeInformation(int tableID,String needRemove){
        if(tableID > 2)
            return false;
        String tableName = informationTable[tableID];
        if(needRemove == null)
            return false;
        Connection con = GetDBConnection.connectDB("booklibrarymanager","root","");
        String tempID = queryInformation(tableName,needRemove);
        if(ERROR_TIP.equals(tempID))
            return false;
        String sql = "delete from ? where ?=?;";
        PreparedStatement preSQL;
        try{
           preSQL = con.prepareStatement(sql);
           preSQL.setString(1,tableName);
           preSQL.setString(2,tableName + "Id");
           preSQL.setString(3,tempID);
           int ok = preSQL.executeUpdate();
           GetDBConnection.closeCon(con);
           if(ok == 1)
               return true;
           else
               return false;
        }
        catch (SQLException e){
            GetDBConnection.closeCon(con);
            return false;
        }
    }
    public boolean addNewBook(String bookName,String author,String classification,String press){
        if(bookName == null || author == null || classification == null || press == null)
            return false;
        String authorId= queryInformation(informationTable[AUTHOR_INFORMATION],author);
        String classificationId = queryInformation(informationTable[CLASSIFICATION_INFORMATION],classification);
        String pressId = queryInformation(informationTable[PRESS_INFORMATION],press);
        if(authorId == null || classification == null || pressId == null)
            return false;
        if(ERROR_TIP.equals(authorId) || ERROR_TIP.equals(classificationId) || ERROR_TIP.equals(pressId))
            return false;
        Date entryDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String formatDate = format.format(entryDate);
        String status = CAN_USE;
        String sql = "insert into bookinformation values(?,?,?,?,?,?,?);";
        PreparedStatement preSQL;
        try{
            preSQL = selfcon.prepareStatement(sql);
            preSQL.setString(1,Root.getNextId("bookinformation"));
            preSQL.setString(2,bookName);
            preSQL.setString(3,authorId);
            preSQL.setString(4,classification);
            preSQL.setString(5,pressId);
            preSQL.setString(6,formatDate);
            preSQL.setString(7,status);
            int ok = preSQL.executeUpdate();
            if(ok == 1)
                return true;
            else
                return false;
        }
        catch (SQLException e)
        {
            return false;
        }
    }
    public boolean updateBookStatus(String bookId,String status){
        if(!CAN_USE.equals(status) && !CANT_USE.equals(status) && !LENT.equals(status))
            return false;
        String sql = "update bookinformation set status=? where bookId=?;";
        PreparedStatement preSQL;
        try{
            preSQL = selfcon.prepareStatement(sql);
            preSQL.setString(1,status);
            preSQL.setString(2,bookId);
            int ok = preSQL.executeUpdate();
            if(ok == 1)
                return true;
            else
                return false;
        }
        catch(SQLException e){
            return false;
        }
    }
    public boolean removeBook(String bookId){
        if(bookId == null)
            return false;
        String sql = "delete from bookinformation where bookId = ?;";
        PreparedStatement preSQL;
        try{
            preSQL = selfcon.prepareStatement(sql);
            preSQL.setString(1,bookId);
            int ok = preSQL.executeUpdate();
            if(ok == 0)
                return false;
            else
                return true;
        }
        catch (SQLException e){
            return false;
        }
    }
    public boolean setUserStatus(String userId,String status){
        if(!USER_ALIVE.equals(status) && !USER_CLOSE.equals(status))
            return false;
        String sql = "update userinformation set status = ? where userId = ?;";
        PreparedStatement preSQL;
        try{
            preSQL = selfcon.prepareStatement(sql);
            preSQL.setString(1,status);
            preSQL.setString(2,userId);
            int ok = preSQL.executeUpdate();
            if(ok == 1)
                return true;
            else
                return false;
        }
        catch (SQLException e){
            return false;
        }
    }
    public boolean sendMessagetoReader(String userId,String content){
        String sql = "insert into ? values(?,?,?);";
        Date curtime = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strCurTime = sdf.format(curtime);
        PreparedStatement preSQL;
        try{
            preSQL = selfcon.prepareStatement(sql);
            preSQL.setString(1,"Manager");
            preSQL.setString(2,strCurTime);
            preSQL.setString(3,content);
            int ok = preSQL.executeUpdate();
            if(ok == 0)
                return false;
            else
                return true;
        }
        catch (SQLException e){
            return false;
        }
    }
}
