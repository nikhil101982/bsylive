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

    this.route.params.subscribe((param) => {
      this.courseName = param.course;
    });

    this.service.getDays().subscribe(data => {
      this.daysNames = data;
    })

    this.service.getDaysDetails().subscribe(data => {
      this.daysDetails = data;
      this.onClickDay('Day 1');
    })

  }

  onClickjoinLecture(index) {
    // console.log("aaaaaaaaaaaa", index, this.daysList);
    // this.iFrameLink = this.daysList[index].iFrameLink;
    // var iframe1 = document.getElementById('iframe');
    // iframe1.style.display = 'block';

    this.showIframe = true;
  }


  convertDateFormat(date) {
    let pipe = new DatePipe('en-US');
    return pipe.transform(date, 'yyyy-MM-ddTHH:mm:ss');
  }

  onClickDay(dayname) {
    this.selectedDayName = dayname;
    this.showIframe = false;
    if (dayname == 'Day 1') {
      this.daysList = this.daysDetails.day1;
    }
    else if (dayname == 'Day 2') {
      this.daysList = this.daysDetails.day2;
    }
    else if (dayname == 'Day 3') {
      this.daysList = this.daysDetails.day3;
    }
    else if (dayname == 'Day 4') {
      this.daysList = this.daysDetails.day4;
    }
  }

}
