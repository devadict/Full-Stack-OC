import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Observable, fromEvent, map, startWith, tap, shareReplay, of } from 'rxjs';
import { AuthService } from 'src/app/features/auth/services/auth.service';
import { PostResponse } from 'src/app/features/posts/interfaces/api/postResponse.interface';
import { Topic } from 'src/app/features/topics/interfaces/topic.interface';
import { TopicsService } from 'src/app/features/topics/service/topics.service';
import { User } from 'src/app/interfaces/user.interface';
import { Location } from '@angular/common';

@Component({
  selector: 'app-me',
  templateUrl: './me.component.html',
  styleUrls: ['./me.component.scss']
})
export class MeComponent implements OnInit {

  public screenWidth$ = fromEvent(window, 'resize')
  .pipe(
    map(() => window.innerWidth),
    startWith(window.innerWidth),
    shareReplay(1)
  );

  
  public readonly nbCols$ = this.screenWidth$.pipe(map(width => width <= 620 ? 1 : 2));

  public user: User | undefined;
  public form!: FormGroup;
  subscribedTopics$: Observable<Topic[]> | undefined;


  constructor(private authService: AuthService,
    private fb: FormBuilder,
    private topicsService: TopicsService,
    private location: Location) { }

  public ngOnInit(): void {
    this.authService.me().subscribe(
      (user: User) => this.user = user
    );
   
    this.initForm();
    this.loadSubscribedTopics();

  }

  updateUser(userId: number) {
    const id: number = userId;
    const name: string = this.form.value.name ?? "";
    const email: string = this.form.value.email ?? "";

    this.authService.updateUser(name, email, id).subscribe(
      (messageResponse: PostResponse) => {
        console.log(messageResponse)
      });
    
  }

  loadSubscribedTopics(): void {
    this.topicsService.all().pipe(
      map((topics: Topic[]) => this.filterSubscribedTopics(topics)),
      tap((subscribedTopics: Topic[]) => this.subscribedTopics$ = of(subscribedTopics))
    ).subscribe();
  }
  
  filterSubscribedTopics(topics: Topic[]): Topic[] {
    return topics.filter((topic: Topic) => this.isUserSubscribed(topic));
  }
  
  isUserSubscribed(topic: Topic): boolean {
    return !!topic.subscribers.find((user: User) => user.id === this.user?.id);
  }
  
  private initForm(user?: User) {
    this.form = this.fb.group({
      email: [this.user?.email , [Validators.required]],
      name: [this.user?.name, [Validators.required, Validators.min(3)]],
      userId: this.user?.id
    });
  }

  unsubscribeFromTopic(topic: Topic): void {
    const userIndex = topic.subscribers.findIndex(user => user.id === this.user?.id);
    if (userIndex !== -1) {
      this.topicsService.unsubscribeFromTopic(topic.id).subscribe(
        () => {
          topic.subscribers.splice(userIndex, 1);
          console.log('Successfully unsubscribed from the topic:', topic.name);
          window.location.reload();
        },
        (error: any) => {
          console.error('Error unsubscribing from the topic:', error);
        }
      );
    }
  }

  public back() {
    window.history.back();
  }

}