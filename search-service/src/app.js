import { Hono } from "hono";
import { MongoClient } from "mongodb";
import { logger } from "hono/logger";
import { cors } from "hono/cors";
import jwt from "jsonwebtoken";

const uri =
  Deno.env.get("SPRING_DATA_MONGODB_URI") ||
  "mongodb://localhost:27017/hotel-db";
const client = new MongoClient(uri);

try {
  await client.connect();
  console.log("Connected to MongoDB successfully!");

  const db = client.db("my_database");
  const collection = db.collection("users");

  await collection.insertOne({ name: "Alice", age: 30 });
} catch (error) {
  console.error("Connection error:", error);
} finally {
  await client.close();
}

const db = client.db();

const JWT_SECRET = "9a4f2c8oiuytrewd3ghye1f2a3b4c5d6e7f8a9b0c1d";

export const createApp = async () => {
  await client.connect();
  const app = new Hono();

  const userCollection = await db.collection("hotel");

  app.use(logger());
  app.use(cors({ origin: "*", credentials: true }));

  app.get("/", (c) => {
    return c.text("hello form user service");
  });

  app.get("/api/search/hotels", async (c) => {
    const city = await c.req.query("city");

    if (city == null) {
      const hotels = await userCollection.find().toArray()
      return c.json(hotels);
    }

    const hotels = await userCollection.find({ city: city }).toArray()
    return c.json(hotels);
  });

  app.get("/api/internal/book/:id", async (c) => {
    const id = await c.req.param("id");

    const hotel = await userCollection.findOne({ _id: id })

    const count = hotel.rooms - 1;
    console.log(hotel)
    await userCollection.updateOne({ _id: id }, { "$set": { "rooms": count } });

    const updatedDetails = await userCollection.findOne({ _id: id });
    console.log(updatedDetails);

    return c.json({ hotel: updatedDetails });
  });

  return app;
};