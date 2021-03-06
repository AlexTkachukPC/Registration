import { Component, OnInit } from '@angular/core';
import { Observable } from "rxjs";
import { UserService } from "../user/user.service";
import { User } from '../user/user';
import { Router } from '@angular/router';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {

  users$: Observable<User[]>

  constructor(private userService: UserService,
              private router: Router) {}

  ngOnInit(): void {
      this.reloadData();
  }

  reloadData() {
    this.users$ = this.userService.getUsersList();
  }

  deleteUser(id: number) {
    this.userService.deleteUser(id)
      .subscribe(_ => this.reloadData());
  }

  userDetails(id: number) {
    this.router.navigate(['users', id]).then();
  }
}
