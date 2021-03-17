import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTable } from '@angular/material';
import { QueuesService } from 'src/app/services/queues.service';

@Component({
  selector: 'app-finished-queues',
  templateUrl: './finished-queues.component.html',
  styleUrls: ['./finished-queues.component.scss']
})
export class FinishedQueuesComponent implements OnInit {
  displayedColumns: string[] = ['city', 'createdAt', 'executionDate', 'task', 'type'];
  @ViewChild(MatTable, {static: true}) table: MatTable<any>;
  dataSource;
  constructor(private queuesService: QueuesService) { 
    this.queuesService.getFinishedQueues().subscribe(
      res => {
        console.log(res)
        this.dataSource = res;
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
    city: 'Peja',
    createdAt: '2021-02-03T21:22:10.656+00:00',
    executionDate: '2021-02-03T22:07:18.000+00:00',
    task: 'PrimeNumberWritter',
    type: 'FAILED'
  }
];

