import { Component, OnInit } from '@angular/core';
import { ServiceService } from '../service.service';

class ChangePasswordParams {
  oldPassword: string = "";
  newPassword: string = "";
  retypePassword: string = "";
}

@Component({
  selector: 'app-account-settings',
  templateUrl: './account-settings.component.html',
  styleUrls: ['./account-settings.component.css']
})
export class AccountSettingsComponent implements OnInit {

  showCurrentPassword: boolean = false;
  showNewPassword: boolean = false;
  showRetypePassword: boolean = false;

  passwordMismatch: boolean = false;

  userDetails: any;

  changePasswordParams: ChangePasswordParams = new ChangePasswordParams();

  constructor(private service: ServiceService) { }

  ngOnInit(): void {
    this.userDetails = this.service.getUserData();
  }

  onClickCurrentPassword() {
    this.showCurrentPassword = !this.showCurrentPassword;
  }

  onClickNewPassword() {
    this.showNewPassword = !this.showNewPassword;
  }

  onClickRetypePassword() {
    this.showRetypePassword = !this.showRetypePassword;
  }

  onClickChangePassword() {
    console.log("aaaaaaaaaa", this.changePasswordParams);
    this.passwordMismatch = false;
    if (this.changePasswordParams.newPassword !== this.changePasswordParams.retypePassword) {
      this.passwordMismatch = true;
    }
    if (!this.passwordMismatch) {
      console.log("hit change password api");
      let obj = {
        userEmail: this.userDetails.userEmail,
        password: this.changePasswordParams.oldPassword,
        newPassword: this.changePasswordParams.retypePassword
      }
      this.service.updatePassword(obj).subscribe(res => {
        console.log("change password completed = ", res)
      })
    }
  }
}
