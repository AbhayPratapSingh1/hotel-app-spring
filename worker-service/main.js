import { redisClient } from "./redis_connection.js";
import { work } from "./work.js";

const main = async () => {
  const redis = await redisClient();
  while (true) {
    const request = await redis.brPop("BOOKING_QUEUE", 1000);

    const booking = JSON.parse(request.element);
    await work(booking);
  }
};

main();
