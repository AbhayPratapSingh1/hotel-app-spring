import { redisClient } from "./redis_connection.js";
import { work } from "./work.js";

const redisUri = Deno.env.get("REDIS_URI") || "redis://localhost:6379";
const redisQueueName = Deno.env.get("RECEIPT_QUEUE") || "RECEIPT_QUEUE";

const main = async () => {
  const redis = await redisClient(redisUri);
  while (true) {
    const request = await redis.brPop(redisQueueName, 1000);
    const booking = JSON.parse(request.element);
    await work(booking);
  }
};

main();
