import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { UpdateRoutingModule } from './update-routing.module';
import { UpdateComponent } from './update.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';


@NgModule({
  declarations: [
    UpdateComponent
  ],
  imports: [
    CommonModule,
    UpdateRoutingModule,
    FormsModule,
    ReactiveFormsModule
  ]
})
export class UpdateModule { }
