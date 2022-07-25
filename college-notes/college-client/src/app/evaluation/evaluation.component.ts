import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from "rxjs";

import { EvaluationService } from "../evaluation.service";

import { Evaluation } from '../evaluation/evaluation.model';

@Component({
  selector: 'app-evaluation',
  templateUrl: './evaluation.component.html',
  styleUrls: ['./evaluation.component.css']
})
export class EvaluationComponent implements OnInit {

  listOfEvaluation: Observable<Evaluation[]>;
  public classe:any;
  public enseignement;

  public currentEvaluation;
  public note;
  public selectedRow:number;

  constructor(private evaluationService: EvaluationService,
    private router:Router) {
       let data = this.router.getCurrentNavigation().extras.state;
        if(data){
          this.classe=data.classe;
          this.enseignement=data.enseignement;
        }
    }

  ngOnInit(): void {
    this.reloadData();
  }

  public selectEvaluation(event: any, item: any,i:number) {
    this.currentEvaluation = item;
    this.selectedRow =i;
  }

  reloadData() {
    this.listOfEvaluation = this.evaluationService.getNoteEvaluation(this.classe.id,this.enseignement.id,"I");
  }

  onNoteChange(note: number) {
    console.log(note);  
  }

}
