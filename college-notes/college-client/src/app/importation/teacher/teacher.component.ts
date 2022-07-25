import { HttpClient, HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Role } from 'src/app/models/role-model';
import { Teacher } from 'src/app/models/teacher-model';
import { RoleService } from '../role.service';
import { TeacherService } from '../teacher.service';

@Component({
  selector: 'app-teacher',
  templateUrl: './teacher.component.html',
  styleUrls: ['./teacher.component.css']
})
export class ImportTeacherComponent implements OnInit {

  listOfTeacher: Observable<Teacher[]>;
  loaded = 0;
  currentFileUpload: File;


  constructor(private http: HttpClient, private teacherService: TeacherService) { }

  ngOnInit(): void {
    this.loadTeacher();
  }

  // Selected file is stored into selectedFiles.
  selectFile(event) {
    this.currentFileUpload = event.target.files[0];
  }

  // Uploads the file to backend server.
  upload() {
    this.teacherService.saveTeacherFile(this.currentFileUpload)
      .subscribe(event => {
        if (event instanceof HttpResponse) {
          this.loadTeacher();
        }
      });
  }

  loadTeacher() {
    this.listOfTeacher = this.teacherService.getAllTeacher();
  }

}
