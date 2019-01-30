package sample;

import java.math.*;
import java.sql.SQLException;
import java.util.*;
import java.security.*;
import java.io.*;

public class ELGamal {

    Scanner scanner = new Scanner(System.in);


    BigInteger p, b, c, secretKey,EC,Y1,r;
    Random sc = new SecureRandom();
    public  void calculate() {
        secretKey = BigInteger.probablePrime(100, sc);
        p = BigInteger.probablePrime(512, sc); //P
        b = new BigInteger("119");                  //G
        c = b.modPow(secretKey, p);                     // =G^x mod p
        r = new BigInteger(1024, sc);         // k
          Y1 = b.modPow(r, p);                   /// Y1 = G^k mod p

    }

    public String getEL_p(){
        return String.valueOf(secretKey);
    }

    public String get_y1(){
            return String.valueOf(Y1);
    }

    public String get_prim(){
        return String.valueOf(p);
    }

    public void encrypt(String path,String user) throws IOException, SQLException {

        database d = new database();

        String p_from_database = d.get_prim_database(user);

        BigInteger p = new BigInteger(p_from_database);


        RandomAccessFile f = new RandomAccessFile(path, "r");
        byte [] plaintext = new byte[(int)f.length()];
        f.read(plaintext);

          BigInteger message = new BigInteger(plaintext);
             f.close();
           System.out.println(message);
           EC = message.multiply(c.modPow(r, p)).mod(p);//b =y^k*M mod p
        System.out.println(EC);
        String en_msg = EC.toString();

        try (PrintWriter out = new PrintWriter(path)) {
            System.out.println(en_msg);
        }
        System.out.println("Encryption Done");

      }
        //
        // Decryption
        //
        public void decrypt(String path,String user) throws IOException, SQLException {
        database d = new database();

        String p_from_database = d.get_prim_database(user);
        BigInteger p = new BigInteger(p_from_database);
        String se = d.get_a_database(user);
        String y1_data = d.get_y1_database(user);
        BigInteger Y1 = new BigInteger(y1_data);
        String encrypt_message = readFile(path);
           BigInteger EC = new BigInteger(encrypt_message);
            System.out.println(EC);


        BigInteger secreteKey = new BigInteger(se);
        BigInteger crmodp = Y1.modPow(secreteKey, p);
        BigInteger dd = crmodp.modInverse(p);
        BigInteger ad = dd.multiply(EC).mod(p);
            byte[] array = ad.toByteArray();
            if (array[0] == 0) {
                byte[] tmp = new byte[array.length - 1];
                System.arraycopy(array, 1, tmp, 0, tmp.length);
                array = tmp;
            }
            String doc2 = new String(array, "UTF-8");
            System.out.println("Message After Decryption : " + doc2);

            try (PrintStream out = new PrintStream(path)) {
                out.print(doc2);
            }




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