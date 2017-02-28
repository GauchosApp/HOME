package m2wapps.ar.ifitweremyhome;

/**
 * Created by mariano on 27/02/2017.
 */

class Datos {
    //1 positivo, 0 negativo, 2 neutral
    private int tipo;
    private String titulo, detalle;
    Datos(int tipo, String titulo, String detalle){
        this.tipo = tipo;
        this.titulo = titulo;
        this.detalle = detalle;
    }


    String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
}
