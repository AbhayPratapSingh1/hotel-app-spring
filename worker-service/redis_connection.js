import { createClient } from "redis";


export const redisClient = async () => {
  const client = createClient({ url: "redis://localhost:6379" });
  await client.connect();

  return client;
}

