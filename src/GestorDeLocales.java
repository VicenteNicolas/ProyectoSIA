/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author vicen
 */
import java.sql.*;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.*;

public class GestorDeLocales {

    // Mapa de locales donde la clave es el id_comuna y el valor es una lista de locales en esa comuna
    private Map<Integer, List<LocalDeVotacion>> localesPorComuna = new HashMap<>();
    private static Connection connection;
    private GestorDeVotantes gestorDeVotantes;

    public GestorDeLocales(Connection connection) throws SQLException {
        this.connection = connection;
        this.gestorDeVotantes = new GestorDeVotantes(connection, this);
        cargarLocalesDesdeBaseDeDatos();
    }

    public LocalDeVotacion asignarVotante(Votante votante) throws SQLException {
        if (!esRegionExistente(votante.getRegion())) {
            System.out.println("La región ingresada no existe.");
            return null;
        }
        LocalDeVotacion localAsignado = encontrarLocalAsignado(votante);
        if (localAsignado != null) {
            // Encontrar el local actual del votante (si ya está asignado a alguno)
            LocalDeVotacion localAnterior = encontrarLocalDeVotante(votante);

            if (localAnterior != null && !localAsignado.equals(localAnterior)) {
                // Restar 1 de la capacidad actual del local anterior
                actualizarCapacidadLocalEnBD(localAnterior); // Actualizar en la base de datos
                localAnterior.decrementarCapacidadActual();

            }

            // Asignar el votante al nuevo local
            localAsignado.getVotantesAsignados().add(votante);
            localAsignado.incrementarCapacidadActual(); // Incrementar capacidad actual en memoria
            actualizarCapacidadLocalEnBD(localAsignado);

        } else {
            System.out.println("No se pudo asignar al votante con RUT " + votante.getRut() + " porque no hay locales con capacidad disponible en su comuna.");
        }
        return localAsignado;
    }

    public LocalDeVotacion encontrarLocalDeVotante(Votante votante) {
        // Obtiene el idComuna del votante
        int idComuna = votante.getIdComuna();
        // Verifica si existen locales para la comuna del votante
        List<LocalDeVotacion> localesDeComuna = localesPorComuna.get(idComuna);
        if (localesDeComuna != null) {
            // Itera sobre los locales de la comuna específica para encontrar el que tiene al votante
            for (LocalDeVotacion local : localesDeComuna) {
                if (local.getVotantesAsignados().contains(votante)) {
                    return local; // Se encontró el local
                }
            }
        }

        // No se encontró el local
        return null;
    }

    public LocalDeVotacion obtenerLocalPorId(int idLocal) throws SQLException {
        LocalDeVotacion local = null;
        String query = "SELECT id_local, nombre, direccion, capacidad_maxima, id_comuna, capacidad_actual FROM locales_votacion WHERE id_local = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, idLocal);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {

                    local = new LocalDeVotacion(
                            rs.getInt("id_local"),
                            rs.getString("nombre"),
                            rs.getString("direccion"),
                            rs.getInt("capacidad_maxima"),
                            rs.getInt("capacidad_actual"),
                            rs.getInt("id_comuna")
                    );
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error al obtener el local por ID: " + e.getMessage(), e);
        }

