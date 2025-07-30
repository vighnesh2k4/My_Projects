package JAVAtraining;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.args.ListPosition;

public class JedisEx {

    public static void main(String[] args) {
        Jedis jedis = new Jedis();

        
        jedis.set("name", "Vighneshwar");
        System.out.println("String - name: " + jedis.get("name"));
        jedis.append("name", " Reddy");
        System.out.println("String - name After append: " + jedis.get("name"));
        jedis.incr("visits");  // automatically sets visits = 1 if it doesn't exist
        System.out.println("Visits after INCR: " + jedis.get("visits"));
        jedis.decr("visits");
        System.out.println("Visits after DECR: " + jedis.get("visits"));
        jedis.incrBy("visits", 2);
        System.out.println("Visits after INCRBY 2: " + jedis.get("visits"));
        jedis.decrBy("visits", 3);
        jedis.incrByFloat("visits",1.0);
        System.out.println("Visits after INCRBYFLOAT 1.0:"+jedis.get("visits"));
        System.out.println("Visits after DECRBY 3: " + jedis.get("visits"));
        String fullName=jedis.get("name");
        System.out.println("Deleted string: "+jedis.del("name"));
        jedis.set("name1", fullName);
        System.out.println("get range: "+jedis.getrange("name1", 0, 10));
        System.out.println("strlen name1: "+jedis.strlen("name1"));
        jedis.set("visits", "22");
        System.out.println("strlen visits: "+jedis.strlen("visits"));
        System.out.println("substr: "+jedis.substr("name1",12,-1));
        
        
        jedis.lpush("lst1", "apple");
        jedis.rpush("lst1", "orange");
        jedis.rpushx("lst1","watermelon");
        jedis.linsert("lst1", ListPosition.BEFORE, "orange", "banana");
        jedis.lpushx("lst2", "chicken");
        jedis.rpushx("lst2","mutton");
        System.out.println("lst2: "+jedis.lrange("lst2", 0, -1));
        System.out.println("index1 of lst1: "+jedis.lindex("lst1", 1));
        System.out.println("len of lst1: "+jedis.llen("lst1"));
        System.out.println("lst1: "+jedis.lrange("lst1", 0,-1));
        jedis.lpop("lst1");
        jedis.rpop("lst1");
        System.out.println("after pop lst1: "+jedis.lrange("lst1", 0,-1));
        jedis.lpush("lst1", "orange");
        jedis.lrem("lst1", 1, "orange");
        System.out.println("after rem orange 1 lst1: "+jedis.lrange("lst1", 0,-1));
        jedis.lset("lst1", 0, "burger");
        System.out.println("after lset burger lst1: "+jedis.lrange("lst1", 0,-1));
        jedis.ltrim("lst1", 0, 4);
        System.out.println("after ltrim 0 to 4 lst1: "+jedis.lrange("lst1", 0,-1));
        

        Map<String, String> user = new HashMap<>();
        user.put("name", "vighnesh");
        user.put("email", "vighnesh@gmail.com");
        jedis.hset("huser1", user);
        jedis.hset("huser1", "password","121212");
        System.out.println("Hash - huser1 name: " + jedis.hget("huser1", "name"));
        System.out.println("Hash - all fields: " + jedis.hgetAll("huser1"));        
        jedis.hdel("huser1", "password");
        System.out.println("Hash - all fields after del: " + jedis.hgetAll("huser1")); 
        System.out.println("Hash - hexists email: " + jedis.hexists("huser1", "email")); 
        System.out.println("Hash - all keys: " + jedis.hkeys("huser1")); 
        System.out.println("Hash - huser1 len: " + jedis.hlen("huser1")); 
        System.out.println("Hash - huser1 email len: " + jedis.hstrlen("huser1","email")); 

        
        String setKey = "tags";
        jedis.sadd(setKey, "java", "redis", "database");
        jedis.sadd(setKey, "solr"); 
        Set<String> tags = jedis.smembers(setKey);
        System.out.println("Set - tags: " + tags);
        System.out.println("Number of members in set: "+jedis.scard(setKey));
        jedis.sadd("langs", "python","r","c","java","mojo");
        jedis.sadd("langs", "javascript");
        System.out.println("Set - langs: "+jedis.smembers("langs"));
        System.out.println("Sdiff tags-langs: "+jedis.sdiff("tags","langs"));
        System.out.println("Sdiff langs-tags: "+jedis.sdiff("langs","tags"));
        System.out.println("Sinter tags-langs: "+jedis.sinter("tags","langs"));
        System.out.println("Sunion langs-tags: "+jedis.sunion("langs","tags"));
        System.out.println("java ismember of tags: "+jedis.sismember("tags","java"));
        System.out.println("random pop from langs: "+jedis.spop("langs"));
        System.out.println("rem from tags: "+jedis.srem("tags","database"));
        

        jedis.zadd("myzset", 1.0, "orange");
        jedis.zadd("myzset", 2.0, "yellow");
        jedis.zadd("myzset", 3.0, "pink");
        jedis.zadd("myzset", 4.0, "blue");
        jedis.zadd("myzset", 5,"black");
        jedis.zadd("myzset", 6,"white");
        System.out.println("ZCARD myzset: " + jedis.zcard("myzset"));
        System.out.println("ZCOUNT myzset [1.0, 3.0]: " + jedis.zcount("myzset", 1.0, 3.0));
        System.out.println("ZRANGEBYSCORE myzset [1.0, 3.0]: " + jedis.zrangeByScore("myzset", 1.0, 3.0));
        jedis.zadd("myzset2", 5.0, "red");
        jedis.zadd("myzset3", 6.0, "brown");
        System.out.println("ZPOPMAX myzset: " + jedis.zpopmax("myzset"));
        System.out.println("ZPOPMIN myzset: " + jedis.zpopmin("myzset"));
        System.out.println("ZRANGE myzset: " + jedis.zrange("myzset", 0, -1));
        System.out.println("ZRANK myzset black: " + jedis.zrank("myzset", "black"));
        jedis.zrem("myzset", "blue");
        System.out.println("After ZREM, myzset: " + jedis.zrange("myzset", 0, -1));
        jedis.zremrangeByRank("myzset", 0,0);
        System.out.println("After ZREMRANGEBYRANK, myzset: " + jedis.zrange("myzset", 0, -1));
        jedis.zremrangeByScore("myzset", 2.0, 2.0);
        System.out.println("After ZREMRANGEBYSCORE, myzset: " + jedis.zrange("myzset", 0, -1));
        System.out.println("ZREVRANGE myzset: " + jedis.zrevrange("myzset", 0, -1));
        System.out.println("ZREVRANGEBYSCORE myzset [3.0, 1.0]: " + jedis.zrevrangeByScore("myzset", 3.0, 1.0));
        System.out.println("ZREVRANK myzset black: " + jedis.zrevrank("myzset", "black"));
        System.out.println("ZSCORE myzset black: " + jedis.zscore("myzset", "black"));

        jedis.close();
    }
}

