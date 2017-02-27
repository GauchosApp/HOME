package m2wapps.ar.ifitweremyhome;

/**
 * Created by mariano on 27/02/2017.
 */

class Datos {
    //0 positivo, 1 negativo, 2 neutral
    private int tipo;
    private String titulo, detalle;
    Datos(int tipo, String titulo, String detalle){
        this.tipo = tipo;
        this.titulo = titulo;
        this.detalle = detalle;
    }


    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
}
