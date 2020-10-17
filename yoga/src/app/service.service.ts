import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ServiceService {

  updateLogInOption = new Subject();

  url: string = "http://localhost:8080/";

  headerDict = {
    'Content-Type': 'application/json',
    // 'Accept': 'application/json',
    // 'Access-Control-Allow-Headers': 'Content-Type',
  }

  requestHeaders = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
  };


  constructor(private http: HttpClient) { }

  getUserName() {
    return sessionStorage.getItem('userName');
  }

  logInButtonUpdate(val) {
    this.updateLogInOption.next(val);
  }

  getDays() {
    let link = `${this.url}getDays/${this.getUserName()}`
    return this.http.get(link, this.requestHeaders);
    // return this.http.get('./assets/jsons/days.json')
  }

  getDaysDetails() {
    return this.http.get('./assets/jsons/course-details.json')
  }

  getCoursesData() {
    let link = `${this.url}coursesBasedOnUserName/${this.getUserName()}`
    return this.http.get(link, this.requestHeaders);
    // return this.http.get('./assets/jsons/courses.json')
  }

  uploadFile(file: File) {
    const endpoint = 'url';
    const formData: FormData = new FormData();
    formData.append('fileKey', file, file.name);
    return this.http.post(endpoint, formData);
  }

  getLogInData(params) {
    return this.http.post(this.url + 'login', params, this.requestHeaders);
  }

  getForgotPasswordData(params) {
    return this.http.post(this.url + 'forgotPassword', params, this.requestHeaders);
  }

  createAccount(params) {
    return this.http.post(this.url + 'createAccount', params, this.requestHeaders);
  }


}
