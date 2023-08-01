import { Injectable } from '@angular/core';
import { TopicsResponse } from '../interfaces/api/topicsResponse.interface';
import { Topic } from '../interfaces/topic.interface';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { TopicResponse } from '../interfaces/api/topicResponse.interface';

@Injectable({
  providedIn: 'root'
})
export class TopicsService {

  private pathService = 'api/topic';

  constructor(private httpClient: HttpClient) { }

  public all(): Observable<Topic[]> {
    return this.httpClient.get<Topic[]>(this.pathService);
  }

  public subscribeToTopic(topicId: number): Observable<TopicResponse> {
    return this.httpClient.post<TopicResponse>(`${this.pathService}/${topicId}/follow`, null);
  }

  public unsubscribeFromTopic(topicId: number): Observable<any> {
    return this.httpClient.delete(`${this.pathService}/${topicId}/unfollow`);
  }
  public detail(id: string): Observable<Topic> {
    return this.httpClient.get<Topic>(`${this.pathService}/${id}`);
  }
}
