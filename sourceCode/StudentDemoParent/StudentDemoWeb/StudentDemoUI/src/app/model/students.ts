export class StudentsDto {
  students: Student[];
  totalElement: number;
  totalPage: number;
  currentPage: number;
  pageSize: number;
}
export class Student {
  id: number;
  firstName: string;
  lastName: string;
  email: string;
}
