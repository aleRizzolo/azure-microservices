import bcrypt from "bcrypt"
import { Request, Response } from "express"

import { UserModel } from "../models"
import { Login, Signup } from "../types"

export const login = async (req: Request, res: Response) => {
  const { email, password }: Login = req.body

  //check if the user exists
  const user = await UserModel.findOne({ email })

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

export const signUp = async (req: Request, res: Response) => {
  const { email, password }: Signup = req.body
  const SALT: number = 10

  //check if the email exists
  const emailAlreadyInUse = await UserModel.findOne({ email })

  if (emailAlreadyInUse) {
    return res.status(400).send({ message: "Email already in use" })
  }

  const hashedPassword = await bcrypt.hash(password, SALT)

  // Create the user
  const user: Signup = new UserModel(req.body)
  user.password = hashedPassword

  await UserModel.create(user)

  return res.status(201).send({ message: "User saved" })
}

export const deleteAccount = async (req: Request, res: Response) => {
  //same interface as login because is useless to create another interface just for this
  const { email }: Login = req.body

  //check if the email exists
  const emailExists = await UserModel.findOne({ email })

  if (!emailExists) {
    return res.status(404).send({ message: "Email not found" })
  }

  const deleteAccount = await UserModel.findOneAndDelete({ email: email })

  return res.status(200).send({ message: "Account deleted" })
}

export const modifyAccount = async (req: Request, res: Response) => {
  const email: Signup = req.body.emai
  //find user by email
  const user = await UserModel.findOne({ email })

  if (!user) {
    return res.status(404).send({ message: "User not found" })
  }

  //take everything from the body
  //save again with all the info
  //need to create another interface
}
