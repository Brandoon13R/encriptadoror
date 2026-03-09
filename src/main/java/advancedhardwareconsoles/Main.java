package advancedhardwareconsoles;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;

public class Main {

    private static SecretKeySpec generarClave(String miClave) throws Exception {
        byte[] key = miClave.getBytes(StandardCharsets.UTF_8);
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        key = sha.digest(key);
        return new SecretKeySpec(key, "AES");
    }

    public static String encriptar(String mensaje, String clave) throws Exception {
        SecretKeySpec secretKey = generarClave(clave);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encriptado = cipher.doFinal(mensaje.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encriptado);
    }

    public static String desencriptar(String mensajeEncriptado, String clave) throws Exception {
        SecretKeySpec secretKey = generarClave(clave);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] original = cipher.doFinal(Base64.getDecoder().decode(mensajeEncriptado));
        return new String(original, StandardCharsets.UTF_8);
    }

    public static void main(String[] args) {
        try {
            java.util.Scanner scanner = new java.util.Scanner(System.in);

            System.out.println("Dame el mensaje que quieres encriptar:");
            String mensajeOriginal = scanner.nextLine();
            String miPassword = "19102003"; //Puedes cambiar la contraseña a tu gusto

            // Encriptar
            String encriptado = encriptar(mensajeOriginal, miPassword);
            System.out.println("Mensaje encriptado: " + encriptado);

            // Desencriptar
            System.out.println("Dame la contraseña:"); // El usuario debe ingresar la contrasena que ya establecio para desencriptar el mensaje
            String miPasswordDesencriptar = scanner.nextLine(); // Se guardara el mensaje que el usuario ingrese.
            String desencriptado = desencriptar(encriptado, miPasswordDesencriptar);
            System.out.println("Mensaje original: " + desencriptado);

        } catch (javax.crypto.BadPaddingException e) {
            System.err.println("¡Error! La contraseña es incorrecta.");
        } catch (Exception e) {
            System.err.println("Ocurrió un error inesperado: " + e.getMessage());
        }
    }
}