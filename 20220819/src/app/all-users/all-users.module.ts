import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AllUsersRoutingModule } from './all-users-routing.module';
import { AllUsersComponent } from './all-users.component';


@NgModule({
  declarations: [
    AllUsersComponent
  ],
  imports: [
    CommonModule,
    AllUsersRoutingModule
  ]
})
export class AllUsersModule { }
