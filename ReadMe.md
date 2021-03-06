# BookLibraryManage


## [包总览](#1)
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

## [操作手册](#2)
*   [登录界面](#2.1)
    * [用户登录](#2.1.1)
    * [用户注册](#2.1.2)
*   [用户界面](#2.2)
    * [借书操作](#2.2.1)
    * [还书操作](#2.2.2)
    * [用户反馈](#2.2.3)
*   [管理员界面](#2.3)
    * [用户管理](#2.3.1)
    * [书籍管理](#2.3.2)
    * [权限管理（*仅超级管理员）](#2.3.3)
    * [获取反馈](#2.3.4)
<br/>
<br/>
<br/>

## <a name ="1">包总览</a>
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

<br/>
<br/>
<br/>

## <a name="2">操作手册</a>
### <a name="2.1">登录界面</a>
> #### 具有对不同类别用户的登录以及普通用户的注册的功能
> * <a name="2.1.1">用户登录</a>
>   * 操作步骤
>       1. 在用户名密码的文本框中填入文本
>       2. 点击登录按钮
>       3. 返回登录结果
>   * 注意事项
>       1. 如果帐号被封禁会返回封禁提示
>       2. 除1中情况其余皆返回登录失败
> * <a name="2.1.2">用户注册</a>
>   * 操作步骤
>       1. 点击注册按钮，弹出注册表
>       2. 填写相关信息，点击确定
>       3. 返回注册结果
>   * 注意事项
>       1. 注册表对不完整填写不做提示，不填写所有信息可能会导致插入出错
>       2. 填写信息不可以以空格打头或者以空格结尾，如果出现会提示相关出错位置
>       3. 除2中情况皆返回注册失败
### <a name="2.2">用户界面</a>
> #### 具有对书籍的缺省查询，借还书以及发送信息给管理员等功能
> * <a name="2.2.1">借书操作</a>
>   * 操作步骤
>       1. 对书籍进行缺省查询
>       2. 在查询结果中选中相关书籍
>       3. 点击借阅，返回操作结果
>   * 注意事项
>       1. 在对书籍进行操作时先进行缺省查询或更新，避免出现错误操作
>       2. 对书籍的查询信息全部省略会查询所有书籍
>       3. 如果书籍已被借出会返回已被借出的提示
>       4. 除3中情况所有错误操作返回借阅失败
> * <a name="2.2.2">还书操作</a>
>   * 操作步骤
>       1. 选中已借阅书单中的一本书
>       2. 点击还书按钮，返回操作结果
>   * 注意事项
>       1. 每次操作之前先进行刷新，避免错误操作
>       2. 若为选中书籍，会返回相关提示
>       3. 除2中情况所有错误操作返回还书失败
> * <a name="2.2.3">用户反馈</a>
>   * 操作步骤
>       1. 填写用户反馈
>       2. 点击发送
### <a name="2.3">管理员界面</a>
> #### 具有对书籍的缺省查询，书籍细节信息添加，用户管理功能
> #### root用户还具有授予、去除管理员的功能
> * <a name="2.3.1">用户管理（封禁、删除）</a>
>   * 操作步骤
>       1. 对用户进行缺省查询
>       2. 从查询结果中选中操作对象
>       3. 返回操作结果
>   * 注意事项
>       1. 每次操作前后推荐进行一次缺省查询以更新操作结果
>       2. 如果未选中对象，提示未选中
>       3. 除2中情况，所有错误操作皆返回失败
>       4. 对一个已封禁用户再次封禁为解封操作
> * <a name="2.3.2">书籍管理</a>
>   * 书籍详细信息（出版社、作者、类别）管理（删除、添加）
>       * 操作步骤
>           1. 向相应文本框中输入信息
>           2. 点击相应按钮
>           3. 返回操作结果
>       * 注意事项
>           1. 信息对应文本框为空不作处理
>           2. 如果插入重复信息会被判断为错误操作
>           3. 错误信息会返回所有错误位置，未被返回的位置为正确
>           4. 如果对所有非空信息的操作都成功，则返回操作成功
>   * 书籍添加
>       * 操作步骤
>           1. 填写书名、出版社、作者、类别信息
>           2. 点击相应按钮，返回操作结果
>       * 注意事项
>           1. 上述信息必须填写不可为空
>           2. 除上述信息外的所有信息均忽略
>           3. 入库事件会由系统自动生成
>           4. 所有添加失败的操作均返回错误
>   * 书籍删除
>       * 操作步骤
>           1. 对书籍进行缺省查询
>           2. 选中查询结果的对象，点击相应按钮
>           3. 返回操作结果
>       * 注意事项
>           1. 每次操作前后推荐进行缺省查询以刷新信息
>           2. 全缺省查询为查询所有图书
>           3. 未选中对象的操作会返回提示
>           4. 除3中情况，其余失败操作返回错误
> * <a name="2.3.3">权限管理（*仅超级管理员）</a>
>   * 操作步骤
>       1. 对用户进行缺省查询
>       2. 选中查询结果中的对象，点击相应按钮
>       3. 返回操作结果
>   * 注意事项
>       1. 每次操作前后推荐进行缺省查询以刷新信息
>       2. 全缺省查询为查询所有用户
>       3. 未选中对象的操作会返回提示
>       4. 如果无法对对象做相应操作返回提示
>       5. 除3、4中情况失败操作均返回错误
> * <a name="2.3.4">获取反馈</a>
>   * 可点击刷新获取最新消息