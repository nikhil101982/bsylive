import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminCourseSectionComponent } from './admin-course-section.component';

describe('AdminCourseSectionComponent', () => {
  let component: AdminCourseSectionComponent;
  let fixture: ComponentFixture<AdminCourseSectionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdminCourseSectionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminCourseSectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
