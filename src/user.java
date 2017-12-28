import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
public class user extends SQLBase{
    //用户名
    String userName=null;
    //用户标号
    String userId = null;
    String host = null;
    //查询相关
    //查询书名
    String queryBookName=null;
    //查询作者名
    String queryAuthorName=null;
    //查询出版社名
    String queryPressName=null;
    user(){ }
    //set相关 因为要进行可缺省查询 所以直接加%匹配所有 在查询中使用like 进行查询 查询完毕后 在进行升序排序
    // 完全匹配字典序最小 因为下一次比较为null对任意
    void setQueryBookName(String queryBookName){
        this.queryBookName= "\'"+queryBookName+"%\'";
    }
    void setQueryAuthorName(String queryAuthorName){
        this.queryAuthorName = "\'"+queryAuthorName+"%\'";
    }
    void setQueryPressName(String queryPressName){
        this.queryPressName="\'"+queryPressName+"%\'";
    }
    void setUserName(String userName){
        this.userName = userName;
    }
    void setUserHost(String host){
        this.host = host;
    }
    String makeQuerySQLCommand(){
        String SQLCommand = "select * from bookinformation";
        String FromInformation = " ";
        String QueryInformation = " ";
        if(queryBookName==null && queryAuthorName==null && queryPressName==null){
            return SQLCommand;
        }
        else{
            QueryInformation +=" where ";
        }
        // 添加书名查询 若queryBookName不为null 则
        // 对 where 约束条件进行追加
        // 因为bookName 为 bookInformation表的信息
        // 所以直接追加信息 即可
        if(queryBookName!=null) {
            QueryInformation+=" bookName like "+queryBookName+" ";
        }
        // 添加作者名查询 若queryAuthorName不为null 则
        // 对 from 表 进行追加
        // 因为authorInformation.authorId 与 bookInformation 为依赖关系 联立authorInformation
        // 对 where 约束条件进行追加
        // 使用伪链表 若前一项不为空 先追加 and
        // 再追加约束条件
        if(queryAuthorName!=null) {
            FromInformation += " , authorInformation ";
            if(queryBookName!=null) {
                QueryInformation +=" and ";
            }
            QueryInformation += " authorinformation.authorName likes "+queryAuthorName +" and authorinformation.authorId = "+
            "bookinformation.authorId ";
        }
        // 添加作者名查询 若queryPressName不为null 则
        // 对 from 表 进行追加
        // 因为pressInformation.authorId 与 bookInformation 为依赖关系 联立pressInformation
        // 对 where 约束条件进行追加
        // 使用伪链表 若前两项不为空 先追加 and
        // 再追加约束条件
        if(queryPressName!=null){
            FromInformation += ", pressinformation ";
            if(queryAuthorName!=null){
                QueryInformation +=" and ";
            }else if(queryBookName!=null){
                QueryInformation +=" and ";
            }
            QueryInformation+=" pressinformation.pressName likes "+queryPressName+" and pressinformation.pressId = "+
                    "bookinformation.pressId ";
        }
        SQLCommand = SQLCommand+FromInformation + QueryInformation;
        if(QueryInformation !=null){
            SQLCommand +=" order by bookName";
        }
        return SQLCommand;
    }
    //资源锁操作 未实现
    //执行借书操作
    //准备工作取得用户编号
    //准备工作检查该书是否借走
    //操作一 更新bookinformation
    //操作二 更新rendinformation
    //bookinformation中的status 1 为 正常可借阅 2 为已借出 3 为已收回但无法操作
    void getUserId(){
        String SQLQueryCommand = "select userId,userName from userinformation where host = "+'\''+host+'\'';
        System.out.println(SQLQueryCommand);
        query(SQLQueryCommand);
        userId = table[1][0];
        userName = table[1][1];
    }
    boolean checkRend(String bookId){
        String SQLCommand = "select status from bookInformation where bookId =  \'"+bookId+" \'";
        try{
            Statement statement = con.createStatement();
            System.out.println(SQLCommand);
            query(SQLCommand);
            String Table[][] = table;
            if(Table[1][0]=="1") return true;
            else return false;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    void rendBook(String bookId){
        if(!checkRend(bookId)){
            System.out.println("The book was rended.");
            return ;
        }
        if(userId ==null ||userName ==null) getUserId();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date rendDate = new Date();
        String strRendDate = sdf.format(rendDate);
        Calendar returndate = Calendar.getInstance();
        returndate.add(Calendar.DAY_OF_YEAR,30);
        Date returnDate = returndate.getTime();
        String strReturnDate = sdf.format(returnDate);
        String SQLUpdateCommand = "update bookinformation set status = 2 where bookId = "+"\'"+bookId+"\'";
        String SQLInsertCommand = "insert rendinformation values(\'"+userId+"\',\'"+bookId+"\',\'"+strRendDate+"\',"+
                "\'"+strReturnDate+ "\'"+")";
        try{
            System.out.println("SQLUpdateCommand is "+SQLUpdateCommand);
            System.out.println("SQLInsertCommand is "+SQLInsertCommand);
            Statement statement = con.createStatement();
            statement.executeUpdate(SQLUpdateCommand);
            statement.executeUpdate(SQLInsertCommand);
            System.out.println("Successfully rend.");
        }
        catch(SQLException e) {}
    }
    //执行还书操作
    //准备工作取得用户编号
    //操作一 更新bookinformation
    //操作二 更新rendinformation
    void returnBook(String bookId){
        if(userId ==null ||userName ==null) getUserId();
        String SQLUpdateCommand = "update bookinformation set status = 3 where bookId = "+"\'"+bookId+"\'";
        String SQLDeleteCommand = "delete from rendinformation where bookId = "+"\'"+bookId+"\'";
        try{
            System.out.println("SQLUpdateCommand is "+SQLUpdateCommand);
            System.out.println("SQLInsertCommand is "+SQLDeleteCommand);
            Statement statement = con.createStatement();
            statement.executeUpdate(SQLUpdateCommand);
            statement.executeUpdate(SQLDeleteCommand);
            System.out.println("Successfully return.");
        }
        catch(SQLException e) {}
    }
    //获得自己所属表里信息
    String makeGetMessageSQLCommand(){
        if(userId==null || userName==null) getUserId();
        String SQLCommand = "select * from "+userId+"Message";
        System.out.println(SQLCommand);
        return SQLCommand;
    }
    void sendMessageSQLCommand(String sendMessage){
        Date curtime = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strCurTime = sdf.format(curtime);
        String SQLCommand = "insert into rootMessage values (\'"+userId+"\',\'"+strCurTime+"\',\'"+sendMessage+"\')";
        try{
            System.out.println(SQLCommand);
            Statement statement = con.createStatement();
            statement.executeUpdate(SQLCommand);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
}
