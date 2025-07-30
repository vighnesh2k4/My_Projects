package JAVAtraining;

import java.util.List;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.exceptions.JedisConnectionException;

public class JedisEx2 {
	public static void main(String args[]) {
		Jedis jedis = new Jedis();
		Transaction transaction = jedis.multi();
        try {
            transaction.set("name", "venky");
            transaction.set("mail", "venky@email.com");
            transaction.incr("counter");
            List<Object> result = transaction.exec();
            System.out.println("Transaction result: " + result);
        } catch (JedisConnectionException e) {
            transaction.discard();
            System.out.println("Transaction failed. All changes are discarded.");
        }
        
        Pipeline pipeline = jedis.pipelined();
        try {
            pipeline.set("user1", "inder");
            pipeline.set("userid1", "id1");
            pipeline.incr("incrementing");
            List<Object> results = pipeline.syncAndReturnAll(); //or sync
            System.out.println("Pipeline results: " + results);
        } catch (JedisConnectionException e) {
            System.out.println("Pipeline error: " + e.getMessage());
        }
        
        jedis.close();
	}
}
