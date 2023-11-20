package co.edu.uniquindio.proyecto.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.io.Serializable;

@Embeddable

public class DetalleCompraArticuloKey implements Serializable{

    private Integer articuloTuristico;
    private Integer compra;

    public DetalleCompraArticuloKey() {
    }

    public DetalleCompraArticuloKey(int articuloTuristico, int compra) {
        this.articuloTuristico = articuloTuristico;
        this.compra = compra;
    }

    public int getArticuloTuristico() {
        return this.articuloTuristico;
    }

    public void setArticuloTuristico(int articuloTuristico) {
        this.articuloTuristico = articuloTuristico;
    }

    public int getCompra() {
        return this.compra;
    }

    public void setCompra(int compra) {
        this.compra = compra;
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof DetalleCompraArticuloKey)) return false;
        final DetalleCompraArticuloKey other = (DetalleCompraArticuloKey) o;
        if (!other.canEqual((Object) this)) return false;
        if (this.getArticuloTuristico() != other.getArticuloTuristico()) return false;
        if (this.getCompra() != other.getCompra()) return false;
        return true;
    }

    protected boolean canEqual(Object other) {
        return other instanceof DetalleCompraArticuloKey;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + this.getArticuloTuristico();
        result = result * PRIME + this.getCompra();
        return result;
    }

    public String toString() {
        return "DetalleCompraArticuloKey(articuloTuristico=" + this.getArticuloTuristico() + ", compra=" + this.getCompra() + ")";
    }



}
