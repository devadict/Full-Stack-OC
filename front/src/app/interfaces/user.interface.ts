import { Topic } from "../features/topics/interfaces/topic.interface";

export interface User {
	id: number,
	name: string,
	email: string,
	created_at: Date,
	updated_at: Date,
	subscribed: Topic[]
}