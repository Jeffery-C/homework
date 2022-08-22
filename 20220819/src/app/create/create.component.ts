import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { UserModel } from '../model/UserModel';
import { UserService } from '../service/user.service';

@Component({
  selector: 'app-create',
  templateUrl: './create.component.html',
  styleUrls: ['./create.component.css']
})
export class CreateComponent implements OnInit {

  userForm: FormGroup;

  constructor(private userService: UserService, private formBuilder: FormBuilder, private router: Router) { }

  ngOnInit(): void {
    this.createForm();
  }

  createForm() {
    this.userForm = this.formBuilder.group({
      id: null,
      name: ['', Validators.required],
      age: [null, Validators.required]
    })
  }

  createUser() {

    if (this.userForm.status == "VALID") {
      console.log(this.userForm.value.name);
      this.userService.createUser({
        name: this.userForm.value.name,
        age: this.userForm.value.age
      })
        .subscribe((result) => {
          this.router.navigate(['']);
          this.userForm.reset();
        });
    }
  }

}
