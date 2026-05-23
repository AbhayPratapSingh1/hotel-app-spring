import { createApp } from "./src/app.js";
import { MongoClient } from "mongodb";
import { createClient } from "redis";
import { CacheManager } from "./src/cacheManager.js";

const redisUri = Deno.env.get("REDIS_URI") || "redis://localhost:6379";
const redisClient = createClient({ url: redisUri });

await redisClient.connect();

const uri = Deno.env.get("MONGODB_URI") || "mongodb://localhost:27017/hotel-db";
const client = new MongoClient(uri);

try {
  await client.connect();
  console.log("Connected to MongoDB successfully!");
} catch (error) {
  console.error("Connection error:", error);
} finally {
  await client.close();
}

client.connect();
const db = await client.db();

const main = async () => {
  const cacheManager = new CacheManager(redisClient);
  const app = await createApp(db, cacheManager);

  Deno.serve({ port: 8080 }, app.fetch);
};

main();
