import { Component, OnInit } from '@angular/core';
import { ServiceService } from '../service.service';

@Component({
  selector: 'app-courses',
  templateUrl: './courses.component.html',
  styleUrls: ['./courses.component.css']
})
export class CoursesComponent implements OnInit {

  constructor(private service: ServiceService) { }

  coursesList: any;
  enableCourses: boolean = false;
  coursesMsg: string;

  ngOnInit() {
    this.getCoursesList();
  }

  getCoursesList() {
    this.service.getCoursesData().subscribe(res => {
      this.enableCourses = false;
      this.coursesList = res['courses'];
      console.log("courses list", res);
      if(res['courses'].length === 0) {
      this.enableCourses = true;
      this.coursesMsg = res['message'];
      }
    })
  }

}
