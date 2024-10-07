/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author vicen
 */
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ReporteGenerador {

    public static void generarReporteVotantes(List<Votante> votantes, String nombreArchivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo))) {
            // Escribir la cabecera
            String cabeceraRut = "RUT";
            String cabeceraRegion = "Region";
            String cabeceraComuna = "Nombre Comuna";
            String cabeceraLocal = "ID Local";

            // Calcular anchos máximos
            int maxAnchoRut = cabeceraRut.length();
            int maxAnchoRegion = cabeceraRegion.length();
            int maxAnchoComuna = cabeceraComuna.length();
            int maxAnchoLocal = cabeceraLocal.length();

            for (Votante votante : votantes) {
                maxAnchoRut = Math.max(maxAnchoRut, votante.getRut().length());
                maxAnchoRegion = Math.max(maxAnchoRegion, votante.getRegion().length());
                maxAnchoComuna = Math.max(maxAnchoComuna, votante.getComuna().length());
                maxAnchoLocal = Math.max(maxAnchoLocal, String.valueOf(votante.getIdLocal()).length());
            }

            // Mostrar cabecera con anchos dinámicos
            String formatoCabecera = " | %-" + maxAnchoRut + "s | %-" + maxAnchoRegion + "s | %-" + maxAnchoComuna + "s | %-" + maxAnchoLocal + "s |\n";
            String lineaSeparadora = " " + "-".repeat(maxAnchoRut + 2) + "+" + "-".repeat(maxAnchoRegion + 2) + "+" + "-".repeat(maxAnchoComuna + 2) + "+" + "-".repeat(maxAnchoLocal + 2) + "-";

            // Escribir cabecera en el archivo
            writer.write(lineaSeparadora + "\n");
            writer.write(String.format(formatoCabecera, cabeceraRut, cabeceraRegion, cabeceraComuna, cabeceraLocal));
            writer.write(lineaSeparadora + "\n");

            // Escribir cada votante
            for (Votante votante : votantes) {
                writer.write(String.format(formatoCabecera,
                        votante.getRut(),
                        votante.getRegion(),
                        votante.getComuna(),
                        votante.getIdLocal()));
            }

            // Escribir la línea separadora final
            writer.write(lineaSeparadora + "\n");

            System.out.println("Reporte generado: " + nombreArchivo);
        } catch (IOException e) {
            System.out.println("Error al generar el reporte: " + e.getMessage());
        }
    }
}
