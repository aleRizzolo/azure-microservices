import * as dotenv from "dotenv"
import express, { Express } from "express"

import { authRoutes } from "./routes"
import { connectToDatabase } from "./db"

dotenv.config()

const port = process.env.PORT || 8080
const app: Express = express()

app.use(express.json())
app.use(express.urlencoded({ extended: true }))

connectToDatabase()

app.use("/", authRoutes)

app.listen(port, () => {
  console.info(`Server is listening at ${port}`)
})
