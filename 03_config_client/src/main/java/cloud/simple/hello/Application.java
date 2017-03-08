package cloud.simple.hello;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

// 用来发现config-server服务，利用其来加载应用配置
@EnableDiscoveryClient
@SpringBootApplication
public class Application {

  // public static void main(String[] args) throws Exception {
  // SpringApplication.run(Application.class, args);
  // }

  public static void main(String[] args) {
    new SpringApplicationBuilder(Application.class).web(true).run(args);
  }

}
