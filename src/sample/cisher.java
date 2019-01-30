package sample;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.SQLException;

/**
 * Created by Raju on 25-May-18.
 */
public class cisher {


    static char[] chars = {
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
            'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',
            'q', 'r', 's', 't', 'u', 'v', 'w', 'x',
            'y', 'z', '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'A', 'B', 'C', 'D',
            'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
            'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
            'U', 'V', 'W', 'X', 'Y', 'Z', '!', '@',
            '#', '$', '%', '^', '&', '(', ')', '+',
            '-', '*', '/', '[', ']', '{', '}', '=',
            '<', '>', '?', '_', '"', '.', ',', ' '
    };


    public void ciper_encrypt(String path,String user) throws IOException, SQLException {

        database d = new database();
        int key = Integer.parseInt(d.get_key_database(user));
        String encrypt_message = readFile(path);
        String enc = encrypt(encrypt_message, key);
        System.out.println("Encryption Done!");
        System.out.println("Encrypted text: " + enc);
        try (PrintStream out = new PrintStream(path)) {
            out.print(enc);
        }
    }
    public void ciper_decryption(String path,String user) throws IOException, SQLException {

        database d = new database();
        int key = Integer.parseInt(d.get_key_database(user));
        String encrypt_message = readFile(path);
        String dec = decrypt(encrypt_message, key);
        System.out.println("Decryption Done!");
        System.out.println("Decrypted text: " + dec);
        try (PrintStream out = new PrintStream(path)) {
            out.print(dec);
        }
    }
    // Caesar cipher
    static String encrypt(String text, int offset)
    {
        char[] plain = text.toCharArray();

        for (int i = 0; i < plain.length; i++) {
            for (int j = 0; j < chars.length; j++) {
                if (j <= chars.length - offset) {
                    if (plain[i] == chars[j]) {
                        plain[i] = chars[j + offset];
                        break;
                    }
                }
                else if (plain[i] == chars[j]) {
                    plain[i] = chars[j - (chars.length - offset + 1)];
                }
            }
        }
        return String.valueOf(plain);
    }

    static String decrypt(String cip, int offset)
    {
        char[] cipher = cip.toCharArray();
        for (int i = 0; i < cipher.length; i++) {
            for (int j = 0; j < chars.length; j++) {
                if (j >= offset && cipher[i] == chars[j]) {
                    cipher[i] = chars[j - offset];
                    break;
                }
                if (cipher[i] == chars[j] && j < offset) {
                    cipher[i] = chars[(chars.length - offset +1) + j];
                    break;
                }
            }
        }
        return String.valueOf(cipher);
    }

    public String readFile(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            return sb.toString();
        } finally {
            br.close();
        }
    }
}
