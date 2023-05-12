### 前端

#### 单方法页面

| 前端页面                            | 触发条件 + url         | type | dataType | 传给后端参数                               | 接收后端参数              |
| ----------------------------------- | ---------------------- | ---- | -------- | ------------------------------------------ | ------------------------- |
| Login.html<br /> (登录功能)         | /users/login           | POST | JSON     | username 用户名，<br />password 密码       | 成功200                   |
| Register.html<br /> (注册功能)      | /users/reg             | POST | JSON     | username 用户名，<br />password 密码       | 成功200                   |
| Index.html <br />(主页面)           | /products/hot_list     | GET  | JSON     | 无                                         | 成功200+商品数据列表      |
| password.html<br />（修改密码功能） | /users/change_password | POST | JSON     | oldPassword 原密码<br />newPassword 新密码 | 成功200<br />返回登录页面 |
|                                     |                        | POST | JSON     |                                            |                           |
|                                     |                        |      |          |                                            |                           |
|                                     |                        |      |          |                                            |                           |
|                                     |                        |      |          |                                            |                           |



#### 多方法页面

**userdata.html （修改个人资料页面）**

| 触发条件 + url                             | type | dataType | 传给后端参数                                                 | 接收后端参数                                                 |
| ------------------------------------------ | ---- | -------- | ------------------------------------------------------------ | ------------------------------------------------------------ |
| ready页面加载即触发<br />/users/get_by_uid | GET  | JSON     | 无                                                           | username 用户名<br />phone 电话<br />email 邮箱<br />gender 性别 |
| 点击修改按钮<br />/users/change_info       | POST | JSON     | username 用户名<br />phone 电话<br />email 邮箱<br />gender 性别 | 成功200<br />修改成功后重新加载当前的页面                    |

![](image\userdatahtml.png)



**address.html (收获管理页面)**

首先所有方法都在**showAddressList()**的**methods**中，而showAddressList()是ready页面加载即触发

| 触发条件 + url                                               | type | dataType | 传给后端参数          | 接收后端参数                 |
| ------------------------------------------------------------ | ---- | -------- | --------------------- | ---------------------------- |
| 数据装载之后自动调用中调用getByUid()<br />/addresses         | GET  | JSON     | pageNum:1             | 第一页的所有用户地址数据列表 |
| 点击具体页数按钮时触发showpage()<br />/addresses             | GET  | JSON     | 此时所点击的pageNum数 | 该页的所有用户地址数据列表   |
| 点击设为默认按钮时触发setDefault()<br />/addresses/"+aid+"/set_default | GET  | JSON     | aid  收货地址id       | 设置成功并刷新页面           |
| 点击删除按钮时触发del()<br />/addresses/delete/ " + aid "    | GET  | JSON     | aid  收货地址id       | 删除成功并刷新页面           |

另外**新增收货地址**和**修改收货地址**需要在新的页面设置才显得清晰，分别在**addAddress.html**和**updateAddress.html**中展示

![](image\addresshtml.png)



**addAddress.html 新增收货地址**





![](image\snipaste20230504_170052.png)

![](image\addAddresshtml.png)



#### 特殊页面

**upload.html（上传头像功能）**

| 触发条件 + url                         | type | dataType | 传给后端参数 | 接收后端参数                                     |
| -------------------------------------- | ---- | -------- | ------------ | ------------------------------------------------ |
| 点击上传按钮<br />/users/change_avatar | POST | JSON     | 图片数据     | 将服务器端返回的头像地址设置到img标签的src属性上 |

![](image\snipaste20230316_231026.png)





### 控制层

#### UserController  

URL  ： users

| 方法                              | URL             | type | 形参列表                          | 返回类型             | 所用业务类.方法               |
| :-------------------------------- | --------------- | ---- | --------------------------------- | -------------------- | ----------------------------- |
| reg (注册)                        | reg             | POST | user对象                          | JsonResult(JSON类型) | userService.reg               |
| login (登录)                      | login           | POST | username，password，session       | JsonResult(JSON类型) | userService.login             |
| changePassword （修改密码）       | change_password | POST | oldPassword，newPassword，session | JsonResult(JSON类型) | userService.changePassword    |
| getInfoByUid （获取用户基本信息） | get_by_uid      | GET  | session                           | JsonResult(JSON类型) | userService.getInfoByUid      |
| changeInfo （更新用户基本信息）   | change_info     | POST | session， user对象                | JsonResult(JSON类型) | userService.changeInfo        |
| changeAvatar （头像上传功能）     | change_avatar   | POST | session， file(MultipartFile类型) | JsonResult(JSON类型) | userService.updateAvatarByUid |



