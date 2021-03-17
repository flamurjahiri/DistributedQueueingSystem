import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http'

@Injectable({
  providedIn: 'root'
})
export class HttpService {

  constructor(private http: HttpClient) { }

  setAuthHeaders() {
    let headers = new HttpHeaders();
    headers = headers.append('Content-Type', 'application/json');
    return headers;
  }

  get(url: string) {
    const headers = this.setAuthHeaders();
    return this.http.get(url, { headers: headers})
  }
  post(url: string, body: any) {
    const headers = this.setAuthHeaders();
    return this.http.post(url, body, { headers: headers})
  }
  put(url: string, body: any) {
    const headers = this.setAuthHeaders();
    return this.http.put(url, body, { headers: headers})
  }
  delete(url: string, body: any) {
    const headers = this.setAuthHeaders();
    return this.http.delete(url, { headers: headers})
  }
 }
