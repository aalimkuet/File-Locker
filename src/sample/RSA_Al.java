package sample;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigInteger;
import java.util.Random;

class RSA_Al{

    //Bit size of the probable primes
    final static int bits = 512;

    //Encryption exponent
    final static BigInteger e = new BigInteger("65537");

    public static void main(String [] args)throws IOException{
        BigInteger p, q, N, phiN, d;

        while(true){
            p = generateProbablePrime(bits);
            q = generateProbablePrime(bits);
            N = calculateProduct(p,q);
            phiN = phi(p,q);
            if(gcd(e,phiN).equals(BigInteger.ONE))
                break;
        }

        //generate the d decryption exponent
        d = inverse(e,phiN);


        //Read in the source code from a file named "code.txt"
        RandomAccessFile f = new RandomAccessFile("H:\\software\\src\\code.txt", "r");
        byte [] plaintext = new byte[(int)f.length()];
        f.read(plaintext);

        BigInteger message = new BigInteger(plaintext);
        //Print all of the results
        System.out.println("P = " + p);
        System.out.println("Q = " + q);
        System.out.println("N = " + N);
        System.out.println("d = " + d);
        System.out.println();
        System.out.println("Message = " + message);
        BigInteger myCipher = encrypt(e,p,q,message);
        System.out.println("Plain Text = " + myCipher);
        BigInteger myPlain = decrypt(d,p,q,myCipher);
        System.out.println("Cipher Text = " + myPlain);
      //  myPlain = new BigInteger("97700281625356344816449899117871197015686806575345939811391413358027456390152970448065764842308378933799571041330413518646055840259353404553859489185254850545205856527637562929005181282618738173448328113383848494147732296298787192083298232008486915403008975932776811954229634810991102359466232237691080620295");
        // convert the cipher text into text
        byte[] array = myPlain.toByteArray();
        if (array[0] == 0) {
            byte[] tmp = new byte[array.length - 1];
            System.arraycopy(array, 1, tmp, 0, tmp.length);
            array = tmp;
        }
        String doc2 = new String(array, "UTF-8");
        System.out.println(doc2);
    }

    //Generates a probable prime of the bitsize specified
    public static BigInteger generateProbablePrime(int bitSize){
        return BigInteger.probablePrime(bitSize, new Random());
    }

    //Calculate N (p*q)
    public static BigInteger calculateProduct(BigInteger p, BigInteger q){
        return p.multiply(q);
    }

    //Calculate phi(N)
    public static BigInteger phi(BigInteger p, BigInteger q){
        BigInteger phiN = p.subtract(BigInteger.ONE);
        phiN = phiN.multiply(q.subtract(BigInteger.ONE));
        return phiN;
    }

    //Calculate GCD
    public static BigInteger gcd(BigInteger e, BigInteger n){
        if(n.equals(BigInteger.ZERO))
            return e;
        return gcd(n, e.mod(n));
    }

    //calculate multiplicative inverse of a%n using the extended euclidean GCD algorithm
    public static BigInteger inverse (BigInteger a, BigInteger N){
        BigInteger [] ans = extendedEuclid(a,N);

        if (ans[1].compareTo(BigInteger.ZERO) == 1)
            return ans[1];
        else return ans[1].add(N);
    }

    //Calculate d = gcd(a,N) = ax+yN
    public static BigInteger [] extendedEuclid (BigInteger a, BigInteger N){
        BigInteger [] ans = new BigInteger[3];
        BigInteger ax, yN;

        if (N.equals(BigInteger.ZERO)) {
            ans[0] = a;
            ans[1] = BigInteger.ONE;
            ans[2] = BigInteger.ZERO;
            return ans;
        }

        ans = extendedEuclid (N, a.mod(N));
        ax = ans[1];
        yN = ans[2];
        ans[1] = yN;
        BigInteger temp = a.divide(N);
        temp = yN.multiply(temp);
        ans[2] = ax.subtract(temp);
        return ans;
    }

    /*
    Chinese Remainder Theorem to calculate m^d(mod pq),where:
    m = message
    d = decryption exponent
    pq = factors of N
    */
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

    //Encrypt using d
    public static BigInteger encrypt(BigInteger d, BigInteger p, BigInteger q,BigInteger m){
        return crt(d,p,q,m);
    }

    //decrypt using e
    public static BigInteger decrypt(BigInteger e, BigInteger p, BigInteger q,BigInteger m){
        return crt(e,p,q,m);
    }


}