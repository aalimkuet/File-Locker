package sample;

import java.io.*;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.Random;

public class rsa {

    final static int bits = 512;

    BigInteger p, q, N, phiN, d, ef;

    public void calculateParameter(){
        while(true){
            p = generateProbablePrime(bits);
            q = generateProbablePrime(bits);
            ef = new BigInteger("65537");
            N = calculateProduct(p,q);
            phiN = phi(p,q);
            if(gcd(ef,phiN).equals(BigInteger.ONE))
                break;
        }
        d = inverse(ef,phiN);


    }

    public String getEF(){
        return String.valueOf(ef);
    }

    public String getD(){
        return String.valueOf(d);
    }

    public String getP(){
        return String.valueOf(p);
    }

    public String getQ(){
        return String.valueOf(q);
    }

    public static BigInteger generateProbablePrime(int bitSize){
        return BigInteger.probablePrime(bitSize, new Random());
    }

    public static BigInteger calculateProduct(BigInteger p, BigInteger q){
        return p.multiply(q);
    }

    public static BigInteger phi(BigInteger p, BigInteger q){
        BigInteger phiN = p.subtract(BigInteger.ONE);
        phiN = phiN.multiply(q.subtract(BigInteger.ONE));
        return phiN;
    }


    public static BigInteger gcd(BigInteger e, BigInteger n){
        if(n.equals(BigInteger.ZERO))
            return e;
        return gcd(n, e.mod(n));
    }

    public static BigInteger inverse (BigInteger a, BigInteger N){
        BigInteger [] ans = extendedEuclid(a,N);

        if (ans[1].compareTo(BigInteger.ZERO) == 1)
            return ans[1];
        else return ans[1].add(N);
    }

    public static BigInteger [] extendedEuclid (BigInteger a, BigInteger N){
        BigInteger [] ans = new BigInteger[3];
        BigInteger ax, yN;

        if (N.equals(BigInteger.ZERO)) {
            ans[0] = a;
            ans[1] = BigInteger.ONE;
            ans[2] = BigInteger.ZERO;
            return ans;
        }
        ans = extendedEuclid (N, a.mod (N));
        ax = ans[1];
        yN = ans[2];
        ans[1] = yN;
        BigInteger temp = a.divide(N);
        temp = yN.multiply(temp);
        ans[2] = ax.subtract(temp);
        return ans;
    }


    public static BigInteger crt(BigInteger d, BigInteger p, BigInteger q, BigInteger m){
        BigInteger dp, dq, qInverse, m1, m2, h;

        dp = d.mod(p.subtract(BigInteger.ONE));
        dq = d.mod(q.subtract(BigInteger.ONE));
        qInverse = inverse(q,p);

        m1 = m.modPow(dp,p);
        m2 = m.modPow(dq,q);
        h = qInverse.multiply(m1.subtract(m2)).mod(p);
        m = m2.add(h.multiply(q));

        return m;
    }

    public void encrypt(String path,String user) throws IOException, SQLException {

        database d = new database();
        String e_from_database = d.get_E_database(user);
        String p_from_database = d.get_P_database(user);
        String q_from_database = d.get_Q_database(user);


        RandomAccessFile f = new RandomAccessFile(path, "r");
        byte [] plaintext = new byte[(int)f.length()];
        f.read(plaintext);

        BigInteger message = new BigInteger(plaintext);
        f.close();
        System.out.println(message);
        message = crt(new BigInteger(e_from_database),new BigInteger(p_from_database),new BigInteger(q_from_database),message);
        System.out.println(message);
        String en_msg = message.toString();

        try (PrintWriter out = new PrintWriter(path)) {
            out.println(en_msg);
        }
        System.out.println("Encryption Done");
    }


    public void decrypt(String path,String user) throws IOException, SQLException {
        database d = new database();
        String d_from_database = d.get_D_database(user);
        String p_from_database = d.get_P_database(user);
        String q_from_database = d.get_Q_database(user);

        String encrypt_message = readFile(path);

        BigInteger message;
        message = new BigInteger(encrypt_message);
        System.out.println(message);
        message = crt(new BigInteger(d_from_database),new BigInteger(p_from_database),new BigInteger(q_from_database),message);
         System.out.println(message);
        byte[] array = message.toByteArray();
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