#### ProductController

URL:  products

| 方法                                  | URL          | type | 形参列表 | 返回类型                  | 所用业务类.方法            |
| ------------------------------------- | ------------ | ---- | -------- | ------------------------- | -------------------------- |
| getHotList （获取热销排行前四名商品） | hot_list     | GET  | 无       | JsonResult<List<Product>> | productService.findHotList |
| findById （通过商品的id获取商品详情） | {id}/details | GET  | id       | JsonResult<Product>       | productService.findById    |



#### OrderController

URL：orders

| 方法                | URL    | type | 形参列表                           | 返回类型          | 所用业务类.方法     |
| ------------------- | ------ | ---- | ---------------------------------- | ----------------- | ------------------- |
| create （创建订单） | create | GET  | aid，cids (Integer[]类型)，session | JsonResult<Order> | orderService.create |



#### DistrictController

URL：districts

| 方法                                 | URL      | type | 形参列表 | 返回类型                   | 所用业务类.方法             |
| ------------------------------------ | -------- | ---- | -------- | -------------------------- | --------------------------- |
| getByParent （根据父区域获得子区域） | {"/",""} | Post | parent   | JsonResult<List<District>> | districtService.getByParent |



#### CartController

URL:  carts

| 方法                                                         | URL           | type | 形参列表                                | 返回类型                     | 所用业务类.方法            |
| ------------------------------------------------------------ | ------------- | ---- | --------------------------------------- | ---------------------------- | -------------------------- |
| addToCart （将商品添加到购物车）                             | add_to_cart   | GET  | pid，amount，session                    | JsonResult<Void>             | cartService.addToCart      |
| getVOByUid （查询某用户的购物车数据列表 --分页）             | {"", "/"}     | GET  | session，pageNum （表示显示第几页数据） | JsonResult<PageInfo<CartVO>> | cartService.getVOByUidPage |
| addNum （增加用户的购物车中某商品的数量）                    | {cid}/num/add | POST | cid，session                            | JsonResult<Integer>          | cartService.addNum         |
| getVOByCid （获取用户在购物车里所选中的(也就是想买的)购物车项的清单） | list          | GET  | cids（Integer[]类型），session          | JsonResult<List<CartVO>>     | cartService.getVOByCids    |



#### AddressController

URL：addresses

| 方法                                             | URL                        | type | 形参列表              | 返回类型                      | 所用业务类.方法               |
| ------------------------------------------------ | -------------------------- | ---- | --------------------- | ----------------------------- | ----------------------------- |
| addNewAddress （新增收货地址）                   | add_new_address            | POST | address，session      | JsonResult<Void>              | addressService.addNewAddress  |
| getByUidWithPage （获取用户所有收货地址 --分页） | {"", "/"}                  | GET  | session，pageNum      | JsonResult<PageInfo<Address>> | addressService.getAddressPage |
| getByUid （获取用户所有收货地址 --不分页）       | get_by_uid                 | GET  | session               | JsonResult<List<Address>>     | addressService.getByUid       |
| setDefault （设置默认地址）                      | {aid}/set_default          | GET  | aid，session          | JsonResult<Void>              | addressService.setDefault     |
| delete（删除地址）                               | delete/{aid}               | GET  | aid，session          | JsonResult<Void>              | addressService.delete         |
| updateAddress（修改地址）                        | update/{oldAddressId}      | POST | aid，address，session | JsonResult<Void>              | addressService.update         |
| findByAid (获取指定地址详细信息)                 | find_by_aid/{oldAddressId} | GET  | oldAddressId          | JsonResult<Address>           | addressService.findByAid      |



#### BaseController

控制层的基类 有三个方法**handleException**， **getUidFromSession**， **getUsernameFromSession** 和一个成功状态码属性**OK = 200**

handleException 异常处理方法

