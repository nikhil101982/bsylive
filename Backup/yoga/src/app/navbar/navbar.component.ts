import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import * as $ from 'jquery'
import { ServiceService } from '../service.service';

import Swal from 'sweetalert2'

class LogInParams {
  userEmail: string = "";
  password: string = "";
}

class ForgotPasswordParams {
  userEmail: string = "";
}

class CreateNewAccParams {
  userName: string = "";
  userEmail: string = "";
  password: string = "";
}


@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  showCreateAccSection: boolean = false;
  showLoginSection: boolean = true;
  showForgotpassSection: boolean = false;
  showLogIn: boolean = false;
  showLogInError: boolean = false;

  userDetails: any;
  userName: string;

  logInParams: LogInParams = new LogInParams();
  forgotPassParams: ForgotPasswordParams = new ForgotPasswordParams();
  createNewParams: CreateNewAccParams = new CreateNewAccParams();

  constructor(private router: Router, private service: ServiceService) {
    this.service.updateLogInOption.subscribe(res => {
      this.showLogInOption();
    })
  }

  ngOnInit() {
    this.showLogInOption();
    this.userDetails = this.service.getUserData();
    this.userName = this.userDetails['userName'];
  }

  resetData() {
    this.showLoginDiv();
    this.logInParams = { userEmail: "", password: "" };
    this.forgotPassParams = { userEmail: "" };
    this.createNewParams = { userEmail: "", password: "", userName: "" };
  }


  showLogInOption() {
    this.showLogIn = false;
    if (window.location.href.indexOf('login') >= 0) {
      this.showLogIn = true;
    }
  }


  showCreateAccDiv() {
    this.showCreateAccSection = true;
    this.showLoginSection = false;
    this.showForgotpassSection = false;
  }

  showLoginDiv() {
    this.showLoginSection = true;
    this.showCreateAccSection = false;
    this.showForgotpassSection = false;
  }

  showForgotPasswordDiv() {
    this.showForgotpassSection = true;
    this.showLoginSection = false;
    this.showCreateAccSection = false;
  }


  onClickLogIn() {
    this.showLogInError = false;
    let obj = {
      userEmail: this.logInParams.userEmail,
      password: btoa(this.logInParams.password)
    }
    // localStorage.setItem('userDetails', JSON.stringify({ userEmail: this.logInParams.userEmail }));
    var oneHr = 60 * 60 * 1000;

    this.service.getLogInData(obj)
      .subscribe((res) => {
        console.log("log in res = ", res);
        if (res['status'] === 'success') {
          console.log("success", res, res['status'])
          this.userName = res['userDetails'].userName;
          localStorage.setItem('timestamp', JSON.stringify(new Date().getTime() + oneHr))
          localStorage.setItem('userDetails', JSON.stringify(res['userDetails']));
          this.router.navigate(['/courses']);
        }
        if (res['status'] === 'failure') {
          console.log("error", res, res['status'])
          this.showLogInError = true;
          this.showAlert('error', res['message']);
        }
      });
  }

  onClickJoinFree() {
    let params = {
      userName: this.createNewParams.userName,
      userEmail: this.createNewParams.userEmail,
      password: btoa(this.createNewParams.password)
    }
    this.service.createAccount(params)
      .subscribe((res) => {
        console.log("create account res = ", res);
        if (res['status'] === 'success') {
          this.showAlert('success', res['message']);
        }
        if (res['status'] === 'failure') {
          this.showLogInError = true;
          this.showAlert('error', res['message']);
        }
      });
  }

  onClickResetPassword() {
    let text = "We are sending instrauctions to reset your password. If you do not receive an email, check your spam folder or make sure this email address is registered with Bihar Yoga"

    this.service.getForgotPasswordData(this.forgotPassParams)
      .subscribe((res) => {
        console.log("reset password res= ", res);
        if (res['status'] === 'success') {
          Swal.fire({
            icon: 'info',
            title: res['message'],
            showConfirmButton: true,
          })
        }
        if (res['status'] === 'failure') {
          this.showLogInError = true;
          this.showAlert('error', res['message']);
        }
      });
  }

  showAlert(type, text) {
    console.log("type, text = ", type, text);
    Swal.fire({
      // position: 'top-end',
      icon: type,
      title: text,
      showConfirmButton: false,
      timer: 2000
    })
  }

  onClickLogout() {
    console.log("log out");
    let obj = {
      userEmail: this.userDetails.userEmail
    }
    this.service.logout(obj).subscribe(res => {
      if (res['status'] === 'success') {
        this.router.navigate(['login']);
        localStorage.clear();
      }
      if (res['status'] === 'failure') {
         this.showAlert('error', res['message']);
      }
    })
  }

}
