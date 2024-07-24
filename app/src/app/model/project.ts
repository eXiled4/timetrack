import {User} from "./user";

export class Project {
  id: number;
  name: string;
  description: string | null;
  status: string | null;
  users: User[];

  constructor(project: Partial<Project> = {}) {
    this.id = project.id || 0;
    this.name = project?.name || '';
    this.description = project?.description || '';
    this.status = project?.status || '';
    this.users = project.users?.map(user => new User(user)) || [];
  }
}
