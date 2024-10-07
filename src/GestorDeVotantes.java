/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author vicen
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GestorDeVotantes {

    private GestorDeLocales gestorDeLocales;
    private Connection connection;

    public GestorDeVotantes(Connection connection, GestorDeLocales gestorDeLocales) {
        this.connection = connection;
        this.gestorDeLocales = gestorDeLocales;
    }

    public List<Votante> obtenerTodosLosVotantes() throws SQLException {
        List<Votante> votantes = new ArrayList<>();
        String sql = "SELECT rut, region, nombre_comuna, id_local, id_comuna FROM votantes"; // Ajusta los nombres de columnas según tu tabla

        try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                String rut = resultSet.getString("rut");
                String region = resultSet.getString("region");
                String nombreComuna = resultSet.getString("nombre_comuna");
                int idLocal = resultSet.getInt("id_local");
                int idComuna = resultSet.getInt("id_comuna");

                Votante votante = new Votante(rut, region, nombreComuna, idLocal, idComuna);
                votantes.add(votante);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener los votantes: " + e.getMessage());
            throw e;
        }

        return votantes;
    }

    public boolean esRutExistente(String rut) {
        String query = "SELECT COUNT(*) FROM votantes WHERE rut = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, rut);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error al verificar el RUT: " + e.getMessage());
        }
        return false;
    }

    public boolean actualizarDireccionVotante(String rut, String nuevaRegion, String nuevaComuna) throws SQLException, RegionNoExistenteException, ComunaNoExistenteException {

        if (!gestorDeLocales.esRegionExistente(nuevaRegion)) {
            throw new RegionNoExistenteException("La región especificada no existe.");
        }

        if (!verificarComunaPerteneceARegion(nuevaComuna, nuevaRegion)) {
            throw new ComunaNoExistenteException("La comuna no pertenece a la región especificada.");
        }

        int idComuna = obtenerIdComunaPorNombre(nuevaComuna);

        if (idComuna <= 0) {
            System.out.println("No se encontró la comuna especificada.");
            return false; // O lanza una excepción personalizada si es necesario
        }

        String query = "UPDATE votantes SET region = ?, id_comuna = ?, nombre_comuna = ? WHERE rut = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, nuevaRegion); // Actualiza la región
            stmt.setInt(2, idComuna); // Actualiza el id_comuna
            stmt.setString(3, nuevaComuna); // Actualiza el nombre_comuna
            stmt.setString(4, rut);
            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                // Asigna un nuevo local de votación basado en el nuevo id_comuna
                LocalDeVotacion nuevoLocal = gestorDeLocales.asignarVotante(new Votante(rut, nuevaRegion, nuevaComuna, 0, idComuna));
                if (nuevoLocal != null) {
                    // Actualiza el id_local del votante si se ha asignado un nuevo local
                    actualizarIdLocalVotante(rut, nuevoLocal.getIdLocal());

                } else {
                    System.out.println("No se pudo asignar un nuevo local de votación.");
                    return false; // Indica que la actualización del local falló
                }
            }

            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.out.println("Error al actualizar la dirección del votante: " + e.getMessage());
        }
        return false; // Devuelve falso si hubo un error
    }

    private void actualizarIdLocalVotante(String rut, int nuevoIdLocal) throws SQLException {
        String query = "UPDATE votantes SET id_local = ? WHERE rut = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, nuevoIdLocal);
            stmt.setString(2, rut);
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("El local de votación del votante con RUT " + rut + " ha sido actualizado correctamente.");
            } else {
                System.out.println("No se pudo actualizar el local de votación del votante con RUT " + rut + ".");
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar el local del votante: " + e.getMessage());
        }
    }

    public boolean eliminarVotante(String rut) throws SQLException {
        Votante votante = obtenerVotantePorRut(rut);
        if (votante == null) {
            System.out.println("El votante con RUT " + rut + " no existe.");
            return false;
        }

        LocalDeVotacion local = gestorDeLocales.encontrarLocalDeVotante(votante);
        if (local != null) {
            // Si se encuentra un local, actualiza su capacidad
            gestorDeLocales.actualizarCapacidadLocalEnBD(local);
            gestorDeLocales.decrementarCapacidadLocal(local); // Decrementar la capacidad del local
        } else {

            local = obtenerLocalPorRutDeVotante(votante.getRut());
            if (local != null) {
                // Actualiza la capacidad del local en la base de datos
                gestorDeLocales.actualizarCapacidadLocalEnBD(local);
                gestorDeLocales.decrementarCapacidadLocal(local); // Decrementar la capacidad del local
            }
        }

        String query = "DELETE FROM votantes WHERE rut = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, rut);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            System.out.println("Error al eliminar el votante: " + e.getMessage());
        }
        return false;
    }

    public LocalDeVotacion obtenerLocalPorRutDeVotante(String rut) {
        LocalDeVotacion local = null;
        String query = "SELECT l.id_local, l.nombre, l.direccion, l.capacidad_maxima, l.capacidad_actual, l.id_comuna "
                + "FROM locales_votacion l "
                + "INNER JOIN votantes v ON l.id_local = v.id_local "
                + "WHERE v.rut = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, rut);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int idLocal = rs.getInt("id_local");
                String nombre = rs.getString("nombre");
                String direccion = rs.getString("direccion");
                int capacidadMaxima = rs.getInt("capacidad_maxima");
                int capacidadActual = rs.getInt("capacidad_actual");
                int idComuna = rs.getInt("id_comuna");

                local = new LocalDeVotacion(idLocal, nombre, direccion, capacidadMaxima, capacidadActual, idComuna);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener el local por RUT: " + e.getMessage());
        }
        return local;
    }

    public void addVotante(Votante votante) throws SQLException {
        if (!verificarComunaPerteneceARegion(votante.getComuna(), votante.getRegion())) {
            System.out.println("La comuna no pertenece a la región especificada.");
            return;
        }

        int idComuna = obtenerIdComunaPorNombre(votante.getComuna());

        String query = "INSERT INTO votantes (rut, region, id_local, id_comuna, nombre_comuna) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, votante.getRut());
            stmt.setString(2, votante.getRegion());
            stmt.setInt(3, votante.getIdLocal());
            stmt.setInt(4, idComuna);
            stmt.setString(5, votante.getComuna());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al agregar el votante: " + e.getMessage());
        }
    }

    public List<Votante> getVotantes() throws SQLException {
        List<Votante> votantes = new ArrayList<>();
        String query = "SELECT * FROM votantes";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                String rut = rs.getString("rut");
                String region = rs.getString("region");
                int idComuna = rs.getInt("id_comuna");
                String nombreComuna = obtenerNombreComunaPorId(idComuna);
                int idLocal = rs.getInt("id_local");
                votantes.add(new Votante(rut, region, nombreComuna, idLocal, idComuna));
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener los votantes: " + e.getMessage());
        }
        return votantes;
    }

    public Votante obtenerVotantePorRut(String rut) throws SQLException {
        String query = "SELECT * FROM votantes WHERE rut = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, rut);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String region = rs.getString("region");
                    int idComuna = rs.getInt("id_comuna");
                    String nombreComuna = obtenerNombreComunaPorId(idComuna);
                    int idLocal = rs.getInt("id_local");
                    return new Votante(rut, region, nombreComuna, idLocal, idComuna);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener el votante por RUT: " + e.getMessage());
        }
        return null;
    }

    public int obtenerIdComunaPorNombre(String nombreComuna) {
        String query = "SELECT id_comuna FROM comunas WHERE nombre_comuna = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, nombreComuna);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id_comuna");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar la comuna: " + e.getMessage());
        }
        // Devuelve un valor indicativo de que la comuna no fue encontrada
        return -1;
    }

    private String obtenerNombreComunaPorId(int idComuna) throws SQLException {
        String query = "SELECT nombre_comuna FROM comunas WHERE id_comuna = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idComuna);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("nombre_comuna");
                }
            }
        }
        throw new SQLException("Comuna no encontrada para ID: " + idComuna);
    }

    private int obtenerIdRegionPorNombreComuna(String nombreComuna) throws SQLException {
        String query = "SELECT id_region FROM comunas WHERE nombre_comuna = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, nombreComuna);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id_region");
                }
            }
        }
        throw new SQLException("Comuna no encontrada: " + nombreComuna);
    }

    public int obtenerIdRegionPorNombreRegion(String nombreRegion) throws SQLException {
        String query = "SELECT id_region FROM regiones WHERE nombre = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, nombreRegion);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id_region");
                }
            }
        }
        return 0; // Regresar 0 si no se encuentra la región
    }

    public boolean verificarComunaPerteneceARegion(String nombreComuna, String nombreRegion) throws SQLException {
        int idRegionComuna = obtenerIdRegionPorNombreComuna(nombreComuna);
        int idRegionEsperada = obtenerIdRegionPorNombreRegion(nombreRegion);
        return idRegionComuna == idRegionEsperada;
    }
}
