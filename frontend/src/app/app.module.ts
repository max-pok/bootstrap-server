import { DataService } from './services/data.service';
import { AuthService } from './services/auth.service';
import { RequestService } from './services/request.service';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';

import { RequestFormComponent } from './components/request-form/request-form.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NZ_I18N } from 'ng-zorro-antd/i18n';
import { en_US } from 'ng-zorro-antd/i18n';
import { registerLocaleData } from '@angular/common';
import en from '@angular/common/locales/en';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NzSelectModule } from 'ng-zorro-antd/select';
import { NzInputModule } from 'ng-zorro-antd/input';
import { NzFormModule } from 'ng-zorro-antd/form';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzTableModule } from 'ng-zorro-antd/table';
import { NzDividerModule } from 'ng-zorro-antd/divider';
import { InfoPageComponent } from './components/info-page/info-page.component';

registerLocaleData(en);

@NgModule({
  declarations: [AppComponent, RequestFormComponent, InfoPageComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    NzSelectModule,
    NzInputModule,
    NzFormModule,
    NzButtonModule,
    NzTableModule,
    NzDividerModule,
  ],
  providers: [
    { provide: NZ_I18N, useValue: en_US },
    RequestService,
    AuthService,
    DataService,
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
