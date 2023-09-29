import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordEncrypt {
    public <P> String encrypt(P pwd) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(String.valueOf(pwd).getBytes());

        /* Konversi hash value menjadi bytes */
        byte[] bytes = md.digest();

        /* Konversi dari bentuk decimal ke hexadecimal */
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            s.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }

        return s.toString();
    }
}
