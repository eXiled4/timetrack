export class Timesheet {
  id: number;
  date: Date;
  hours: number;
  comments: string;
  roleUserId: number;

  constructor(timesheet: Partial<Timesheet> = {}) {
    this.id = timesheet.id || 0;
    this.date = timesheet.date ? new Date(timesheet.date) : new Date();
    this.hours = timesheet.hours || 0;
    this.comments = timesheet.comments || '';
    this.roleUserId = timesheet.roleUserId || 0;
  }
}
