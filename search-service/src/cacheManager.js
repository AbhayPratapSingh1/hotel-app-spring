const EXPIRY_TIME = 10;

export class CacheManager {
  constructor(client) {
    this.client = client
  }

  async setHotelsCache(id, hotelDetails) {

    const key = `hotel-set-id:${id}`
    await this.client.set(key, JSON.stringify(hotelDetails), { EX: EXPIRY_TIME })

    console.log("Cache Stored", id);
  }

  async getHotelCache(id) {
    const key = `hotel-set-id:${id}`


    const store = await this.client.get(key);
    const hotelDetail = JSON.parse(store);

    if (hotelDetail === null) {
      console.log("\t\tCache Miss");
      return null;
    }

    this.client.expire(key, EXPIRY_TIME, "XX")


    console.log("\t\tCache Hit");

    return hotelDetail
  }


  async invalidate(id) {
    const key = `hotel-set-id:${id}`

    console.log("clearing cache");
    await this.client.del(key);

  }
}


