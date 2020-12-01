import { Component, OnInit } from '@angular/core';
import { ServiceService } from '../service.service';
import Swal from 'sweetalert2'
import { Router } from '@angular/router';


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
  enableSuccessMsg: boolean = false;

  userDetails: any;
  successMsgText: any;

  changePasswordParams: ChangePasswordParams = new ChangePasswordParams();

  constructor(private service: ServiceService, private router: Router) { }

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
    this.enableSuccessMsg = false;
    this.passwordMismatch = false;
    if (this.changePasswordParams.newPassword !== this.changePasswordParams.retypePassword) {
      this.passwordMismatch = true;
    }
    if (!this.passwordMismatch) {
      console.log("hit change password api");
      let obj = {
        userEmail: this.userDetails.userEmail,
        password: btoa(this.changePasswordParams.oldPassword),
        newPassword: btoa(this.changePasswordParams.retypePassword)
      }
      this.service.updatePassword(obj).subscribe(res => {
        console.log("change password completed = ", res)
        if (res) {
          if (res['status'] === 'success') {
            this.enableSuccessMsg = true;
            this.successMsgText = res['message'];
            this.showAlert('success', res['message'])
            localStorage.clear();
            this.router.navigate(['/login']);
          }
          if (res['status'] === 'failure') {
            this.showAlert('error', res['message'])
          }
        }
      })
    }
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
}
