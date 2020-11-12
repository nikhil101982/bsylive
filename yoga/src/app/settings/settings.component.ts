import { Component, OnInit } from '@angular/core';
import { Roles } from '../constants/roles';
import { ServiceService } from '../service.service';

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.css']
})
export class SettingsComponent implements OnInit {

  accounts: boolean = false;
  user: boolean = false;

  userDetails: any;
  userRole: string;
  enableUserSection: boolean = false;

  constructor(private service: ServiceService) { }

  ngOnInit(): void {
    this.onClickAccounts();

    this.userDetails = this.service.getUserData();

    // if (this.userDetails.userRole) this.userRole = this.userDetails.userRole;

    this.userRole = this.userDetails.userRole;

    if (this.userRole === Roles.admin) {
      this.enableUserSection = true;
    }

  }

  onClickAccounts() {
    this.accounts = true;
    this.user = false;
  }

  onClickUser() {
    this.user = true;
    this.accounts = false;
  }

}
