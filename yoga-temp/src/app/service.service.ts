import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from "@angular/common/http";


@Injectable({
  providedIn: 'root'
})
export class ServiceService {

  constructor(private http: HttpClient) { }

  getDays() {
    return this.http.get('./assets/jsons/days.json')
  }

  getDaysDetails() {
    return this.http.get('./assets/jsons/course-details.json')
  }

}
