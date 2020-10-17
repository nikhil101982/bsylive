import { Component, OnInit } from '@angular/core';
import { ServiceService } from '../service.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(private service: ServiceService) { }

  ngOnInit() {
    this.service.logInButtonUpdate(true);
  }

  ngOnDestroy() {
    this.service.logInButtonUpdate(false);
  }

}
