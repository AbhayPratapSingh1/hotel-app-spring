import { createClient } from "redis";

export const redisClient = async (url) => {
  const client = createClient({ url });
  await client.connect();

  return client;
};
