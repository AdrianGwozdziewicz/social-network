import {Component, Input, OnInit} from '@angular/core';
import {Observable, Subject} from "rxjs";
import {Post} from "../../model/post";
import {UserService} from "../../services/user.service";
import {User} from "../../model/user";

@Component({
  selector: 'app-timeline',
  templateUrl: './timeline.component.html',
  styleUrls: ['./timeline.component.css']
})
export class TimelineComponent implements OnInit {

  @Input() username;
  @Input() refreshSubject: Subject<User>;
  posts$: Observable<Post[]>;

  constructor(private userService: UserService) {
  }

  ngOnInit() {
    this.loadPosts(this.username);
    this.refreshSubject.pipe().subscribe((user) => this.loadPosts(user.username));
  }

  loadPosts(username: string) {
    this.posts$ = this.userService.getUserTimeline(username ? username : this.username);
  }


}
