package UserRelated;

//用于存储登录信息 创建user类
public class userInformation {
    public String hostName;
    public String password;
    public String name;
    public String sex;

    public void setHostName(String setHostName){
        this.hostName = setHostName;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setSex(String sex){
        this.sex = sex;
    }
}
