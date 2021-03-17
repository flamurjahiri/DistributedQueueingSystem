import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AddQueueComponent } from './components/add-queue/add-queue.component';
import { FailedQueuesComponent } from './components/failed-queues/failed-queues.component';
import { FinishedQueuesComponent } from './components/finished-queues/finished-queues.component';
import { MainLayoutComponent } from './components/main-layout/main-layout.component';
import { QueuesComponent } from './components/queues/queues.component';
import { TodayQueuesComponent } from './components/today-queues/today-queues.component';


const routes: Routes = [
  {
    path: '', 
    component: MainLayoutComponent,
    children: [
      {
        path: 'add',
        component: AddQueueComponent
      },
      {
        path: 'failed',
        component: FailedQueuesComponent
      },
      {
        path: 'finished',
        component: FinishedQueuesComponent,
      },
      {
        path: 'queues',
        component: QueuesComponent
      },
      {
        path: 'today',
        component: TodayQueuesComponent
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
