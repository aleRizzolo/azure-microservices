import mongoose from "mongoose"

import { User } from "../types"

const UserSchema = new mongoose.Schema<User>({
    email: {
        type: String,
        unique: true,
        required: true
    },

    password: {
        type: String,
        required: true
    },

    firstName: {
        type: String,
        required: true
    },

    lastName: {
        type: String,
        required: true
    },
})

export default mongoose.model<User>("User", UserSchema)