| 名称                                                         | 异常类型                    | 状态码 |
| ------------------------------------------------------------ | --------------------------- | ------ |
| 用户名被占用的异常                                           | UsernameDuplicatedException | 4000   |
| 用户没有被找到的异常                                         | UserNotFoundException       | 4001   |
| 用户名对应的密码错误,即密码匹配的异常                        | PasswordNotMatchException   | 4002   |
| 收货地址总数超出限制的异常(20条)                             | AddressCountLimitException  | 4003   |
| 收货地址数据不存在的异常                                     | AddressNotFoundException    | 4004   |
| 收货地址数据非法访问的异常                                   | AccessDeniedException       | 4005   |
| 访问的商品数据不存在的异常                                   | ProductNotFoundException    | 4006   |
| 购物车表不存在该商品的异常                                   | CartNotFoundException       | 4007   |
| 数据在插入过程中所产生的异常                                 | InsertException             | 5000   |
| 更新数据时产生的异常                                         | UpdateException             | 5001   |
| 删除数据时产生的异常                                         | DeleteException             | 5002   |
| 修改地址时产生的异常                                         | UpdateAddressException      | 5003   |
| 文件为空的异常(没有选择上传的文件就提交了表单,或选择的文件是0字节的空文件) | FileEmptyException          | 6000   |
| 上传的文件时文件大小超出限制的异常                           | FileSizeException           | 6001   |
| 上传的文件时文件类型异常                                     | FileTypeException           | 6002   |
| 文件状态异常(如上传文件时该文件正在打开状态)                 | FileStateException          | 6003   |
| 上传的文件时文件读写异常                                     | FileUploadIOException       | 6004   |



```java
/**
 * 1.@ExceptionHandler表示该方法用于统一处理捕获抛出的异常
 * 2.什么样的异常才会被这个方法处理呢?
 * 需要是ServiceException.class,这样的话只要是抛出ServiceException异常就会被拦截到handleException方法,
 * 此时handleException方法就是请求处理方法,返回值就是需要传递给前端的数据
 * 3.被ExceptionHandler修饰后如果项目发生异常,那么异常对象就会被自动传递给此方法的参数列表上,
     所以形参就需要写Throwable e用来接收异常对象
 */
@ExceptionHandler({ServiceException.class, FileUploadException.class})
public JsonResult<Void> handleException(Throwable e) {
    //创建响应结果对象即JsonResult对象
    JsonResult<Void> result = new JsonResult<>(e);
    if (e instanceof UsernameDuplicatedException) {
        result.setState(4000);
        result.setMessage("用户名已经被占用");
    } else if (e instanceof UserNotFoundException) {
        result.setState(4001);
        result.setMessage("用户数据不存在的异常");
    } ...
  }
    return result;
}
```



getUidFromSession  获取session对象中的uid

| 方法              | 形参列表 | 返回类型                        |
| ----------------- | -------- | ------------------------------- |
| getUidFromSession | session  | Integer (当前登录的用户uid的值) |



getUsernameFromSession 获取当前登录用户的username

| 方法                   | 形参列表 | 返回类型                      |
| ---------------------- | -------- | ----------------------------- |
| getUsernameFromSession | String   | String (当前登录用户的用户名) |







### 业务层

#### UserServiceImpl  

| 方法                                         | 形参列表                                | 返回类型 | 所用持久层类.方法                             |
| -------------------------------------------- | --------------------------------------- | -------- | --------------------------------------------- |
| reg (用户注册功能)                           | user对象                                | void     | userMapper.findByUsername， userMapper.insert |
| getUserById (判断用户是否存在)               | uid                                     | User     | userMapper.findByUid                          |
| login (用户登录功能)                         | username, password                      | User     | userMapper.findByUsername                     |
| changePassword (修改密码功能)                | uid, username, oldPassword, newPassword | void     | userMapper.updatePasswordByUid                |
| getInfoByUid （获取用户基本信息）            | uid                                     | User     | 无                                            |
| changeInfo （更新用户基本信息）              | uid， user对象                          | void     | userMapper.updateInfoByUid                    |
| updateAvatarByUid （更新用户的头像）         | uid，avatar，modifiedUser               | void     | userMapper.updateAvatarByUid                  |
| getMD5Password （定义一个md5算法的加密处理） | password， salt                         | String   | 无                                            |



#### ProductServiceImpl 

处理商品数据的业务层实现类

| 方法                                  | 形参列表 | 返回类型      | 所用持久层类.方法         |
| ------------------------------------- | -------- | ------------- | ------------------------- |
| findHotList (获取热销排行前四名商品)  | 无       | List<Product> | productMapper.findHotList |
| findById （通过商品的id获取商品详情） | id       | Product       | productMapper.findById    |



#### OrderServiceImpl

用户收货订单业务层接口的实现类

