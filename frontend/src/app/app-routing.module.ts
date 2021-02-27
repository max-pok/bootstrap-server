import { LoginComponent } from './components/login/login.component';
import { AuthService } from './services/auth.service';
import { InfoPageComponent } from './components/info-page/info-page.component';
import { AppComponent } from './app.component';
import { RequestFormComponent } from './components/request-form/request-form.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

const routes: Routes = [
  { path: 'home', component: InfoPageComponent, canActivate: [AuthService] },
  { path: 'login', component: LoginComponent },
  {
    path: 'addrequest',
    component: RequestFormComponent,
    canActivate: [AuthService],
  },
  { path: '**', component: InfoPageComponent, canActivate: [AuthService] },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
