import { StudentsDto } from '../model/students';
import { StudentService } from '../student.service';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-students',
  templateUrl: './students.component.html',
  styleUrls: ['./students.component.css']
})
export class StudentsComponent implements OnInit, OnDestroy {

  public title: String = 'Student List';

  public currentPage = 0;
  public readonly pageSize = 3;
  public firstName = '';
  public lastName = '';

  public studentsDto: StudentsDto = new StudentsDto();
  public pages: number[] = [1];


  private studentsSubscription: Subscription;
  private studentDeleteSubscription: Subscription;

  constructor(private studentService: StudentService) { }

  ngOnInit() {
    this.getStudents();
  }

  private getStudents(): void {
    console.log('Get Student list', this.studentService);
    this.studentsSubscription = this.studentService.getStudentList(this.firstName, this.lastName, this.currentPage, this.pageSize)
      .subscribe((studentDto) => {
        this.studentsDto = studentDto;
        this.pages = [];
        for (let i = 1; i <= this.studentsDto.totalPage; i++) {
          this.pages.push(i);
        }
      });
  }

  public search(): void {
    console.log('f ' + this.firstName + ' l ' + this.lastName);
    this.currentPage = 0;
    this.getStudents();
  }

  public deleteStudent(studentId: number): void {
    this.studentDeleteSubscription = this.studentService.deleteStudent(studentId).subscribe((result) => {
      this.getStudents();
    });
  }


  public onPrevious(): void {
    console.log('onPrevious is clicked');
    if (this.currentPage > -1) {
      this.currentPage -= 1;
      this.getStudents();
    }
  }

  public goToPage(pageNumber: number): void {
    console.log('onPage is clicked');
    this.currentPage = pageNumber - 1;
    this.getStudents();
  }

  public onNext(): void {
    console.log('pageNumber is clicked');
    if (this.currentPage < this.studentsDto.totalPage - 1) {
      this.currentPage += 1;
      this.getStudents();
    }
  }

  ngOnDestroy() {
    if (this.studentsSubscription) {
      this.studentsSubscription.unsubscribe();
    }
    if (this.studentDeleteSubscription) {
      this.studentDeleteSubscription.unsubscribe();
    }
  }
}
