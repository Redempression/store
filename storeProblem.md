https://blog.csdn.net/maxiangyu_/category_11772319.html

## 1、测试时的异常：(IllegalStateException)

```java
java.lang.IllegalStateException: Failed to load ApplicationContext

Caused by: org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'userMapper' defined in file [F:\Study\项目\电商\手撕\store\target\classes\com\cy\store\mapper\UserMapper.class]: Invocation of init method failed; nested exception is java.lang.IllegalArgumentException: Property 'sqlSessionFactory' or 'sqlSessionTemplate' are required

Caused by: java.lang.IllegalArgumentException: Property 'sqlSessionFactory' or 'sqlSessionTemplate' are required
```



原因：

​	可能是 spring整合mybatis 3.0.0和其他的配置之间不匹配

解决：

```xml
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>3.0.0</version>
</dependency>
```

改成

```xml
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>2.1.4</version>
</dependency>
```

成功：

![](image\snipaste20230301_220011.png)







## 2、关于自定义异常在接口开发中继承RuntimeException的使用总结

    在接口开发的过程中，为了程序的健壮性，我们经常考虑到代码执行的异常，并给前端一个友好的展示，这里就用到自定义异常，继承RuntimeException类。这个RuntimeException和普通的Exception有什么区别呢？

（1）Exception：非运行时异常，在项目运行之前必须处理掉。一般由程序员 try…catch掉。

（2）RuntimeException：运行时异常，在项目运行之后出错则直接终止运行，异常由JVM虚拟机处理。
    在接口的逻辑判断出现异常时，可能会影响后面的代码。或者说绝不容忍（允许）该代码块出错，那么我们就用RuntimeException，但是我们又不能因为系统挂掉，只能在后台抛出异常而不给前端返回友好的提示吧，至少给前端返回出现异常的原因吧。因此接口的自定义异常作用就体现了出来。



## 3、关于请求方式用post还是get

为了保证信息的安全性，注册、登录等操作通常都会使用POST请求，GET请求一般用来获取信息

1. 根据HTTP规范，GET用于信息获取。

GET请求的数据会附在URL之后（就是把数据放置在HTTP协议头中），以?分割URL和传输数据，参数之间以&相连，

如：login.action?

GET方式提交的数据最多只能是1024字节

2.根据HTTP规范，POST表示可能修改变服务器上的资源的请求。

POST把提交的数据则放置在是HTTP包的包体中。

理论上POST没有限制，可传较大量的数据

POST的安全性要比GET的安全性高。

总的来说：Get是向服务器发索取数据的一种请求，而Post是向服务器提交数据的一种请求

注：

1、对于处理指定请求方式的控制器方法，SpringMVC中提供了@RequestMapping的派生注解

处理get请求的映射-->@GetMapping

处理post请求的映射-->@PostMapping

处理put请求的映射-->@PutMapping

处理delete请求的映射-->@DeleteMapping

2、常用的请求方式有get，post，put，delete

在保证相同请求地址的情况下，可用不同的请求方式来表示不同的功能，这时我们习惯于用

get表示用于查询用户信息的请求方式；

post表示用于新增用户信息的请求方式；

put表示用于修改用户信息的请求方式；

delete表示用于删除用户信息的请求方式。

但是目前浏览器只支持get和post，若在form表单提交时，为method设置了其他请求方式的字符串（put或delete），则按照默认的请求方式get处理

若要发送put和delete请求，则需要通过spring提供的过滤器HiddenHttpMethodFilter，在第七章RESTful部分会讲到

3、题外话，post才有请求体，get是没有请求体的。原因：

首先请求体里保存的是数据，get会把数据拼接到请求地址后，所以不需要请求体；但是post不会拼接，post会把数据存放在请求体中，所以post有请求体







## 4、前端ajax传给后端明明是两个属性数据，为什么后端User对象可以直接接收？

这归结于 SpringBoot 约定大于配置的开发思想来完成的，省略了大量的配置甚至注解的编写

两种接收数据方式：

1、请求处理方法的参数列表设置为pojo类型来接收前端数据，SpringBoot会将前端url地址中的参数名和pojo类的属性名进行比较，如果这两个名称相同，则将值注入到pojo类中对应的属性上。  如下面reg方法

2、请求处理方法的参数列表设置为非pojo类型来接收前端数据，SpringBoot会直接将请求的参数名和方法的参数名进行比较，如果这两个名称相同，则自动完成值的依赖注入。 如下面login方法



```java
    /**
     * 注册
     */
    @RequestMapping("reg")
    //@ResponseBody //表示此方法的响应结果以json格式进行数据的响应给到前端
	/**
     * 由于前端通过ajax 的 data: $("#form-reg").serialize(),获取的数据
     * 如username=Tom&password=123会传入进User对象对应的username和password
     * 因为容器里有User的对象，所以从前端传过来的数据是直接找同名对象进行传参
     * */
    public JsonResult<Void> reg(User user) {
        userService.reg(user);
        return new JsonResult<>(OK);
    }


    /**
     * 登录
     */
    @RequestMapping("login")
    public JsonResult<User> login(String username, String password) {
        User data = userService.login(username, password);
        return new JsonResult<>(OK, data);
    }
```



```html
<!--注册d-->
<script type="text/javascript">
			//1.监听注册按钮是否被点击,如果被点击可以执行一个方法
    		//(这里不能像ajax函数那样删去function()只留下{},这是官方规定的!)
			$("#btn-reg").click(function () {

				//let username = $("#username").val();
				//let pwd = $("#password").val();
				//上面这两行是动态获取表单中控件的数据,但是如果这样获取的话ajax函数中
				//就是data: "username="+username + "&password="+pwd,但太麻烦了,如
				// 果这个表单提交的是用户的兴趣爱好,那数据就很多了,一个表单20个数据都很正
				// 常,如果此时还用这种方式就太麻烦了,所以不建议用这种方式

				//2.发送ajax()的异步请求来完成用户的注册功能

				// console.log($("#form-reg").serialize())
				$.ajax({
					url: "/users/reg",
					type: "POST",

					//serialize这个API会自动检测该表单有什么控件,每个控件检测后还会获取每个控
					// 件的值,拿到这个值后并自动拼接成形如username=Tom&password=123的结构
					data: $("#form-reg").serialize(),

					dataType: "JSON",
					success: function (json) {
						//1.js是弱数据类型,这个地方不用声明json的数据类型
						//2.如果服务器成功响应就会将返回的数据传给形参,比如{state: 4000,message: "用户名
						// 已经被占用",data: null}
						if (json.state == 200) {
							alert("注册成功")
						} else {
							alert("注册失败")
						}
					},
					error: function (xhr) {
						//如果问题不在可控范围内,服务器就不会返回自己定
						//义的json字符串:{state: 4000,message: "用户名已经被占用",data: null}
						//而是返回一个XHR类型的对象,该对象也有一个状态码名字是status
						alert("注册时产生未知的错误!" + xhr.status);
					}
				});
			});
		</script>
```







