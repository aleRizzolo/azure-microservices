import axios from "axios"
import * as dotenv from "dotenv"

import { Signup } from "../types"

dotenv.config()

const axiosInstance = axios.create({
  baseURL: process.env.USER_URL,
})

export const axiosCreateUser = async (userToSave: Signup) => {
  let result: any

  await axiosInstance
    .post("/signup", {
      email: userToSave.email,
      password: userToSave.password,
      firstName: userToSave.firstName,
      lastName: userToSave.lastName,
    })
    .then((response) => {
      result = response.data.data
    })
    .catch((err) => {
      console.log(err)
      if (err.code === "ECONNREFUSED") {
        throw new Error("User-microservice error: connection refused")
      } else {
        throw new Error(err)
      }
    })

  return result
}