        return local;
    }

    private String normalizarTexto(String texto) {
        if (texto == null) {
            return null;
        }
        return Normalizer.normalize(texto, Form.NFD)
                .replaceAll("\\p{M}", "")
                .toLowerCase();
    }

    public static String normalizarRegion(String region) throws SQLException {
        String regionExacta = buscarRegionExacta(region);
        if (regionExacta != null) {
            return regionExacta;
        } else {
            return region;
        }
    }

    private static String buscarRegionExacta(String region) throws SQLException {
        String regionExacta = null;
        String query = "SELECT nombre FROM regiones WHERE nombre LIKE ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, "%" + region + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    regionExacta = rs.getString("nombre");
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error al buscar la región: " + e.getMessage(), e);
        }
        return regionExacta;
    }

    public boolean esRegionExistente(String nombreRegion) throws SQLException {
        String nombreNormalizado = normalizarTexto(nombreRegion);
        String query = "SELECT nombre FROM regiones WHERE LOWER(nombre) LIKE ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, "%" + nombreNormalizado + "%");
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            throw new SQLException("Error al verificar existencia de la región: " + e.getMessage(), e);
        }
    }

    public boolean esComunaExistente(String nombreComuna) {
        String query = "SELECT COUNT(*) FROM comunas WHERE nombre_comuna = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, nombreComuna);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Devuelve true si hay al menos una comuna con ese nombre
            }
        } catch (SQLException e) {
            System.out.println("Error al verificar la existencia de la comuna: " + e.getMessage());
        }
        return false;
    }

    private LocalDeVotacion encontrarLocalAsignado(Votante votante) {
        List<LocalDeVotacion> locales = localesPorComuna.get(votante.getIdComuna());
        if (locales == null) {
            return null;
        }

        for (LocalDeVotacion local : locales) {
            System.out.println("Capacidad actual del local: " + local.getCapacidadActual() + "/" + local.getCapacidadMaxima());
            if (local.getVotantesAsignados().size() < local.getCapacidadMaxima()) {
                return local;
            } else {
                System.out.println("Local sin capacidad disponible: " + local.getNombre());
            }
        }
        return null;
    }

    public boolean eliminarLocal(String nombre) throws SQLException {
        boolean eliminadoDeBD = eliminarLocalDeBaseDeDatos(nombre);
        if (!eliminadoDeBD) {
            return false; // No se pudo eliminar de la base de datos
        }

        // Eliminar local del mapa
        for (List<LocalDeVotacion> localesEnComuna : localesPorComuna.values()) {
            Iterator<LocalDeVotacion> iterator = localesEnComuna.iterator();
            while (iterator.hasNext()) {
                LocalDeVotacion local = iterator.next();
                if (local.getNombre().equals(nombre)) {
                    iterator.remove();
                    return true;
                }
            }
        }

        if (eliminadoDeBD) {
            return true; 
        }
        return false;
    }

    private boolean eliminarLocalDeBaseDeDatos(String nombre) throws SQLException {
        String query = "DELETE FROM locales_votacion WHERE nombre = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, nombre);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            throw new SQLException("Error al eliminar el local de la base de datos: " + e.getMessage(), e);
        }
    }

    public void addLocal(LocalDeVotacion local) {
        // Añadir local al mapa de acuerdo a su id_comuna
        localesPorComuna.computeIfAbsent(local.getIdComuna(), k -> new ArrayList<>()).add(local);
    }

    public Map<Integer, List<LocalDeVotacion>> getLocalesPorComuna() {
        return localesPorComuna;
    }

    public void actualizarCapacidadLocalEnBD(LocalDeVotacion local) throws SQLException {
        String query = "UPDATE locales_votacion SET capacidad_actual = ? WHERE id_local = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, local.getCapacidadActual());
            stmt.setInt(2, local.getIdLocal());

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("La capacidad del local con ID " + local.getIdLocal() + " ha sido actualizada.");
            } else {
                System.out.println("No se pudo actualizar la capacidad del local con ID " + local.getIdLocal() + ". Verifica que el ID sea correcto.");
            }
        } catch (SQLException e) {
            throw new SQLException("Error al actualizar la capacidad del local en la base de datos: " + e.getMessage(), e);
        }
    }

    public void decrementarCapacidadLocal(LocalDeVotacion local) throws SQLException {
        String query = "UPDATE locales_votacion SET capacidad_actual = capacidad_actual - 1 WHERE id_local = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, local.getIdLocal());
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("La capacidad del local con ID " + local.getIdLocal() + " ha sido decrementada.");
            } else {
                System.out.println("No se pudo actualizar la capacidad del local con ID " + local.getIdLocal() + ".");
            }
        } catch (SQLException e) {
            throw new SQLException("Error al decrementar la capacidad del local en la base de datos: " + e.getMessage(), e);
        }
    }

    private void cargarLocalesDesdeBaseDeDatos() throws SQLException {
        String query = "SELECT id_local, nombre, direccion, capacidad_maxima, id_comuna, capacidad_actual FROM locales_votacion";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                int idLocal = rs.getInt("id_local");
                String nombre = rs.getString("nombre");
                String direccion = rs.getString("direccion");
                int capacidadMaxima = rs.getInt("capacidad_maxima");
                int idComuna = rs.getInt("id_comuna"); // Obtener id_comuna
                int capacidadActual = rs.getInt("capacidad_actual");
                LocalDeVotacion local = new LocalDeVotacion(idLocal, nombre, direccion, capacidadMaxima, capacidadActual, idComuna);

                // Añadir local al mapa de acuerdo a su id_comuna
                localesPorComuna.computeIfAbsent(idComuna, k -> new ArrayList<>()).add(local);
            }
        } catch (SQLException e) {
            throw new SQLException("Error al cargar los locales desde la base de datos: " + e.getMessage(), e);
        }
    }

    public List<LocalDeVotacion> obtenerLocalesPorComuna(int idComuna) {
        List<LocalDeVotacion> localesDeComuna = new ArrayList<>();

        // Obtener los locales de la comuna especificada
        List<LocalDeVotacion> locales = localesPorComuna.get(idComuna);

        if (locales != null) {
            localesDeComuna.addAll(locales); // Añadir todos los locales de la comuna
        }

        return localesDeComuna;
    }

    public List<LocalDeVotacion> obtenerLocalesPorNombreComuna(String nombreComuna) {
        List<LocalDeVotacion> localesDeComuna = new ArrayList<>();

        // Obtener el idComuna desde el nombre de la comuna
        int idComuna = gestorDeVotantes.obtenerIdComunaPorNombre(nombreComuna);

        // Verificar que el idComuna sea válido (diferente de 0, o un valor no válido)
        if (idComuna != 0) {
            // Llamar al método que obtiene los locales por idComuna
            localesDeComuna = obtenerLocalesPorComuna(idComuna);
        } else {
            System.out.println("La comuna " + nombreComuna + " no fue encontrada.");
        }

        return localesDeComuna;
    }

    public List<LocalDeVotacion> obtenerLocalesPorRegion(int idRegion) throws SQLException {
        List<LocalDeVotacion> localesDeRegion = new ArrayList<>();

        // Iterar sobre todas las comunas y locales
        for (Map.Entry<Integer, List<LocalDeVotacion>> entry : localesPorComuna.entrySet()) {
            int idComuna = entry.getKey();
            List<LocalDeVotacion> locales = entry.getValue();

            // Obtener el idRegion de la comuna desde la base de datos o de algún otro recurso
            int idRegionDeComuna = obtenerIdRegionPorComuna(idComuna);

            // Verificar si la comuna pertenece a la región deseada
            if (idRegionDeComuna == idRegion) {
                localesDeRegion.addAll(locales); // Añadir todos los locales de la comuna si pertenece a la región
            }
        }

        return localesDeRegion;
    }

    public int obtenerIdRegionPorComuna(int idComuna) throws SQLException {
        String query = "SELECT id_region FROM comunas WHERE id_comuna = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idComuna);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id_region");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener el idRegion de la comuna: " + e.getMessage());
            throw e;
        }
        return -1; // Retornar un valor no válido si no se encuentra la región
    }
}
