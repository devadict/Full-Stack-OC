import { User } from "src/app/interfaces/user.interface";

export interface Message {
    id: number,
    content: string,
    user: User
}