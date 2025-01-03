import { Routes } from '@angular/router';
import {RouteOptimizerComponent} from './features/logistics/components/route-optimizer/route-optimzer.component';

export const routes: Routes = [
  { path: '', redirectTo: 'optimizer', pathMatch: 'full' },
  { path: 'optimizer', component: RouteOptimizerComponent }
];
