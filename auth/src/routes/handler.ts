import bcrypt from "bcrypt"
import { Request, Response } from "express"

import { LoginModel } from "../models"
import { Email, Login, Signup } from "../types"
import { axiosCreateUser, sendMessage } from "../services"

export const authLogin = async (req: Request, res: Response) => {
  const { email, password }: Login = req.body

  //check if the user exists
  const user = await LoginModel.findOne({ email })

  if (user === null) {
    return res.status(404).send({ message: "User doesn't exist" })
  }

  const hashedPassword = user.password

  const passwordMatch = await bcrypt.compare(password, hashedPassword)

  if (passwordMatch === false) {
    return res.status(401).send({ message: "Password is wrong" })
  }

  return res.status(200).send({ message: "Logged in" })
}

export const authSignup = async (req: Request, res: Response) => {
  const user: Signup = req.body

  try {
    const createUser = await axiosCreateUser(user)

    sendMessage(user.email)

    return res.status(201).send({ success: true, data: createUser })
  } catch (err) {
    return res.status(500).send({ message: err })
  }
}
