import { HttpClient, HttpEventType, HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { ClasseService } from 'src/app/importation/classe.service';
import { Classe } from 'src/app/classe/classe-model';


@Component({
  selector: 'app-classe',
  templateUrl: './classe.component.html',
  styleUrls: ['./classe.component.css']
})
export class ImportClasseComponent implements OnInit {

  listOfClasse: Observable<Classe[]>;
  loaded = 0;
  currentFileUpload: File;

  constructor(private http: HttpClient, private classeService: ClasseService) { }

  ngOnInit(): void {
    this.loadClasse();
  }

  // Selected file is stored into selectedFiles.
  selectFile(event) {
    this.currentFileUpload = event.target.files[0];
  }

  // Uploads the file to backend server.
  upload() {
    this.classeService.saveClasseFile(this.currentFileUpload)
      .subscribe(event => {
        if (event instanceof HttpResponse) {
          this.loadClasse();
        }
      });
  }

  loadClasse(){
    this.listOfClasse = this.classeService.getAllClasse();
  }

}
