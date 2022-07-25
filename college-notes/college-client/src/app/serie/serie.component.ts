import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpEventType, HttpResponse } from '@angular/common/http';
import { tap } from 'rxjs/operators';
import { SerieService } from './serie.service';
import { Observable } from 'rxjs';
import { Serie } from './serie.model';

@Component({
  selector: 'app-serie',
  templateUrl: './serie.component.html',
  styleUrls: ['./serie.component.css']
})
export class SerieComponent implements OnInit {
  listOfSerie: Observable<Serie[]>;
  loaded = 0;
  currentFileUpload: File;

  constructor(private http: HttpClient, private serieService: SerieService) { }

  ngOnInit(): void {
    this.loadSerie();
  }

  // Selected file is stored into selectedFiles.
  selectFile(event) {
    this.currentFileUpload = event.target.files[0];
  }

  // Uploads the file to backend server.
  upload() {
    this.serieService.saveSerieFile(this.currentFileUpload)
      .pipe(tap(event => {
        if (event.type === HttpEventType.UploadProgress) {
          this.loaded = Math.round(100 * event.loaded / event.total);
        }
      })).subscribe(event => {
        if (event instanceof HttpResponse) {
          this.loadSerie();
        }
      });
  }

  loadSerie(){
    this.listOfSerie = this.serieService.getAllSerie();
  }

}
