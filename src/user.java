import java.sql.*;
public class user extends SQLBase{
    //用户名
    String userName=null;
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

}