| 方法              | 形参列表                                                     | 返回类型 | 所用持久层类.方法                                            |
| ----------------- | ------------------------------------------------------------ | -------- | ------------------------------------------------------------ |
| create (创建订单) | aid              该订单的地址id <br />uid              该订单的用户id <br />username   该订单的用户名 <br />cids             该订单所买的商品所对应的购物车项的id (Integer[ ]) | Order    | cartService.getVOByCids<br/>addressService.getByAid<br/>orderMapper.insertOrder<br/>orderMapper.insertOrderItem |



#### DistrictServiceImpl

省市区的数据查询业务层接口的实现类

| 方法                                            | 形参列表          | 返回类型       | 所用持久层类.方法             |
| ----------------------------------------------- | ----------------- | -------------- | ----------------------------- |
| getByParent（根据父代号来查询区域信息(省市区)） | parent 父代号     | List<District> | districtMapper.findByParent   |
| getNameByCode （根据code值获取对应名字）        | code 自身的代码号 | String         | districtMapper.findNameByCode |



#### CartServiceImpl

购物车业务层接口的实现类

| 方法                                                         | 形参列表                                                     | 返回类型                                  | 所用持久层类.方法                                            |
| ------------------------------------------------------------ | ------------------------------------------------------------ | ----------------------------------------- | ------------------------------------------------------------ |
| addToCart （将商品添加到购物车）                             | uid 当前登录用户的id <br />pid 商品的id <br />amount 增加的数量<br />username 当前登录的用户名 | void                                      | cartMapper.findByUidAndPid<br />productMapper.findById<br />cartMapper.insert<br />cartMapper.updateNumByCid |
| getVOByUidPage <br />（查询某用户的购物车数据-分页）         | pageNum 表示显示第几页数据<br />uid 用户id                   | PageInfo<CartVO> 该用户的购物车数据的列表 | cartMapper.findVOByUid                                       |
| addNum （增加用户的购物车中某商品的数量）                    | cid 购物车数据id <br />uid 用户id<br />username 用户名       | Integer（增加成功后新的数量）             | cartMapper.findByCid<br />cartMapper.updateNumByCid          |
| getVOByCids <br />（查询指定cid的数据 这里对应<br/>前端页面中用户在购物车里所选中的(也就是想买的)购物车项的清单） | uid 用户id<br /> cids 所选中的购物车数据id                   | List<CartVO> （封装好的购物车订单列表）   | cartMapper.findVOByCids                                      |



#### AddressServiceImpl

用户收货地址业务层接口的实现类

| 方法                                                         | 形参列表                                                 | 返回类型                         | 所用持久层类.方法                                            |
| ------------------------------------------------------------ | -------------------------------------------------------- | -------------------------------- | ------------------------------------------------------------ |
| addNewAddress （添加新的收货地址）                           | address，uid，username                                   | void                             | addressMapper.countByUid<br />districtService.getNameByCode<br />addressMapper.insert |
| findByAid （通过aid找到对应地址）                            | aid                                                      | Address                          | addressMapper.findByAid                                      |
| getByUid <br />（根据用户的uid查询用户的收货地址数据）       | uid                                                      | List<Address> <br />收货地址数据 | addressMapper.findByUid                                      |
| getAddressPage<br />（查询用户收货地址的分页信息）           | pageNum，uid                                             | PageInfo<Address>                | addressMapper.findByUid                                      |
| setDefault <br />（修改某个用户的某条收货地<br />址数据为默认收货地址） | aid 收货地址 id<br/> uid 用户id<br/> username 修改执行人 | void                             | addressMapper.findByUid<br />addressMapper.updateNonDefault<br />addressMapper.updateDefaultByAid |
| delete<br />（删除用户选中的收货地址数据）                   | aid 收货地址id<br /> uid 用户id<br /> username 用户名    | void                             | addressMapper.findByUid<br />addressMapper.deleteByAid<br />addressMapper.countByUid<br />addressMapper.findLastModified<br />addressMapper.updateDefaultByAid |
| update（修改用户的收货地址）                                 | address                                                  | void                             | addressMapper.findByUid<br />addressMapper.updateAddressByAid |
| getByAid<br />（通过用户给定的地址，来<br />填充到收获订单里） | aid  地址id<br />uid  用户id                             | Address                          | addressMapper.findByUid                                      |



AddressServiceImpl特殊属性：

```java
/**
     * 为了方便日后修改最大收货地址数量,可以在配置文件
     * application.properties中定义user.address.max-count=20
     */
    //spring读取配置文件中数据:@Value("${user.address.max-count}")
@Autowired
private IDistrictService districtService;
```

