import { StudentsDto, Student } from './model/students';
import { HttpErrorResponse } from '@angular/common/http';
import { HttpParams } from '@angular/common/http';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class StudentService {

  private Url = 'http://localhost:8080/api/students';
  private SearchUrl = '/search';

  constructor(private http: HttpClient) { }

  public getStudentList(firstName: string, lastName: string, currentPage: number, pageSize: number): Observable<StudentsDto> {
    console.log('Search students.');
    // Initialize Params Object
    let params = new HttpParams();
    // Begin assigning parameters
    params = params.append('firstName', firstName);
    params = params.append('lastName', lastName);
    params = params.append('currentPage', '' + currentPage);
    params = params.append('pageSize', '' + pageSize);
    return this.http.get<StudentsDto>(this.Url + this.SearchUrl, { params: params });
  }

  public getStudent(studentId: number): Observable<Student> {
    console.log('Get a student.');
    return this.http.get<Student>(this.Url + '/' + studentId).pipe(
      catchError(this.handleError)
    );
  }

  public addStudent(student: Student): Observable<Student> {
    console.log('Add a student.');
    return this.http.post<Student>(this.Url, student).pipe(
      catchError(this.handleError)
    );
  }

  public updateStudent(student: Student): Observable<Student> {
    console.log('Update a student.');
    return this.http.put<Student>(this.Url + '/' + student.id, student).pipe(
      catchError(this.handleError)
    );
  }

  public deleteStudent(studentId: number): Observable<Student> {
    console.log('Delete a student.');
    return this.http.delete<Student>(this.Url + '/' + studentId).pipe(
      catchError(this.handleError)
    );
  }


  private handleError(error: HttpErrorResponse) {
    if (error.error instanceof ErrorEvent) {
      // A client-side or network error occurred. Handle it accordingly.
      console.error('An error occurred:', error.error.message);
    } else {
      // The backend returned an unsuccessful response code.
      // The response body may contain clues as to what went wrong,
      console.error(
        `Backend returned code ${error.status}, ` +
        `body was: ${error.error}`);
    }
    // return an observable with a user-facing error message
    return throwError(
      'Something bad happened; please try again later.');
  };
}