## 5编写修改个人资料功能时点击性别修改无效原因

https://zhuanlan.zhihu.com/p/87509607

SQL语句中 if 标签 意思为不为空且不为空字符串，而Interger字段的参数传0，在mybatis会将其转化为空字符串，会被当成 ’ ’，类似相同问题的属性还有Float型，Double型。最本质的问题还是mybatis底层运用OGNL表达式获取值的，OGNL表达式对空字符串的解析。

所以这就为什么性别女改成男可以，男改成女不行。(男设定为 1  女设定为 0 )

```xml
<!--Integer updateInfoByUid (User user);-->
<update id="updateInfoByUid">
    update t_user set
                      <if test="phone != null and phone != '' ">phone = #{phone},</if>
                      <if test="email != null and email != '' ">email = #{email},</if>
                      <if test="gender != null and gender != ''">gender = #{gender},</if>
                      modified_user = #{modifiedUser},
                      modified_time = #{modifiedTime}
                  where uid = #{uid}
</update>
```





修改后：

```xml
<!--Integer updateInfoByUid (User user);-->
<update id="updateInfoByUid">
    update t_user set
                      <if test="phone != null ">phone = #{phone},</if>
                      <if test="email != null ">email = #{email},</if>
                      <if test="gender != null ">gender = #{gender},</if>
                      modified_user = #{modifiedUser},
                      modified_time = #{modifiedTime}
                  where uid = #{uid}
</update>
```







## 6、对于用户地址的列表展示以及分页进行vue+mybatis方式实现



### 传统方式：

html页面：

![](image\snipaste20230312_165219.png)



js:

![](image\snipaste20230312_162940.png)

过于复杂，而且没有考虑分页。



### vue+mybatis分页插件方式：

html:

<img src="image\snipaste20230312_154823.png"/>



js:

![](image\snipaste20230312_165110.png)



> 在html中自定义属性，然后通过vue获取该属性以及其值的方式
>
> https://www.jb51.net/article/253388.htm
>
>  data-radius语法： data-属性
>
> 页面里：
>
>  ```html
>  <div @mouseover='changeRadius($event)' @mouseout='changeRadius(false)' @click='setlocation($event)'>
>            <div data-radius='100'>100m</div>
>            <div data-radius='300'>300m</div>
>            <div data-radius='500'>500m</div>
>            <div data-radius='1000'>1000m</div>
>  </div>
>  ```
>
> 获取属性的值：ev.target.dataset.radius
>
> vue的methods:
>
> ```js
> setlocation:function(ev){
>     this.dispradius = ev.target.dataset.radius + '米'
> }
> ```
>
> 注意属性名无论是大小写在vue里一律用小写表示
>
> 该方式在首页，上一页，下一页按钮中有所体现：
>
> 如在首页中：注意data-pageNums的n是大写
>
> ```html
> <input v-if="ress.hasPreviousPage" type="button" v-bind:data-pageNums="1" @click="showpage" value="首页"/>
> ```
>
> vue中引用：pagenums的n需用小写不然找不到
>
> ```js
> showpage:function (ve){
> 
>    var gta = ve.target.dataset.pagenums;
>    axios({
>       method:"GET",
>       url:"/addresses",
>       params:{
>          pageNum:gta
>       },
>       dataType: JSON
>    })
>     ...
> ```
>
> 这样做就可以通过前端页面中自定义属性及值传到vue中，再通过vue传递到后端了



当然使用分页插件还需注意：

配置分页插件 pagehelper

```xml
<!--分页插件 pagehelper -->
<dependency>
    <groupId>com.github.pagehelper</groupId>
    <artifactId>pagehelper-spring-boot-starter</artifactId>
    <version>1.0.0</version>
</dependency>
```

控制层：

![](image\snipaste20230312_170437.png)

service层：

![](image\snipaste20230312_170557.png)



![](image\snipaste20230312_170639.png)



持久层：

![](image\snipaste20230312_170746.png)



![](image\snipaste20230312_170827.png)





## 7、浏览器会自动添加display：none属性的解决方法

https://blog.csdn.net/m0_53246386/article/details/112393703

将class名修改一下：

```html
<td><a class="btn btn-xs add-del btn-info"><span class="fa fa-trash-o"></span> 删除</a></td>
<td><a class="btn btn-xs add-def btn-default"> 设为默认</a></td>
```

改成：

```html
<td><a class="btn btn-xs ddd-del btn-info"><span class="fa fa-trash-o"></span> 删除</a></td>
<td><a class="btn btn-xs ddd-def btn-default"> 设为默认</a></td>
```





## 8、vue中动态拼接src路径

```html
<!--原路径-->
<img src="../images/portal/00GuangBo1040A5GBR0731/collect.png" class="img-responsive" /
```



```html
<!--
	vue动态拼接
	products.image类似于 /images/portal/00GuangBo1040A5GBR0731/ 是一个动态值(通过外层循环获得不同的products.image，这里没写)
-->
<img v-bind:src="'..'+products.image+'collect.png'" class="img-responsive" />
```

 

> 参考：
>
> https://www.jb51.net/article/244425.htm





## 9、关于使用vue的循环语句v-for 引发的错误  Error compiling template: Cannot use v-for on stateful component root element because it renders multiple elements.

![](image\snipaste20230314_183546.png)



错误代码：

```html
<div class="panel-heading">
	<p class="panel-title">热销排行</p>
</div>
<div id="hot-list" v-for="products in ress" class="panel-body panel-item">
      <div class="col-md-12">
         <div class="col-md-7 text-row-2"><a href="product.html">{{products.title}}</a></div>
         <div class="col-md-2">{{products.price}}</div>
         <div class="col-md-3"><img v-bind:src="'..'+products.image+'collect.png'" class="img-responsive" /></div>
      </div>
</div>
```



