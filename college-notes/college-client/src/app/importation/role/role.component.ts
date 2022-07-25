import { HttpClient, HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Role } from 'src/app/models/role-model';
import { RoleService } from '../role.service';

@Component({
  selector: 'app-role',
  templateUrl: './role.component.html',
  styleUrls: ['./role.component.css']
})
export class ImportRoleComponent implements OnInit {

  listOfRole: Observable<Role[]>;
  loaded = 0;
  currentFileUpload: File;


  constructor(private http: HttpClient, private roleService: RoleService) { }

  ngOnInit(): void {
    this.loadRole();
  }

  // Selected file is stored into selectedFiles.
  selectFile(event) {
    this.currentFileUpload = event.target.files[0];
  }

  // Uploads the file to backend server.
  upload() {
    this.roleService.saveRoleFile(this.currentFileUpload)
      .subscribe(event => {
        if (event instanceof HttpResponse) {
          this.loadRole();
        }
      });
  }

  loadRole() {
    this.listOfRole = this.roleService.getAllRole();
  }

}
