import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavbarComponent } from './navbar/navbar.component';
import { CoursesComponent } from './courses/courses.component';
import { CoursesDetailsComponent } from './courses-details/courses-details.component';
import { HttpClientModule } from '@angular/common/http';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import { AccountSettingsComponent } from './account-settings/account-settings.component';
import { SettingsComponent } from './settings/settings.component';
import { UserComponent } from './settings/user/user.component';
import { NgMultiSelectDropDownModule } from 'ng-multiselect-dropdown';
import { AdminCourseSectionComponent } from './settings/admin-course-section/admin-course-section.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { DatepickerComponentComponent } from './shared/datepicker-component/datepicker-component.component';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    CoursesComponent,
    CoursesDetailsComponent,
    LoginComponent,
    HomeComponent,
    AccountSettingsComponent,
    SettingsComponent,
    UserComponent,
    AdminCourseSectionComponent,
    DatepickerComponentComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    NgMultiSelectDropDownModule.forRoot(),
    NgbModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
