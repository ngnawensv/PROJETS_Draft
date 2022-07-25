import { HttpClient, HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { UserService } from 'src/app/importation/user.service';
import { User } from 'src/app/models/user-model';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class ImportUserComponent implements OnInit {

  listOfUser: Observable<User[]>;
  loaded = 0;
  currentFileUpload: File;


  constructor(private http: HttpClient, private userService: UserService) { }

  ngOnInit(): void {
    this.loadUser();
  }

  // Selected file is stored into selectedFiles.
  selectFile(event) {
    this.currentFileUpload = event.target.files[0];
  }

  // Uploads the file to backend server.
  upload() {
    this.userService.saveUserFile(this.currentFileUpload)
      .subscribe(event => {
        if (event instanceof HttpResponse) {
          this.loadUser();
        }
      });
  }

  loadUser() {
    this.listOfUser = this.userService.getAllUser();
  }
}
