import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from './features/auth/services/auth.service';
import { User } from './interfaces/user.interface';
import { SessionService } from './services/session.service';
import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
 public isResponsive!: boolean;

  constructor(
    private authService: AuthService,
    private router: Router,
    private sessionService: SessionService,
    private breakpointObserver: BreakpointObserver) {
  }

  public ngOnInit(): void {
    this.autoLog();
    this.breakpointObserver.observe([Breakpoints.Small, Breakpoints.Handset]).subscribe(result => {
      this.isResponsive = result.matches;
    });
  }

  public $isLogged(): Observable<boolean> {
    return this.sessionService.$isLogged();
  }

  public logout(): void {
    this.sessionService.logOut();
    this.router.navigate([''])
  }

  public autoLog(): void {
    this.authService.me().subscribe(
      (user: User) => {
        this.sessionService.logIn(user);
      },
      (_) => {
        this.sessionService.logOut();
      }
    )
  }
}
