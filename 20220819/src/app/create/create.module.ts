import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CreateRoutingModule } from './create-routing.module';
import { CreateComponent } from './create.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';


@NgModule({
  declarations: [
    CreateComponent
  ],
  imports: [
    CommonModule,
    CreateRoutingModule,
    FormsModule,
    ReactiveFormsModule
  ]
})
export class CreateModule { }
