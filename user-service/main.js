import { createApp } from "./src/app.js";



const main = async () => {
  const app = await createApp();




  Deno.serve({ port: 8081 }, app.fetch);
}

main();
