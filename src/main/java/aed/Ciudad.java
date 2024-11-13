package aed;

public class Ciudad{
    private int id;
    private int indiceSuperavit;
    private int ganancias;
    private int perdidas;

    public Ciudad(int id, int indiceSuperavit){
        this.indiceSuperavit = indiceSuperavit;
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
    public int getGanancia(){
        return this.ganancias;
    }
    public int getPerdida(){
        return this.perdidas;
    }
    public int getSuperavit(){
        return this.ganancias - this.perdidas;
    }
    public int obtenerId(){
        return this.id;
    }
    public void setIndiceSuperavit(int indice){
        this.indiceSuperavit = indice;
    }
    public int getIndiceSuperavit(){
        return this.indiceSuperavit;
    }
}
