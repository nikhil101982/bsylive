import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { of } from 'rxjs';
import { ServiceService } from 'src/app/service.service';
import { DateServiceService } from 'src/app/shared/date-service/date-service.service';
import Swal from 'sweetalert2';


class LectureForm {
  lectureId: any = "";
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
  // duration: number = null
}

class RemoveLectureParams {
  fromId: string = "";
  dayId: string = ""
  selectedDays: any = [];
  lectureName: string = ""
  lectureId: any = ""
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

  enableDeleteLectureForm: boolean = false;

  showAddCourse: boolean = false;
  showRemoveCourse: boolean = false;
  updateCourse: boolean = false;

  lectureForm: LectureForm = new LectureForm();


  // create course variables

  createCourse: CreateCourseParams = new CreateCourseParams();
  createCourseError: string;

  enableDateError: boolean = false;

  //


  // remove course variables

  allCoursesForDelete: any = [];
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

  selectedDeletedDaysSettings: any = {};

  selectedLectureObj: any;
  allLectures: any = [];
  allLecturesSettings: any = {};
  fromDaysDeleteList: any = [];
  toDaysDeleteList: any = [];
  selectedDeleteDaysList: any = [];
  selectedDeleteDaysList2: any = [];

  enableRemoveBtn: boolean = false;

  removeLecturesParams: RemoveLectureParams = new RemoveLectureParams();


  startTime = null;
  endTime = null;

  errorMsg: string = "";

  disableLiveIframe: boolean = false;
  disableVideoIframe: boolean = false;

  //

