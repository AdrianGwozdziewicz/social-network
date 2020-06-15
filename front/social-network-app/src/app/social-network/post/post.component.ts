import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {UserService} from "../../services/user.service";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {Subject} from "rxjs";
import {User} from "../../model/user";

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.css']
})
export class PostComponent implements OnInit {

  @Input() username;
  @Input() refreshSubject: Subject<User>;
  @Output() onSave = new EventEmitter();
  postForm: FormGroup;

  constructor(private userService: UserService,
              private fb: FormBuilder) {
  }

  ngOnInit(): void {
    this.createForm(this.username);
    if (!!this.refreshSubject) {
      this.refreshSubject.pipe().subscribe((res) => this.createForm(res.username));
    }
  }

  private createForm(username: string) {
    this.postForm = this.fb.group({
        username: new FormControl(username, Validators.required),
        message: new FormControl(null, Validators.required)
      }
    );
  }

  addPost() {
    const formValue = this.postForm.getRawValue();
    console.log(formValue.username, formValue.message);
    this.userService.newPost(formValue.username, formValue.message)
      .subscribe(() => this.onSave.emit());
  }

}
