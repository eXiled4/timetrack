export class User {
  id: number;
  name: string;
  email: string;
  position: string;
  timesheets: any[];

  constructor(user: Partial<User> = {}) {
    this.id = user.id || 0;
    this.name = user.name || '';
    this.email = user.email || '';
    this.position = user.position || '';
    this.timesheets = user.timesheets || [];
  }
}
