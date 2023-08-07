import { Component, OnInit } from '@angular/core';
import { PostsService } from '../../service/posts.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { SessionService } from 'src/app/services/session.service';
import { Post } from '../../interfaces/post.interface';
import { Message } from '../../interfaces/message.interface';
import { PostResponse } from '../../interfaces/api/postResponse.interface';

@Component({
  selector: 'app-detail',
  templateUrl: './detail.component.html',
  styleUrls: ['./detail.component.scss']
})
export class DetailComponent implements OnInit {
  public messageForm!: FormGroup;
  public post: Post | undefined;
  public comments: Message[] | undefined; 


  constructor( private route: ActivatedRoute,
    private fb: FormBuilder,
    private postsService: PostsService,
    private sessionService: SessionService) { }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id')!;
    this.postsService.getComments(id).subscribe((comments: Message[]) => this.comments = comments);
    this.initForm();
    this.postsService
      .detail(id)
      .subscribe((post: Post) => this.post = post);
  }
  
  public back() {
    window.history.back();
  }

  public sendMessage(): void {
  
    const userId: number= this.sessionService.user?.id ?? 0;
    const postId: number = Number(this.post!.id || this.route.snapshot.paramMap.get('id')!);
    const content: string = this.messageForm.value.content
    
    this.postsService.createMessage(userId, postId, content).subscribe(
      (messageResponse: PostResponse) => {
       console.log(messageResponse)
      });
      window.location.reload();
  }

  private initForm(message?: Message): void {
    console.log(message);
    console.log(this.sessionService.user!.id);
    this.messageForm = this.fb.group({
      content: [message ? message.content : '', [Validators.required]],
      postId: [0, [Validators.required]],
      userId: this.sessionService.user?.id
    });
  }

}
