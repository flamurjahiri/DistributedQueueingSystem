import { MaterialModule } from './modules/material/material.module';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import {NgxMaterialTimepickerModule} from 'ngx-material-timepicker';
import { HttpClientModule } from '@angular/common/http';


import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MainLayoutComponent } from './components/main-layout/main-layout.component';
import { AddQueueComponent } from './components/add-queue/add-queue.component';
import { QueuesComponent } from './components/queues/queues.component';
import { TodayQueuesComponent } from './components/today-queues/today-queues.component';
import { FailedQueuesComponent } from './components/failed-queues/failed-queues.component';
import { FinishedQueuesComponent } from './components/finished-queues/finished-queues.component';

@NgModule({
  declarations: [
    AppComponent,
    MainLayoutComponent,
    AddQueueComponent,
    QueuesComponent,
    TodayQueuesComponent,
    FailedQueuesComponent,
    FinishedQueuesComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialModule,
    FormsModule,
    NgxMaterialTimepickerModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
