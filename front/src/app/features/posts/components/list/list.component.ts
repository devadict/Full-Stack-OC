import { Component, OnInit } from '@angular/core';
import { PostsService } from '../../service/posts.service';
import { SessionService } from 'src/app/services/session.service';
import { User } from 'src/app/interfaces/user.interface';
import { fromEvent, map, startWith, shareReplay } from 'rxjs';
@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.scss']
})
export class ListComponent implements OnInit {

  public screenWidth$ = fromEvent(window, 'resize')
  .pipe(
    map(() => window.innerWidth),
    startWith(window.innerWidth),
    shareReplay(1)
  );

  public readonly nbCols$ = this.screenWidth$.pipe(map(width => width <= 620 ? 1 : 2));


  public posts$ = this.postsService.all();

  constructor(
    private sessionService: SessionService,
    private postsService: PostsService
  ) { }

  ngOnInit(): void {
  }

  get user(): User | undefined {
    return this.sessionService.user;
  }
}
