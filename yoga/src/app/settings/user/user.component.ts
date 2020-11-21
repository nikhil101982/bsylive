import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ServiceService } from 'src/app/service.service';
import Swal from 'sweetalert2'


class CreateAccountParams {
  userName: string;
  userEmail: string;
  password: string;
}

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {

  constructor(private service: ServiceService, private router: Router) { }

  createAccount: CreateAccountParams = new CreateAccountParams();

  // email
  userEmailSettings = {};
  selecteduserEmailItems = [];
  userEmails: any = [];

  // courses
  userCourseSettings = {};
  selectedUserCourseItems = [];
  userCourses: any = [];

  // all courses
  allCoursesSettings = {};
  selectedAllCoursesItems = [];
  allCourses: any = [];


  showUpdateCourse: boolean = false;
  showAddUser: boolean = false;
  showDelete: boolean = false;


  selectedDeleteEmail: string = "";
  selectedEmail: string = "";

  allCoursesSelectedList: any = [];
  existingCouresSelectedList: any = [];


  ngOnInit() {

    this.onClickUpdateCourse();
    this.getAllEmailsList();
    this.getAllCourses();
  }


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

  getAllEmailsList() {

    // this.userEmails = [
    //   {
    //     userEmail: 'sudheer@test.com',
    //     userName: 'sudheer'
    //   },
    //   {
    //     userEmail: 'sudheer123@test.com',
    //     userName: 'sudheer123'
    //   }
    // ]
    // this.userEmailSettings = this.getDropdownSettingsData('userName', 'userEmail', true);
    // this.getSelectedEmailCourses();

    this.service.getAllEmails().subscribe(res => {
      console.log("all email ids = ", res);
      if (res['status'] === 'success') {
        this.userEmails = res['userEmails'];
        this.userEmailSettings = this.getDropdownSettingsData('userEmail', 'userEmail', true);
      }
    })
  }

  getSelectedEmailCourses() {

    // this.userCourseSettings = this.getDropdownSettingsData('courseId', 'courseName', false);

    // let data = [
    //   { courseId: 1, courseName: 'Hatha Yoga' },
    //   { courseId: 2, courseName: 'Raja Yoga' },
    // ]
    // this.userCourses = data;
    // this.selectedUserCourseItems = this.userCourses;
    this.service.getSelectedEmailCourses(this.selectedEmail).subscribe(res => {
      if (res['status'] === 'success') {
        this.userCourses = res['courses'];
        console.log("user courses", this.userCourses);

        this.selectedUserCourseItems = this.userCourses;
        this.userCourseSettings = this.getDropdownSettingsData('courseId', 'courseName', false);
      }
      console.log("courses list", res);
    })
  }


  getAllCourses() {

    // this.allCoursesSettings = this.getDropdownSettingsData('courseId', 'courseName', false);

    // let data = [
    //   { courseId: 1, courseName: 'Hatha Yoga' },
    //   { courseId: 2, courseName: 'Raja Yoga' },
    //   { courseId: 3, courseName: 'Yoga 123' },
    //   { courseId: 4, courseName: 'Yoga 1122' },
    //   { courseId: 5, courseName: 'Yoga 345678' },
    //   { courseId: 6, courseName: 'Yoga 9988' },
    //   { courseId: 7, courseName: 'Yoga 76445' }
    // ]

    // this.allCourses = data;

    this.service.getAllCourses().subscribe(res => {
      if (res['status'] === 'success') {
        console.log("all courses = ", this.allCourses);
        this.allCourses = res['courses'];
        this.allCoursesSettings = this.getDropdownSettingsData('courseId', 'courseName', false);
      }
    })
  }


  onItemSelect(item: any, section) {

    console.log(item, section);
    if (section === 'delete') {
      this.selectedDeleteEmail = item.userEmail
    }

    if (section === 'userEmail') {
      console.log("userEmail", section);
      this.selectedEmail = item.userEmail;
      this.getSelectedEmailCourses();
    }

    if (section === 'existingCourse') {
      console.log("existingCourse", section);
      this.existingCouresSelectedList.push(item);
    }

    if (section === 'allCourses') {
      console.log("allCourses", section);
      this.allCoursesSelectedList.push(item);
    }
  }


  onItemDeSelect(item: any, section) {

    console.log(item);
    if (section === 'delete') {
      this.selectedDeleteEmail = "";
    }

    if (section === 'userEmail') {
      console.log("userEmail", section);
      this.selectedEmail = "";
    }

    if (section === 'existingCourse') {
    var index;
   this.existingCouresSelectedList = this.userCourses;
       console.log("existingCourse", section, this.userCourses);
       this.userCourses.map((data, i) =>{
      if(data.courseId == item.courseId){
      console.log("index ==== ", data, i)
index = i
      }
      })
      this.existingCouresSelectedList.splice(index, 1);
      console.log("final list = ", this.existingCouresSelectedList)
     }

    if (section === 'allCourses') {
    var index; 
    this.allCoursesSelectedList= this.allCourses;
      console.log("allCourses", section, this.allCourses);
      this.allCourses.map((course, i) => {
      if(course.courseId == item.courseid){
index = i
      }
      })
     //  const index = this.allCoursesSelectedList.findIndex(element => element.courseId === item.courseId);
      this.allCoursesSelectedList.splice(index, 1);
      console.log("all courses final list = ", this.allCoursesSelectedList)
    }
  }


  onSelectAll(items: any, section) {
    console.log(items);
    if (section === 'existingCourse') {
      console.log("existingCourse", section);
      this.existingCouresSelectedList = items;
    }

    if (section === 'allCourses') {
      console.log("allCourses", section);
      this.allCoursesSelectedList = items;
    }
    console.log("qwwqqqqw", this.existingCouresSelectedList, this.allCoursesSelectedList);
  }


  onClickUpdateCourse() {
    this.showUpdateCourse = true;
    this.showAddUser = false;
    this.showDelete = false;
  }

  onclickAddUser() {
    this.showAddUser = true;
    this.showUpdateCourse = false;
    this.showDelete = false;
  }

  onClickDeleteUser() {
    this.showDelete = true;
    this.showUpdateCourse = false;
    this.showAddUser = false;
  }

  onClickAddUserBtn() {
    let params = {
      userName: this.createAccount.userName,
      userEmail: this.createAccount.userEmail,
      password: btoa(this.createAccount.password)
    }
    this.service.createAccountByAdmin(params).subscribe((res) => {
      console.log("create account res in admin = ", res);
      if (res['status'] === 'success') {
        this.showAlertMessage('success', res['message']);
        this.router.navigate(['/courses']);
      }
      if (res['status'] === 'failure') {
        this.showAlertMessage('error', res['message']);
      }
    });
  }


  onClickDeleteUserBtn() {
    if (this.selectedDeleteEmail) {
      this.service.deleteUser(this.selectedDeleteEmail).subscribe(res => {
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

  updateUserCourses() {
    let obj = {
      userEmail: this.selectedEmail,
      existingCourses: this.existingCouresSelectedList.length > 0 ? this.existingCouresSelectedList : this.userCourses,
      newCourses: this.allCoursesSelectedList
    }
    console.log("update user obj = ", obj);
    if (this.selectedEmail) {
      this.service.updateUserCoursesByAdmin(obj).subscribe(res => {
        console.log("res = ", res);
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
}
