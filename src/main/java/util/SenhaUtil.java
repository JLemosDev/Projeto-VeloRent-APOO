package util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * SenhaUtil — utilitário para hash de senha com SHA-256.
 *
 * Nunca armazenamos senhas em texto puro. O hash é irreversível:
 * para verificar, fazemos hash da senha digitada e comparamos com o hash salvo.
 */
public class SenhaUtil {

    private SenhaUtil() {}

    /** Retorna o hash SHA-256 da senha em hexadecimal (64 chars). */
    public static String hash(String senha) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] bytes = digest.digest(senha.getBytes(java.nio.charset.StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 não disponível.", e);
        }
    }

    /** Verifica se a senha digitada corresponde ao hash armazenado. */
    public static boolean verificar(String senhaDigitada, String hashArmazenado) {
        return hash(senhaDigitada).equals(hashArmazenado);
    }
}