  constructor(private service: ServiceService, private router: Router, private dateService: DateServiceService) { }

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
    this.fetchAllCoursesForDelete();
  }

  onClickUpdateCourse() {
    this.updateCourse = true;
    this.showAddCourse = false;
    this.showRemoveCourse = false;
    this.enableCourseDetailsSection = false;
    this.enableLectureForm = false;
    this.getAllCourses();
  }


  // create course section

  clearCreateCourseData() {
    this.createCourse = {
      courseName: "",
      startDate: "",
      endDate: "",
      language: "hindi",
      // duration: null
    }
  }

  returnDate(date, section) {
    this.enableDateError = false;
    this.createCourseError = "";
    console.log("date in course compoenet = ", date, section)
    if (section === 'startDate') {
      this.createCourse.startDate = date;
    }

    if (section === 'endDate') {
      this.createCourse.endDate = date;
    }

    if (this.createCourse.startDate && this.createCourse.endDate) {
      let compare = this.dateService.dateCompare(this.createCourse.startDate, this.createCourse.endDate);
      console.log("compare date = ", compare)
      if (compare) {
        this.enableDateError = true;
      }
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

    else if (this.enableDateError === true) {
      this.createCourseError = "End date should be more than start date"
    }

    // else if (this.createCourse.duration === null) {
    //   this.createCourseError = "Please provide duration";
    // }

    // else if (this.createCourse.duration === 0) {
    //   this.createCourseError = "duration should be more than zero(0)";
    // }
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

  fetchAllCoursesForDelete() {

    this.service.getAllCoursesForDelete().subscribe(res => {
      console.log("res", res);
      if (res['status'] === 'success') {
        this.allCoursesForDelete = res['courses'];
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

  getAllCourses() {

    this.service.getAllCourses().subscribe(res => {
      console.log("res", res);
      if (res['status'] === 'success') {
        this.allCourses = res['courses'];
      }
      if (res['status'] === 'failure') {
        this.showAlertMessage('error', res['message']);
      }
    })

  }

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
        this.getCourseAllDays(this.courseId);
        this.getAllLectures(this.courseId)
      }
      if (res['status'] === 'failure') {
        this.showAlertMessage('error', res['message']);
      }
    })
  }


  lectureData(data) {
    this.enableLectureForm = false;
    this.enableDeleteLectureForm = false
    if (data) {
      this.enableLectureForm = true;
      console.log("lecture data = ", data);
      this.lectureForm.lectureId = data.lectureId;
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
      lectureId: "",
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
    this.enableDeleteLectureForm = false;
    this.clearDeleteParamsObj();

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

  timeCompare() {
    this.errorMsg = "";
    let form = this.lectureForm;
    console.log("time compare = ", form, form.startTime.split(' ')[0].split(':'), Number(form.startTime.split(' ')[0].split(':')[0]), form.endTime.split(' '));
    let start = this.lectureForm.startTime;
    let end = this.lectureForm.endTime;

    let startSpaceSep = start.split(' ');
    let endSpaceSep = end.split(' ');

    let startColanSep = startSpaceSep[0].split(':');
    let endColanSep = endSpaceSep[0].split(':');

    if (startSpaceSep[1] === endSpaceSep[1]) {
      console.log("time if()");

      if (Number(startColanSep[0]) === Number(endColanSep[0])) {
        console.log("inside 2nd if()");
        if (Number(startColanSep[1]) === Number(endColanSep[1]) ||
          Number(startColanSep[1]) > Number(endColanSep[1])) {
          this.errorMsg = "End time should greater than Start time";
        }
      }
      if (Number(startColanSep[0]) > Number(endColanSep[0])) {
        console.log("time hours not equal", startColanSep[0], endColanSep[0]);
        this.errorMsg = "End time should greater than Start time";
      }

    }

  }


  errorHandling() {

    console.log("asasasa", this.lectureForm);

    this.errorMsg = "";
    let data = this.lectureForm;

    if (data.lectureName == "") {
      this.errorMsg = "Please provide lecture name";
    }

    else if (data.fromDayId) {
      if (data.toDayId == "" || data.toDayId == null) {
        this.errorMsg = "Please provide to day";
      }
      else {
        this.errorMsg = "";
      }
    }

    else if (data.toDayId) {
      if (data.fromDayId == "" || data.fromDayId == null) {
        this.errorMsg = "Please provide from day";
      }
      else {
        this.errorMsg = "";
      }
    }

    else if (((data.fromDayId == "" || data.fromDayId == null) ||
      (data.toDayId == "" || data.toDayId == null)) && (data.dayIds.length == 0)) {
      this.errorMsg = "Please provide select day"
    }

    else if (data.startTime == "") {
      this.errorMsg = "Please provide start time";
    }

    else if (data.endTime == "") {
      this.errorMsg = "Please provide end time";
    }

    else if ((data.liveIFrameLink == "" || data.liveIFrameLink == null) &&
      (data.videoIFrameLink == "" || data.videoIFrameLink == null)) {
      this.errorMsg = "Please provide any IFrame link";
    }

  }


  saveLecture() {

    this.lectureForm.courseId = this.courseId;
    this.lectureForm.currentDate = this.service.convertDateFormat(new Date());
    console.log("save lecture req. body = ", this.lectureForm);

    this.timeCompare();
    this.errorHandling();

    if (this.errorMsg == "") {
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
    }
    console.log("save lecture = ", this.lectureForm, this.startTime, this.endTime);
  }




  // delete lecture code section start


  onClickDeleteLectureMainBtn() {
    this.enableLectureForm = false;
    this.enableDeleteLectureForm = true;
    this.clearDeleteParamsObj();
  }


  getAllLectures(courseId) {

    console.log("course Id = ", courseId);

    this.service.getAllLectures(courseId).subscribe(res => {
      console.log("lectures = ", res);
      this.allLectures = res['lecture'];
      this.allLecturesSettings = this.getDropdownSettingsData('lectureId', 'lectureName', true)
    })
  }


  onSelectLecture(data) {
    console.log("select lecture", data);
    this.enableRemoveBtn = true;
    this.selectedLectureObj = data;
    let obj = { lId: data.lectureId, cId: this.courseId }
    this.service.getSelectedLectureDays(obj).subscribe((res) => {
      console.log("selected lecture days =", res);
      this.selectedDeleteDaysList2 = res['days'];
      this.selectedDeletedDaysSettings = this.getDropdownSettingsData('dayId', 'dayName', false);
    })
  }


  onDeSelectLecture(data) {
    this.enableRemoveBtn = false;
    this.clearDeleteParamsObj();
    console.log("de select lecture", data, this.removeLecturesParams);
  }


  onDeleteItemSelect(ev, section) {

    console.log("onDeleteItemSelect", ev, section);

    if (section === 'from') {
      this.removeLecturesParams.fromId = ev.dayId;
    }

    if (section === 'to') {
      this.removeLecturesParams.dayId = ev.dayId;
    }

    if (section === 'select') {
      this.removeLecturesParams.selectedDays.push(ev);
    }
    console.log("onDeleteItemSelect", this.removeLecturesParams);
  }


  onDeleteItemDeSelect(ev, section) {

    console.log("onDeleteItemDeSelect", ev, section);

    if (section === 'from') {
      this.removeLecturesParams.fromId = "";
    }

    if (section === 'to') {
      this.removeLecturesParams.dayId = "";
    }

    if (section === 'select') {
      let index = this.selectedDeleteDaysList.findIndex(day => day.dayId == ev.dayId);
      this.selectedDeleteDaysList.splice(index, 1);
      this.removeLecturesParams.selectedDays = this.selectedDeleteDaysList;
    }
    console.log("selectedDeleteDaysList", this.removeLecturesParams);
  }


  onSelectDeleteAll(ev, section) {
    console.log("onSelectDeleteAll", ev, section);
    if (section === 'select') {
      this.removeLecturesParams.selectedDays = ev;
    }
    console.log("onSelectDeleteAll", this.removeLecturesParams);
  }


  removeLectureErrorHandling() {
    console.log("error handling section = ", this.removeLecturesParams);
    this.errorMsg = "";

    let data = this.removeLecturesParams;
    if ((data.fromId == "" || data.fromId == null || data.fromId == undefined) &&
      (data.dayId == "" || data.dayId == null || data.dayId == undefined) &&
      (data.selectedDays.length === 0)
    ) {
      this.errorMsg = "Please select From Day and To Day (OR) Selected Day";
    }

    else if ((data.fromId == "" || data.fromId == null || data.fromId == undefined) &&
      (data.dayId !== "" || data.dayId !== null || data.dayId !== undefined) &&
      (data.selectedDays.length === 0)
    ) {
      this.errorMsg = "Please select From Day";
    }
    else if ((data.dayId == "" || data.dayId == null || data.dayId == undefined) &&
      (data.fromId !== "" || data.fromId !== null || data.fromId !== undefined) &&
      (data.selectedDays.length === 0)
    ) {
      this.errorMsg = "Please select To Day";
    }
    else {
      this.removeLecture();
    }
  }


  removeLecture() {

    this.removeLecturesParams.lectureName = this.selectedLectureObj.lectureName;
    this.removeLecturesParams.lectureId = this.selectedLectureObj.lectureId;
    console.log("removeLecturesParams = ", this.removeLecturesParams);

    this.service.deleteLecture(this.removeLecturesParams).subscribe(res => {

      console.log("delete lecture res = ", res);
      if (res['status'] === 'success') {
        this.showAlertMessage('success', res['message']);
        this.router.navigate(['/courses']);
      }
      if (res['status'] === 'failure') {
        this.showAlertMessage('error', res['message']);
      }
    })
  }

  clearDeleteParamsObj() {
    this.removeLecturesParams = {
      fromId: "",
      dayId: "",
      selectedDays: [],
      lectureName: "",
      lectureId: ""
    };

    this.fromDaysDeleteList = "";
    this.toDaysDeleteList = "";
    this.selectedDeleteDaysList = [];
  }


  // delete lecture code section end



  // delete this function

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
