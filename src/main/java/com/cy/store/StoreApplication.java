package com.cy.store;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


//@Configuration
@SpringBootApplication
// MapperScan 注解指定当前项目中的Mapper接口路径的位置，在项目启动的时候会自动加载所有的接口
@MapperScan("com.cy.store.mapper")
public class StoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(StoreApplication.class, args);
    }


    /**
     * 方式2:采用java代码的形式来设置文件的上传大小的限制:  方法一在application.properties配置文件里
     *
     * 1.该代码必须在主类中进行配置,因为主类是最早加载的,而配置文件必须是最早加载的
     * 2.在主类中定义一个方法,方法名无所谓,但方法需要用@bean修饰,表示该方法返回值是一个bean对象,
     *   并且该bean对象被bean修饰,也就是这个方法返回了一个对象,然后把该对象交给bean管理,类似spring中的bean标签,
     *   含义是一样的,只是这里改为了注解
     * 3.用@Configuration修饰主类使@bean注解生效,但其实@SpringBootApplication是@SpringBootConfiguration,
     *    @EnableAutoConfiguration,@ComponentScan三个注解的合并,所以可以不需要@Configuration
     * 4.方法返回值是MultipartConfigElement类型,表示所要配置的目标的元素
     *
     * */
//    @Bean
//    public MultipartConfigElement getMultipartConfigElement() {
//        //1.创建一个配置的工厂类对象
//        MultipartConfigFactory factory = new MultipartConfigFactory();
//
//        //2.设置需要创建的对象的相关信息
//        factory.setMaxFileSize(DataSize.of(10, DataUnit.MEGABYTES));
//        factory.setMaxRequestSize(DataSize.of(15,DataUnit.MEGABYTES));
//
//        //3.通过工厂类创建MultipartConfigElement对象
//        return factory.createMultipartConfig();
//    }

}
