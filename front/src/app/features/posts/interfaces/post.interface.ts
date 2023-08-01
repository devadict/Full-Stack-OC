import { User } from "src/app/interfaces/user.interface";
import { Topic } from "../../topics/interfaces/topic.interface";
import { Message } from "./message.interface";

export interface Post {
	id: number,
	title: string,
    content: number,
    topic: Topic,
    author: User,
    comments: Message[],
	date: Date,
}