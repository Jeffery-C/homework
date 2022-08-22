import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UserModel } from '../model/UserModel';
import { UserService } from '../service/user.service';
import { UserModule } from './user.module';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {

  id = 0;

  user: UserModel;

  constructor(private route: ActivatedRoute, private userService: UserService, private router: Router) { }

  ngOnInit(): void {

    const idString = this.route.snapshot.paramMap.get('id');

    if (idString === null || idString?.trim() === "" || isNaN(+idString) || !Number.isInteger(+idString)) {
      return;
    }
    if (+idString < 1) return;
    this.id = +idString;
    this.getUserById(this.id);
  }

  getUserById(id: number) {
    this.userService.getUserById(id).subscribe((result) => this.success(result));
  }

  success(value: UserModel) {
    this.user = value;
  }

  deleteUser() {
    let doDelete = confirm('Are you sure you want to delete this user?');
    if (!doDelete) return;

    this.userService.deleteUser(this.id)
    .subscribe((result) => {
      this.router.navigate(['']);
    });
  }


}
