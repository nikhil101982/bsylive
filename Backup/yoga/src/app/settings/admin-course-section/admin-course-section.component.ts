import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ServiceService } from 'src/app/service.service';
import Swal from 'sweetalert2';


class LectureForm {
  lectureName: string = "";
  liveIFrameLink: string = "";
  videoIFrameLink: string = "";
  startTime: string = "";
  endTime: string = "";
  fromDayId: any = "";
  toDayId: any = "";
  dayIds: any = [];
  courseId: any = "";
  currentDate: string = "";
}


class CreateCourseParams {
  courseName: string = ""
  startDate: any = "";
  endDate: any = "";
  language: string = "hindi"
  duration: number = null
}

@Component({
  selector: 'app-admin-course-section',
  templateUrl: './admin-course-section.component.html',
  styleUrls: ['./admin-course-section.component.css']
})
export class AdminCourseSectionComponent implements OnInit {

  showSections: boolean = true;

  courseObj: any;

  courseId: any;

  enableCourseDetailsSection: boolean = false;
  enableLectureForm: boolean = false;

  showAddCourse: boolean = false;
  showRemoveCourse: boolean = false;
  updateCourse: boolean = false;

  lectureForm: LectureForm = new LectureForm();


  // create course variables

  createCourse: CreateCourseParams = new CreateCourseParams();
  createCourseError: string;

  //


  // remove course variables

  allCourses: any = [];
  allCourseSettings: any = {};

  selectedDeleteItems: any = [];
  deleteCourseError: string = ""

  //

  // update course variables

  allDays: any = [];
  allDaysSettings: any = {};
  selectedDaysSettings: any = {};

  fromDaysList: any = [];
  toDaysList: any = [];
  selectedDaysList: any = [];

  startTime = null;
  endTime = null;


  disableLiveIframe: boolean = false;
  disableVideoIframe: boolean = false;

  //

  constructor(private service: ServiceService, private router: Router) { }

  ngOnInit(): void {

    this.onClickAddCourse();

  }


  onClickAddCourse() {
    this.showAddCourse = true;
    this.showRemoveCourse = false;
    this.updateCourse = false;
    this.clearCreateCourseData();
  }

  onClickRemoveCourse() {
    this.showRemoveCourse = true;
    this.showAddCourse = false;
    this.updateCourse = false;
    this.fetchAllCourses();
  }

  onClickUpdateCourse() {
    this.updateCourse = true;
    this.showAddCourse = false;
    this.showRemoveCourse = false;
    this.enableCourseDetailsSection = false;
    this.enableLectureForm = false;
    this.fetchAllCourses();
  }


  // create course section

  clearCreateCourseData() {
    this.createCourse = {
      courseName: "",
      startDate: "",
      endDate: "",
      language: "hindi",
      duration: null
    }
  }

  returnDate(date, section) {
    console.log("date in course compoenet = ", date, section)
    if (section === 'startDate') {
      this.createCourse.startDate = date;
    }

    if (section === 'endDate') {
      this.createCourse.endDate = date;
    }
  }


  saveCourse() {

    this.createCourseError = "";
    console.log("error validation check = ", this.createCourse, this.createCourse.courseName === "");

    if (this.createCourse.courseName === "") {
      this.createCourseError = "Please provide course name";
    }

    else if (this.createCourse.startDate === "" || this.createCourse.startDate === null) {
      this.createCourseError = "Please provide start date";
    }

    else if (this.createCourse.endDate === "" || this.createCourse.endDate === null) {
      this.createCourseError = "Please provide end date";
    }

    else if (this.createCourse.duration === null) {
      this.createCourseError = "Please provide duration";
    }

    else if (this.createCourse.duration === 0) {
      this.createCourseError = "duration should be more than zero(0)";
    }
    else {
      console.log("hit api");
      this.service.createCourse(this.createCourse).subscribe(res => {
        console.log("create course res = ", res);
        if (res['status'] === 'success') {
          this.showAlertMessage('success', res['message']);
          this.router.navigate(['/courses']);
        }
        if (res['status'] === 'failure') {
          this.showAlertMessage('error', res['message']);
        }
      })
    }
    console.log("save params = ", this.createCourse);
  }