```yaml
#Spring读取配置文件中的数据： @Value("${user.address.max-count}")
user.address.max-count=20
```





### 持久层

#### UserMapper

| 方法                                                | 形参列表                                  | 返回类型 |
| --------------------------------------------------- | ----------------------------------------- | -------- |
| insert （插入用户的数据）                           | user                                      | Integer  |
| findByUsername （根据用户名来查询用户的数据）       | username                                  | User     |
| updatePasswordByUid （根据用户的uid来修改用户密码） | uid，password，modifiedUser，modifiedTime | Integer  |
| findByUid （根据用户的id查询用户的数据）            | uid                                       | User     |
| updateInfoByUid （通过用户id来更新用户信息）        | user                                      | Integer  |
| updateAvatarByUid （根据用户uid修改用户的头像）     | uid，avatar，modifiedUser，modifiedTime   | Integer  |



#### ProductMapper

| 方法                                | 形参列表    | 返回类型                                             |
| ----------------------------------- | ----------- | ---------------------------------------------------- |
| findHotList (查询热销商品的前四名)  | 无          | List<Product>  热销商品前四名的集合                  |
| findById (通过商品的id获取商品详情) | id 商品的id | Product 匹配的商品详情，如果没有匹配的数据则返回null |



#### OrderMapper

| 方法                                       | 形参列表                         | 返回类型             |
| ------------------------------------------ | -------------------------------- | -------------------- |
| insertOrder (插入订单数据)                 | order 订单数据                   | Integer 受影响的行数 |
| insertOrderItem (插入某一个订单中商品数据) | orderItem 订单中商品数据  订单项 | Integer 受影响的行数 |



#### DistrictMapper

| 方法                                    | 形参列表          | 返回类型                                  |
| --------------------------------------- | ----------------- | ----------------------------------------- |
| findByParent (根据父代码号查询区域信息) | parent 父代码号   | List<District> 某个父区域下所有的区域列表 |
| findNameByCode (根据code值获取对应名字) | code 自身的代码号 | String 区域名                             |



#### CartMapper

| 方法                                                        | 形参列表                                                     | 返回类型                                                     |
| ----------------------------------------------------------- | ------------------------------------------------------------ | ------------------------------------------------------------ |
| insert (插入购物车数据)                                     | cart 购物车数据                                              | Integer 受影响的行数                                         |
| updateNumByCid<br /> (修改购物车数据中商品的数量)           | cid 购物车数据的id <br />num 新的数量 <br />modifiedUser 修改执行人 <br />modifiedTime 修改时间 | Integer 受影响的行数                                         |
| findByUidAndPid<br />(根据用户id和商品id查询购物车中的数据) | uid 用户id<br />pid 商品id                                   | Cart 匹配的购物车数据，<br />如果该用户的购物车中并没有该商品，则返回null |
| findVOByUid (查询某用户的购物车数据)                        | uid 用户id                                                   | List<CartVO><br /> 该用户的购物车与商品信息值对象数据的列表  |
| findByCid (通过cid获取某条购物数据信息)                     | cid 购物车数据id                                             | Cart 该条购物数据信息)                                       |
| findVOByCids (查询指定cid的数据)                            | cids 购物车数据id列表                                        | List<CartVO> 购物车与商品信息值对象列表                      |



#### AddressMapper

| 方法                                                         | 形参列表                                                     | 返回类型                                           |
| ------------------------------------------------------------ | ------------------------------------------------------------ | -------------------------------------------------- |
| insert <br />（插入用户的收货地址的数据）                    | address （收货地址数据）                                     | Integer 受影响的行数                               |
| countByUid <br />（根据用户的id来统计用户收货地址的数量）    | uid (用户id)                                                 | Integer 当前用户收获地址的总数                     |
| findByUid <br />（根据用户的uid查询用户的收货地址数据）      | uid (用户uid)                                                | List<Address> 收货地址数据                         |
| findByAid<br />（根据aid查询收货地址数据）                   | aid (收货地址aid)                                            | Address<br />收货地址数据,如果没有找到则返回null值 |
| updateNonDefault<br />(根据用户uid修改用户的收货地址统一设置为非默认) | uid (用户uid)                                                | Integer 受影响的行数                               |
| updateDefaultByAid<br />(将指定的收货地址修改为默认)         | aid (收货地址aid)<br />modifiedUser (修改人)<br/>modifiedTime (修改时间) | Integer 受影响的行数                               |
| deleteByAid<br />(将指定的收货地址删除)                      | aid (收货地址aid)                                            | Integer 受影响的行数                               |
| findLastModified<br />(根据用户uid查询用户最后一次被修改的收货地址数据) | uid (用户id)                                                 | Address 收货地址数据                               |
| updateAddressByAid<br />(将指定的收货地址修改)               | address (修改的地址)                                         | Integer 受影响的行数                               |





