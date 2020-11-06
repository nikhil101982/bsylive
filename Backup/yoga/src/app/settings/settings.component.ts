import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.css']
})
export class SettingsComponent implements OnInit {

  accounts: boolean = false;
  payment: boolean = false;

  constructor() { }

  ngOnInit(): void {
    this.onClickAccounts();
  }

  onClickAccounts() {
    this.accounts = true;
    this.payment = false;
  }

  onClickPayment() {
    this.payment = true;
    this.accounts = false;
  }

}
