import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { UserModel } from '../model/UserModel';
import { UserService } from '../service/user.service';

@Component({
  selector: 'app-update',
  templateUrl: './update.component.html',
  styleUrls: ['./update.component.css']
})
export class UpdateComponent implements OnInit {

  userForm: FormGroup;

  id = 0;

  user: UserModel;

  constructor(private route: ActivatedRoute, private userService: UserService, private formBuilder: FormBuilder, private router: Router) {
    this.createForm();
  }

  createForm() {
    this.userForm = this.formBuilder.group({
      id: null,
      name: ['', Validators.required],
      age: [null, Validators.required]
    })
  }

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
    this.userForm.controls["name"].setValue(this.user.name);
    this.userForm.controls["age"].setValue(this.user.age);
  }

  updateUser() {
    if (this.id < 1) return;
    if (this.userForm.status == "VALID") {
      this.userService.updateUser({
        name: this.userForm.value.name,
        age: this.userForm.value.age
      }, this.id)
        .subscribe((result) => {
          this.router.navigate(['','user', this.user.id]);
          this.userForm.reset();
        });
    }
  }
}
