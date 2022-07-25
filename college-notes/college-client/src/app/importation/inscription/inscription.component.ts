import { HttpClient, HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Inscription } from 'src/app/models/inscription-model';
import { InscriptionService } from '../inscription.service';

@Component({
  selector: 'app-inscription',
  templateUrl: './inscription.component.html',
  styleUrls: ['./inscription.component.css']
})
export class ImportInscriptionComponent implements OnInit {

  listOfInscription: Observable<Inscription[]>;
  loaded = 0;
  currentFileUpload: File;


  constructor(private http: HttpClient, private inscriptionService: InscriptionService) { }

  ngOnInit(): void {
    this.loadInscription();
  }

  // Selected file is stored into selectedFiles.
  selectFile(event) {
    this.currentFileUpload = event.target.files[0];
  }

  // Uploads the file to backend server.
  upload() {
    this.inscriptionService.saveInscriptionFile(this.currentFileUpload)
      .subscribe(event => {
        if (event instanceof HttpResponse) {
          this.loadInscription();
        }
      });
  }

  loadInscription() {
    this.listOfInscription = this.inscriptionService.getAllInscription();
  }

}