### SQL语句

#### UserMapper.xml

resultMap

```xml
<resultMap id="UserEntityMap" type="com.cy.store.entity.User">
        <id column="uid" property="uid"></id>
        <result column="is_delete" property="isDelete"></result>
        <result column="created_user" property="createdUser"></result>
        <result column="created_time" property="createdTime"></result>
        <result column="modified_user" property="modifiedUser"></result>
        <result column="modified_time" property="modifiedTime"></result>
    </resultMap>
```



| id                                                        | SQL                                                          |
| --------------------------------------------------------- | ------------------------------------------------------------ |
| insert （插入用户的数据）                                 | <insert id="insert" useGeneratedKeys="true" keyProperty="uid"><br/>        insert into t_user(<br/>                         username,`password`,salt,phone,email,gender,avatar,is_delete,<br/>                         created_user,created_time,modified_user,modified_time<br/>        ) values (<br/>                        #{username},#{password},#{salt},#{phone},#{email},#{gender},#{avatar},<br/>                        #{isDelete}, #{createdUser},#{createdTime},#{modifiedUser},#{modifiedTime}      <br />        )<br/></insert> |
| findByUsername <br />（根据用户名来查询用户的数据）       | <select id="findByUsername" resultMap="UserEntityMap"><br/>        select * from t_user where username = #{username}<br/></select> |
| updatePasswordByUid <br />（根据用户的uid来修改用户密码） | <update id="updatePasswordByUid"><br/>        update t_user set password = #{password},<br/>                                     modified_user = #{modifiedUser},<br/>                                     modified_time = #{modifiedTime}<br/>                              where uid = #{uid}<br/> </update> |
| findByUid <br />（根据用户的id查询用户的数据）            | <select id="findByUid" resultMap="UserEntityMap"><br/>        select * from t_user where uid = #{uid};<br/></select> |
| updateInfoByUid <br />（通过用户id来更新用户信息）        | <update id="updateInfoByUid"><br/>        update t_user set<br/>                   <if test="phone != null ">phone = #{phone},</if><br/>                   <if test="email != null ">email = #{email},</if><br/>                   <if test="gender != null ">gender = #{gender},</if><br/>                   modified_user = #{modifiedUser},<br/>                   modified_time = #{modifiedTime}<br/>              where uid = #{uid}<br/>    </update> |
| updateAvatarByUid <br />（根据用户uid修改用户的头像）     | <update id="updateAvatarByUid"><br/>        update t_user set avatar = #{avatar},<br/>                          modified_user = #{modifiedUser},<br/>                          modified_time = #{modifiedTime}<br/>                      where uid = #{uid}<br/></update> |





#### ProductMapper.xml

resultMap

```xml
<resultMap id="ProductEntityMap" type="com.cy.store.entity.Product">
    <id column="id" property="id"/>
    <result column="category_id" property="categoryId"/>
    <result column="item_type" property="itemType"/>
    <result column="sell_point" property="sellPoint"/>
    <result column="created_user" property="createdUser"/>
    <result column="created_time" property="createdTime"/>
    <result column="modified_user" property="modifiedUser"/>
    <result column="modified_time" property="modifiedTime"/>
</resultMap>
```

| id                                        | SQL                                                          |
| ----------------------------------------- | ------------------------------------------------------------ |
| findHotList <br />(查询热销商品的前四名)  | <select id="findHotList" resultMap="ProductEntityMap"><br/>    select * from t_product where status = 1 ORDER BY priority DESC LIMIT 0,4<br/></select> |
| findById <br />(通过商品的id获取商品详情) | <select id="findById" resultMap="ProductEntityMap"><br/>        select * from t_product where id = #{id};<br/></select> |



#### OrderMapper.xml

| id                                              | SQL                                                          |
| ----------------------------------------------- | ------------------------------------------------------------ |
| insertOrder<br />(插入订单数据)                 | <insert id="insertOrder" useGeneratedKeys="true" keyProperty="oid"><br/>    insert into t_order (<br/>            uid, recv_name, recv_phone, recv_province, recv_city, recv_area, recv_address,<br/>            total_price,status, order_time, pay_time, created_user, created_time, modified_user,<br/>            modified_time<br/>    ) values (<br/>            #{uid}, #{recvName}, #{recvPhone}, #{recvProvince}, #{recvCity}, #{recvArea},<br/>            #{recvAddress}, #{totalPrice}, #{status}, #{orderTime}, #{payTime}, #{createdUser},<br/>            #{createdTime}, #{modifiedUser}, #{modifiedTime}<br/>    )<br/></insert> |
| insertOrderItem<br />(插入某一个订单中商品数据) | <insert id="insertOrderItem" useGeneratedKeys="true" keyProperty="id"><br/>        insert into t_order_item (<br/>                oid, pid, title, image, price, num, created_user,<br/>                created_time, modified_user, modified_time<br/>        ) values (<br/>                #{oid}, #{pid}, #{title}, #{image}, #{price}, #{num}, #{createdUser},<br/>                #{createdTime}, #{modifiedUser}, #{modifiedTime}<br/>        )<br/>    </insert> |



#### DistrictMapper.xml

| id                                           | SQL                                                          |
| -------------------------------------------- | ------------------------------------------------------------ |
| findByParent<br />(根据父代码号查询区域信息) | <select id="findByParent" resultType="com.cy.store.entity.District"><br/>        SELECT * FROM t_dict_district WHERE parent = #{parent} ORDER BY `code` ASC<br/></select> |
| findNameByCode<br />(根据code值获取对应名字) | <select id="findNameByCode" resultType="java.lang.String"><br/>        select name from t_dict_district where `code` = #{code} ;<br/></select> |



#### CartMapper

resultMap

```xml
<resultMap id="CartEntityMap" type="com.cy.store.entity.Cart">
    <id column="cid" property="cid"/>
    <result column="created_user" property="createdUser"/>
    <result column="created_time" property="createdTime"/>
    <result column="modified_user" property="modifiedUser"/>
    <result column="modified_time" property="modifiedTime"/>
</resultMap>
```

| id                                                          | SQL                                                          |
| ----------------------------------------------------------- | ------------------------------------------------------------ |
| insert<br />(插入购物车数据)                                | <insert id="insert" useGeneratedKeys="true" keyProperty="cid"><br/>        insert into t_cart <br />                    (uid, pid, price, num, created_user, created_time,<br />                      modified_user, modified_time)<br />              values (#{uid}, #{pid}, #{price}, #{num}, #{createdUser}, #{createdTime},<br />                          #{modifiedUser}, #{modifiedTime}<br />                        )<br/></insert> |
| updateNumByCid<br />(修改购物车数据中商品的数量)            | <update id="updateNumByCid"><br/>        update t_cart set<br/>                     num=#{num},<br/>                    modified_user=#{modifiedUser},<br/>                    modified_time=#{modifiedTime}<br/>              where cid=#{cid}<br/></update> |
| findByUidAndPid<br />(根据用户id和商品id查询购物车中的数据) | <select id="findByUidAndPid" resultMap="CartEntityMap"><br/>        select * from t_cart where uid=#{uid} AND pid=#{pid}<br/></select> |
| findVOByUid<br />(查询某用户的购物车数据)                   | <select id="findVOByUid" resultType="com.cy.store.vo.CartVO"><br/>        select<br/>                     cid,<br/>                     uid,<br/>                     pid,<br/>                     t_cart.price,<br/>                     t_cart.num,<br/>                     title,<br/>                     t_product.price as realPrice,<br/>                     image<br/>            from<br/>                        t_cart left join t_product<br/>                                   on t_cart.pid = t_product.id<br/>            where<br/>    	              uid = #{uid}<br/>            order by<br/>    	             t_cart.created_time desc<br/>    </select> |
| findByCid<br />(通过cid获取某条购物数据信息)                | <select id="findByCid" resultMap="CartEntityMap"><br/>        select * from t_cart where cid=#{cid}<br/></select> |
| findVOByCids<br />(查询指定cid的数据)                       | <select id="findVOByCids" resultType="com.cy.store.vo.CartVO"><br/>        select<br/>                cid,<br/>                uid,<br/>                pid,<br/>                t_cart.price,<br/>                t_cart.num,<br/>                title,<br/>                t_product.price as realPrice,<br/>                image<br/>            from<br/>                t_cart left join t_product<br/>                        on t_cart.pid = t_product.id<br/>            where<br/>    	        cid in (<br/>    	         <foreach collection="array" item="cid" separator=","><br/>                            #{cid}<br/>                        </foreach><br/>    	        )<br/>            order by<br/>    	        t_cart.created_time desc<br/></select> |





#### AddressMapper

resultMap

```xml
<resultMap id="AddressEntityMap" type="com.cy.store.entity.Address">
    <id column="aid" property="aid"/>
    <result column="province_name" property="provinceName"/>
    <result column="province_code" property="provinceCode"/>
    <result column="city_name" property="cityName"/>
    <result column="city_code" property="cityCode"/>
    <result column="area_name" property="areaName"/>
    <result column="area_code" property="areaCode"/>
    <result column="is_default" property="isDefault"/>
    <result column="created_user" property="createdUser"/>
    <result column="created_time" property="createdTime"/>
    <result column="modified_user" property="modifiedUser"/>
    <result column="modified_time" property="modifiedTime"/>
</resultMap>
```

| id                                                           | SQL                                                          |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| insert<br />(插入用户的收货地址的数据)                       | <insert id="insert" useGeneratedKeys="true" keyProperty="aid"><br/>        INSERT INTO t_address (<br/>                   uid, `name`, province_name, province_code, city_name, <br/>                   city_code, area_name, area_code, zip,address, phone,<br/>                   tel,tag, is_default, created_user, created_time, <br/>                   modified_user, modified_time<br/>       		 ) VALUES (<br/>                   #{uid}, #{name}, #{provinceName}, #{provinceCode}, <br/>                   #{cityName}, #{cityCode}, #{areaName}, #{areaCode}, <br/>                   #{zip}, #{address}, #{phone}, #{tel}, #{tag}, <br/>                  #{isDefault}, #{createdUser}, #{createdTime}, <br/>                   #{modifiedUser}, #{modifiedTime}<br/>        )<br/></insert> |
| countByUid<br />(根据用户的id来统计用户收货地址的数量)       | <select id="countByUid" resultType="java.lang.Integer"><br/>        select count(*) from t_address where uid = #{uid}<br/>    </select> |
| findByUid<br />(根据用户的uid查询用户的收货地址数据)         | <select id="findByUid" resultMap="AddressEntityMap"><br/>        select * from t_address where uid = #{uid}<br/>                                order by is_default DESC, created_time DESC<br/>    </select> |
| findByAid<br />(根据aid查询收货地址数据)                     | <select id="findByAid" resultMap="AddressEntityMap"><br/>    select * from t_address where aid=#{aid}<br/>    </select> |
| updateNonDefault<br />(根据用户uid修改用户的收货<br />地址统一设置为非默认) | <update id="updateNonDefault"><br/>    update t_address set is_default=0 where uid=#{uid}<br/>    </update> |
| updateDefaultByAid<br />(将指定的收货地址修改为默认)         | <update id="updateDefaultByAid"><br/>    update t_address set is_default=1,<br/>                        modified_user=#{modifiedUser},<br/>                        modified_time=#{modifiedTime}<br/>                    where aid=#{aid}<br/>    </update> |
| deleteByAid<br />(将指定的收货地址删除)                      | <delete id="deleteByAid" ><br/>        delete from t_address where aid = #{aid}<br/>    </delete> |
| findLastModified<br />(根据用户uid查询用户最后一<br />次被修改的收货地址数据) | <select id="findLastModified" resultMap="AddressEntityMap"><br/>        select * from t_address where uid = #{uid}<br/>                                order by modified_time DESC limit 0,1<br/>    </select> |
| updateAddressByAid<br />(将指定的收货地址修改)               | <update id="updateAddressByAid"><br/>        update t_address set<br/>              <if test="name != null">`name`=#{name},</if><br/>              <if test="provinceName != null">province_name=#{provinceName},</if><br/>              <if test="provinceCode != null">province_code=#{provinceCode},</if><br/>              <if test="cityName != null">city_name=#{cityName},</if><br/>              <if test="cityCode != null">city_code=#{cityCode},</if><br/>              <if test="areaName != null">area_name=#{areaName},</if><br/>              <if test="areaCode != null">area_code=#{areaCode},</if><br/>              <if test="zip != null">zip=#{zip},</if><br/>              <if test="address != null">address=#{address},</if><br/>              <if test="phone != null">phone=#{phone},</if><br/>              <if test="tel != null">tel=#{tel},</if><br/>              <if test="tag != null">tag=#{tag},</if><br/>              modified_user=#{modifiedUser},<br/>              modified_time=#{modifiedTime}<br/>         where aid=#{aid}<br/>    </update> |





