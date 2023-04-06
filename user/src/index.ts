import * as dotenv from "dotenv"
import { connectToDatabase } from "./db"
import express, { Express } from "express"

import { userRoutes } from "./routes"

dotenv.config()

const port = process.env.PORT || 8080
const app: Express = express()

app.use(express.json())
app.use(express.urlencoded({ extended: true }))

app.use("/", userRoutes)

connectToDatabase()

app.listen(port, () => {
  console.info(`Server is listening at ${port}`)
})
