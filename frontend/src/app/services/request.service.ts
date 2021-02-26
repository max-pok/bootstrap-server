import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Request } from '../models/request';

@Injectable({
  providedIn: 'root',
})
export class RequestService {
  private requestUrl: string;

  constructor(private http: HttpClient) {
    this.requestUrl = 'http://localhost:9092/request';
  }

  public findAll(): Observable<Request[]> {
    return this.http.get<Request[]>(this.requestUrl);
  }

  public getLocationsAndLicences(): Observable<String[]> {
    return this.http.get<String[]>(this.requestUrl);
  }

  public getLicenses(): Observable<String[]> {
    return this.http.get<String[]>(this.requestUrl);
  }

  public getResponse(request: Request) {
    return this.http.get<String>(
      `${this.requestUrl}/${request.customer_id}/${request.license_key}/response`
    );
  }

  public save(request: Request) {
    return this.http.post<Request>(this.requestUrl, request);
  }
}
