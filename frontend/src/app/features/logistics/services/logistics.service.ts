import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, catchError } from 'rxjs';
import {
  OptimizationRequest,
  OptimizationResponse,
  Route
} from '../models/logistics.model';
import { ErrorHandlingService } from './error-handling.service';
import {environment} from '@env/environment';

@Injectable({
  providedIn: 'root'
})
export class LogisticsService {
  private apiUrl = `${environment.apiUrl}/logistics`;

  constructor(
    private http: HttpClient,
    private errorHandling: ErrorHandlingService
  ) {}

  optimizeRoutes(request: OptimizationRequest): Observable<OptimizationResponse> {
    return this.http.post<OptimizationResponse>(`${this.apiUrl}/optimize`, request)
      .pipe(catchError(this.errorHandling.handleError));
  }

  getRouteDetails(routeId: string): Observable<Route> {
    return this.http.get<Route>(`${this.apiUrl}/route/${routeId}`)
      .pipe(catchError(this.errorHandling.handleError));
  }

  simulateRoutes(request: any): Observable<Route[]> {
    return this.http.post<Route[]>(`${this.apiUrl}/routes/simulate`, request)
      .pipe(catchError(this.errorHandling.handleError));
  }
}
