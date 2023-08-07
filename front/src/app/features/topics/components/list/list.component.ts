import { Component, OnDestroy, OnInit } from '@angular/core';
import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { TopicsService } from '../../service/topics.service';
import { User } from 'src/app/interfaces/user.interface';
import { Observable, fromEvent, map, startWith, tap, shareReplay, Subscription } from 'rxjs';
import { SessionService } from 'src/app/services/session.service';
import { Topic } from '../../interfaces/topic.interface';

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.scss']
})
export class ListComponent implements OnInit, OnDestroy {

  public screenWidth$ = fromEvent(window, 'resize')
  .pipe(
    map(() => window.innerWidth),
    startWith(window.innerWidth),
    shareReplay(1)
  );

  
  public readonly nbCols$ = this.screenWidth$.pipe(map(width => width <= 620 ? 1 : 2));

  public topics$!: Observable<Topic[]> | undefined;

  userId: number | undefined;

  isSmallScreen!: boolean;
  gridCols!: number;
  private breakpointSubscription!: Subscription;
  

  constructor(
    private topicsService: TopicsService,
    private sessionService: SessionService,
    private breakpointObserver: BreakpointObserver
  ) { }

  ngOnInit(): void {
    const currentUser = this.sessionService.user;
    this.userId = currentUser ? currentUser.id : undefined;
    this.topics$ = this.topicsService.all().pipe(
      tap((topics: Topic[]) => {
        topics.forEach((topic: Topic) => {
          topic.isUserSubscribed = this.isUserSubscribed(topic);
        });
      })
    );
    this.breakpointSubscription = this.breakpointObserver.observe([Breakpoints.Small])
    .subscribe(result => {
      this.isSmallScreen = result.matches;
      this.updateGridCols();
    });
  }

ngOnDestroy(): void {
  this.breakpointSubscription.unsubscribe(); 
}

  updateGridCols() {
    this.gridCols = this.isSmallScreen ? 1 : 2;
  }

  isUserSubscribed(topic: Topic): boolean {
    return !!topic.subscribers.find(user => user.id === this.userId);
  }

  subscribeToTopic(topic: Topic): void {
    if (!topic.subscribers.find(user => user.id === this.userId)) {
      this.topicsService.subscribeToTopic(topic.id).subscribe(
        () => {
          topic.subscribers.push({ id: this.userId } as User);
          console.log('Successfully subscribed to the topic:', topic.name);
        },
        (error: any) => {
          console.error('Error subscribing to the topic:', error);
        }
      );
    }
  }

  unsubscribeFromTopic(topic: Topic): void {
    const userIndex = topic.subscribers.findIndex(user => user.id === this.userId);
    if (userIndex !== -1) {
      this.topicsService.unsubscribeFromTopic(topic.id).subscribe(
        () => {
          topic.subscribers.splice(userIndex, 1);
          console.log('Successfully unsubscribed from the topic:', topic.name);
        },
        (error: any) => {
          console.error('Error unsubscribing from the topic:', error);
        }
      );
    }
  }
  get user(): User | undefined {
    return this.sessionService.user;
  }
}