错误原因:

由于相关vue代码的el引用了 id="hot-list" 致使该id所在的标签应为最大的父标签，其他与这个vue代码相关的动作应该在其内部完成

```js
//部分代码如下：
var vue = new Vue({
   el:"#hot-list",
   data:{
      ress:{}
   },
   methods: ......
```



解决：

```html
<div class="panel-heading">
   <p class="panel-title">热销排行</p>
</div>
<div id="hot-list" class="panel-body panel-item">
   <div v-for="products in ress">
      <div class="col-md-12">
         <div class="col-md-7 text-row-2"><a href="product.html">{{products.title}}</a></div>
         <div class="col-md-2">{{products.price}}</div>
         <div class="col-md-3"><img v-bind:src="'..'+products.image+'collect.png'" class="img-responsive" /></div>
      </div>
   </div>
</div>
```



页面显示：

![](image\snipaste20230314_184229.png)



> 附：具体热销排行前端代码
>
> ```html
> <!--html-->
> <div class="panel-heading">
>    <p class="panel-title">热销排行</p>
> </div>
> <div id="hot-list" class="panel-body panel-item">
>    <div v-for="products in ress">
>       <div class="col-md-12">
>          <div class="col-md-7 text-row-2"><a href="product.html">{{products.title}}</a></div>
>          <div class="col-md-2">{{products.price}}</div>
>          <div class="col-md-3"><img v-bind:src="'..'+products.image+'collect.png'" class="img-responsive" /></div>
>       </div>
>    </div>
> </div>
> ```
>
> ```js
> //JS
> <script type="text/javascript">
>    $(document).ready(function () {
>       showHotProductList();
>    });
> 
>    function showHotProductList(){
>       var vue = new Vue({
>          el:"#hot-list",
>          data:{
>             ress:{}
>          },
>          methods:{
>             getHotList:function (){
>                axios({
>                   method:"GET",
>                   url:"/products/hot_list",
>                   dataType: JSON
>                })
>                      .then(function (value) {
>                         if (value.data.state == 200){
>                            var ress = value.data.data;
>                            vue.ress = ress;
>                         }else {
>                            alert("收获热销商品排行失败")
>                         }
>                      })
>                      .catch(function (reason) { })
>             },
>          },
> 
>          /*数据装载之后自动调用*/
>          mounted:function (){
>             this.getHotList();
>          }
>       });
>    }
> </script>
> ```





## 10、个人添加功能--修改收货地址功能

### 代码：

#### 后端:

##### 1、持久层：

###### 1.1规划需要执行的SQL语句

```sql
#通过获取到的收货地址id拿到整个address信息
select * from t_address where aid=#{aid}

#通过获取到的收货地址id修改整个地址并通过if语句进行智能修改，如下映射文件
```



###### 1.2设计接口和[抽象方法

```java
public interface AddressMapper {
   
    /**
     * 根据aid查询收货地址数据
     * @param aid 收货地址aid
     * @return 收货地址数据,如果没有找到则返回null值
     */
    Address findByAid(Integer aid);


    /**
     * 将指定的收货地址修改
     * @param address 修改的地址
     * @return 受影响的行数
     */
    Integer updateAddressByAid(Address address);
}
```



###### 1.3编写映射

```xml
<mapper namespace="com.cy.store.mapper.AddressMapper">

    <!--映射规则-->
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

    <!--Address findByAid(Integer aid);-->
    <select id="findByAid" resultMap="AddressEntityMap">
    select * from t_address where aid=#{aid}

    </select>

    <!--Integer updateAddressByAid(Address address);-->
    <update id="updateAddressByAid">
        update t_address set
                            <if test="name != null">`name`=#{name},</if>
                            <if test="provinceName != null">province_name=#{provinceName},</if>
                            <if test="provinceCode != null">province_code=#{provinceCode},</if>
                            <if test="cityName != null">city_name=#{cityName},</if>
                            <if test="cityCode != null">city_code=#{cityCode},</if>
                            <if test="areaName != null">area_name=#{areaName},</if>
                            <if test="areaCode != null">area_code=#{areaCode},</if>
                            <if test="zip != null">zip=#{zip},</if>
                            <if test="address != null">address=#{address},</if>
                            <if test="phone != null">phone=#{phone},</if>
                            <if test="tel != null">tel=#{tel},</if>
                            <if test="tag != null">tag=#{tag},</if>
                            modified_user=#{modifiedUser},
                            modified_time=#{modifiedTime}
                    where aid=#{aid}
    </update>
</mapper>
```



##### 2、业务层：

###### 2.1规划异常

①如果收货地址不存在抛AddressNotFoundException

> ```java
> /** 收货地址数据不存在的异常 */
> public class AddressNotFoundException extends ServiceException {
>     /**重写ServiceException的所有构造方法*/
> }
> ```

②判断地址数据归属是否正确抛AccessDeniedException

> ```java
> /** 判断地址数据归属是否正确的异常 */
> public class AccessDeniedException extends ServiceException {
>     /**重写ServiceException的所有构造方法*/
> }
> ```

③修改数据时产生未知的异常抛UpdateAddressException

> ```java
> /** 修改数据时产生未知的异常 */
> public class UpdateAddressException extends ServiceException {
>     /**重写ServiceException的所有构造方法*/
> }
> ```



###### 2.2设计接口和抽象方法及实现

```java
public interface IAddressService {
    
    Address findByAid(Integer aid);

    /**
     * 修改用户的收货地址
     * @param address
     */
    void update(Address address);

}
```

```java
@Service
public class AddressServiceImpl implements IAddressService {

    @Autowired
    private AddressMapper addressMapper;
    
    

    @Override
    public Address findByAid(Integer aid) {
        Address address = addressMapper.findByAid(aid);
        if(address == null){
            throw new AddressNotFoundException("收货地址不存在");
        }

        return address;

    }

    @Override
    public void update(Address address) {
        //1.查询该条地址数据是否存在
        Address result = findByAid(address.getAid());
        //2.判断地址数据归属是否正确
        if (!result.getUid().equals(address.getUid())) {
            throw new AccessDeniedException("非法数据访问");
        }
        //3.修改地址数据
        Integer rows = addressMapper.updateAddressByAid(address);
        if (rows != 1) {
            throw new UpdateAddressException("修改数据时产生未知的异常");
        }
    }

}
```



