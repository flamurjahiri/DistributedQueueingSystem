import { Component, OnInit } from '@angular/core';
import { NgModule } from '@angular/core';
import * as moment from 'moment';
import { QueuesService } from 'src/app/services/queues.service';

@Component({
  selector: 'app-add-queue',
  templateUrl: './add-queue.component.html',
  styleUrls: ['./add-queue.component.scss']
})
export class AddQueueComponent implements OnInit {
  city: string;
  createdAt: string;
  task: string;
  selectDate: Date;
  selectTime: string;
  todayDate: Date;

  tasks: string[];
  constructor(private queuesService: QueuesService) {
    this.tasks = ['PrimeNumberWritter', 'GetTodayQueues', 'WriteToFile', 'ReadFromFile', 'WriteFinishedQueue', 'WriteFailedQueue']
    this.todayDate = new Date(Date.now());
  }

  ngOnInit() {
    this.selectDate = new Date();
  }
  onSubmit() {
    var executionDateInput = new Date(this.selectDate);
      var parts = this.selectTime.match(/(\d+):(\d+) (AM|PM)/);
      if (parts) {
          var hours = parseInt(parts[1]),
              minutes = parseInt(parts[2]),
              tt = parts[3];
          if (tt === 'PM' && hours < 12) hours += 12;
          executionDateInput.setHours(hours, minutes, 0, 0);
      }
    // let date = moment(new Date(this.selectDate).setTime(startTime)).toISOString(); 
    let executionDate = moment(new Date(executionDateInput)).toISOString();
   
    let data = {
      city: this.city,
      executionDate: executionDate,
      type: "NEW",
      task: this.task
    }
    this.queuesService.postQueues(data).subscribe(
      res => {
        console.log('res', res)
      },
      err => {
        console.log(err)
      }
    )
  }
  setTask(event) {
    this.task = event.value;
  }
}
