import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {User} from "../model/user";
import {Post} from "../model/post";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private readonly url = "http://localhost:8080/users/"

  constructor(private httpClient: HttpClient) {
  }

  getUsers(): Observable<User[]> {
    return this.httpClient.get<User[]>(this.url);
  }

  newPost(username: string, message: string): Observable<any> {
    return this.httpClient.post(this.url + username + "/posts", message);
  }

  editPost(username: string, postId: number, message: string): Observable<any> {
    return this.httpClient.put(`${this.url}${username}/posts/${postId}`, message);
  }

  deletePost(username: string, postId: number): Observable<any> {
    return this.httpClient.delete(`${this.url}${username}/posts/${postId}`);
  }

  follow(username: string, userToFollow: string): Observable<any> {
    return this.httpClient.post(this.url + username + "/follow/" + userToFollow, {});
  }

  unFollow(username: string, userToUnFollow: string): Observable<any> {
    return this.httpClient.post(this.url + username + "/un-follow/" + userToUnFollow, {});
  }

  getUserWall(username: string): Observable<Post[]> {
    return this.httpClient.get<Post[]>(this.url + username + "/wall");
  }

  getUserTimeline(username: any) {
    return this.httpClient.get<Post[]>(this.url + username + "/timeline");
  }
}