##### 3、控制层：

###### 3.1处理异常在BaseController中

```java
else if (e instanceof AddressNotFoundException) {
    result.setState(4004);
    result.setMessage("用户的收货地址数据不存在的异常");
} else if (e instanceof AccessDeniedException) {
    result.setState(4005);
    result.setMessage("收货地址数据非法访问的异常");
} else if (e instanceof UpdateAddressException) {
            result.setState(5003);
            result.setMessage("修改数据时产生未知的异常");
} 
```



###### 3.2 设计请求

- /addresses/find_by_aid/{oldAddressId}
- Integer oldAddressId
- GET
- JsonResult<Address>

```java
/**
 * 获取指定地址详细信息
 * @param oldAddressId
 * @return
 */
@GetMapping("find_by_aid/{oldAddressId}")
public JsonResult<Address> findByAid(@PathVariable("oldAddressId") Integer oldAddressId){

    return new JsonResult<>(OK,addressService.findByAid(oldAddressId));
}
```



- /address/update/{oldAddressId}
- Integer aId,     HttpSession session,    Address address
- POST
- JsonResult<Void>

```java
/**
 * 修改地址
 * /address/update/{oldAddressId}
 * JsonResult<Void>
 * POST
 * Address address
 */
@PostMapping("update/{oldAddressId}")
public JsonResult<Void> updateAddress(@PathVariable("oldAddressId") Integer aid,
                                      Address address, HttpSession session){
    address.setAid(aid);
    address.setUid(getUidFromSession(session));
    address.setModifiedUser(getUsernameFromSession(session));

    addressService.update(address);
    return new JsonResult<>(OK);
}
```





#### 前端：

首先通过**addre.html**页面的修改按钮进入**updateAddress.html**  

> 注意跳转页面的方式：v-bind:href="'updateAddress.html?oldAddressId='+address.aid"
>
> 通过vue语法进行拼接url，所传的参数正是所点击该收货地址的aid

```html
<td><a v-bind:href="'updateAddress.html?oldAddressId='+address.aid"  class="btn btn-xs btn-info">
    <span class="fa fa-edit"></span> 修改</a>
</td>
```

![](image\snipaste20230315_190040.png)





其次由于将地址aid发送给修改页面后，修改页需要从url中裁取获得该aid,**实现方法在jquery-getUrlParam.js中**

```html
<script type="text/javascript" src="../js/jquery-getUrlParam.js"></script>
```

(目前怎么实现裁取可以先不学),所以需要在product.html页面中导入该js文件,这里我在body标签内部的最后引入该js文件

##### 具体前端代码：

###### html:

![](image\snipaste20230315_190944.png)

###### js:

