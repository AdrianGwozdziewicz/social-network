import {User} from "./user";

export interface Post {

  message?: string;
  author?: User;
  createdDate?: string;

}
