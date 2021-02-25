import { InfoPageComponent } from './components/info-page/info-page.component';
import { AppComponent } from './app.component';
import { RequestFormComponent } from './components/request-form/request-form.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

const routes: Routes = [
  { path: 'home', component: InfoPageComponent },
  { path: 'addrequest', component: RequestFormComponent },
  { path: '**', component: InfoPageComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
