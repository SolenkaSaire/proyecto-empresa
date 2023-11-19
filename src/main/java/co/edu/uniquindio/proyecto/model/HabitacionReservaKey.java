package co.edu.uniquindio.proyecto.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class HabitacionReservaKey implements Serializable {
    private int reserva;
    private int habitacion;

    public HabitacionReservaKey() {
    }

    public HabitacionReservaKey(int reserva, int habitacion) {
        this.reserva = reserva;
        this.habitacion = habitacion;
    }

    public int getReserva() {
        return this.reserva;
    }

    public void setReserva(int reserva) {
        this.reserva = reserva;
    }

    public int getHabitacion() {
        return this.habitacion;
    }

    public void setHabitacion(int habitacion) {
        this.habitacion = habitacion;
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof HabitacionReservaKey)) return false;
        final HabitacionReservaKey other = (HabitacionReservaKey) o;
        if (!other.canEqual((Object) this)) return false;
        if (this.getReserva() != other.getReserva()) return false;
        if (this.getHabitacion() != other.getHabitacion()) return false;
        return true;
    }

    protected boolean canEqual(Object other) {
        return other instanceof HabitacionReservaKey;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + this.getReserva();
        result = result * PRIME + this.getHabitacion();
        return result;
    }

    public String toString() {
        return "HabitacionReservaKey(reserva=" + this.getReserva() + ", habitacion=" + this.getHabitacion() + ")";
    }
}