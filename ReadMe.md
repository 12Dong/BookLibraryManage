# BookLibraryManage


## 包总览
*   [RegisterUser](#1.1)
    * [RegisterUser.MailCreator](#1.1.1)
    * [PrivilegeDivision](#1.1.2)
*   [SQLQuery](#1.2)
    * [SQLQuery.Base](#1.2.1)
    * [SQLQuery.Connect](#1.2.2)
*   [UserRelated](#1.3)
*   [GUI.login_GUI](#1.4)
    * [ManagerUI](#1.4.1)
    * [reader_GUI](#1.4.2)
    * [Register_GUI](#1.4.3)

## 操作手册
*   [登录界面](#2.1)
    * [用户登录](#2.1.1)
    * [用户注册](#2.1.2)
*   [用户界面](#2.2)
    * [借书操作](#2.2.1)
    * [还书操作](#2.2.2)
    * [用户反馈](#2.2.3)
*    

### <a name = "1.1">RegisterUser</a>
> 用于用户创建时相关初始化
> #### <a name = "1.1.1">RegisterUser.MailCreator</a>
> * MailCreator类
>   * 用于用户创建时消息列表创建
> #### <a name="1.1.2">RegisterUser.PrivilegeDivision</a>
> * PrivilegeDivision类
>   * 用于用户的权限分配

### <a name="1.2">SQLQuery</a>
> 用于SQL的基础操作包括连接，关闭，检查链接，SELECT语句
> #### <a name="1.2.1">SQLQuery.Base</a>
> * SQLBase类
>   * 用与基础查询，检查数据库连接
> #### <a name="1.2.2">SQLQuery.Connect</a>
> * GetDBConnection类
>   * 用于数据库的连接以及关闭
### <a name="1.3">UserRelated</a>
> 包括Root用户，Manager用户以及普通user的操作
> * user类 ： 继承SLQBase类
>   * 对书本进行缺省查询
>   * 借还书操作
>   * 接受管理员消息
>   * 发送消息
> * Manager类 ：继承user类
>   * 可以对作者，出版社，类型的信息进行管理
>   * 可以对书本的状态进行管理
>   * 可添加新书
>   * 可对普通用户进行封号处理
> * Root类 ： 继承Manager类
>   * 对用户进行注册
> * userInformation类
>   * 保存用户信息
### <a name="1.4">GUI.login_GUI</a>
> 包括登录、注册、用户界面、管理员界面以及相应的事件处理
> * login类
>   * 登录界面
>   * 可实现注册和登录
> * LoginMouseListener类
    * 用于相应登录事件
> #### <a name="1.4.1">GUI.login_GUI.ManagerUI</a>
> * ManagerFrame类
>   * 管理员主界面
> * AddDetailListener类
>   * 响应增删书籍作者类别出版社信息时间
> * Information类
>   * 用于存储每条查询信息的数据结构
> * ManagerUI类
>   * 用于测试ManagerFrame
> * OperatorBookListener类
>   * 响应增删图书事件
> * OperatorUserListener类
>   * 响应删除封禁用户事件
> * QueryBookListener类
>   * 响应图书管理界面缺省查询事件
> * QueryManagerListener类
>   * 响应管理员界面缺省查询事件
> * QueryUserListener类
>   * 响应用户界面缺省查询事件
> * UserTableModel类
>   * JTable类的辅助类
> #### <a name="1.4.2">GUI.login_GUI.reader_GUI</a>
> * Communication类
>   * 用于给管理员发送消息
> * main类
>   * 用于测试ReaderFrame
> * ReaderFrame类
>   * 用户主界面
> * RenderBook类
>   * 用于借书操作
> * ReturnBook类
>   * 用于还书操作
> #### <a name="1.4.3">GUI.login_GUI.Register_GUI</a>
> * main类
>   * 用于对注册界面的测试
> * SubmitMouseListener类
>   * 用于响应提交注册表事件