```js
<script type="text/javascript" src="../js/jquery-getUrlParam.js"></script>
<script type="text/javascript">
    //调用jquery-getUrlParam.js文件的getUrlParam方法获取地址aid
    var oldAddressId = $.getUrlParam("oldAddressId");
    console.log("oldAddressId=" + oldAddressId);

    /**因为清空后下拉列表的select标签没有option标签,所以需要设置一个默认的option标
     * 签并给市,县加上该标签.option标签并不会把内容发送到后端,而是将value值发
     * 送给后端,所以用value表示当前这个区域的code值
     * */
    var defaultOption = "<option value='0'>-----请选择-----</option>";


    var oldProvinceCode;
    var oldCityCode;
    var oldAreaCode;

    $(document).ready(function () {


        //加载省的数据罗列时代码量较多,建议定义在外部方法中,然后在这里调用定义的方法
        showFindByAid();

        //将省,市,县的下拉列表内容设为"-----请选择-----"
        /**
         * select标签默认获取第一个option的内容填充到下拉列表中,所以即使加载
         * 页面时省区域的下拉列表中已经有了所有省但仍然会显示-----请选择-----
         * */
        $("#old-province-list").append(defaultOption);

        $("#old-city-list").append(defaultOption);
        $("#old-area-list").append(defaultOption);
    });

    function showFindByAid() {
        $.ajax({
            url: "/addresses/find_by_aid/" + oldAddressId,
            type: "GET",
            dataType: "JSON",
            success: function (json) {
                if (json.state == 200) {
                    console.log("name=" + json.data.name);
                    //html()方法:
                    // 假设有个标签<div id="a"></div>
                    //那么$("#a").html(<p></p>)就是给该div标签加p标签
                    //$("#a").html("我爱中国")就是给该div标签填充"我爱中国"内容
                    //$("#a").val(data) 是针对input框value赋值的问题
                    $("#old-name").val(json.data.name);
                    oldProvinceCode = json.data.provinceCode;
                    oldCityCode = json.data.cityCode;
                    oldAreaCode = json.data.areaCode;
                    $("#old-address").val(json.data.address);
                    $("#old-zip").val(json.data.zip);
                    $("#old-tag").val(json.data.tag);
                    $("#old-phone").val(json.data.phone);
                    $("#old-tel").val(json.data.tel);
                    showProvinceList();
                    showCityList();
                    showAreaList();
                } else if (json.state == 4004) { // 用户的收货地址数据不存在的异常
                    alert("用户的收货地址数据不存在的异常");
                    location.href = "address.html";
                } else if (json.state == 4005) { // 收货地址数据非法访问的异常
                    alert("收货地址数据非法访问的异常");
                    location.href = "address.html";
                } else {
                    alert("获取该收获地址信息失败！" + json.message);
                }
            },
        });
    }

    //省的下拉列表数据展示
    function showProvinceList() {
        console.log(oldProvinceCode);
        $.ajax({
            url: "/districts",//发送请求用于获取所有省对象
            type: "POST",
            data: "parent=86",
            dataType: "JSON",
            success: function (json) {
                if (json.state == 200) {
                    var list = json.data;//获取所有省对象的List集合
                    for (var i = 0; i < list.length; i++) {
                        if (list[i].code == oldProvinceCode) {
                            var opt =
                                "<option value='" + list[i].code + "' selected='selected'>" + list[i].name + 
                                "</option>";
                        } else {
                            opt =
                                "<option value='" + list[i].code + "'>" + list[i].name + "</option>";
                        }

                        $("#old-province-list").append(opt);
                    }
                } else {
                    <!--这个其实永远不会执行,因为没有编写
                    异常,控制层返回的状态码永远是OK-->
                    alert("省/直辖区的信息加载失败")
                }
            }
            //这里没有写属性error,不知道为啥不用写,感觉写了更好
        });
    }


    function showCityList() {
        console.log(oldProvinceCode);
        var parent = oldProvinceCode;
        $.ajax({
            url: "/districts",
            type: "POST",
            data: "parent=" + oldProvinceCode,
            dataType: "JSON",
            success: function (json) {
                if (json.state == 200) {
                    var list = json.data;
                    for (var i = 0; i < list.length; i++) {
                        if (list[i].code == oldCityCode) {
                            var opt =
                                "<option value='" + list[i].code + "' selected='selected'>" + list[i].name +
                                "</option>";
                        } else {
                            opt =
                                "<option value='" + list[i].code + "'>" + list[i].name + "</option>";
                        }
                        $("#old-city-list").append(opt);
                    }
                } else {
                    alert("市的信息加载失败")
                }
            }
        });

    }


    function showAreaList() {
        var parent = oldCityCode;
        $.ajax({
            url: "/districts",
            type: "POST",
            data: "parent=" + oldCityCode,
            dataType: "JSON",
            success: function (json) {
                if (json.state == 200) {
                    var list = json.data;
                    for (var i = 0; i < list.length; i++) {
                        if (list[i].code == oldAreaCode) {
                            var opt =
                                "<option value='" + list[i].code + "' selected='selected'>" + list[i].name +
                                "</option>";
                        } else {
                            opt =
                                "<option value='" + list[i].code + "'>" + list[i].name + "</option>";
                        }
                        $("#old-area-list").append(opt);
                    }
                } else {
                    alert("县的信息加载失败")
                }
            }
        });
    }

    /**
     * change()函数用于监听某个控件是否发生改变,一旦发生改变就
     * 会触发参数形式的函数,所以参数需要是function(){}
     * */
    $("#old-province-list").change(function () {
        //先获取到省区域父代码号
        var parent = $("#old-province-list").val();

        /**
         * 如果我选择了河南省洛阳市涧西区,然后又选择了河北省,此时需要
         * 将市,县下拉列表的所有option清除并显示内容-----请选择-----
         * empty()表示某标签的所有子标签(针对此页面来说select的子标
         * 签只有option)
         * */
        $("#old-city-list").empty();
        $("#old-area-list").empty();
        //填充默认值:-----请选择-----
        $("#old-city-list").append(defaultOption);
        $("#old-area-list").append(defaultOption);

        if (parent == 0) {//如果继续程序,后面的ajax接收的json数据中的data是
            return;//空集合[],进不了for循环,没有任何意义,所以直接在这里终止程序
        }
        $.ajax({
            url: "/districts",
            type: "POST",
            data: "parent=" + parent,
            dataType: "JSON",
            success: function (json) {
                if (json.state == 200) {
                    var list = json.data;
                    for (var i = 0; i < list.length; i++) {
                        if (list[i].code == oldCityCode) {
                            var opt =
                                "<option value='" + list[i].code + "' selected='selected'>" + list[i].name + 
                                "</option>";
                        } else {
                            opt =
                                "<option value='" + list[i].code + "'>" + list[i].name + "</option>";
                        }
                        $("#old-city-list").append(opt);
                    }
                } else {
                    alert("市的信息加载失败")
                }
            }
        });
    });

    $("#old-city-list").change(function () {
        var parent = $("#old-city-list").val();
        $("#old-area-list").empty();
        $("#old-area-list").append(defaultOption);

        if (parent == 0) {
            return;
        }
        $.ajax({
            url: "/districts",
            type: "POST",
            data: "parent=" + parent,
            dataType: "JSON",
            success: function (json) {
                if (json.state == 200) {
                    var list = json.data;
                    for (var i = 0; i < list.length; i++) {
                        if (list[i].code == oldAreaCode) {
                            var opt =
                                "<option value='" + list[i].code + "' selected='selected'>" + list[i].name +
                                "</option>";
                        } else {
                            opt =
                                "<option value='" + list[i].code + "'>" + list[i].name + "</option>";
                        }
                        $("#old-area-list").append(opt);
                    }
                } else {
                    alert("县的信息加载失败")
                }
            }
        });
    });


    //1.监听注册按钮是否被点击,如果被点击可以执行一个方法(这里不能像ajax函数那样删去function()只留下{},这是官方规定的!)
    $("#btn-update-new-address").click(function () {
        console.log($("#form-update-new-address").serialize())
        $.ajax({
            url: "/addresses/update/" + oldAddressId,
            type: "POST",

            //serialize这个API会自动检测该表单有什么控件,每个控件检测后还会获取每个控
            // 件的值,拿到这个值后并自动拼接成形如username=Tom&password=123的结构
            data: $("#form-update-new-address").serialize(),

            dataType: "JSON",
            success: function (json) {
                //1.js是弱数据类型,这个地方不用声明json的数据类型
                //2.如果服务器成功响应就会将返回的数据传给形参,比如{state: 4000,message: "用户名"
                // 已经被占用",data: null}
                if (json.state == 200) {
                    alert("修改收货地址成功")
                } else {
                    alert("修改收货地址失败")
                }
            },
            error: function (xhr) {
                //如果问题不在可控范围内,服务器就不会返回自己定
                //义的json字符串:{state: 4000,message: "用户名已经被占用",data: null}
                //而是返回一个XHR类型的对象,该对象也有一个状态码名字是status
                alert("修改收货地址时产生未知的错误!" + xhr.status);
            }
        });
    });
</script>
```



#### 遇到问题：

##### **①将一个页面的数据传到另一个页面，另一个页面如何获取？**

