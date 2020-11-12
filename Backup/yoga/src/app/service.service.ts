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

  getUserData() {
    return JSON.parse(localStorage.getItem('userDetails'));
  }

  logInButtonUpdate(val) {
    this.updateLogInOption.next(val);
  }

  getDays(id) {
    let link = `${this.url}getDays/${id}`
    return this.http.get(link, this.requestHeaders);
    // return this.http.get('./assets/jsons/days.json')
  }

  getDaysDetails(params) {
    let link = `${this.url}getCourseByAdmin/${params.courseId}/${params.dayId}`
    return this.http.get(link, this.requestHeaders)
    // return this.http.get('./assets/jsons/course-details.json')
  }

  getCoursesData() {
    let link = `${this.url}courses/${this.getUserData().userEmail}/${this.getUserData().userRole}`
    return this.http.get(link, this.requestHeaders);
    // return this.http.get('./assets/jsons/courses.json')
  }


  // for admin

  getAllCourses() {
    let link = `${this.url}coursesForAdmin`
    return this.http.get(link, this.requestHeaders);
  }

  getAllEmails() {
    let link = `${this.url}userEmails`
    return this.http.get(link, this.requestHeaders);
  }

  getSelectedEmailCourses(useremail) {
    let link = `${this.url}courses/${useremail}/${this.getUserData().userRole}`
    return this.http.get(link, this.requestHeaders);
  }

  deleteUser(useremail) {
    let link = `${this.url}removeUser/${useremail}`
    return this.http.post(link, this.requestHeaders);
  }

  updateUserCoursesByAdmin(params) {
    return this.http.put(this.url + 'updateUserCourses', params, this.requestHeaders)
  }

  createAccountByAdmin(params) {
    return this.http.post(this.url + 'createAccountByAdmin', params, this.requestHeaders);
  }

  //


  uploadFile(file: File) {
    const endpoint = 'url';
    const formData: FormData = new FormData();
    formData.append('fileKey', file, file.name);
    return this.http.post(endpoint, formData);
  }

  // login component

  getLogInData(params) {
    return this.http.post(this.url + 'login', params, this.requestHeaders);
  }

  getForgotPasswordData(params) {
    return this.http.post(this.url + 'forgotPassword', params, this.requestHeaders);
  }

  createAccount(params) {
    return this.http.post(this.url + 'createAccount', params, this.requestHeaders);
  }

  logout(params) {
    return this.http.post(this.url + 'logout', params, this.requestHeaders)
  }

  updatePassword(params) {
    return this.http.post(this.url + 'changePassword', params, this.requestHeaders)
  }

  //
}
