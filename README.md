# retrofit-spring-boot-starter
## Why is there another retrofit-spring-boot-starter

First of all, thank [lianjiatech](https://github.com/LianjiaTech/retrofit-spring-boot-starter) for providing an almost perfect project of [retrofit-spring-boot-starter](https://github.com/LianjiaTech/retrofit-spring-boot-starter).

However, in use, I found that it will create a retrofit instance for each API Interface file, which in my opinion is a waste of resources. After reading the code, I think it is difficult to modify the original basis in a short time, so I repeated a wheel.

In my work, the team will use retrofit as the API of BFF layer HTTP client to request micro services. Therefore, there will be hundreds of interface files in BFF. Therefore, I improved the time of creating retrofit instance, allowing one retrofit interface to inherit one base interface, which can define and configure retrofit attributes

You can see the effect I want from the fourth step of introduction

## How to use it
1. add retrofit-spring-boot-starter dependency to maven pom.xml 
```
<dependency>
   <groupId>io.github.liuziyuan</groupId>
   <artifactId>retrofit-spring-boot-starter</artifactId>
   <version>0.0.3</version>
</dependency>
```
2. add `@EnableRetrofit` to your Spring boot Starter Class, create a config class for Retrofit
```
@EnableRetrofit  
@Slf4j  
public class HelloApplication extends SpringBootServletInitializer {  
    public static void main(String[] args) {  
        SpringApplication.run(HelloApplication.class, args);  
  }  
}
```
You can specify basepackage like `@EnableRetrofit(basePackages = "xxx.demo.api")`, "xxx.demo.api" is your retrofit APIs folder name. By default, all files in the directory where the starter class file is located will be scanned

```
@Configuration
public class RetrofitConfig {
    @Bean
    public RetrofitResourceDefinitionRegistry retrofitResourceDefinitionRegistry() {
        return new RetrofitResourceDefinitionRegistry();
    }
}

```
3. create an Interface file, and use `@RetrofitBuilder`
 ```
@RetrofitBuilder(baseUrl = "${app.test.base-url}")  
public interface TestApi {  
  
    @GET("/v1/test/")  
    Call<Result> test();  
}
```
pls keep app.test.base-url on your resources' config file,
baseUrl can also be a URL as http://localhost:8080

4. add other attributes for  `@RetrofitBuilder`, if you need
```
@RetrofitBuilder(baseUrl = "${app.test.base-url}",  
  addConverterFactory = {GsonConverterFactory.class, JacksonConverterFactory.class},  
  addCallAdapterFactory = {RxJavaCallAdapterFactory.class},  
  client = MyOkHttpClient.class)  
@RetrofitInterceptor(handler = MyRetrofitInterceptor1.class)  
@RetrofitInterceptor(handler = MyRetrofitInterceptor2.class)  
public interface TestApi {  
  
    @GET("/v1/test/")  
    Call<Result> test();  
}
```

and your custom OKHttpClient need extends BaseOkHttpClientBuilder
```
@Component
public class MyOkHttpClient extends BaseOkHttpClientBuilder {  
  
  @Override  
  public OkHttpClient.Builder builder(OkHttpClient.Builder builder) {  
        return builder;  
  }  
}
```
and you could add your custom OKHttpClient Interceptor, pls extends BaseInterceptor

```
@Component
public class MyRetrofitInterceptor2 extends BaseInterceptor {  
  
 @SneakyThrows  
 @Override  protected Response executeIntercept(Chain chain) {  
        Request request = chain.request();  
        return chain.proceed(request);  
  }  
}
```
and you could set include,exclude and sort on @RetrofitInterceptor like `@RetrofitInterceptor(handler = MyRetrofitInterceptor2.class, exclude = {"/v1/test/"})`

When exclude is used, the corresponding API will ignore this interceptor.

When you use sort, please ensure that all interceptors use sort, because by default, sort is 0. You can ensure the execution order of your interceptors through int type.
By default, the interceptor is loaded from top to bottom.

5. If you have hundreds of Interface method, it is from a same source Base URL, and you want your code structure to be more orderly and look consistent with the source service structure, you could do this ,
   Define an empty Interface file
 ```
@RetrofitBuilder(baseUrl = "${app.test.base-url}",  
  addConverterFactory = {GsonConverterFactory.class, JacksonConverterFactory.class},  
  addCallAdapterFactory = {RxJavaCallAdapterFactory.class},  
  client = MyOkHttpClient.class)  
@RetrofitInterceptor(handler = MyRetrofitInterceptor1.class)  
@RetrofitInterceptor(handler = MyRetrofitInterceptor2.class)  
public interface TestApi {  

}
```
and create other API Interface extend Parent class
```
public interface TestInheritApi extends TestApi {  
  
    @GET("/v1/test/inherit/")  
    Call<Result> test1();  
}
```
6. Use Retrofit API On Controller
```
@Slf4j  
@RestController  
@RequestMapping("/v1/hello")  
public class HelloController {  
  @Autowired  
//    @Qualifier("io.liuziyuan.demo.api.TestApi2")  
  private TestApi2 api2;
  ```

Yes, Congratulations, your code should work normally


If you inject the Interface and the inherited Interface at the same time, the following errors may occur

```
Description:

Field api in io.liuziyuan.demo.controller.HelloController required a single bean, but 2 were found:
	- io.liuziyuan.demo.api.TestApi: defined in null
	- io.liuziyuan.demo.api.TestInheritApi: defined in null

Action:

Consider marking one of the beans as @Primary, updating the consumer to accept multiple beans, or using @Qualifier to identify the bean that should be consumed
```
So, you need use @Qualifier("io.liuziyuan.demo.api.TestApi")

Each API interface in the project has been set with the qualifier attribute, so you do not do anything.

