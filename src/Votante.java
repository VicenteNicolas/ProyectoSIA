
import java.util.Objects;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author vicen
 */
public class Votante {
    private String rut;
    private String region;
    private String comuna;
    private int idLocal; // Añadido para la asignación a un local
    private int idComuna;

    public Votante(String rut, String region, String comuna, int idLocal, int idComuna) {
        this.rut = rut;
        this.region = region;
        this.comuna = comuna;
        this.idLocal = idLocal;
        this.idComuna = idComuna;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Votante votante = (Votante) obj;
        return Objects.equals(rut, votante.rut);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rut);
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getComuna() {
        return comuna;
    }

    public void setComuna(String comuna) {
        this.comuna = comuna;
    }

    public int getIdLocal() {
        return idLocal;
    }

    public void setIdLocal(int idLocal) {
        this.idLocal = idLocal;
    }
    
    public int getIdComuna(){
        return idComuna;
    }
    
    public void setIdComuna(int idComuna){
        this.idComuna = idComuna;
    }

    @Override
    public String toString() {
        return "Votante{" +
                "rut='" + rut + '\'' +
                ", region='" + region + '\'' +
                ", comuna='" + comuna + '\'' +
                ", idLocal=" + idLocal +
                '}';
    }
}
