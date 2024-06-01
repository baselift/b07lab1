import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashSet;

// This class and its methods assume the following precondition:
// Precondition: f contains non-zero coefficients and power array length = coeff array length
public class Polynomial {
    double[] coefficients;
    int[] powers;

    public Polynomial() {
        this.coefficients = new double[]{};
        this.powers = new int[]{};
    }

    public Polynomial(int maxDegree) {
        this.coefficients = new double[maxDegree];
        this.powers = new int[maxDegree];
    }

    public Polynomial(double[] coefficients, int[] powers) {
        this.coefficients = coefficients.clone();
        this.powers = powers.clone();
    }

    public Polynomial(File file) throws IOException {
        String polynomial = Files.readAllLines(file.toPath()).get(0);
        String[] terms = polynomial.split("[+\\-]");
        double[] coeff = new double[terms.length];
        int[] pows = new int[terms.length];

        for (int i = 0; i < terms.length; i++) {
            String term = terms[i];
            boolean hasX = term.contains("x");
            String[] coefficientThenPower = term.split("x");
            if (coefficientThenPower.length == 1) {
                // if there is a match, then it is "%dx", if not then it is "%d"
                coeff[i] = Double.parseDouble(coefficientThenPower[0]);
                pows[i] = hasX ? 1 : 0;
            } else {
                coeff[i] = Double.parseDouble(coefficientThenPower[0]);
                pows[i] = Integer.parseInt(coefficientThenPower[1]);
            }
        }
        this.coefficients = coeff;
        this.powers = pows;
    }

    public Polynomial add(Polynomial f) {
        if (f.isZeroPolynomial()) {
            return this;
        } else if (isZeroPolynomial()) {
            return f;
        }

        int[] maxThenUnique = getMaxandUniqueValues(this.powers, f.powers);
        // create array such that at any index i, array[i] = coefficient associated with x^i
        double[] maxPolynomial = new double[maxThenUnique[0] + 1];

        // add at the index powers[i] the coefficient[i] since the coefficient corresponds with the power at
        // each index i
        updateCoefficientArray(maxPolynomial, powers, coefficients); // since coeff are non-zero, output is redundant
        maxThenUnique[1] -= updateCoefficientArray(maxPolynomial, f.powers, f.coefficients);

        // return new array with no zero entries
        int lastIndex = 0;
        int[] newPowers = new int[maxThenUnique[1]];
        double[] newCoeff = new double[maxThenUnique[1]];
        for (int i = 0; i < maxPolynomial.length; i++) {
            double val = maxPolynomial[i];
            if (val != 0.0) {
                newCoeff[lastIndex] = val;
                newPowers[lastIndex] = i;
                lastIndex++;
            }
        }

        return new Polynomial(newCoeff, newPowers);
    }

    private int updateCoefficientArray(double[] toUpdate, int[] powers, double[] coefficients) {
        int zerosFromAddition = 0;

        for (int i = 0; i < powers.length; i++) {
            toUpdate[powers[i]] += coefficients[i];
            /*
            Let x = toUpdate[powers[i]]. Since we generate coefficient array with size = max degree, it is
            possible for some value at indices to be 0, if not all values are unique. But also values at indices
            can be 0, if x and -x are added. So we also need to have our final array size be decremented since
            we do not want coeff 0.
             */
            if (toUpdate[powers[i]] == 0) {zerosFromAddition++;}
        }
        return zerosFromAddition;
    }

    private int[] getMaxandUniqueValues(int[] arr1, int[] arr2) {
        // returns array [max, # of unique items]
        int max = arr1[0];
        HashSet<Integer> onlyUnique = new HashSet<>();
        for (int val: arr1) {
            onlyUnique.add(val);
            if (max < val) {max = val;}
        }
        for (int val: arr2) {
            onlyUnique.add(val);
            if (max < val) {max = val;}
        }
        return new int[]{max, onlyUnique.size()};
    }

    public boolean isZeroPolynomial() {
        return (powers.length == 0 || coefficients.length == 0);
    }

    public double evaluate(double x) {
        double result = 0;

        for (int i = 0; i < coefficients.length; i++) {
            result += Math.pow(x, powers[i]) * coefficients[i];
        }

        return result;
    }

    public boolean hasRoot(double x) {
        return evaluate(x) == 0;
    }

    public Polynomial multiply(Polynomial f) {
        Polynomial result = new Polynomial();
        for (int thisIndex = 0; thisIndex < powers.length; thisIndex++) {
            Polynomial intermediate = new Polynomial(f.powers.length);
            for (int fIndex = 0; fIndex < f.powers.length; fIndex++) {
                intermediate.powers[fIndex] = powers[thisIndex] + f.powers[fIndex];
                intermediate.coefficients[fIndex] = coefficients[thisIndex] * f.coefficients[fIndex];
            }
            result = result.add(intermediate);
        }
        return result;
    }

    public void saveToFile(String fileName) throws IOException {
        try (BufferedWriter w = new BufferedWriter(new FileWriter(fileName))) {
            w.write(this.toString());
        }
    }

    public String toString() {
        StringBuilder representation = new StringBuilder();
        boolean empty = true;
        for (int i = 0; i < powers.length; i++) {
            String sign = coefficients[i] < 0 ? "" : "+"; // if it is -ve, then the coefficient already has -
            String format = i == 0 ? "%.1fx%d" : sign + "%.1fx%d";
            representation.append(String.format(format, coefficients[i], powers[i]));
            empty = false;
        }
        return empty ? "0" : representation.toString();
    }
}
