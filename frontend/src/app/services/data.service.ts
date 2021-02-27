import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Client } from '../models/client';

@Injectable({
  providedIn: 'root',
})
export class DataService {
  private requestUrl: string;

  constructor(private http: HttpClient) {
    this.requestUrl = 'http://localhost:9092/clients-info';
  }

  public getClientsData() {
    return this.http.get<Client>(this.requestUrl);
  }

  public getClientDataById(client_id: string) {
    return this.http.get<Client>(this.requestUrl + `/${client_id}`);
  }

  public resetAll() {
    return this.http.post(`${this.requestUrl}/dev/319475513/reset-all`, '');
  }
}
