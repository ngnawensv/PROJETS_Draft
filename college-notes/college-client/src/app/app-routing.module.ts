import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { LoginComponent } from './login/login.component';
import { EvaluationComponent } from './evaluation/evaluation.component';
import { HomeComponent } from './home/home.component'

import { SerieComponent } from './serie/serie.component';
import { ClasseComponent } from './classe/classe.component';
import { ImportClasseComponent } from './importation/classe/classe.component';
import { AdminGuard } from './auth/admin.guard';
import { ImportCoursComponent } from './importation/cours/cours.component';
import { ImportInscriptionComponent } from './importation/inscription/inscription.component';
import { ImportRoleComponent } from './importation/role/role.component';
import { ImportUserComponent } from './importation/user/user.component';
import { ImportTeacherComponent } from './importation/teacher/teacher.component';
import { ImportEnseignementComponent } from './importation/enseignement/enseignement.component';


const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'home', component: HomeComponent, canActivate: [AdminGuard] },
  { path: 'evaluation', component: EvaluationComponent, canActivate: [AdminGuard] },
  { path: 'importation/serie', component: SerieComponent, canActivate: [AdminGuard] },
  { path: 'importation/classe', component: ImportClasseComponent, canActivate: [AdminGuard] },
  { path: 'importation/cours', component: ImportCoursComponent, canActivate: [AdminGuard] },
  { path: 'importation/inscription', component: ImportInscriptionComponent, canActivate: [AdminGuard] },
  { path: 'importation/role', component: ImportRoleComponent, canActivate: [AdminGuard] },
  { path: 'importation/utilisateur', component: ImportUserComponent, canActivate: [AdminGuard] },
  { path: 'importation/enseignant', component: ImportTeacherComponent, canActivate: [AdminGuard] },
  { path: 'importation/enseignement', component: ImportEnseignementComponent, canActivate: [AdminGuard] },
  { path: 'classe', component: ClasseComponent, canActivate: [AdminGuard] },
  { path: '**', component: LoginComponent } // If no matching route found, go back to home route
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
