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

  uploadFile(file: File) {

    console.log("file upload in service  = ", file);
    const endpoint = 'url';
    const formData: FormData = new FormData();
    formData.append('fileKey', file, file.name);
    return this.http.post(endpoint, formData);
  }

}
