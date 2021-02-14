import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class DateServiceService {

  constructor() { }


  correctDate(dt) {
    console.log("dateee ", dt);
    let d = new Date(dt)
    let year = Math.abs(d.getFullYear());
    let month = Math.abs(d.getMonth() + 1);
    let day = Math.abs(d.getDate());
    return new Date(year + "-" + month + "-" + day);
  }


  dateCompare(date1, date2) {
    console.log("new method = ", date1, date2);

    let dt1 = this.correctDate(date1);
    let dt2 = this.correctDate(date2);

    let d1 = dt1.getTime();
    let d2 = dt2.getTime();

    console.log("date after = ", d1, d2, d1 - d2, d1 > d2)

    if (d1 > d2) {
      return true
    }

    return false;
  }

}
