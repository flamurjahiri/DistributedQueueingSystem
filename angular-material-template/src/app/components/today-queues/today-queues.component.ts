import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTable } from '@angular/material';
import { QueuesService } from 'src/app/services/queues.service';

@Component({
  selector: 'app-today-queues',
  templateUrl: './today-queues.component.html',
  styleUrls: ['./today-queues.component.scss']
})
export class TodayQueuesComponent implements OnInit {
  dataSource;
  displayedColumns: string[] = ['city', 'createdAt', 'executionDate', 'task', 'type'];
  @ViewChild(MatTable, {static: true}) table: MatTable<any>;
  constructor(private queueService: QueuesService) { 
    this.queueService.getTodayQueues().subscribe(
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
  }
];
