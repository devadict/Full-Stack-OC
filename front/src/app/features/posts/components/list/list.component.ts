import { Component, OnInit } from '@angular/core';
import { PostsService } from '../../service/posts.service';
import { SessionService } from 'src/app/services/session.service';
import { User } from 'src/app/interfaces/user.interface';
import { fromEvent, map, startWith, shareReplay } from 'rxjs';

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.scss'],
})
export class ListComponent implements OnInit {
  public screenWidth$ = fromEvent(window, 'resize').pipe(
    map(() => window.innerWidth),
    startWith(window.innerWidth),
    shareReplay(1)
  );

  public readonly nbCols$ = this.screenWidth$.pipe(map((width) => (width <= 620 ? 1 : 2)));

  public posts$ = this.postsService.all();
  public isSortingAsc = false; // To track the sorting order

  constructor(
    private sessionService: SessionService,
    private postsService: PostsService
  ) {}

  ngOnInit(): void {}

  get user(): User | undefined {
    return this.sessionService.user;
  }

  sortPostsByDate() {
    this.isSortingAsc = !this.isSortingAsc; // Toggle the sorting order
    this.posts$ = this.posts$.pipe(
      map((data) => {
        const sortedPosts = data.posts.slice();
        sortedPosts.sort((a, b) => {
          const dateA = new Date(a.date).getTime();
          const dateB = new Date(b.date).getTime();
          return this.isSortingAsc ? dateA - dateB : dateB - dateA;
        });
        return { ...data, posts: sortedPosts };
      })
    );
  }
}