> 方式一：(针对只传过来一个参数的情况)
>
> 通过传入
>
> ```html
> <script type="text/javascript" src="../js/jquery-getUrlParam.js"></script>
> ```
>
> 具体实现方法：
>
> 如上面 v-bind:href="'updateAddress.html?oldAddressId='+address.aid"   将address.aid以oldAddressId名传来后
>
> ```js
> <script type="text/javascript">
>  //调用jquery-getUrlParam.js文件的getUrlParam方法获取地址aid
>  var oldAddressId = $.getUrlParam("oldAddressId");
> 	//此时 oldAddressId 就是传过来的aid
> ....
> </script>
> ```
>
> 方式二:（如果是多个参数，如显示所选中的购物车清单列表）可使用如下方式 
>
> 详见：https://www.bbsmax.com/A/ZOJPGL0ezv/
>
> 例如url地址为：http://localhost:8080/web/orderConfirm.html?cids=20&cids=1&cids=2
>
> 在js里直接输入：
>
> ```js
> /**
> 	location.search在客户端获取Url参数的方法
> 	location.search是从当前URL的?号开始的字符串
> 	如:http://www.baidu.com/s?wd=baidu&cl=3
> 	它的search就是?wd=baidu&cl=3
> 	location.search.substr(1)与location.search一样
> 	console.log(location.search.substr(0))是从当前URL的?号后开始的字符串
> 	
> 	如: location.search.substr(1).split("&")[0]
> 	可以返回第一个参数:wd=baidu
> 	
> 	如: location.search.split('?')[1]
> 	可以返回所有参数:wd=baidu&cl=3
> */
> 
> 
> console.log(location.search.substr(1));       //是从当前URL的?号后开始的字符串
> console.log(location.search.substr(0));		 //从当前URL的?号开始的字符串
> console.log(location.search.substr(1).split("&")[0]);  //可以返回第一个参数 和 方式1获取的参数一致
> console.log(location.search.split('?')[1]);  //可以返回所有参数和 location.search.substr(0)一致
> console.log(location.search);				//从当前URL的?号开始的字符串
> ```
>
> 前端显示的数据为：
>
> ![](image\snipaste20230319_180040.png)
>
> 



##### **②为何不用vue直接将参数绑定html里**

> 详见 js中的 showFindByAid()方法
>
> ```js
> /** 
>     假设有个标签<div id="a"></div>
>     那么$("#a").html(<p></p>)就是给该div标签加p标签
>     $("#a").html("我爱中国")就是给该div标签填充"我爱中国"内容
>     $("#a").val(data) 是针对input框value赋值的问题 
> */
> $("#old-name").val(json.data.name);
> ```
>
> 所以这里有两种方式(我所知的)：
>
> 1、$("#a").**html**(data)； 针对**innerHTML ** 将当前页面所有id为a的元素的innerHTML设为data  
>
> ​										标签**div，span，label，td**...等等这些标签里的值是innerHTML  
>
> 2、$("#a").**val**(data) ； 是针对**value**赋值的问题 
>
> ​									标签**input，textarea（文本域），select（选择框）**这些标签里的值是value
>
> https://ask.csdn.net/questions/356954





##### **③如何将修改前的省市区的code值传到下拉列表默认第一个？**

> 首先select下拉列表的默认第一个的属性是 selected="selected", 所以只要在for循环中添加if语句，如果两个
>
> code值相同就在option里加上selected="selected"，否则不加，js代码如下：
>
> ```js
> $.ajax({
>             url: "/districts",
>             type: "POST",
>             data: "parent=" + parent,
>             dataType: "JSON",
>             success: function (json) {
>                 if (json.state == 200) {
>                     var list = json.data;
>                     for (var i = 0; i < list.length; i++) {
>                         if (list[i].code == oldCityCode) {
>                             var opt =
>                                 "<option value='" + list[i].code + "' selected='selected'>" + list[i].name + 
>                                 "</option>";
>                         } else {
>                             opt =
>                                 "<option value='" + list[i].code + "'>" + list[i].name + "</option>";
>                         }
>                         $("#old-city-list").append(opt);
>                     }
>                 } else {
>                     alert("市的信息加载失败")
>                 }
>             }
>         });
> ```
>
> 另外将code值设为value值，而code值在数据库里设定是非零的数，这就使得在后来将
>
> ```html
> <option value='0'>-----请选择-----</option>
> ```
>
> 加入进去不会和code值相同造成替换问题

效果如下：

> ![](image\snipaste20230315_200740.png)

与新增地址效果对比：

> 因为select标签默认获取第一个option的内容填充到下拉列表中,所以即使加载页面时省区域的下拉列表中已经有了所有省但仍然会显示        -----请选择-----
>
> ![](image\snipaste20230315_200826.png)



##### **④在将修改前的省市区的code值传到各个方法时的问题**

> 将修改前的省市区的code值传到**showProvinceList(); showCityList(); showCityList();**三个方法时，出现有的可以传入，
>
> 有的无法传入问题。
>
> 在**showProvinceList()** 中完全可以传入
>
> 在**showCityList()和showCityList()**完全传不入

###### 分析原因：

