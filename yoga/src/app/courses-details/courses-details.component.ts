import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { DatePipe } from '@angular/common';
import { ServiceService } from '../service.service';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-courses-details',
  templateUrl: './courses-details.component.html',
  styleUrls: ['./courses-details.component.css']
})
export class CoursesDetailsComponent implements OnInit {

  courseName: string;
  courseId: any;
  showIframe: boolean = false;
  enableLecturePage: boolean = false;
  enableContentDiv: boolean = false;
  daysNames: any;
  daysDetails: any;
  daysList: any;
  selectedDayName: string;
  iFrameDynamicLink: any = '';
  popupDetails: any;
  lectureData: any;

  fileToUpload: File = null;
  indexIFrameLink: any;
  day: any;
  month: any;
  year: any;

  constructor(private route: ActivatedRoute, private service: ServiceService, private sanitizer: DomSanitizer) { }

  ngOnInit() {

    this.setDateFormat();

    this.route.params.subscribe((param) => {
      this.courseName = param.course;
      this.courseId = param.courseId
    });
    this.getDays();
  }

  showContent() {
    this.enableContentDiv = !this.enableContentDiv;
  }

  setDateFormat() {

    const d = new Date();
    this.day = new Intl.DateTimeFormat('en', { day: '2-digit' }).format(d)
    this.month = new Intl.DateTimeFormat('en', { month: 'short' }).format(d)
    this.year = new Intl.DateTimeFormat('en', { year: 'numeric' }).format(d)
  }


  getDays() {
    this.service.getDays(this.courseId).subscribe(data => {
      this.daysNames = data['days'];
      this.onClickDay(this.daysNames[0])
    })
  }

  getDaysDetails(day) {
    let obj = {
      courseId: this.courseId,
      dayId: day.dayId
    }
    this.service.getDaysDetails(obj).subscribe(data => {
      this.daysDetails = data;
      this.daysList = data['lecture'];
      // this.lectureData = data;
    });
  }

  onClickjoinLecture(index) {
    console.log("index = ", index)
    // this.daysList[index].disableJoinBtn = true;
    let getIframeLink = "";

    if (this.daysList[index].videoIframeDynamicLink) {
      getIframeLink = this.daysList[index].videoIframeDynamicLink;
    }
    else {
      getIframeLink = this.daysList[index].liveIframeDynamicLink;
    }

    if (getIframeLink) {
      // let urlIframe = this.sanitizer.bypassSecurityTrustResourceUrl(this.daysList[index].iFrameLink);
      let urlIframe = this.sanitizer.bypassSecurityTrustResourceUrl(getIframeLink);
      this.iFrameDynamicLink = urlIframe;
      this.showIframe = true;
    }
    else {
      console.log("dont have iframe links in videoIframeDynamicLink and liveIframeDynamicLink")
    }
  }

  clearIframeLink() {
    this.iFrameDynamicLink = "";
  }

  onClickCancelLecture() {
    this.showIframe = false;
  }

  onClickDetails(index) {
    this.popupDetails = this.daysList[index].popupList;
  }

  onClickDay(day) {
    this.selectedDayName = day.dayName;
    this.showIframe = false;
    // this.daysList = this.daysDetails[day.dayName];
    this.getDaysDetails(day);
    // if (dayname == 'day1') {
    //   this.daysList = this.daysDetails[dayname];
    // }
    // else if (dayname == 'Day 2') {
    //   this.daysList = this.daysDetails.day2;
    // }
    // else if (dayname == 'Day 3') {
    //   this.daysList = this.daysDetails.day3;
    // }
    // else if (dayname == 'Day 4') {
    //   this.daysList = this.daysDetails.day4;
    // }
    // else if (dayname == 'Day 5') {
    //   this.daysList = this.daysDetails.day5;
    // }
  }

  isActive(item) {
    return this.selectedDayName == item;
  };

  onClickLectureName(index) {
    this.indexIFrameLink = index;
    this.lectureData = this.daysList[index].lectureList;
    this.enableLecturePage = true;
  }

  enableLecture() {
    this.enableLecturePage = false;
  }

  handleFileInput(files: FileList) {
    this.fileToUpload = files.item(0);
  }

  uploadFile() {
    this.service.uploadFile(this.fileToUpload)
      .subscribe(data => {
      }, error => {
        console.log(error);
      }
      );
  }

}
