import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NEVER, Observable } from 'rxjs';
import { UserModel } from '../model/UserModel';
import { UserService } from '../service/user.service';

@Component({
  selector: 'app-all-users',
  templateUrl: './all-users.component.html',
  styleUrls: ['./all-users.component.css']
})
export class AllUsersComponent implements OnInit {

  userList: UserModel[] = [];

  constructor(private userService: UserService, private router: Router, private location: Location) {
    this.userList = [];
    this.getAllUsers();
  }

  ngOnInit(): void {
  }

  getAllUsers() {
    this.userService.getAllUsers().subscribe((result) => this.success(result));
  }

  success(value: UserModel[]) {
    this.userList = value;
  }

  deleteUser(id: number) {
    let doDelete = confirm('Are you sure you want to delete this user?');
    if (!doDelete) return;

    this.userService.deleteUser(id)
      .subscribe((result) => {
        // this.router.navigate(['user']);
        this.router.navigateByUrl("user/create", { skipLocationChange: true }).then(() => {
          console.log(decodeURI(this.location.path()));
          this.router.navigate(['user']);
        });
      });
  }

}