> 首先认为是**showCityList()和showCityList()**两个方法有问题，但是他们和**showProvinceList()** 仅仅只有引用id属性不同，
>
> 而且是三者是同级的关系，其次，认为是传值的原因，可是如果出现这种请况**showProvinceList()**就不会可以传入
>
> 最后通过一个一个debug发现**前端浏览器在调用js中的ajax代码的规律**：**他会首先将同级所有方法的除了success，error其他的都调用一遍，然后再一个一个success里的代码调用，而拿到三个code值的代码在showFindByAid()的success里。我以为是一个方法所有执行完再执行下一个。**
>
> 由于我是在外面创建的三个code属性，然后当执行到**showFindByAid()**时将里面拿到的code值一个一个指向外面的这三个属性，如下：
>
> ```js
> <script type="text/javascript">
>     //调用jquery-getUrlParam.js文件的getUrlParam方法获取商品id
>     var oldAddressId = $.getUrlParam("oldAddressId");
> 
>     var defaultOption = "<option value='0'>-----请选择-----</option>";
> 
>     // 创建三个属性
>     var oldProvinceCode;
>     var oldCityCode;
>     var oldAreaCode;
> 
>     $(document).ready(function () {
>         //加载省的数据罗列时代码量较多,建议定义在外部方法中,然后在这里调用定义的方法
>         showFindByAid();
>         
>         showProvinceList();
>         showCityList();
>         showAreaList();
> 
>         $("#old-province-list").append(defaultOption);
> 
>         $("#old-city-list").append(defaultOption);
>         $("#old-area-list").append(defaultOption);
>     });
> 
>     function showFindByAid() {
>         $.ajax({
>             url: "/addresses/find_by_aid/" + oldAddressId,
>             type: "GET",
>             dataType: "JSON",
>             success: function (json) {
>                 if (json.state == 200) {
>                     ...
>                     oldProvinceCode = json.data.provinceCode;
>                     oldCityCode = json.data.cityCode;
>                     oldAreaCode = json.data.areaCode;
>                     ...
>                 } ...
>             },
>         });
>     }
> 
>     //省的下拉列表数据展示
>     function showProvinceList() {
>         console.log(oldProvinceCode);
>         $.ajax({
>             url: "/districts",//发送请求用于获取所有省对象
>             type: "POST",
>             data: "parent=86",
>             dataType: "JSON",
>             success: function (json) {
>                 if (json.state == 200) {
>                     var list = json.data;//获取所有省对象的List集合
>                     for (var i = 0; i < list.length; i++) {
>                         if (list[i].code == oldProvinceCode) {
>                             ...
>                         } else {
>                             ...
>                         }
>                         $("#old-province-list").append(opt);
>                     }
>                 } else {...}
>             }
>         });
>     }
> 
>     function showCityList() {
>         var parent = oldProvinceCode;
>         $.ajax({
>             url: "/districts",
>             type: "POST",
>             data: "parent=" + oldProvinceCode,
>             dataType: "JSON",
>             success: function (json) {
>                 if (json.state == 200) {
>                     var list = json.data;
>                     for (var i = 0; i < list.length; i++) {
>                         if (list[i].code == oldCityCode) {
>                             ...
>                         } else {
>                             ...
>                         }
>                         $("#old-city-list").append(opt);
>                     }
>                 } else {...}
>             }
>         });
> 
>     }
> 
>     function showAreaList() {
>         var parent = oldCityCode;
>         $.ajax({
>             url: "/districts",
>             type: "POST",
>             data: "parent=" + oldCityCode,
>             dataType: "JSON",
>             success: function (json) {
>                 if (json.state == 200) {
>                     var list = json.data;
>                     for (var i = 0; i < list.length; i++) {
>                         if (list[i].code == oldAreaCode) {
>                           ...
>                         } else {
>                           ...
>                         }
>                         $("#old-area-list").append(opt);
>                     }
>                 } else {...}
>             }
>         });
>     }
> </script>
> ```
>
> 一开始写成四个方法同级，想着**showFindByAid();**执行完将三个code值指向外面的三个，然后省市区三个方法调用外面的三个
>
> 属性获得code值，然后即可，**事实上前端是将同级所有方法的除了success，error其他的都调用一遍，然后再一个一个success里的代码调用，这也就解释了为什么在showProvinceList() 中完全可以传入，因为showProvinceList() 是只在success中用到了code值，传参数是固定86，而另两个方法需要传入父代号code参数，这也就为什么后端显示的数据是undefined。**

###### 解决方式：

> 将**showProvinceList(); showCityList(); showCityList();**三个方法放到**showFindByAid();的success**里，如下：
>
> ```js
> <script type="text/javascript">
>     //调用jquery-getUrlParam.js文件的getUrlParam方法获取商品id
>     var oldAddressId = $.getUrlParam("oldAddressId");
> 
>     var defaultOption = "<option value='0'>-----请选择-----</option>";
> 
>     // 创建三个属性
>     var oldProvinceCode;
>     var oldCityCode;
>     var oldAreaCode;
> 
>     $(document).ready(function () {
>         //加载省的数据罗列时代码量较多,建议定义在外部方法中,然后在这里调用定义的方法
>         showFindByAid();
> 
>         $("#old-province-list").append(defaultOption);
> 
>         $("#old-city-list").append(defaultOption);
>         $("#old-area-list").append(defaultOption);
>     });
> 
>     function showFindByAid() {
>         $.ajax({
>             url: "/addresses/find_by_aid/" + oldAddressId,
>             type: "GET",
>             dataType: "JSON",
>             success: function (json) {
>                 if (json.state == 200) {
>                     ...
>                     oldProvinceCode = json.data.provinceCode;
>                     oldCityCode = json.data.cityCode;
>                     oldAreaCode = json.data.areaCode;
>                     
>                     showProvinceList();
>                     showCityList();
>                     showAreaList();
>                     ...
>                 } ...
>             },
>         });
>     }
> 
>     //省的下拉列表数据展示
>     function showProvinceList() {
>         ...
>     }
>     //市的下拉列表数据展示
>     function showCityList() {
>         ...
>     }
>     //区的下拉列表数据展示
>     function showAreaList() {
>         ...
>     }
> </script>
> ```



## 11、在ajax函数中data参数的数据设置的方式

- data:$(“选择的form表单”).serialize()。当需要提交的参数过多并且在同一个表单中时使用

- data:new FormData($(“选择的form表单”)[0])。只适用提交文件

- data:“username=TOM”。手动拼接,适合参数值固定并且参数值列表有限.等同于

  ```js
  var user = "控件某属性值或控件文本内容或自己声明的值"
  data: "username="+user
  ```

  

- 使用JSON格式提交数据

  ```js
  data: {
      "username": "Tom",
      "age": 18
  }
  ```






## 12、在处理购物车问题时，由于是用vue的方式进行后端传数据给前端，针对小计问题，除了能在前端处理，在后端如何处理

> 开始时，本人在值对象CartVO里添加了小计属性，并给其get方法，至于为什么不给set，本人认为对于金钱的计算，计算机完全可以胜任，我们只需拿到即可，如果有修改方法可能会造成安全隐患。如下：
>
> ```java
> public Double getXj() {
>     //商品单价
>     BigDecimal bigDecimalPrice = new BigDecimal("" + price);
>     //选定该商品的数量
>     BigDecimal bigDecimalBuyCount = new BigDecimal("" + num);
> 
>     //BigDecimal里的multiply()方法是两个相乘
>     BigDecimal bigDecimalXJ = bigDecimalBuyCount.multiply(bigDecimalPrice);
>     xj = bigDecimalXJ.doubleValue();
>     return xj;
> }
> ```
>
> 这是我后来修改后的2.0版本，1.0是在这个方法里从外面传了price和num，并不是本地调用，出现的问题就是后端可以拿到数据。如下：
>
> ![](image\snipaste20230316_230854.png)
>
> 而前端无法获取，如下：
>
> ![](image\snipaste20230316_231024.png)
>
> 奇怪的是只有xj没有。
>
> 通过查询得知 由于Spring Boot 内置了jackson来完成JSON的序列化和反序列化操作，**而jackson将对象转换为json，基于属性的set和get方法的。** 
>
> **个人通过测试发现仅仅与get方法有关**，其形参列表不能有任何参数，由此解决
>
> ![](image\snipaste20230316_231544.png)



