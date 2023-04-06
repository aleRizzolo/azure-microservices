import { Router } from "express"

import { deleteAccount, login, signUp } from "./handler"

const userRoutes = Router()

userRoutes.post("/login", login)
userRoutes.post("/signup", signUp)
userRoutes.delete("/deleteAccount", deleteAccount)

export default userRoutes
