import { Hono } from "hono"

export const createApp = () => {
  const app = new Hono();

  app.get("/", (c) => {
    return c.text("Hello")
  })
  return app;
}