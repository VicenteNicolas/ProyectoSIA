/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author vicen
 */
public class GestorDeDatos {
    

    public static String normalizarRut(String rut) {
        return rut.replaceAll("[^0-9Kk]", "").toUpperCase();
    }

    public static boolean esRutValido(String rut) {
        String rutNormalizado = normalizarRut(rut);
        if (rutNormalizado.length() < 2) {
            return false; // El RUT debe tener al menos 2 caracteres
        }

        // Separa el cuerpo del RUT y el dígito verificador
        String cuerpo = rutNormalizado.substring(0, rutNormalizado.length() - 1);
        char digitoVerificadorIngresado = rutNormalizado.charAt(rutNormalizado.length() - 1);

        // Calcula el dígito verificador
        char digitoVerificadorCalculado = calcularDigitoVerificador(cuerpo);

        // Compara el dígito verificador ingresado con el calculado
        return digitoVerificadorIngresado == digitoVerificadorCalculado;
    }

    private static char calcularDigitoVerificador(String cuerpo) {
        int suma = 0;
        int multiplicador = 2;
        for (int i = cuerpo.length() - 1; i >= 0; i--) {
            suma += Character.getNumericValue(cuerpo.charAt(i)) * multiplicador;
            multiplicador = (multiplicador == 7) ? 2 : multiplicador + 1;
        }
        int resto = 11 - (suma % 11);
        return (resto == 11) ? '0' : (resto == 10) ? 'K' : (char) (resto + '0');
    }
}

