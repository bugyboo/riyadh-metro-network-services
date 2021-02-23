import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModule } from './material/material.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { HttpClientModule } from '@angular/common/http';

import { HomeComponent } from './home/home.component';
import { MainComponent } from './home/main/main.component';
import { TrainsComponent } from './home/trains/trains.component';
import { UserManagerComponent } from './home/user-manager/user-manager.component';

import { AgmCoreModule } from '@agm/core';
import { LoginComponent } from './login/login.component';
import { SaveUserDialogComponent } from './home/dialogs/save-user-dialog/save-user-dialog.component';
import { DeleteUserDialogComponent } from './home/dialogs/delete-user-dialog/delete-user-dialog.component';
import { TrainEditComponent } from './home/dialogs/train-edit/train-edit.component';
import { TrainTimeEditComponent } from './home/dialogs/train-time-edit/train-time-edit.component';
import { StationEditComponent } from './home/dialogs/station-edit/station-edit.component';
import { RouteEditComponent } from './home/dialogs/route-edit/route-edit.component';
import { DeleteComponent } from './home/dialogs/delete/delete.component';
import { DistPipe } from './pipes/dist.pipe';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    MainComponent,
    TrainsComponent,
    UserManagerComponent,
    LoginComponent,
    SaveUserDialogComponent,
    DeleteUserDialogComponent,
    TrainEditComponent,
    TrainTimeEditComponent,
    StationEditComponent,
    RouteEditComponent,
    DeleteComponent,
    DistPipe
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    ReactiveFormsModule, FormsModule,
    MaterialModule,
    HttpClientModule,
    AgmCoreModule.forRoot({
      apiKey: 'AIzaSyAPAooCPGKasQ2ORFeJc7UyE5rC8cq7ADU'
    })
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
