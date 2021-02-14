import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

class LogInParams {
  userEmail: string;
  password: string;
}

class ForgotPasswordParams {
  userEmail: string;
}

class CreateNewAccParams {
  userName: string;
  userEmail: string;
  password: string;
}

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  showCreateAccSection: boolean = false;
  showLoginSection: boolean = true;
  showForgotpassSection: boolean = false;

  logInParams: LogInParams = new LogInParams();
  forgotPassParams: ForgotPasswordParams = new ForgotPasswordParams();
  createNewParams: CreateNewAccParams = new CreateNewAccParams();

  constructor(private router: Router) { }

  ngOnInit() {
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
    let obj = {
      userEmail: this.logInParams.userEmail,
      password: btoa(this.logInParams.password)
    }
    console.log("log in params = ", obj);
    this.router.navigate(['/courses']);
  }

  onClickJoinFree() {
    let params = {
      userName: this.createNewParams.userName,
      userEmail: this.createNewParams.userEmail,
      password: btoa(this.createNewParams.password)
    }
    console.log("create acc params = ", params);
  }

  onClickResetPassword() {
    console.log("on click reset params = ", this.forgotPassParams)
  }

}
