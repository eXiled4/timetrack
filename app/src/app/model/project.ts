export class Project {
  name: string;
  description: string | null;
  constructor(project: Partial<Project> = {}) {
    this.name = project?.name || '';
    this.description = project?.description || '';
  }
}
