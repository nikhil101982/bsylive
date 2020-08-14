import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { DatePipe } from '@angular/common';
import { ServiceService } from '../service.service';

@Component({
  selector: 'app-courses-details',
  templateUrl: './courses-details.component.html',
  styleUrls: ['./courses-details.component.css']
})
export class CoursesDetailsComponent implements OnInit {

  courseName: string;
  showIframe: boolean = false;
  daysNames: any;
  daysDetails: any;
  daysList: any;
  selectedDayName: string;
  iFrameLink: any;

  constructor(private route: ActivatedRoute, private service: ServiceService) { }

  ngOnInit() {


  }

  onClickjoinLecture() {
    this.showIframe = true;
  }

}
