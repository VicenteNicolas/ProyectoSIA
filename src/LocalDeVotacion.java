/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author vicen
 */
import java.util.ArrayList;
import java.util.List;

public class LocalDeVotacion {

    private int idLocal; // ID del local
    private String nombre;
    private String direccion;
    private int capacidadMaxima;
    private int capacidadActual;
    private int idComuna; // Añadido para identificar la comuna del local
    private List<Votante> votantesAsignados = new ArrayList<>();

    // Constructor con idLocal
    public LocalDeVotacion(int idLocal, String nombre, String direccion, int capacidadMaxima, int capacidadActual, int idComuna) {
        this.idLocal = idLocal;
        this.nombre = nombre;
        this.direccion = direccion;
        this.capacidadMaxima = capacidadMaxima;
        this.capacidadActual = capacidadActual;
        this.idComuna = idComuna;
        this.votantesAsignados = new ArrayList<>();
    }

    // Constructor sin idLocal
    public LocalDeVotacion(String nombre, String direccion, int capacidadMaxima, int capacidadActual, int idComuna) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.capacidadMaxima = capacidadMaxima;
        this.capacidadActual = capacidadActual;
        this.idComuna = idComuna;
    }

    // Getters y setters
    public int getIdLocal() {
        return idLocal;
    }

    public void setIdLocal(int idLocal) {
        this.idLocal = idLocal;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public void setCapacidadMaxima(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
    }

    public int getCapacidadActual() {
        return capacidadActual; 
    }

    public void setCapacidadActual(int capacidadActual) {
        this.capacidadActual = capacidadActual;
    }

    public int getIdComuna() {
        return idComuna;
    }

    public void setIdComuna(int idComuna) {
        this.idComuna = idComuna;
    }

    public List<Votante> getVotantesAsignados() {
        return votantesAsignados;
    }

    public void incrementarCapacidadActual() {
        if (capacidadActual < capacidadMaxima) {
            capacidadActual++;
        } else {
            System.out.println("El local ha alcanzado su capacidad máxima.");
        }
    }

    public void decrementarCapacidadActual() {
        if (capacidadActual > 0) {
            capacidadActual--;
        } else {
            System.out.println("La capacidad actual ya está en 0 y no se puede decrementar.");
        }
    }

    @Override
    public String toString() {
        return "LocalDeVotacion{"
                + "idLocal=" + idLocal
                + ", nombre='" + nombre + '\''
                + ", direccion='" + direccion + '\''
                + ", capacidadMaxima=" + capacidadMaxima
                + ", idComuna=" + idComuna
                + '}';
    }
}
