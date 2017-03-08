package cloud.simple.hello;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;

// @Configuration
// @EnableAutoConfiguration
// @EnableDiscoveryClient 用来将config-server注册到上面配置的服务注册中心上去
@EnableDiscoveryClient
@EnableConfigServer
@SpringBootApplication
public class Application {

  // public static void main(String[] args) {
  // SpringApplication.run(Application.class, args);
  // }

  public static void main(String[] args) {
    new SpringApplicationBuilder(Application.class).web(true).run(args);
  }
}
