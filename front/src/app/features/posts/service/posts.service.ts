import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { PostsResponse } from '../interfaces/api/postsResponse.interface';
import { Post } from '../interfaces/post.interface';
import { PostResponse } from '../interfaces/api/postResponse.interface';
import { MessageRequest } from '../interfaces/api/messageRequest.interface';
import { Message } from '../interfaces/message.interface';

@Injectable({
  providedIn: 'root'
})
export class PostsService {

  private pathService = 'api/post';

  constructor(private httpClient: HttpClient) { }

  public all(): Observable<PostsResponse> {
    return this.httpClient.get<PostsResponse>(this.pathService);
  }

  public detail(id: string): Observable<Post> {
    return this.httpClient.get<Post>(`${this.pathService}/${id}`);
  }

  public getComments(id: string): Observable<Message[]> {
    return this.httpClient.get<Message[]>(`${this.pathService}/${id}/comments`);
  }

  public create(title: string, content: string, topicId: number): Observable<PostResponse> {
    const form = { 'topicId': topicId, 'content': content, 'title': title };
    return this.httpClient.post<PostResponse>(this.pathService, form);
  }

  public createMessage(userId: number, postId: number, content: string): Observable<PostResponse> {
    const message = { 'postId': postId, 'content': content, userId: userId};
    return this.httpClient.post<PostResponse>(`${this.pathService}/${message.postId}/comment`, message);
  }
  

  // public create(form: FormData)
}
