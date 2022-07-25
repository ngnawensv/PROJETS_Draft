import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { TokenStorageService } from '../auth/token-storage.service';
import { EnseignementService } from '../enseignement.service';
import { ClasseEnseignement } from '../models/classe-enseignement-model';

@Component({
  selector: 'app-classe-list',
  templateUrl: './classe.component.html',
  styleUrls: ['./classe.component.css']
})
export class ClasseComponent implements OnInit {

  listOfClasse: Observable<ClasseEnseignement[]>;

  constructor(private enseignementService: EnseignementService,
    private tokenStorage: TokenStorageService) { }

  ngOnInit(): void {
    this.reloadData();
  }

  reloadData() {
    let user = this.tokenStorage.getUser();
    if (user) {
      this.listOfClasse = this.enseignementService.getEnseignementForTeacher(user.username);
    }
  }

}
