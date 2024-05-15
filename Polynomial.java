public class Polynomial {
    double[] coefficients;

    public Polynomial() {
        this.coefficients = new double[]{0};
    }

    public Polynomial(double[] coefficients) {
        this.coefficients = coefficients.clone();
    }

    public Polynomial add(Polynomial f) {
        // return new polynomial or return existing polynomial modified?
        int maxTerms = Math.max(coefficients.length, f.coefficients.length);
        double[] newCoeff = new double[maxTerms];

        for (int i = 0; i < maxTerms; i++) {
            if (i >= coefficients.length) {
                /*
                example: coefficients = {0, 1, 2, 3}, f.coefficients = {0, 1, 2, 3, 4, 5}
                maxTerms is 6, but coefficients only has 4 terms. Then if i = 5, we should just
                set the term in the new array to the value of f.coefficients at index 5.
                Notice if this block is reached this implies that coefficients has min length.
                 */
                newCoeff[i] = f.coefficients[i];
            } else if (i >= f.coefficients.length) {
                /*
                example: coefficients = {0, 1, 2, 3}, f.coefficients = {0, 10}
                maxTerms is 4, but f.coefficients only has 2 terms. Then if i = 2, we should just
                set the term in the new array to the value of coefficients at index 2.
                Notice if this block is reached this implies that f.coefficients has min length.
                 */
                newCoeff[i] = coefficients[i];
            } else {
                // when there is no index out of bounds issues
                newCoeff[i] = (f.coefficients)[i] + coefficients[i];
            }
        }

        return new Polynomial(newCoeff);
    }

    public double evaluate(double x) {
        double result = 0;

        for (int i = 0; i < coefficients.length; i++) {
            result += Math.pow(x, i) * coefficients[i];
        }

        return result;
    }

    public boolean hasRoot(double x) {
        return evaluate(x) == 0;
    }
}
