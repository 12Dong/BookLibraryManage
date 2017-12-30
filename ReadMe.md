# BookLibraryManage


## 包总览
*   [RegisterUser](#1)
    * [RegisterUser.MailCreator](#1.1)
    * [PrivilegeDivision](#1.2)
*   [SQLQuery](#2)
    * [SQLQuery.Base](#2.1)
    * [SQLQuery.Connect](#2.2)
*   [UserRelated](#3)

### <a name = "1">registerUser</a>
> 用于用户创建时相关初始化
> #### <a name = "1.1">RegisterUser.MailCreator</a>
> * MailCreator类
>   * 用于用户创建时消息列表创建
> #### <a name="1.2">RegisterUser.PrivilegeDivision</a>
> * PrivilegeDivision类
>   * 用于用户的权限分配

### <a name="2">SQLQuery</a>
> 用于SQL的基础操作包括连接，关闭，检查链接，SELECT语句
> #### <a name="2.1">SQLQuery.Base</a>
> * SQLBase类
>   * 用与基础查询，检查数据库连接
> #### <a name="2.2">SQLQuery.Connect</a>
> * GetDBConnection类
>   * 用于数据库的连接以及关闭
### <a name="3">UserRelated</a>
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
