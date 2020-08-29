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
  showIframe: boolean = false;
  enableLecturePage: boolean = false;
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
    });

    this.service.getDays().subscribe(data => {
      this.daysNames = data;
    })

    this.service.getDaysDetails().subscribe(data => {
      this.daysDetails = data;
      console.log("eeeeeeeee", data)
      // this.lectureData = data;
      this.onClickDay('Day 1');
    });
  }

  setDateFormat() {

    const d = new Date();
    this.day = new Intl.DateTimeFormat('en', { day: '2-digit' }).format(d)
    this.month = new Intl.DateTimeFormat('en', { month: 'short' }).format(d)
    this.year = new Intl.DateTimeFormat('en', { year: 'numeric' }).format(d)

    console.log("date = ", this.day, this.month, this.year)
  }

  onClickjoinLecture(index) {
    console.log("index = ", index)
    // this.daysList[index].disableJoinBtn = true;
    let urlIframe = this.sanitizer.bypassSecurityTrustResourceUrl(this.daysList[index].iFrameLink);
    this.iFrameDynamicLink = urlIframe;
    this.showIframe = true;
  }

  onClickCancelLecture() {
    this.showIframe = false;
  }

  onClickDetails(index) {
    this.popupDetails = this.daysList[index].popupList;
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
    else if (dayname == 'Day 5') {
      this.daysList = this.daysDetails.day5;
    }
  }

  onClickLectureName(index) {
    console.log("inside lecture = ", index, this.daysList[index]);
    this.indexIFrameLink = index;
    this.lectureData = this.daysList[index].lectureList;
    this.enableLecturePage = true;
  }

  enableLecture() {
    this.enableLecturePage = false;
  }

  handleFileInput(files: FileList) {
    console.log("upload file data = ", files)
    this.fileToUpload = files.item(0);
  }

  uploadFile() {
    console.log("upload file data = ", this.fileToUpload);
    this.service.uploadFile(this.fileToUpload)
      .subscribe(data => {
        console.log("file uploaded successfully", data)
      }, error => {
        console.log(error);
      }
      );
  }

}
