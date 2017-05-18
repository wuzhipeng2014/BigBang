package JRedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Set;

/**
 * note: 需要开启本地的redis服务进程 Created by zhipengwu on 17-5-17.
 */
public class JedisTest {
    // jedis 线程池
    public static JedisPool pool = new JedisPool(new JedisPoolConfig(), "localhost");

    public static void main(String[] args) {
        Jedis jedis = pool.getResource();
        createJedisClientPool();
        pool.destroy();
    }

    /**
     * 使用线程池创建Jedis client
     */
    public static void createJedisClientPool() {
        Jedis jedis = pool.getResource();
        jedis.set("name", "rabbit");
        String name = jedis.get("name");
        jedis.zadd("sose", 0, "car");
        jedis.zadd("sose", 0, "bike");
        Set<String> sose = jedis.zrange("sose", 0, -1);
        for (String item:sose){
            System.out.println(item);
        }

        jedis.close();

    }

    /**
     * 直接创建jedis client
     */
    public static void createJedisClientDirectly() {
        Jedis jedis = new Jedis("localhost");
        jedis.set("foo", "second");
        String value = jedis.get("foo");

        System.out.println(value);
    }
}
