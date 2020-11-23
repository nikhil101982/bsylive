import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DatepickerComponentComponent } from './datepicker-component.component';

describe('DatepickerComponentComponent', () => {
  let component: DatepickerComponentComponent;
  let fixture: ComponentFixture<DatepickerComponentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DatepickerComponentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DatepickerComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
