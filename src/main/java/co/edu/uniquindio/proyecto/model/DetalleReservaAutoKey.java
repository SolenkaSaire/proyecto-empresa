package co.edu.uniquindio.proyecto.model;


import jakarta.persistence.Embeddable;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.io.Serializable;

@Embeddable
public class DetalleReservaAutoKey implements Serializable {

    private Integer reservaAutomovil;
    private Integer automovil;

    public DetalleReservaAutoKey() {
    }

    public DetalleReservaAutoKey(int reservaAutomovil, int automovil) {
        this.reservaAutomovil = reservaAutomovil;
        this.automovil = automovil;
    }

    public int getReservaAutomovil() {
        return this.reservaAutomovil;
    }

    public void setReservaAutomovil(int reservaAutomovil) {
        this.reservaAutomovil = reservaAutomovil;
    }

    public int getAutomovil() {
        return this.automovil;
    }

    public void setAutomovil(int automovil) {
        this.automovil = automovil;
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof DetalleReservaAutoKey)) return false;
        final DetalleReservaAutoKey other = (DetalleReservaAutoKey) o;
        if (!other.canEqual((Object) this)) return false;
        if (this.getReservaAutomovil() != other.getReservaAutomovil()) return false;
        if (this.getAutomovil() != other.getAutomovil()) return false;
        return true;
    }

    protected boolean canEqual(Object other) {
        return other instanceof DetalleReservaAutoKey;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + this.getReservaAutomovil();
        result = result * PRIME + this.getAutomovil();
        return result;
    }

    public String toString() {
        return "DetalleReservaAutoKey(reservaAutomovil=" + this.getReservaAutomovil() + ", automovil=" + this.getAutomovil() + ")";
    }


}
