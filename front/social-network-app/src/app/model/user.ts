import {Post} from "./post";

export interface User {

  username?: string
  followers?: User[];
  posts?: Post[];

  followerNames?: string[]
}
