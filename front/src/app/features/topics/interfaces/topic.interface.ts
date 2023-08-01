import { User } from "src/app/interfaces/user.interface";

export interface Topic {
    id: number,
    name: string,
    description: string,
    subscribers: User[],
    isUserSubscribed?: boolean
}