  //


  // remove course section

  fetchAllCourses() {

    this.service.getAllCourses().subscribe(res => {
      console.log("res", res);
      if (res['status'] === 'success') {
        this.allCourses = res['courses'];
        this.allCourseSettings = this.getDropdownSettingsData('courseId', 'courseName', false)
      }
      if (res['status'] === 'failure') {
        this.showAlertMessage('error', res['message']);
      }
    })
  }


  deleteCourse() {
    console.log("delete = ", this.selectedDeleteItems);
    this.deleteCourseError = "";

    let reqBody = this.prepareReqBody();

    console.log("delete reqBody", reqBody);

    if (reqBody.length === 0) {
      this.deleteCourseError = "Please select course(s)"
    }
    else {
      this.service.removeCourse(reqBody).subscribe(res => {
        console.log("remove course res", res);
        if (res['status'] === 'success') {
          this.showAlertMessage('success', res['message']);
          this.router.navigate(['/courses']);
        }
        if (res['status'] === 'failure') {
          this.showAlertMessage('error', res['message']);
        }
      })
    }
  }


  prepareReqBody() {
    let list = [];
    if (this.selectedDeleteItems.length > 0) {
      this.selectedDeleteItems.map((data) => {
        list.push({ courseId: data.courseId })
      })
      return list;
    }
    return [];
  }


  //



  // update course

  showCourseDetails(course) {
    console.log("selected course", course);
    this.enableCourseDetailsSection = false;
    this.enableLectureForm = false;
    this.courseId = course.courseId

    this.service.getSelectedCourseData(this.courseId).subscribe(res => {
      console.log("course obj = ", res);
      if (res['status'] === 'success') {
        this.enableCourseDetailsSection = true;
        this.showSections = false;
        this.courseObj = res;
        this.getCourseAllDays(this.courseId)
      }
      if (res['status'] === 'failure') {
        this.showAlertMessage('error', res['message']);
      }
    })
  }


  lectureData(data) {
    this.enableLectureForm = false;
    if (data) {
      this.enableLectureForm = true;
      console.log("lecture data = ", data);
      this.lectureForm.lectureName = data.lectureName;
      this.lectureForm.liveIFrameLink = data.liveIframeDynamicLink;
      this.lectureForm.videoIFrameLink = data.videoIframeDynamicLink;
      this.startTime = data.startTime;
      this.endTime = data.endTime;
      this.fromDaysList = this.allDays.filter(day => day.dayId == data.fromDayId);
      this.toDaysList = this.allDays.filter(day => day.dayId == data.toDayId);
    }
  }


  getCourseAllDays(courseId) {

    this.service.getDays(courseId).subscribe(res => {
      console.log("days = ", res);
      if (res['status'] === 'success') {
        this.allDays = res['days'];
        this.allDaysSettings = this.getDropdownSettingsData('dayId', 'dayName', true);
        this.selectedDaysSettings = this.getDropdownSettingsData('dayId', 'dayName', false);
      }
      if (res['status'] === 'failure') {
        this.showAlertMessage('error', res['message']);
      }
    })

  }

  onClickCreateLectureBtn() {

    this.lectureForm = {
      lectureName: "",
      liveIFrameLink: "",
      videoIFrameLink: "",
      startTime: "",
      endTime: "",
      fromDayId: "",
      toDayId: "",
      dayIds: [],
      currentDate: "",
      courseId: ""
    }

    this.startTime = "";
    this.endTime = "";

    this.enableLectureForm = true;

    console.log("lecture form = ", this.lectureForm);
  }


  returnSelectedTime(time, section) {
    if (section === 'startTime') {
      this.lectureForm.startTime = time;
    }
    if (section === 'endTime') {
      this.lectureForm.endTime = time;
    }
  }


  saveLecture() {

    this.lectureForm.courseId = this.courseId;
    this.lectureForm.currentDate = this.service.convertDateFormat(new Date());
    console.log("save lecture req. body = ", this.lectureForm);

    this.service.saveLecture(this.lectureForm).subscribe(res => {
      console.log("save lecture res = ", res);
      if (res['status'] === 'success') {
        this.showAlertMessage('success', res['message']);
        this.router.navigate(['/courses']);
      }
      if (res['status'] === 'failure') {
        this.showAlertMessage('error', res['message']);
      }
    })
    console.log("save lecture = ", this.lectureForm, this.startTime, this.endTime);
  }

