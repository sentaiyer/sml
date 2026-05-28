import { CommonModule } from '@angular/common';
import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ApiService } from '../services/ApiService';
import { switchMap, finalize } from 'rxjs/operators';

interface Vehiculo {
  id: number;
  placa: string;
  tipo: string;
}

interface Ingreso {
  id: number;
  fechaIngreso: string;
  fechaSalida: string | null;
  vehiculo: Vehiculo;
}

@Component({
  selector: 'app-home',
  imports: [CommonModule, FormsModule],
  templateUrl: './home.html',
  styleUrl: './home.css',
})

export class Home implements OnInit {

  placaIngreso = '';
  cargando = false;
  guardandoVehiculo = false;
  registrandoIngreso = false;
  mensaje = '';
  error = '';
  ingresos: Ingreso[] = [];
  tipo=''
  cargandoIngreso = false;
  cargandoLista = false;



  constructor(private apiService: ApiService, private cdr: ChangeDetectorRef) {}

  ngOnInit(): void {
this.cargarIngresos();

  }




  registrarIngreso(): void {

    this.limpiarMensajes();

    if (!this.placaIngreso) {
      alert('Ingresa una placa para registrar el ingreso.');
      return;
    }

    const body = {
      placa: this.placaIngreso,
      tipo: this.tipo
    };

    this.apiService.crearVehiculo(body).subscribe({
      next: () => {
        alert('✔ Vehículo registrado correctamente');

      },
      error: () => {
        alert('❌ Error en el servidor o red');
      }
    });
    this.cargarIngresos();
  }

  cargarIngresos(): void {

    this.cargandoLista = true;
    this.apiService.obtenerIngresos()
      .subscribe({
        next: (ingresos) => {
          this.ingresos = ingresos ?? [];
        },
        error: () => {
          this.error = 'No se pudieron cargar los ingresos.';
        }
      });
  }

  formatearFecha(fecha?: string | null): string {
    if (!fecha) {
      return 'Pendiente';
    }

    return new Intl.DateTimeFormat('es-CO', {
      dateStyle: 'medium',
      timeStyle: 'short',
    }).format(new Date(fecha));
  }

  private normalizarPlaca(placa: string): string {
    return placa.trim().toUpperCase();
  }

  private limpiarMensajes(): void {
    this.mensaje = '';
    this.error = '';
  }

  registrarSalida(id: number): void {

    this.cargando = true;

    this.apiService.registrarSalida(id)
      .pipe(finalize(() => this.cargando = false))
      .subscribe({
        next: (res) => {
          this.mensaje = `Salida registrada para ${id}`;
          this.apiService.obtenerIngresos().subscribe({
            next: (data) => {
              this.ingresos = [...(data ?? [])];
              this.cdr.detectChanges();
            }
          });
        },
        error: () => {
          this.error = 'Error registrando salida';
        }
      });

    this.calcularTiempo(this.ingresos[id-1].fechaIngreso, this.ingresos[id-1].fechaSalida);
  }

  calcularTiempo(fechaIngreso: string, fechaSalida: string | null): string {
    if (!fechaSalida) return 'En curso';

    const inicio = new Date(fechaIngreso);
    const salida = new Date(fechaSalida);

    const diffMs = salida.getTime() - inicio.getTime();

    const minutos = Math.floor(diffMs / (1000 * 60));
    const horas = Math.floor(minutos / 60);
    const minutosRestantes = minutos % 60;

    return `${horas}h ${minutosRestantes}m`;
  }

  calcularTarifa(fechaIngreso: string, fechaSalida: string | null): number | string {
    if (!fechaSalida) return 'En curso';

    const inicio = new Date(fechaIngreso);
    const salida = new Date(fechaSalida);

    const diffMs = salida.getTime() - inicio.getTime();

    const minutos = Math.ceil(diffMs / (1000 * 60)); // redondea hacia arriba

    const tarifaPorMinuto = 50;

    return minutos * tarifaPorMinuto;

  }

}
