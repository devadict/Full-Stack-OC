import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { OwnerInfoComponent } from './components/owner-info/owner-info.component';
import { HeaderComponent } from './components/header/header.component';

@NgModule({
  declarations: [
    OwnerInfoComponent,
    HeaderComponent
  ],
  imports: [
    CommonModule
  ],
  exports: [
    OwnerInfoComponent
  ],
})
export class SharedModule { }
