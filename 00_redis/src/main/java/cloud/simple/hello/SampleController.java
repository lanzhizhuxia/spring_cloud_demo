package cloud.simple.hello;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cloud.xq.util.RedisUtil;
import redis.clients.jedis.Jedis;

@Controller
@SpringBootApplication
public class SampleController {

  volatile static int n = 0;
  volatile static int m = 0;

  Executor executor = Executors.newFixedThreadPool(10);

  @ResponseBody
  @RequestMapping(value = "/hello/{nPath}")
  String home(@PathVariable(value = "nPath") int nPath) {

    final int sPath = nPath;

    Runnable task = new Runnable() {
      @Override
      public void run() {
        System.out.println("hello当前线程：" + Thread.currentThread().getName());
        System.out.println("hello请求数量：" + (sPath));
        try {
          Thread.sleep(500);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        System.out.println("hello处理完成：" + (sPath));
      }
    };
    executor.execute(task);


    return "Hello World!" + n;
  }

  @ResponseBody
  @RequestMapping(value = "/user")
  String user() {

    System.out.println("user当前线程：" + Thread.currentThread().getName());
    System.out.println("user请求数量：" + (++m));

    System.out.println("user处理完成：" + (m));
    return "Hello User!" + m;
  }

  @ResponseBody
  @RequestMapping(value = "/redis")
  String test() {

    Jedis jedis = RedisUtil.getJedis();
    String string = jedis.get("name");
    System.out.println(string);
    testBasicString();

    return string;
  }

  /**
   * Redis存储初级的字符串 CRUD
   */
  void testBasicString() {
    Jedis jedis = RedisUtil.getJedis();
    // -----添加数据----------
    jedis.set("name", "minxr");// 向key-->name中放入了value-->minxr
    System.out.println(jedis.get("name"));// 执行结果：minxr
    // -----修改数据-----------
    // 1、在原来基础上修改
    jedis.append("name", "jarorwar"); // 很直观，类似map 将jarorwar
                                      // append到已经有的value之后
    System.out.println(jedis.get("name"));// 执行结果:minxrjarorwar
    // 2、直接覆盖原来的数据
    jedis.set("name", "tony");
    System.out.println(jedis.get("name"));// 执行结果：tony
    // 删除key对应的记录
    jedis.del("name");
    System.out.println(jedis.get("name"));// 执行结果：null
    /**
     * mset相当于 jedis.set("name","minxr"); jedis.set("jarorwar","tony");
     */
    jedis.mset("name", "minxr", "jarorwar", "tony");
    System.out.println(jedis.mget("name", "jarorwar"));
  }


  public static void main(String[] args) throws Exception {
    SpringApplication.run(SampleController.class, "--server.port=8181");
  }

}
