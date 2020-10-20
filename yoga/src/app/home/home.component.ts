import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ServiceService } from '../service.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(private service: ServiceService, private router: Router) { }

  ngOnInit() {
    console.log("login", localStorage.getItem('userDetails'));
    if (localStorage.getItem('userDetails')) {
      console.log("local storage has data");
      this.router.navigate(['courses']);
    }
    this.service.logInButtonUpdate(true);
  }

  ngOnDestroy() {
    this.service.logInButtonUpdate(false);
  }

}
