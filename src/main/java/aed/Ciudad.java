package aed;

public class Ciudad{
    private int id;
    private int ganancias;
    private int perdidas;

    public Ciudad(int id){
        this.id = id;
        this.ganancias = 0;
        this.perdidas = 0;
    }

    public void agregarGanancia(int ganancia){
        this.ganancias += ganancia;
    }
    public void agregarPerdida(int perdida){
        this.perdidas += perdida;
    }
    public int obtenerGanancia(){
        return this.ganancias;
    }
    public int obtenerPerdida(){
        return this.perdidas;
    }
    public int obtenerSuperavit(){
        return this.ganancias - this.perdidas;
    }
    public int obtenerId(){
        return this.id;
    }

}
