import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpService } from './http.service';

@Injectable({
  providedIn: 'root'
})
export class QueuesService {
  apiUrl: string;
  constructor(private httpClient: HttpService) {
    this.apiUrl = environment.apiUrl;
  }

  getFinishedQueues() {
    return this.httpClient.get(`${this.apiUrl}/finishedQueues`)
  }
  getFailedQueues() {
    return this.httpClient.get(`${this.apiUrl}/failedQueues`)
  }
  getTodayQueues() {
    return this.httpClient.get(`${this.apiUrl}/todayQueues`)
  }
  getAllQueues() {
    return this.httpClient.get(`${this.apiUrl}/allQueues`)
  }
  postQueues(body: any) {
    return this.httpClient.post(`${this.apiUrl}/queues`, body)
  }
} 
