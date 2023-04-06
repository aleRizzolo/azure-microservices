import { Router } from "express"

import { authLogin, authSignup } from "./handler"

const authRoutes = Router()

authRoutes.post("/authLogin", authLogin)
authRoutes.post("/authSignup", authSignup)

export default authRoutes