> 附：后端相关代码：在查询后将xj一个一个计算出来
>
> ![](image\snipaste20230316_231714.png)





## 13、统计业务方法耗时-AOP

检测项目所有业务层方法的耗时(开始执行时间和结束执行时间只差值),在不改变项目主体流程代码的前提条件下完成此功能,就要用到AOP

```tex
如果我们想对业务某一些方法同时添加相同的功能需求,并且在不改变业务功能逻辑的基础之上进行完成,就可以使用AOP的切面编程进行开发
```

### 1.AOP

AOP：面向切面（Aspect）编程。AOP并不是Spring框架的特性(Spring已经被整合到了SpringBoot中,所以如果AOP是Spring框架的特性,那么就不需要手动导包,只需要在一个类上写@Aspect注解,鼠标放到该注解上按alt+enter就可以自动导包了,但是事与愿违,所以说AOP并不是Spring框架的特性)，只是Spring很好的支持了AOP。

使用步骤:

```tex
1、首先定义一个类,将这个类作为切面类
2、在这个类中定义切面方法(5种:前置,后置,环绕,异常,最终)
3、将这个切面方法中的业务逻辑对应的代码进行编写和设计
4、通过连接点来连接目标方法,就是用粗粒度表达式和细粒度表达式来进行连接
```
### 2.切面方法注意事项

```tex
1.切面方法的访问访问修饰符必须是public。
2.切面方法的返回值类型可以是void或Object，如果该方法被@Around注解修饰，必须使用Object作为返回值类型，并返回连接点方法的返回	值；如果使用的注解是@Before或@After等其他注解时，则自行决定。
3.切面方法的名称可以自定义。
4.切面方法可以接收参数,参数是ProccedingJoinPoint接口类型的参数.但是被@Around所修饰的方法必须要传递这个参数.其他注解修饰的方法要不要该参数都可以
```

### 3 统计业务方法执行时长

1.因为AOP不是Spring内部封装的技术,所以需要进行导包操作:在pom.xml文件中添加两个关于AOP的依赖aspectjweaver和aspectjtools。

```xml
<!--导入AOP依赖-->
<dependency>
    <groupId>org.aspectj</groupId>
    <artifactId>aspectjweaver</artifactId>
</dependency>
<dependency>
    <groupId>org.aspectj</groupId>
    <artifactId>aspectjtools</artifactId>
</dependency>
```

2.定义一个切面类

在com.cy.store.aop包下创建TimerAspect切面类,给类添加两个注解进行修饰:

- @Aspect(将当前类标记为切面类)
- @Component(将当前类的对象创建使用维护交由Spring容器维护)

```java
@Aspect
@Component
public class TimerAspect {
}
```

3.在类中添加切面方法,这里使用环绕通知的方式来进行编写

**参数ProceedingJoinPoint接口表示连接点,也就是目标方法的对象**

```java
public Object around(ProceedingJoinPoint pjp) throws Throwable {
    //开始时间
    long start = System.currentTimeMillis();
    //调用目标方法,比如login方法,getByUid方法
    Object result = pjp.proceed();
    //结束时间
    long end = System.currentTimeMillis();
    System.out.println("耗时:"+(end-start));
    return result;
}
```

4.将当前环绕通知映射到某个切面上,也就是指定连接的点.给around方法添加注解@Around

```java
@Around("execution(* com.cy.store.service.impl.*.*(..))")
```

- 第一个*表示方法返回值是任意的
- 第二个*表示imp包下的类是任意的
- 第三个*表示类里面的方法是任意的
- (…)表示方法的参数是任意的

5.启动项目，在前端浏览器访问任意一个功能模块进行功能的测试



### 4、如何获得被前置切面切入类的类名和方法名

https://blog.csdn.net/Dgy_jungle/article/details/60586959

https://blog.csdn.net/huangwangz/article/details/120740527

### 5、最终获取各业务模块耗时

```java
/**
 * 统计业务方法耗时-AOP
 * @author 魏敏捷
 * @version 1.0
 */
@Aspect //将当前类标记为切面类
@Component //将当前类的对象创建使用维护交由Spring容器维护
public class TimerAspect {

    @Around("execution(* com.cy.store.service.impl.*.*(..))")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        //开始时间
        long start = System.currentTimeMillis();
        //调用目标方法,比如login方法,getByUid方法
        Object result = pjp.proceed();
        //结束时间
        long end = System.currentTimeMillis();
        System.out.println(pjp.getTarget().getClass().getName() + "类的" +
                           pjp.getSignature().getName() +"方法..." + "耗时:" +(end-start));
        return result;
    }
}
```

```java
com.cy.store.service.impl.UserServiceImpl类的login方法...耗时:23
13
www
com.cy.store.service.impl.ProductServiceImpl类的findHotList方法...耗时:4
com.cy.store.service.impl.ProductServiceImpl类的findById方法...耗时:2
com.cy.store.service.impl.CartServiceImpl类的addToCart方法...耗时:103
com.cy.store.service.impl.CartServiceImpl类的getVOByUidPage方法...耗时:80
com.cy.store.service.impl.AddressServiceImpl类的getByUid方法...耗时:128
com.cy.store.service.impl.CartServiceImpl类的getVOByCids方法...耗时:137
com.cy.store.service.impl.CartServiceImpl类的getVOByCids方法...耗时:308
com.cy.store.service.impl.AddressServiceImpl类的getByAid方法...耗时:5
com.cy.store.service.impl.OrderServiceImpl类的create方法...耗时:413
com.cy.store.service.impl.CartServiceImpl类的getVOByUidPage方法...耗时:8
com.cy.store.service.impl.AddressServiceImpl类的getAddressPage方法...耗时:6
com.cy.store.service.impl.UserServiceImpl类的getInfoByUid方法...耗时:3
```







