import java.io.File;
import java.io.IOException;

public class Driver {
    public static final String fileName = "polynomialRecord.txt";
    public static void main(String [] args) {

        Polynomial z1 = new Polynomial();
        System.out.println("z1 eval: " + z1.evaluate(3));
        Polynomial z2 = new Polynomial();
        Polynomial z3 = z1.add(z2);
        System.out.println("z3: " + z3);

        double [] c1 = {6, 9, 1, 67};
        int [] pow1 = {0, 3, 5, 9};
        Polynomial p1 = new Polynomial(c1, pow1);
        double [] c2 = {-2, -9, -100, -67};
        int [] pow2 = {1, 3, 5, 9};
        Polynomial p2 = new Polynomial(c2, pow2);
        Polynomial s = p1.add(p2);
        System.out.println("s: " + s);

        System.out.println("s(0.1) = " + s.evaluate(0.1));
        if(s.hasRoot(1))
            System.out.println("1 is a root of s");
        else
            System.out.println("1 is not a root of s");
        testMultiply();
    }

    public static void testMultiply() {
        Polynomial f = new Polynomial(new double[]{1, 2}, new int[]{4, 5}); // x^4 + 2x^5
        Polynomial g = new Polynomial(new double[]{1, 3, 4}, new int[]{6, 1, 2}); // x^6 + 3x + 4x^2
        Polynomial gf = g.multiply(f);
        System.out.println(gf);
        try {
            gf.saveToFile(fileName);
            File file = new File(fileName);
            Polynomial gfFromFile = new Polynomial(file);
            System.out.println(gfFromFile); // result should be 2x^11 + x^10 + 8x^7 + 10x^6 + 3x^5
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void testFileConstructor() {

    }
}

