import {Component, Input, OnInit} from '@angular/core';
import {UserService} from "../../services/user.service";
import {Observable, Subject} from "rxjs";
import {Post} from "../../model/post";
import {User} from "../../model/user";

@Component({
  selector: 'app-wall',
  templateUrl: './wall.component.html',
  styleUrls: ['./wall.component.css']
})
export class WallComponent implements OnInit {

  @Input() username;
  @Input() refreshSubject: Subject<User>;

  posts$: Observable<Post[]>;

  constructor(private userService: UserService) {
  }

  ngOnInit() {
    this.loadPosts(this.username);
    this.refreshSubject.pipe().subscribe((user) => this.loadPosts(user.username));
  }

  loadPosts(username?: string) {
    this.posts$ = this.userService.getUserWall(username ? username : this.username);
  }

}
