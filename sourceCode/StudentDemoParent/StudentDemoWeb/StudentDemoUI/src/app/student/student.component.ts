import { Student } from '../model/students';
import { StudentService } from '../student.service';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-student',
  templateUrl: './student.component.html',
  styleUrls: ['./student.component.css']
})
export class StudentComponent implements OnInit, OnDestroy {

  public title: String = 'Student edit';
  public showMessage = false;
  public successMessage = 'Successfully saved';

  public student: Student;

  private studentGetSubscription: Subscription;
  private studentAddSubscription: Subscription;
  private studentUpdateSubscription: Subscription;
  private studentDeleteSubscription: Subscription;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private studentService: StudentService
  ) { }

  ngOnInit() {
    this.parseStudentId();
  }

  private parseStudentId(): void {
    const studentIdParam = this.route.snapshot.paramMap.get('studentId');
    this.student = new Student();
    if (studentIdParam === 'new') {
      this.student.id = 0;
    } else {
      this.student.id = Number(studentIdParam);
      this.getStudent();
    }
  }

  private getStudent(): void {
    if (this.student.id > 0) {
      this.studentGetSubscription = this.studentService.getStudent(this.student.id).subscribe((student) => {
        this.student = student;
      });
    }
  }

  public saveStudent(): void {
    if (this.student.id === 0) {
      this.createStudent();
    } else {
      this.updateStudent();
    }
  }


  private createStudent(): void {
    this.studentAddSubscription = this.studentService.addStudent(this.student).subscribe((updateStudent) => {
      this.student = updateStudent;
      this.setShowMessage(true);
    });
  }

  private updateStudent(): void {
    this.studentUpdateSubscription = this.studentService.updateStudent(this.student).subscribe((updateStudent) => {
      this.student = updateStudent;
      this.setShowMessage(true);
    });
  }

  public setShowMessage(status: boolean): void {
    this.showMessage = status;
  }

  public deleteStudent(): void {
    this.studentDeleteSubscription = this.studentService.deleteStudent(this.student.id).subscribe((result) => {
      if (result) {
        this.router.navigate(['/students']);
      }
    });
  }

  ngOnDestroy() {
    if (this.studentGetSubscription) {
      this.studentGetSubscription.unsubscribe();
    }
    if (this.studentAddSubscription) {
      this.studentAddSubscription.unsubscribe();
    }
    if (this.studentUpdateSubscription) {
      this.studentUpdateSubscription.unsubscribe();
    }
    if (this.studentDeleteSubscription) {
      this.studentDeleteSubscription.unsubscribe();
    }
  }

}
