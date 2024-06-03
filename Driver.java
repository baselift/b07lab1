import java.io.File;
import java.io.IOException;

public class Driver {
    public static final String fileName = "polynomialRecord.txt";
    public static void main(String [] args) {
        try {
            testFileConstructor();
            testAdd();
            testMultiply();
        } catch (IOException ignored) {}
    }

    public static void testFileConstructor() throws IOException {
        /*
        Case 1: Zero polynomial in file is passed
        Case 2: Non-zero Constant polynomial in file is passed
        Case 3: Polynomial of degree 1 is passed
        Case 4: Polynomial containing a term of degree 1 (but is degree > 1) in file is passed
         */
        System.out.println("testFileConstructor:");
        // Case 1:
        Polynomial zero = new Polynomial();
        zero.saveToFile(fileName);
        Polynomial zeroFromFile = new Polynomial(new File(fileName));
        System.out.println(zeroFromFile.isZeroPolynomial());

        // Case 2:
        double[] coeff1 = new double[]{100};
        int[] power1 = new int[]{0};
        Polynomial constant = new Polynomial(coeff1, power1);
        constant.saveToFile(fileName);
        Polynomial constantFromFile = new Polynomial(new File(fileName));
        System.out.println(constantFromFile);

        // Case 3:
        double[] coeff2 = new double[]{-100, 2};
        int[] power2 = new int[]{1, 2};
        Polynomial degOne = new Polynomial(coeff2, power2);
        degOne.saveToFile(fileName);
        Polynomial degOneFromFile = new Polynomial(new File(fileName));
        System.out.println(degOneFromFile);

        // Case 4:
        double[] coeff3 = new double[]{100, 2, 45, 3, 14, 318};
        int[] power3 = new int[]{0, 1, 4, 5, 6, 9};
        Polynomial degOnePlus = new Polynomial(coeff3, power3);
        degOnePlus.saveToFile(fileName);
        Polynomial degOnePlusFromFile = new Polynomial(new File(fileName));
        System.out.println(degOnePlusFromFile);
    }

    public static void testAdd() {
        /*
        Case 1: Perfect canceling polynomials (result is zero polynomial)
        Case 2: Some canceling
        Case 3: ALl unique powers and test commutativity
        Case 4: Some shared powers, no canceling, and test commutativity
        Case 5: Adding polynomial with more or less terms than the other
         */
        System.out.println("testAdd:");
        // Case 1
        double[] coeff = new double[]{100, 2, 45, 3, 14, 37};
        int[] power = new int[]{0, 1, 4, 5, 6, 8};
        double[] reverseCoeff = new double[]{-100, -2, -45, -3, -14, -37};
        Polynomial case1 = new Polynomial(coeff, power);
        Polynomial case1Reverse = new Polynomial(reverseCoeff, power);
        Polynomial perfectCancel = case1Reverse.add(case1);
        System.out.println(perfectCancel);

        // Case 2:
        double[] coeff2 = new double[]{10, 2, 45, 3, 14, 37};
        int[] power2 = new int[]{0, 1, 4, 5, 7, 8};
        double[] coeff3 = new double[]{109, -2, -45, -56, -14, 37};
        int[] power3 = new int[]{1, 3, 4, 5, 6, 8};
        Polynomial case2 = new Polynomial(coeff2, power2);
        Polynomial case2_2 = new Polynomial(coeff3, power3);
        Polynomial someCancel = case2.add(case2_2);
        System.out.println(someCancel);

        // Case 3:
        double[] coeff4 = new double[]{10, 2, 45, 3, 14, 37};
        int[] power4 = new int[]{0, 1, 4, 5, 7};
        double[] coeff5 = new double[]{109, -2, -45, -56, -14, 37};
        int[] power5 = new int[]{2, 3, 6, 9, 10};
        Polynomial case3 = new Polynomial(coeff4, power4);
        Polynomial case3_2 = new Polynomial(coeff5, power5);
        Polynomial unique = case3.add(case3_2);
        Polynomial uniqueFlipped = case3_2.add(case3);
        System.out.println(unique);
        System.out.println(uniqueFlipped);

        // Case 4:
        double[] coeff6 = new double[]{10, 2, 46, 3, 14, 37};
        int[] power6 = new int[]{0, 1, 4, 5, 7, 8};
        double[] coeff7 = new double[]{109, -2, -45, -56, -14, 37};
        int[] power7 = new int[]{1, 3, 4, 5, 6, 8};
        Polynomial case4 = new Polynomial(coeff6, power6);
        Polynomial case4_2 = new Polynomial(coeff7, power7);
        Polynomial someShared = case4.add(case4_2);
        System.out.println(someShared);

        // Case 5:
        double[] coeff8 = new double[]{10, 2, 46, 3, 14, 37, 5, 3};
        int[] power8 = new int[]{0, 1, 4, 5, 7, 8, 9, 6};
        double[] coeff9 = new double[]{109, -2, -45, -56, -14, 37};
        int[] power9 = new int[]{1, 3, 4, 5, 6, 8};
        Polynomial case5 = new Polynomial(coeff8, power8);
        Polynomial case5_2 = new Polynomial(coeff9, power9);
        Polynomial unequalNumberOfTerms = case5.add(case5_2);
        System.out.println(unequalNumberOfTerms);
    }

    public static void testMultiply() throws IOException {
        System.out.println("testMultiply");
        Polynomial f = new Polynomial(new double[]{1, 2}, new int[]{4, 5}); // x^4 + 2x^5
        Polynomial g = new Polynomial(new double[]{-1, 3, 4}, new int[]{6, 1, 2}); // x^6 + 3x + 4x^2
        Polynomial gf = g.multiply(f);
        System.out.println("gf: " + gf);
        gf.saveToFile(fileName);
        Polynomial gfFromFile = new Polynomial(new File(fileName));
        System.out.println("gf from file: " + gfFromFile); // result should be 2x^11 + x^10 + 8x^7 + 10x^6 + 3x^5

        Polynomial negativeG = g.multiply(new Polynomial(new double[]{-1}, new int[]{0}));
        System.out.println(negativeG);
        System.out.println(f.multiply(g));
    }


}

