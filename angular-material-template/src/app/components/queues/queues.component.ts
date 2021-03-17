import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTable } from '@angular/material';
import { QueuesService } from 'src/app/services/queues.service';

@Component({
  selector: 'app-queues',
  templateUrl: './queues.component.html',
  styleUrls: ['./queues.component.scss']
})



export class QueuesComponent implements OnInit {
  displayedColumns: string[] = ['city', 'createdAt', 'executionDate', 'task', 'type'];
  @ViewChild(MatTable, {static: true}) table: MatTable<any>;
  dataSource;
  constructor(private queuesService: QueuesService) {
      this.queuesService.getAllQueues().subscribe(
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
    city: 'Prishtina',
    createdAt: '2021-02-05T22:08:10.656+00:00',
    executionDate: '2021-02-05T22:07:18.000+00:00',
    task: 'WriteToFile',
    type: 'NEW'
  },
  {
    city: 'Peja',
    createdAt: '2021-02-03T21:22:10.656+00:00',
    executionDate: '2021-02-03T22:07:18.000+00:00',
    task: 'PrimeNumberWritter',
    type: 'FAILED'
  },
  {
    city: 'Gjilan',
    createdAt: '2021-02-03T21:22:10.656+00:00',
    executionDate: '2021-02-03T01:00:00.000+00:00',
    task: 'GetTodayQueues',
    type: 'FINISHED'
  }
];