  // delet this function

  convertTimeForReqBody(time) {
    console.log("time = ", time);
    if (time.hour) {
      return `${time.hour}:${time.minute}:00`
    }
    else {
      return "";
    }
  }

  //

  // delet this function

  convertTimeForDataBind(time1) {
    console.log("time convert for data bind = ", time1);
    if (time1) {
      var time = time1.split(':')
      var hour; var minute; var second;

      if (time.length === 3) {
        hour = parseInt(time[0], 10);
        minute = parseInt(time[1], 10);
        second = parseInt(time[2], 10);
      }

      console.log("aaaaaaaa", hour, minute, second)

      return { hour: hour, minute: minute }
    }
    else {
      return { hour: null, minute: null }
    }
  }

  //


  onClickBack() {
    this.enableCourseDetailsSection = false;
    this.showSections = true;
  }


  onKeyupIFrame(event, section) {

    let str = event.target.value.trim();
    this.disableLiveIframe = false;
    this.disableVideoIframe = false;

    if (str.length > 0) {
      if (section === 'live') {
        this.disableVideoIframe = true;
      }

      if (section === 'video') {
        this.disableLiveIframe = true;
      }
    }
  }


  //




  // generic functions

  getDropdownSettingsData(id, text, singleSelectionFlag) {

    const dropdown = {
      idField: id,
      textField: text,
      selectAllText: 'Select All',
      unSelectAllText: 'UnSelect All',
      itemsShowLimit: 5,
      allowSearchFilter: false,
      singleSelection: singleSelectionFlag,
      closeDropDownOnSelection: true,
      noDataAvailablePlaceholderText: 'No Data Found'
    }
    return dropdown;
  }


  onItemSelect(item: any, section) {

    console.log(item, section);

    if (section === 'delete') {
      this.selectedDeleteItems.push(item)
      console.log("item = ", this.selectedDeleteItems);
    }

    if (section === 'from') {
      this.lectureForm.fromDayId = item.dayId
      console.log("item = ", this.lectureForm.fromDayId);
    }

    if (section === 'to') {
      this.lectureForm.toDayId = item.dayId
      console.log("item = ", this.lectureForm.toDayId);
    }

    if (section === 'select') {
      this.lectureForm.dayIds.push(item);
      console.log("item = ", this.lectureForm.dayIds);
    }

  }

  onItemDeSelect(item: any, section) {

    console.log(item);

    if (section === 'delete') {

      let index = this.selectedDeleteItems.findIndex(data => data.courseId === item.courseId);
      console.log("index= ", index);
      this.selectedDeleteItems.splice(index, 1);
      console.log("delected list = ", this.selectedDeleteItems);
    }

    if (section === 'from') {
      this.lectureForm.fromDayId = "";
      console.log("item = ", this.lectureForm.fromDayId);
    }

    if (section === 'to') {
      this.lectureForm.toDayId = "";
      console.log("item = ", this.lectureForm.toDayId);
    }

    if (section === 'select') {

      let index = this.lectureForm.dayIds.findIndex(data => data.dayId === item.dayId);
      console.log("index= ", index);
      this.lectureForm.dayIds.splice(index, 1);
      console.log("delected list = ", this.lectureForm.dayIds);
    }
  }

  onSelectAll(items: any, section) {

    console.log("select all = ", items, section);

    if (section === 'delete') {
      this.selectedDeleteItems = items;
      console.log("select all delete = ", this.selectedDeleteItems);
    }

    if (section === 'select') {
      this.lectureForm.dayIds = items;
    }
  }

  removeErrorMsg() {
    this.deleteCourseError = "";
  }

  showAlertMessage(text, type) {

    console.log("alert message = ", text, type);
    Swal.fire({
      // position: 'top-end',
      icon: type,
      title: text,
      showConfirmButton: false,
      timer: 2000
    })
  }

  //

}
