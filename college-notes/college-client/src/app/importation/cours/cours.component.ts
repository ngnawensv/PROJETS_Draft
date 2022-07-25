import { HttpClient, HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { CoursService } from 'src/app/importation/cours.service';
import { Cours } from 'src/app/models/cours-model';

@Component({
  selector: 'app-cours',
  templateUrl: './cours.component.html',
  styleUrls: ['./cours.component.css']
})
export class ImportCoursComponent implements OnInit {

  listOfCours: Observable<Cours[]>;
  loaded = 0;
  currentFileUpload: File;
  
  
  constructor(private http: HttpClient, private coursService: CoursService) { }

  ngOnInit(): void {
    this.loadCours();
  }

  // Selected file is stored into selectedFiles.
  selectFile(event) {
    this.currentFileUpload = event.target.files[0];
  }

  // Uploads the file to backend server.
  upload() {
    this.coursService.saveCoursFile(this.currentFileUpload)
      .subscribe(event => {
        if (event instanceof HttpResponse) {
          this.loadCours();
        }
      });
  }

  loadCours(){
    this.listOfCours = this.coursService.getAllCours();
  }

}
