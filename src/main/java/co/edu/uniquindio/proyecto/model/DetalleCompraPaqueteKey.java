package co.edu.uniquindio.proyecto.model;


import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public class DetalleCompraPaqueteKey implements Serializable {

    private Integer compra;
    private Integer paqueteTuristico;

    public DetalleCompraPaqueteKey() {
    }

    public DetalleCompraPaqueteKey(Integer compra, Integer paqueteTuristico) {
        this.compra = compra;
        this.paqueteTuristico = paqueteTuristico;
    }

    public Integer getCompra() {
        return compra;
    }

    public void setCompra(Integer compra) {
        this.compra = compra;
    }

    public Integer getPaqueteTuristico() {
        return paqueteTuristico;
    }

    public void setPaqueteTuristico(Integer paqueteTuristico) {
        this.paqueteTuristico = paqueteTuristico;
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof DetalleCompraPaqueteKey)) return false;
        final DetalleCompraPaqueteKey other = (DetalleCompraPaqueteKey) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$compra = this.getCompra();
        final Object other$compra = other.getCompra();
        if (this$compra == null ? other$compra != null : !this$compra.equals(other$compra)) return false;
        final Object this$paqueteTuristico = this.getPaqueteTuristico();
        final Object other$paqueteTuristico = other.getPaqueteTuristico();
        if (this$paqueteTuristico == null ? other$paqueteTuristico != null : !this$paqueteTuristico.equals(other$paqueteTuristico))
            return false;
        return true;
    }

    protected boolean canEqual(Object other) {
        return other instanceof DetalleCompraPaqueteKey;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $compra = this.getCompra();
        result = result * PRIME + ($compra == null ? 43 : $compra.hashCode());
        final Object $paqueteTuristico = this.getPaqueteTuristico();
        result = result * PRIME + ($paqueteTuristico == null ? 43 : $paqueteTuristico.hashCode());
        return result;
    }

    public String toString() {
        return "DetalleCompraPaqueteKey(compra=" + this.getCompra() + ", paqueteTuristico=" + this.getPaqueteTuristico() + ")";
    }
}
