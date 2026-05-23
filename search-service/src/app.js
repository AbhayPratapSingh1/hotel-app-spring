import { Hono } from "hono";
import { logger } from "hono/logger";
import { cors } from "hono/cors";
const delay = async (time) => {
  await new Promise((resolve) => {
    setTimeout(() => {
      resolve(1)
    }, time)
  })
}

const getHotelsWithCity = async (city, userCollection, cacheManager) => {
  const hotelsCache = await cacheManager.getHotelCache(city)
  if (hotelsCache) {
    return hotelsCache;
  }

  await delay(3000)
  const hotels = await userCollection.find({ city }).toArray();
  cacheManager.setHotelsCache(city, hotels)
  return hotels;
}


const getHotels = async (userCollection, cacheManager) => {
  const hotelsCache = await cacheManager.getHotelCache("0")
  if (hotelsCache) {
    return hotelsCache;
  }
  await delay(3000)
  const hotels = await userCollection.find().toArray();

  cacheManager.setHotelsCache("0", hotels)
  return hotels;
}


export const createApp = async (db, cacheManager) => {
  const app = new Hono();

  const userCollection = await db.collection("hotel");

  app.use(logger());
  app.use(cors({ origin: "*", credentials: true }));

  app.get("/", (c) => c.text("hello form user service"));

  app.get("/api/search/hotels", async (c) => {
    const city = await c.req.query("city");
    const cityKey = city == null ? "0" : city;
    if (city) {
      const hotels = await getHotelsWithCity(cityKey, userCollection, cacheManager);
      return c.json(hotels);
    }

    const hotels = await getHotels(userCollection, cacheManager);

    return c.json(hotels);
  });

  app.get("/api/internal/book/:id", async (c) => {
    const id = await c.req.param("id");

    const updatedHotel = await userCollection.findOneAndUpdate(
      { _id: id, rooms: { $gt: 0 }, },
      { $inc: { rooms: -1 }, },
      { returnDocument: "after", }
    );

    cacheManager.invalidate("0");
    console.log(updatedHotel);

    cacheManager.invalidate(updatedHotel.city);

    return c.json(updatedHotel);
  });

  return app;
};
