import {Component, OnInit} from '@angular/core';
import {UserService} from "./services/user.service";
import {Subject} from "rxjs";
import {User} from "./model/user";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  private readonly refresh$ = new Subject<User>();

  users: User[];
  selectedUser: User;
  selectedUserName: string;

  newPost = false;
  wallOn = false;
  postOn = false;


  constructor(private userService: UserService) {
  }

  ngOnInit(): void {
    this.loadUsers();
  }

  loadUsers() {
    this.users = [];
    this.userService.getUsers().subscribe(res => {
      this.users = res;
      const updatedUser = this.users.find(u => u.username === this.selectedUserName);
      if (!!updatedUser) {
        this._selectUser(updatedUser);
        this.refresh$.next(updatedUser);
      }
      this.newPost = false;
    });
  }

  showWall(user: User) {
    this._reset();
    this._selectUser(user);
    this.postOn = true;
  }

  showTimeline(user: User) {
    this._reset();
    this._selectUser(user);
    this.wallOn = true;
  }

  follow(usernameToFollow: string) {
    this.userService.follow(this.selectedUserName, usernameToFollow)
      .subscribe(() => this.loadUsers());
  }

  unFollow(usernameToFollow: string) {
    this.userService.unFollow(this.selectedUserName, usernameToFollow)
      .subscribe(() => this.loadUsers());
  }

  canFollow(username) {
    return !this.selectedUser.followerNames.includes(username);
  }

  private _selectUser(user: User) {
    this.selectedUser = user;
    this.selectedUserName = user.username;
    this.refresh$.next(user);
  }

  _reset() {
    this.newPost = false;
    this.postOn = false;
    this.wallOn = false;
  }

}
