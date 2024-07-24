export class Timesheet {
  id: number;        // Unique identifier for the timesheet
  date: Date;        // The date when the work was performed
  hours: number;     // Number of hours worked on this date
  comments: string;  // Additional comments about the work performed
  roleUserId: number; // Reference to the associated user's ID

  constructor(timesheet: Partial<Timesheet> = {}) {
    this.id = timesheet.id || 0;
    this.date = timesheet.date ? new Date(timesheet.date) : new Date(); // Create a Date object from input or default to now
    this.hours = timesheet.hours || 0;
    this.comments = timesheet.comments || '';
    this.roleUserId = timesheet.roleUserId || 0; // Assuming there's a field to link back to the user via ID
  }
}
