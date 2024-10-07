/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author vicen
 */
import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;

public class Proyecto {

    private static GestorDeVotantes gestorDeVotantes;
    private static GestorDeLocales gestorDeLocales;
    private static final BufferedReader lector = new BufferedReader(new InputStreamReader(System.in));
    private static boolean running = true;

    public static void main(String[] args) throws IOException, SQLException {
        ConexionBD conexionBD = new ConexionBD();

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MenuPrincipal().setVisible(true);
            }
        });

        try {
            Connection connection = conexionBD.getConnection();
            if (connection != null) {
                gestorDeLocales = new GestorDeLocales(connection);
                gestorDeVotantes = new GestorDeVotantes(connection, gestorDeLocales);

                while (running) {
                    mostrarMenu();
                    String opcion = lector.readLine();
                    procesarOpcion(opcion);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en la base de datos: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error en la entrada/salida: " + e.getMessage());
        } finally {
            conexionBD.cerrarConexion(); // Cerrar la conexión al finalizar
        }
    }

    private static void mostrarMenu() {
        System.out.println();
        System.out.println("========== Menu Principal ==========");
        System.out.println("1. Ingresar votante");
        System.out.println("2. Eliminar votante");
        System.out.println("3. Actualizar direccion de votante");
        System.out.println("4. Mostrar votante por RUT");
        System.out.println("5. Ver locales por región o comuna");
        System.out.println("6. Eliminar local");
        System.out.println("7. Generar reporte de votantes");
        System.out.println("8. Salir");
        System.out.print("Elija una opcion: ");
        System.out.println();
    }

    private static void procesarOpcion(String opcion) throws IOException, SQLException {
        switch (opcion) {
            case "1":
                ingresarVotante();
                break;
            case "2":
                eliminarVotante();
                break;
            case "3":
                actualizarDireccionVotante();
                break;
            case "4":
                mostrarVotantePorRut();
                break;
            case "5":
                verLocalesPorRegionOComuna();
                break;
            case "6":
                eliminarLocal();
                break;
            case "7":
                generarReporteVotantes();
                break;
            case "8":
                salirDelPrograma();
                break;
            default:
                System.out.println("Opcion no valida. Por favor, elija una opcion valida.");
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    private static void generarReporteVotantes() throws SQLException {
        List<Votante> votantes = gestorDeVotantes.obtenerTodosLosVotantes();
        String nombreArchivo = System.getProperty("user.home") + "/Downloads/reporte_votantes.txt";
        ReporteGenerador.generarReporteVotantes(votantes, nombreArchivo); // Llama al generador de reportes
    }

    public static void generarReporteVotantesDesdeGUI() {
        try {
            List<Votante> votantes = gestorDeVotantes.obtenerTodosLosVotantes();
            String nombreArchivo = System.getProperty("user.home") + "/Downloads/reporte_votantes.txt";

            // Llama al generador de reportes
            ReporteGenerador.generarReporteVotantes(votantes, nombreArchivo);

            // Mostrar mensaje de éxito
            JOptionPane.showMessageDialog(null, "Reporte generado exitosamente en: " + nombreArchivo, "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al generar el reporte: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Se produjo un error inesperado: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void ingresarVotante() {
        try {
            System.out.println("Ingrese el RUT del votante:");
            String rut = lector.readLine();
            rut = GestorDeDatos.normalizarRut(rut);

            if (!GestorDeDatos.esRutValido(rut)) {
                System.out.println("El RUT ingresado no es válido.");
                return;
            }

            if (gestorDeVotantes.esRutExistente(rut)) {
                System.out.println("El RUT ya existe en el registro de votantes.");
                return;
            }

            System.out.println("Ingrese la región del votante: ");
            String region = lector.readLine();
            region = gestorDeLocales.normalizarRegion(region);

            if (!gestorDeLocales.esRegionExistente(region)) {
                System.out.println("La región ingresada no existe.");
                return;
            }

            System.out.println("Ingrese la comuna del votante: ");
            String comuna = lector.readLine();

            int idComuna = gestorDeVotantes.obtenerIdComunaPorNombre(comuna);
            if (idComuna == -1) {
                System.out.println("Comuna no encontrada");
                return;
            }

            Votante votante = new Votante(rut, region, comuna, 0, idComuna); // Inicialmente el idLocal es 0
            LocalDeVotacion localAsignado = gestorDeLocales.asignarVotante(votante);

            if (localAsignado != null) {
                votante.setIdLocal(localAsignado.getIdLocal()); // Asignar idLocal al votante
                gestorDeVotantes.addVotante(votante);
                Votante votanteVerificado = gestorDeVotantes.obtenerVotantePorRut(rut);
                if (votanteVerificado != null) {
                    System.out.println("Votante con RUT " + votante.getRut() + " ha sido asignado al local: " + localAsignado.getNombre());
                } else {
                    System.out.println("No se pudo asignar al votante con RUT " + votante.getRut());
                }
            } else {
                System.out.println("No se pudo asignar al votante con RUT " + votante.getRut() + " porque no hay locales con capacidad disponible en su región y comuna.");
            }
        } catch (IOException e) {
            System.out.println("Error al leer la entrada: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Error en la base de datos: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Se produjo un error inesperado: " + e.getMessage());
        }
    }

    public static void ingresarVotanteDesdeGUI(String rut, String region, String comuna) {
        try {
            rut = GestorDeDatos.normalizarRut(rut);

            if (!GestorDeDatos.esRutValido(rut)) {
                JOptionPane.showMessageDialog(null, "El RUT ingresado no es válido.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (gestorDeVotantes.esRutExistente(rut)) {
                JOptionPane.showMessageDialog(null, "El RUT ya existe en el registro de votantes.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            region = gestorDeLocales.normalizarRegion(region);

            if (!gestorDeLocales.esRegionExistente(region)) {
                JOptionPane.showMessageDialog(null, "La región ingresada no existe.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int idComuna = gestorDeVotantes.obtenerIdComunaPorNombre(comuna);
            if (idComuna == -1) {
                JOptionPane.showMessageDialog(null, "Comuna no encontrada", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Votante votante = new Votante(rut, region, comuna, 0, idComuna); // Inicialmente el idLocal es 0
            LocalDeVotacion localAsignado = gestorDeLocales.asignarVotante(votante);

            if (localAsignado != null) {
                votante.setIdLocal(localAsignado.getIdLocal()); // Asignar idLocal al votante
                gestorDeVotantes.addVotante(votante);
                Votante votanteVerificado = gestorDeVotantes.obtenerVotantePorRut(rut);
                if (votanteVerificado != null) {
                    JOptionPane.showMessageDialog(null, "Votante con RUT " + votante.getRut() + " ha sido asignado al local: " + localAsignado.getNombre(), "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo asignar al votante con RUT " + votante.getRut(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo asignar al votante con RUT " + votante.getRut() + " porque no hay locales con capacidad disponible en su región y comuna.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en la base de datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Se produjo un error inesperado: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void eliminarVotante() throws IOException, SQLException {
        System.out.println("Ingrese el RUT del votante a eliminar:");
        String rut = lector.readLine();
        rut = GestorDeDatos.normalizarRut(rut);

        if (gestorDeVotantes.eliminarVotante(rut)) {
            System.out.println("Votante con RUT " + rut + " eliminado correctamente.");
        } else {
            System.out.println("No se pudo encontrar o eliminar el votante con RUT " + rut + ".");
        }
    }

    public static void eliminarVotanteDesdeGUI(String rut) {
        try {
            rut = GestorDeDatos.normalizarRut(rut);

            if (gestorDeVotantes.eliminarVotante(rut)) {
                JOptionPane.showMessageDialog(null, "Votante con RUT " + rut + " eliminado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo encontrar o eliminar el votante con RUT " + rut + ".", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en la base de datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Se produjo un error inesperado: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void actualizarDireccionVotante() throws IOException {
        System.out.println("Ingrese el RUT del votante a actualizar:");
        String rut = lector.readLine();
        rut = GestorDeDatos.normalizarRut(rut);

        if (!gestorDeVotantes.esRutExistente(rut)) {
            System.out.println("El RUT no existe en el registro de votantes.");
            return;
        }

        try {
            System.out.println("Ingrese la nueva region: ");
            String nuevaRegion = lector.readLine();
            nuevaRegion = gestorDeLocales.normalizarRegion(nuevaRegion);

            // Verificar si la región existe antes de continuar
            if (!gestorDeLocales.esRegionExistente(nuevaRegion)) {
                throw new RegionNoExistenteException("La región especificada no existe.");
            }

            System.out.println("Ingrese la nueva comuna: ");
            String nuevaComuna = lector.readLine();

            if (gestorDeVotantes.actualizarDireccionVotante(rut, nuevaRegion, nuevaComuna)) {
                System.out.println("Dirección del votante con RUT " + rut + " actualizada correctamente.");
            } else {
                System.out.println("No se pudo actualizar la dirección del votante con RUT " + rut + ".");
            }
        } catch (RegionNoExistenteException e) {
            System.out.println(e.getMessage()); // Muestra el mensaje de error si la región no existe
        } catch (ComunaNoExistenteException e) {
            System.out.println(e.getMessage()); // Muestra el mensaje de error si la comuna no pertenece a la región
        } catch (SQLException e) {
            System.out.println("Error de base de datos: " + e.getMessage());
        }
    }

    public static void actualizarDireccionVotanteDesdeGUI(String rut, String nuevaRegion, String nuevaComuna) {
        try {
            rut = GestorDeDatos.normalizarRut(rut);

            if (!gestorDeVotantes.esRutExistente(rut)) {
                JOptionPane.showMessageDialog(null, "El RUT no existe en el registro de votantes.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            nuevaRegion = gestorDeLocales.normalizarRegion(nuevaRegion);

            // Verificar si la región existe antes de continuar
            if (!gestorDeLocales.esRegionExistente(nuevaRegion)) {
                throw new RegionNoExistenteException("La región especificada no existe.");
            }

            // Intentar actualizar la dirección
            if (gestorDeVotantes.actualizarDireccionVotante(rut, nuevaRegion, nuevaComuna)) {
                JOptionPane.showMessageDialog(null, "Dirección del votante con RUT " + rut + " actualizada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo actualizar la dirección del votante con RUT " + rut + ".", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (RegionNoExistenteException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ComunaNoExistenteException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error de base de datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Se produjo un error inesperado: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void eliminarLocal() throws IOException, SQLException {
        System.out.println("Ingrese el nombre del local a eliminar:");
        String nombre = lector.readLine();

        if (gestorDeLocales.eliminarLocal(nombre)) {
            System.out.println("Local " + nombre + " eliminado correctamente.");
        } else {
            System.out.println("No se pudo encontrar o eliminar el local " + nombre + ".");
        }
    }

    public static void eliminarLocalDesdeGUI(String nombre) {
        try {
            if (gestorDeLocales.eliminarLocal(nombre)) {
                JOptionPane.showMessageDialog(null, "Local " + nombre + " eliminado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo encontrar o eliminar el local " + nombre + ".", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en la base de datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Se produjo un error inesperado: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void mostrarVotantePorRut() throws IOException, SQLException {
        System.out.println("Ingrese el RUT del votante que desea mostrar:");
        String rut = lector.readLine();
        mostrarVotante(rut);
    }

    private static void mostrarVotante(String rut) throws SQLException {
        rut = GestorDeDatos.normalizarRut(rut);

        // Consultar a través del gestor de votantes
        Votante votanteEncontrado = gestorDeVotantes.obtenerVotantePorRut(rut);

        if (votanteEncontrado != null) {
            String nombreLocal;

            // Si el votante tiene un idLocal asignado, busca el local correspondiente
            if (votanteEncontrado.getIdLocal() != 0) {
                LocalDeVotacion localDelVotante = gestorDeLocales.obtenerLocalPorId(votanteEncontrado.getIdLocal());
                if (localDelVotante != null) {
                    nombreLocal = localDelVotante.getNombre();
                } else {
                    nombreLocal = "No asignado"; // Si no se encuentra el local, mostrar "No asignado"
                }
            } else {
                nombreLocal = "No asignado"; // Si el idLocal es 0, mostrar "No asignado"
            }

            // Aquí se calcula el ancho máximo
            int maxAnchoRut = "Rut".length();
            int maxAnchoRegion = "Region".length();
            int maxAnchoComuna = "Comuna".length();
            int maxAnchoLocal = "Local".length();

            maxAnchoRut = Math.max(maxAnchoRut, votanteEncontrado.getRut().length());
            maxAnchoRegion = Math.max(maxAnchoRegion, votanteEncontrado.getRegion().length());
            maxAnchoComuna = Math.max(maxAnchoComuna, votanteEncontrado.getComuna().length());
            maxAnchoLocal = Math.max(maxAnchoLocal, nombreLocal.length());

            // Mostrar cabecera con anchos dinámicos
            String formatoCabecera = " | %-" + maxAnchoRut + "s | %-" + maxAnchoRegion + "s | %-" + maxAnchoComuna + "s | %-" + maxAnchoLocal + "s |\n";
            String lineaSeparadora = " " + "-".repeat(maxAnchoRut) + "---" + "-".repeat(maxAnchoRegion) + "---" + "-".repeat(maxAnchoComuna) + "---" + "-".repeat(maxAnchoLocal) + "-";

            System.out.println("Votante con RUT " + votanteEncontrado.getRut() + ":");
            System.out.println(" " + lineaSeparadora);
            System.out.printf(formatoCabecera, "Rut", "Region", "Comuna", "Local");
            System.out.println(" " + lineaSeparadora);

            // Imprimir datos del votante
            System.out.printf(formatoCabecera, votanteEncontrado.getRut(), votanteEncontrado.getRegion(), votanteEncontrado.getComuna(), nombreLocal);
            System.out.println(" " + lineaSeparadora);
        } else {
            System.out.println("No se encontró un votante con el RUT " + rut + ".");
        }
    }

    public static void mostrarVotanteDesdeGUI(String rut) {
        try {
            rut = GestorDeDatos.normalizarRut(rut);

            // Consultar a través del gestor de votantes
            Votante votanteEncontrado = gestorDeVotantes.obtenerVotantePorRut(rut);

            if (votanteEncontrado != null) {
                String nombreLocal;

                // Si el votante tiene un idLocal asignado, busca el local correspondiente
                if (votanteEncontrado.getIdLocal() != 0) {
                    LocalDeVotacion localDelVotante = gestorDeLocales.obtenerLocalPorId(votanteEncontrado.getIdLocal());
                    nombreLocal = (localDelVotante != null) ? localDelVotante.getNombre() : "No asignado";
                } else {
                    nombreLocal = "No asignado";
                }

                // Crear un mensaje con la información del votante
                String mensaje = String.format("RUT: %s\nRegión: %s\nComuna: %s\nLocal: %s",
                        votanteEncontrado.getRut(),
                        votanteEncontrado.getRegion(),
                        votanteEncontrado.getComuna(),
                        nombreLocal);

                JOptionPane.showMessageDialog(null, mensaje, "Datos del Votante", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró un votante con el RUT " + rut + ".", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error de base de datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Se produjo un error inesperado: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void verLocalesPorRegionOComuna() throws IOException, SQLException {
        System.out.println("Seleccione una opción:");
        System.out.println("1. Ver locales por región");
        System.out.println("2. Ver locales por comuna");
        System.out.print("Elija una opcion: ");
        String opcion = lector.readLine();

        switch (opcion) {
            case "1":
                System.out.println("Ingrese el nombre de la región:");
                String nombreRegion = lector.readLine();
                nombreRegion = gestorDeLocales.normalizarRegion(nombreRegion);
                int idRegion = gestorDeVotantes.obtenerIdRegionPorNombreRegion(nombreRegion);
                if (idRegion == 0) { 
                    System.out.println("La región '" + nombreRegion + "' no se encontró.");
                    return; 
                }
                List<LocalDeVotacion> localesPorRegion = gestorDeLocales.obtenerLocalesPorRegion(idRegion);
                if (localesPorRegion.isEmpty()) {
                    System.out.println("No se encontraron locales para la región: " + nombreRegion);
                } else {
                    System.out.println("Locales ubicados en la " + nombreRegion);
                    for (LocalDeVotacion local : localesPorRegion) {
                        System.out.println("Local: " + local.getNombre() + " - Votantes registrados en el local: " + local.getCapacidadActual());
                    }
                }
                break;
            case "2":
                System.out.println("Ingrese el nombre de la comuna:");
                String nombreComuna = lector.readLine();
                int idComuna = gestorDeVotantes.obtenerIdComunaPorNombre(nombreComuna);
                List<LocalDeVotacion> localesPorComuna = gestorDeLocales.obtenerLocalesPorComuna(idComuna);
                if (localesPorComuna.isEmpty()) {
                    System.out.println("No se encontraron locales para la comuna: " + nombreComuna);
                } else {
                    System.out.println("Locales ubicados en "+ nombreComuna);
                    for (LocalDeVotacion local : localesPorComuna) {
                        System.out.println("Local: " + local.getNombre() + " - Votantes registrados en el local: " + local.getCapacidadActual());
                    }
                }
                break;
            default:
                System.out.println("Opción no válida.");
        }
    }

    
    public static void verLocalesPorRegionOComunaGUI(String tipo, String nombre) {
        try {
            if (tipo.equalsIgnoreCase("region")) {
                nombre = gestorDeLocales.normalizarRegion(nombre);
                int idRegion = gestorDeVotantes.obtenerIdRegionPorNombreRegion(nombre);

                if (idRegion == 0) {
                    JOptionPane.showMessageDialog(null, "La región '" + nombre + "' no se encontró.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                List<LocalDeVotacion> localesPorRegion = gestorDeLocales.obtenerLocalesPorRegion(idRegion);
                if (localesPorRegion.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No se encontraron locales para la región: " + nombre, "Información", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    StringBuilder resultado = new StringBuilder("Locales ubicados en la " + nombre + ":\n");
                    for (LocalDeVotacion local : localesPorRegion) {
                        resultado.append("Local: ").append(local.getNombre())
                                 .append(" - Votantes registrados: ").append(local.getCapacidadActual())
                                 .append("\n");
                    }
                    JOptionPane.showMessageDialog(null, resultado.toString(), "Locales por Región", JOptionPane.INFORMATION_MESSAGE);
                }
            } else if (tipo.equalsIgnoreCase("comuna")) {
                int idComuna = gestorDeVotantes.obtenerIdComunaPorNombre(nombre);

                List<LocalDeVotacion> localesPorComuna = gestorDeLocales.obtenerLocalesPorComuna(idComuna);
                if (localesPorComuna.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No se encontraron locales para la comuna: " + nombre, "Información", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    StringBuilder resultado = new StringBuilder("Locales ubicados en " + nombre + ":\n");
                    for (LocalDeVotacion local : localesPorComuna) {
                        resultado.append("Local: ").append(local.getNombre())
                                 .append(" - Votantes registrados: ").append(local.getCapacidadActual())
                                 .append("\n");
                    }
                    JOptionPane.showMessageDialog(null, resultado.toString(), "Locales por Comuna", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Tipo de búsqueda no válido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error de base de datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
    }
    
    private static void salirDelPrograma() {
        System.out.println("Saliendo del programa.");
        running = false;
    }

}
