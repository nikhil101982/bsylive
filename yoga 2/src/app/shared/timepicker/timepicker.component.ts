import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-timepicker',
  templateUrl: './timepicker.component.html',
  styleUrls: ['./timepicker.component.css']
})
export class TimepickerComponent implements OnInit {

  @Input() selectedTime;

  @Output() onSelectTime = new EventEmitter<any>();

  constructor() { }

  timeList: any = [
    "12:00 AM", "12:30 AM",
    "01:00 AM", "01:30 AM",
    "02:00 AM", "02:30 AM",
    "03:00 AM", "03:30 AM",
    "04:00 AM", "04:30 AM",
    "05:00 AM", "05:30 AM",
    "06:00 AM", "06:30 AM",
    "07:00 AM", "07:30 AM",
    "08:00 AM", "08:30 AM",
    "09:00 AM", "09:30 AM",
    "10:00 AM", "10:30 AM",
    "11:00 AM", "11:30 AM",

    "12:00 PM", "12:30 PM",
    "01:00 PM", "01:30 PM",
    "02:00 PM", "02:30 PM",
    "03:00 PM", "03:30 PM",
    "04:00 PM", "04:30 PM",
    "05:00 PM", "05:30 PM",
    "06:00 PM", "06:30 PM",
    "07:00 PM", "07:30 PM",
    "08:00 PM", "08:30 PM",
    "09:00 PM", "09:30 PM",
    "10:00 PM", "10:30 PM",
    "11:00 PM", "11:30 PM",

    // { time: "12:00 AM" }, { time: "12:30 AM" },
    // { time: "1:00 AM" }, { time: "1:30 AM" },
    // { time: "2:00 AM" }, { time: "2:30 AM" },
    // { time: "3:00 AM" }, { time: "3:30 AM" },
    // { time: "4:00 AM" }, { time: "4:30 AM" },
    // { time: "5:00 AM" }, { time: "5:30 AM" },
    // { time: "6:00 AM" }, { time: "6:30 AM" },
    // { time: "7:00 AM" }, { time: "7:30 AM" },
    // { time: "8:00 AM" }, { time: "8:30 AM" },
    // { time: "9:00 AM" }, { time: "9:30 AM" },
    // { time: "10:00 AM" }, { time: "10:30 AM" },
    // { time: "11:00 AM" }, { time: "11:30 AM" },

    // { time: "12:00 PM" }, { time: "12:30 PM" },
    // { time: "1:00 PM" }, { time: "1:30 PM" },
    // { time: "2:00 PM" }, { time: "2:30 PM" },
    // { time: "3:00 PM" }, { time: "3:30 PM" },
    // { time: "4:00 PM" }, { time: "4:30 PM" },
    // { time: "5:00 PM" }, { time: "5:30 PM" },
    // { time: "6:00 PM" }, { time: "6:30 PM" },
    // { time: "7:00 PM" }, { time: "7:30 PM" },
    // { time: "8:00 PM" }, { time: "8:30 PM" },
    // { time: "9:00 PM" }, { time: "9:30 PM" },
    // { time: "10:00 PM" }, { time: "10:30 PM" },
    // { time: "11:00 PM" }, { time: "11:30 PM" },
  ]

  ngOnInit(): void {
    this.onSelectTimePicker();
  }

  onSelectTimePicker() {
    console.log("time = ", this.selectedTime);
    this.onSelectTime.emit(this.selectedTime);
  }

}
