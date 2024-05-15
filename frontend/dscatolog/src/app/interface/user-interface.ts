export interface UserInterface {
  id: number;
  firstName: string;
  lastName: string;
  email: string;
  roles: [{id: number, authority: string}];
}
