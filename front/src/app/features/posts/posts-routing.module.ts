import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ListComponent } from './components/list/list.component';
import { DetailComponent } from './components/detail/detail.component';
import { FormComponent } from './components/form/form.component';

const routes: Routes = [
  {title: 'Posts', path: '', component: ListComponent},
  {title: 'Posts - detail', path: 'detail/:id', component: DetailComponent},
  {title: 'Posts - update', path: 'update/:id', component: FormComponent},
  {title: 'Posts - create', path: 'create', component: FormComponent},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PostsRoutingModule { }
