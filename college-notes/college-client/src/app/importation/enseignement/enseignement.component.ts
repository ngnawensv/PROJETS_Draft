import { HttpClient, HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Enseignement } from 'src/app/models/enseignement-model';
import { EnseignementService } from '../enseignement.service';

@Component({
  selector: 'app-enseignement',
  templateUrl: './enseignement.component.html',
  styleUrls: ['./enseignement.component.css']
})
export class ImportEnseignementComponent implements OnInit {

  listOfEnseignement: Observable<Enseignement[]>;
  loaded = 0;
  currentFileUpload: File;


  constructor(private http: HttpClient, private enseignementService: EnseignementService) { }

  ngOnInit(): void {
    this.loadEnseignement();
  }

  // Selected file is stored into selectedFiles.
  selectFile(event) {
    this.currentFileUpload = event.target.files[0];
  }

  // Uploads the file to backend server.
  upload() {
    this.enseignementService.saveEnseignementFile(this.currentFileUpload)
      .subscribe(event => {
        if (event instanceof HttpResponse) {
          this.loadEnseignement();
        }
      });
  }

  loadEnseignement() {
    this.listOfEnseignement = this.enseignementService.getAllEnseignement();
  }

}
