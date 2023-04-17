import { Router } from "express"

import { deleteAccount, login, modifyAccount, signUp } from "./handler"

const userRoutes = Router()

userRoutes.post("/login", login)
userRoutes.post("/signup", signUp)
userRoutes.patch("/modifyAccount", modifyAccount)
userRoutes.delete("/deleteAccount", deleteAccount)

export default userRoutes
