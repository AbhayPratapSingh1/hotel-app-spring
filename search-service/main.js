import { createApp } from "./src/app.js";
import { MongoClient } from "mongodb";
import { createClient } from 'redis';
import { CacheManager } from "./src/cacheManager.js";



const redisClient = createClient();

await redisClient.connect();

const uri = Deno.env.get("SPRING_DATA_MONGODB_URI") || "mongodb://localhost:27017/hotel-db";
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
}

main();