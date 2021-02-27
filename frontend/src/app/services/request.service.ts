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

  /* get locations and licences for select input from backend */
  public getLocationsAndLicences(): Observable<String[]> {
    return this.http.get<String[]>(this.requestUrl);
  }

  /* get response on form submit */
  public getResponse(request: Request) {
    return this.http.get<String>(
      `${this.requestUrl}/${request.customer_id}/${request.license_key}/response`
    );
  }

  /* send request to backend */
  public request(request: Request) {
    return this.http.post<Request>(this.requestUrl, request);
  }
}
