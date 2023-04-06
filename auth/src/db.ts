import mongoose from "mongoose"
import * as dotenv from "dotenv"

// Amount of milliseconds to wait for a re-connection to the database
const RECONNECT_TIMEOUT = 5000

// Handle connection error then disconnect all connections
mongoose.connection.on("error", (error) => {
  // Log the error
  console.error("Error in MongoDb connection:", error)
  // Disconnect all connections
  mongoose.disconnect()
})

// Handle mongoose disconnections. Then try to reconnect
mongoose.connection.on("disconnected", function () {
  // Convert the reconnect timeout in seconds
  const timeout: number = RECONNECT_TIMEOUT / 1000

  // Get the current date and time in UTC
  const disconnectionDate: string = new Date().toISOString()

  console.info(`MongoDB disconnected! Trying to reconnect after ${timeout} sec...`, disconnectionDate)

  // After RECONNECT_TIMEOUT milliseconds try to reconnect to db
  setTimeout(connectToDatabase, RECONNECT_TIMEOUT)
})

export const connectToDatabase = async () => {
  console.info("Connecting to database...")

  if (process.env.DATABASE_URI === null) {
    throw new Error("FATAL ERROR: DATABASE_URI env var is not defined")
  }

  const connection = await mongoose.connect(process.env.DATABASE_URI!)

  console.info("Database connection established!")

  return connection
}
