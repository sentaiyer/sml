import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

const API = 'http://localhost:8080';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  constructor(private http: HttpClient) {}

  crearVehiculo(data: any): Observable<any> {

    return this.http.post(`${API}/vehiculos`, data);
  }

  obtenerVehiculos(): Observable<any> {
    return this.http.get(`${API}/vehiculos`);
  }

  registrarIngreso(placa: string): Observable<any> {
    return this.http.post(`${API}/ingresos/placa/${placa}`, {});
  }

  obtenerIngresos(): Observable<any> {
    return this.http.get(`${API}/ingresos`);
  }

  registrarSalida(id: number) {
    return this.http.put(`${API}/ingresos/salida/${id}`, {});
  }
}
