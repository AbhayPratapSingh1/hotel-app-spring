import { Hono } from "hono"
import { MongoClient } from "mongodb";
import { logger } from "hono/logger"
import jwt from "jsonwebtoken";

const uri = Deno.env.get("SPRING_DATA_MONGODB_URI") || "mongodb://localhost:27017/hotel-db"
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

const JWT_SECRET = "9a4f2c8oiuytrewd3ghye1f2a3b4c5d6e7f8a9b0c1d"

export const createApp = async () => {
  await client.connect();
  const app = new Hono();

  const userCollection = await db.collection("user");


  app.use(logger());

  app.get("/", (c) => {
    return c.text("hello form user service")
  })

  app.post("/api/users/register", async (c) => {
    const { username, password } = await c.req.json();

    userCollection.insertOne({ username, password })

    return c.json({ status: "success" })
  })

  app.post("/api/users/login", async (c) => {
    const { username, password } = await c.req.json();

    const usersDetails = await userCollection.findOne({ username, password });

    if (usersDetails === null) {
      return c.text(null, 401);
    }

    const token = createToken(usersDetails._id);

    return c.json({ token })
  })



  return app;
}

const createToken = (userId) => {
  return jwt.sign({ userId }, JWT_SECRET, { expiresIn: "1h" });
}