package UserRelated;


import SQLQuery.Connect.*;

import javax.management.MalformedObjectNameException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Manager extends user{
    public Manager(){}
    public Manager(String userID,String passWord){
    }
    public String userid;
    public String isAdmin;
    public String userName;
    public String userSex;
    public String userStatus;
    public String userRentCount;
    public String userHostName;
    public String queryBookID;
    public String queryBookName;
    public String queryAuthor;
    public String queryClassification;
    public String queryPress;
    public String queryEntyrDate;
    public String queryStatus;
    public static int AUTHOR_INFORMATION = 0;
    public static int CLASSIFICATION_INFORMATION = 1;
    public static int PRESS_INFORMATION = 2;
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
       Connection con = GetDBConnection.connectDB("booklibrarymanager","root","HanDong85");
       String tar;
       if(tableName.equals("authorinformation"))
           tar = "author";
       else if(tableName.equals("classificationinformation"))
           tar = "classification";
       else tar = "press";
       String sql = "select " + tar + "Id" + " from "  + tableName + " where ";
       if(tableName.equals("authorinformation"))
           sql += "authorName = ?;";
       else if(tableName.equals("classificationinformation"))
           sql += "classifcationName = ?;";
       else sql += "pressName = ?;";
       System.out.println(sql);
       PreparedStatement preSQL;
       try{
           preSQL = con.prepareStatement(sql);
           preSQL.setString(1,queryname);
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
           GetDBConnection.closeCon(con);
           return ERROR_TIP;
       }
    }
    //add press || classification || author
    public boolean removeInformation(String type,String removeKey){
        System.out.println("start remove");
       if(type == null || removeKey == null)
           return false;
       Connection con = GetDBConnection.connectDB("booklibrarymanager","root","HanDong85");
       userId = removeKey;
       String[][] res = queryUser();
       if(res == null)
           return false;
       String sql = "delete from " + type + "information where " + type +"Id = ?;";
       PreparedStatement preSQL;
       try{
           preSQL = con.prepareStatement(sql);
           preSQL.setString(1,removeKey);
           //System.out.println("delete from " + type + "information where " + type + "Id = " + removeKey);
           int ok = preSQL.executeUpdate();
           GetDBConnection.closeCon(con);
           if(ok == 1)
              return true;
           return false;
       }
       catch (SQLException e){
           GetDBConnection.closeCon(con);
           return false;
       }
    }
    public boolean addNewDetailInformation(int tableID,String needAdd){
        if(tableID > 2)
            return false;
        String tableName = informationTable[tableID];
        if(needAdd == null)
            return false;
        String tempID = queryInformation(tableName,needAdd);
        if(!ERROR_TIP.equals(tempID))
            return false;
        String next = Root.getNextId(tableName);
        selfcon = GetDBConnection.connectDB("booklibrarymanager","root","HanDong85");
        String sql = "insert into " + tableName +" values (?,?);";
        PreparedStatement preSQL ;
        try{
           preSQL = selfcon.prepareStatement(sql);
           preSQL.setString(1,next);
           preSQL.setString(2,needAdd);
           int ok  = preSQL.executeUpdate();
           GetDBConnection.closeCon(selfcon);
           System.out.println("after 134");
           if(ok == 1)
               return true;
           return false;
        }
        catch (SQLException e){
            GetDBConnection.closeCon(selfcon);
           return false;
        }
    }
    public boolean removeDetailInformation(int tableID,String needRemove){
        if(tableID > 2)
            return false;
        String tableName = informationTable[tableID];
        if(needRemove == null)
            return false;
        String tempID = queryInformation(tableName,needRemove);
        if(ERROR_TIP.equals(tempID))
            return false;
        Connection con = GetDBConnection.connectDB("booklibrarymanager","root","HanDong85");
        String tar;
       if(tableName.equals("authorinformation"))
           tar = "author";
       else if(tableName.equals("classificationinformation"))
           tar = "classification";
       else tar = "press";
        String sql = "delete from " + tableName + " where " + tar + "Id = ?;";
        PreparedStatement preSQL;
        try{
           preSQL = con.prepareStatement(sql);
           preSQL.setString(1,tempID);
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
        System.out.println("test first");
        if(authorId == null || classification == null || pressId == null)
            return false;
        System.out.println("test second");
        if(ERROR_TIP.equals(authorId) || ERROR_TIP.equals(classificationId) || ERROR_TIP.equals(pressId))
            return false;
        selfcon = GetDBConnection.connectDB("booklibrarymanager","root","HanDong85");
        Date entryDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String formatDate = format.format(entryDate);
        String status = CAN_USE;
        String sql = "insert into bookinformation values (?,?,?,?,?,?,?);";
        String ID = Root.getNextId("bookinformation");
        System.out.println(ID + bookName + author + classification + press + formatDate + status);
        PreparedStatement preSQL;
        try{
            preSQL = selfcon.prepareStatement(sql);
            preSQL.setString(1,ID);
            preSQL.setString(2,bookName);
            preSQL.setString(3,author);
            preSQL.setString(4,classification);
            preSQL.setString(5,press);
            preSQL.setString(6,formatDate);
            preSQL.setString(7,status);
            int ok = preSQL.executeUpdate();
            GetDBConnection.closeCon(selfcon);
            if(ok == 1)
                return true;
            else
                return false;
        }
        catch (SQLException e)
        {
            GetDBConnection.closeCon(selfcon);
            return false;
        }
    }
    public boolean updateBookStatus(String bookId,String status){
        if(!CAN_USE.equals(status) && !CANT_USE.equals(status) && !LENT.equals(status))
            return false;
        selfcon = GetDBConnection.connectDB("booklibrarymanager","root","HanDong85");
        String sql = "update bookinformation set status=? where bookId=?;";
        PreparedStatement preSQL;
        try{
            preSQL = selfcon.prepareStatement(sql);
            preSQL.setString(1,status);
            preSQL.setString(2,bookId);
            int ok = preSQL.executeUpdate();
            GetDBConnection.closeCon(selfcon);
            if(ok == 1)
                return true;
            else
                return false;
        }
        catch(SQLException e){
            GetDBConnection.closeCon(selfcon);
            return false;
        }
    }
    public boolean removeBook(String bookId){
        if(bookId == null)
            return false;
        String sql = "delete from bookinformation where bookId = ?;";
        PreparedStatement preSQL;
        selfcon = GetDBConnection.connectDB("booklibrarymanager","root","HanDong85");
        try{
            preSQL = selfcon.prepareStatement(sql);
            preSQL.setString(1,bookId);
            int ok = preSQL.executeUpdate();
            GetDBConnection.closeCon(selfcon);
            if(ok == 0)
                return false;
            else
                return true;
        }
        catch (SQLException e){
            GetDBConnection.closeCon(selfcon);
            return false;
        }
    }
    public boolean setUserStatus(String userId,String status){
        if(!USER_ALIVE.equals(status) && !USER_CLOSE.equals(status))
            return false;
        selfcon = GetDBConnection.connectDB("booklibrarymanager","root","HanDong85");
        String sql = "update userinformation set userStatus = ? where userId = ?;";
        PreparedStatement preSQL;
        try{
            preSQL = selfcon.prepareStatement(sql);
            preSQL.setString(1,status);
            preSQL.setString(2,userId);
            int ok = preSQL.executeUpdate();
            GetDBConnection.closeCon(selfcon);
            if(ok == 1)
                return true;
            else
                return false;
        }
        catch (SQLException e){
            GetDBConnection.closeCon(selfcon);
            return false;
        }
    }
    public boolean sendMessagetoReader(String userId,String content){
        String sql = "insert into ? values(?,?,?);";
        Date curtime = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strCurTime = sdf.format(curtime);
        PreparedStatement preSQL;
        selfcon = GetDBConnection.connectDB("booklibrary","root","HanDong85");
        try{
            preSQL = selfcon.prepareStatement(sql);
            preSQL.setString(1,"Manager");
            preSQL.setString(2,strCurTime);
            preSQL.setString(3,content);
            int ok = preSQL.executeUpdate();
            GetDBConnection.closeCon(selfcon);
            if(ok == 0)
                return false;
            else
                return true;
        }
        catch (SQLException e){
            GetDBConnection.closeCon(selfcon);
            return false;
        }
    }
    public String[][] queryUser() {
        try {
            selfcon = GetDBConnection.connectDB("booklibrarymanager","root","HanDong85");
            boolean has = false;
            String sql = "select * from userinformation ";
            if(userid != null && !"".equals(userid)){
                if(!has) {
                    sql += "where ";
                    has = true;
                }
                else{
                    sql += " and ";
                }
                sql += "userId = '" + userid + "'";
            }
            if(isAdmin != null && !"".equals(isAdmin)){
                if(!has){
                    sql += "where ";
                    has = true;
                }
                else{
                    sql += " and ";
                }
                sql += "isAdmin = " + isAdmin;
            }
            if(userName != null && !"".equals(userName)){
                if(!has){
                    sql += "where ";
                    has = true;
                }
                else{
                    sql += " and ";
                }
                sql += "userName = '" + userName + "'";
            }
            if(userSex != null && !"".equals(userSex)){
                if(!has){
                    sql += "where ";
                    has = true;
                }
                else{
                    sql += " and ";
                }
                sql += "userSex = '" + userSex + "'";
            }
            if(userStatus != null && !"".equals(userStatus)){
                if(!has){
                    sql += "where ";
                    has = true;
                }
                else{
                    sql += " and ";
                }
                sql += "userStatus = " + userStatus;
            }
            if(userRentCount != null && !"".equals(userRentCount)){
                if(!has){
                    sql += "where ";
                    has = true;
                }
                else{
                    sql += " and ";
                }
                sql += "userRendCount = " + userRentCount;
            }
            if(userHostName != null && !"".equals(userHostName)){
                if(!has){
                    sql += "where ";
                    has = true;
                }
                else{
                    sql += " and ";
                }
                sql += "host = '" + userHostName + "'";
            }
            sql += ";";
            System.out.println(sql);
            PreparedStatement preSQL = selfcon.prepareStatement(sql);
            ResultSet rs = preSQL.executeQuery();
            ResultSetMetaData metaData;
            metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            rs.last();
            int recordAmount = rs.getRow();

            System.out.println("CNT :  "  + recordAmount);
            if(recordAmount == 0)
                return null;
            table = new String[recordAmount][columnCount];

            int i = 0;
            rs.beforeFirst();
            while (rs.next()) {
                for (int j = 1; j <= columnCount; j++) {
                    table[i][j - 1] = rs.getString(j);
                    //                       System.out.print(table[i][j-1]+"\t");
                }
                i++;
            }
            for(String[] str:table){
                for(String one:str){
                    System.out.println(one);
                }
            }
            GetDBConnection.closeCon(selfcon);
            return table;
        } catch (SQLException e) {
            GetDBConnection.closeCon(selfcon);
            e.printStackTrace();
        }
        return null;
    }
    public String[][] queryBook(){
        try {
            selfcon = GetDBConnection.connectDB("booklibrarymanager","root","HanDong85");
            //System.out.println(con == null);
            boolean has = false;
            String sql = "select * from bookinformation ";
            if(queryBookID != null && !"".equals(queryBookID)){
                if(!has) {
                    sql += "where ";
                    has = true;
                }
                else{
                    sql += " and ";
                }
                sql += "bookId = '" + queryBookID + "'";
            }
            if(queryBookName!= null && !"".equals(queryBookName)){
                if(!has){
                    sql += "where ";
                    has = true;
                }
                else{
                    sql += " and ";
                }
                sql += "bookName = '" + queryBookName + "'";
            }
            if(queryAuthor != null && !"".equals(queryAuthor)){
                if(!has){
                    sql += "where ";
                    has = true;
                }
                else{
                    sql += " and ";
                }
                sql += "author = '" + queryAuthor + "'";
            }
            if(queryClassification != null && !"".equals(queryClassification)){
                if(!has){
                    sql += "where ";
                    has = true;
                }
                else{
                    sql += " and ";
                }
                sql += "classification = '" + queryClassification + "'";
            }
            if(queryPress != null && !"".equals(queryPress)){
                if(!has){
                    sql += "where ";
                    has = true;
                }
                else{
                    sql += " and ";
                }
                sql += "press = '" + queryPress + "'";
            }
            if(queryEntyrDate != null && !"".equals(queryEntyrDate)){
                if(!has){
                    sql += "where ";
                    has = true;
                }
                else{
                    sql += " and ";
                }
                sql += "entryData = '" + queryEntyrDate + "'";
            }
            if(queryStatus != null && !"".equals(queryStatus)){
                if(!has){
                    sql += "where ";
                    has = true;
                }
                else{
                    sql += " and ";
                }
                sql += "status = '" + userHostName + "'";
            }
            sql += ";";
            System.out.println(sql);
            PreparedStatement preSQL = selfcon.prepareStatement(sql);
            ResultSet rs = preSQL.executeQuery();
            ResultSetMetaData metaData;
            metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            rs.last();
            int recordAmount = rs.getRow();
            table = new String[recordAmount][columnCount];
            int i = 0;
            rs.beforeFirst();
            while (rs.next()) {
                for (int j = 1; j <= columnCount; j++) {
                    table[i][j - 1] = rs.getString(j);
                    //                       System.out.print(table[i][j-1]+"\t");
                }
                i++;
            }
            GetDBConnection.closeCon(selfcon);
            return table;
        } catch (SQLException e) {
            GetDBConnection.closeCon(selfcon);
            e.printStackTrace();
        }
        return null;
    }
    public String[][] getMessage(){
        try{
            Manager tmpManager = new Manager();
            tmpManager.GetDBConnection("booklibrarymanager","root","HanDong85");
            Connection con = tmpManager.con;
            PreparedStatement pstmt = con.prepareStatement("select sender,sendTime,sendMessage from rootmessage");
            ResultSet rs = pstmt.executeQuery();
            ResultSetMetaData metaData;
            metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            rs.last();
            int recordAmount = rs.getRow();
            table = new String[recordAmount][columnCount];
            int i=0;
            rs.beforeFirst();
            while(rs.next()){
                for(int j=1;j<=columnCount;j++){
                    table[i][j-1]=rs.getString(j);
                    //                       System.out.print(table[i][j-1]+"\t");
                }
                i++;
            }

            return table;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
