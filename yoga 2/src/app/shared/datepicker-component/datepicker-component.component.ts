import { Component, EventEmitter, Input, OnInit, Output, SimpleChanges } from '@angular/core';

@Component({
  selector: 'app-datepicker-component',
  templateUrl: './datepicker-component.component.html',
  styleUrls: ['./datepicker-component.component.css']
})
export class DatepickerComponentComponent implements OnInit {

  @Input() selectedDate;

  @Output() onSelectDate = new EventEmitter<any>();

  minDate: any = new Date();
  maxDate: any = new Date();

  constructor() { }

  ngOnInit(): void { }

  onSelectDatePicker(event, date) {
    console.log("date", event, date._inputValue);
    this.onSelectDate.emit(date._inputValue)
  }

}
