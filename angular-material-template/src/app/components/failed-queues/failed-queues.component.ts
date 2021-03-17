import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTable } from '@angular/material';
import { QueuesService } from 'src/app/services/queues.service';

@Component({
  selector: 'app-failed-queues',
  templateUrl: './failed-queues.component.html',
  styleUrls: ['./failed-queues.component.scss']
})
export class FailedQueuesComponent implements OnInit {
  displayedColumns: string[] = ['city', 'createdAt', 'executionDate', 'task', 'type'];
  @ViewChild(MatTable, {static: true}) table: MatTable<any>;
  dataSource;
  constructor(private queuesService: QueuesService) { 
    this.queuesService.getFailedQueues().subscribe(
      res => {
        this.dataSource = res
      },
      err => {
        console.log(err)
      }
    )
  }

  ngOnInit() {
  }

}

export interface Queues {
  city: string;
  createdAt: string;
  executionDate: string;
  task: string;
  type: string;
}

const data: Queues[] = [
  {
    city: 'Gjilan',
    createdAt: '2021-02-03T21:22:10.656+00:00',
    executionDate: '2021-02-03T01:00:00.000+00:00',
    task: 'GetTodayQueues',
    type: 'FINISHED'
  }
];
