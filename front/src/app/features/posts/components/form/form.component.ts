import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { SessionService } from 'src/app/services/session.service';
import { PostResponse } from '../../interfaces/api/postResponse.interface';
import { Post } from '../../interfaces/post.interface';
import { PostsService } from '../../service/posts.service';
import { Topic } from 'src/app/features/topics/interfaces/topic.interface';
import { TopicsService } from 'src/app/features/topics/service/topics.service';

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.scss']
})
export class FormComponent implements OnInit {

  public onUpdate: boolean = false;
  public postForm: FormGroup | undefined;

  public topics: Topic[] = [];

  private id: string | undefined;

  constructor(
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private matSnackBar: MatSnackBar,
    private topicsService: TopicsService,
    private postsService: PostsService,
    private sessionService: SessionService,
    private router: Router
  ) {
  }

  public ngOnInit(): void {  
    this.initForm();
    this.loadTopics()
  }

  public submit(): void {
    // const formData = new FormData();
    // formData.append('title', this.postForm!.get('title')?.value);
    // formData.append('content', this.postForm!.get('content')?.value);
    // formData.append('topicId', this.postForm!.get('topicId')?.value);
    const topicId: number = Number(this.postForm?.value.topicId);
    const title = this.postForm?.value.title;
    const content = this.postForm?.value.content;



      this.postsService
        .create(title, content, topicId)
        .subscribe((postResponse: PostResponse) => this.exitPage(postResponse));
    
  }

  private initForm(post?: Post): void {
    console.log(post);
    console.log(this.sessionService.user!.id);
    this.postForm = this.fb.group({
      title: [post ? post.title : '', [Validators.required]],
      content: [post ? post.content : '', [Validators.required]],
      topicId: [0, [Validators.required]]
    });
  }

  private loadTopics(): void {
    this.topicsService.all().subscribe(
      (topics: Topic[]) => {
        this.topics = topics;
      },
      (error: any) => {
        console.error('Error loading topics:', error);
      }
    );
  }

  private exitPage(postResponse: PostResponse): void {
    this.matSnackBar.open(postResponse.message, "Close", { duration: 3000 });
    this.router.navigate(['posts']);
  }
}