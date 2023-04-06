import mongoose from "mongoose"

import { Login } from "../types"

const LoginSchema = new mongoose.Schema<Login>({
  email: {
    type: String,
    unique: true,
    required: true,
  },

  password: {
    type: String,
    required: true,
  },
})

//we refear to Users collection
export default mongoose.model<Login>("User", LoginSchema)
