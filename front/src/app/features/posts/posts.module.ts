import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PostsRoutingModule } from './posts-routing.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import {MatSelectModule} from '@angular/material/select';
import { ListComponent } from './components/list/list.component';
import { FormComponent } from './components/form/form.component';
import { DetailComponent } from './components/detail/detail.component';
import { MatGridListModule } from '@angular/material/grid-list';


const materialModules = [
  MatButtonModule,
  MatCardModule,
  MatFormFieldModule,
  MatSelectModule,
  MatIconModule,
  MatInputModule,
  MatSnackBarModule,
  MatGridListModule
];

@NgModule({
  declarations: [
    ListComponent,
    FormComponent,
    DetailComponent
  ],
  imports: [
    CommonModule,
    PostsRoutingModule,
    ReactiveFormsModule,
    FormsModule,
    
    PostsRoutingModule,
    ...materialModules
  ]
})
export class PostsModule { }
