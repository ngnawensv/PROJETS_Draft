import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';


import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { EvaluationComponent } from './evaluation/evaluation.component';
import { ClasseComponent } from './classe/classe.component';
import { ImportClasseComponent } from './importation/classe/classe.component'
import { LoginComponent } from './login/login.component';

import { AdminGuard } from './auth/admin.guard';
import { SerieComponent } from './serie/serie.component';
import { authInterceptorProviders } from './auth/auth.interceptor';
import { HasRoleDirective } from './college-directive/has-role.directive';
import { HomeComponent } from './home/home.component';
import { ImportCoursComponent } from './importation/cours/cours.component';
import { ImportInscriptionComponent } from './importation/inscription/inscription.component';
import { ImportRoleComponent } from './importation/role/role.component';
import { ImportUserComponent } from './importation/user/user.component';
import { ImportTeacherComponent } from './importation/teacher/teacher.component';
import { ImportEnseignementComponent } from './importation/enseignement/enseignement.component';


@NgModule({
  declarations: [
    AppComponent,
    EvaluationComponent,
    ClasseComponent,
    LoginComponent,
    SerieComponent,
    HasRoleDirective,
    HomeComponent,
    ImportClasseComponent,
    ImportCoursComponent,
    ImportInscriptionComponent,
    ImportRoleComponent,
    ImportUserComponent,
    ImportTeacherComponent,
    ImportEnseignementComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [AdminGuard, authInterceptorProviders],
  bootstrap: [AppComponent]
})
export class AppModule { }
