import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Request } from '../models/request';

@Injectable({
  providedIn: 'root',
})
export class RequestService {
  private requestUrl: string;
  private responseUrl: string;

  constructor(private http: HttpClient) {
    this.requestUrl = 'http://localhost:9092/request';
    this.responseUrl = 'http://localhost:9092/response';
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
    return this.http.get<String>(this.responseUrl, {
      params: {
        customer_id: request.customer_id,
        license_key: request.license_key,
      },
    });
  }

  public save(request: Request) {
    return this.http.post<Request>(this.requestUrl, request);
  }
}
