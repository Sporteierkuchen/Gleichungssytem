import java.util.ArrayList;
import java.util.List;

public class EquationSystem {
    private final List<Equation> equations;

    public EquationSystem() {
        this.equations = new ArrayList<>();
    }

    public void addEquation(Equation equation) {
        if (equation == null) {
            throw new IllegalArgumentException("Die Gleichung darf nicht null sein.");
        }
        if (!equations.isEmpty() && equation.getCoefficients().length != getExpectedVariableCount()) {
            throw new IllegalArgumentException("Die Anzahl der Variablen in der neuen Gleichung stimmt nicht mit dem System überein.");
        }
        equations.add(equation);
    }

    public String solve() {
        validateSystem();

        int variableCount = equations.get(0).getCoefficients().length;

        // Kopiere das System, um die originale Matrix nicht zu verändern
        double[][] matrix = createMatrix();
        double[] constants = createConstantsArray();

        // Bringe das System in Stufenform (Gaussian elimination)
        performGaussianElimination(matrix, constants);

        // Überprüfe auf unlösbare oder unendlich viele Lösungen
        checkForSpecialCases(matrix, constants);

        // Berechne die Lösung (Back substitution)
        double[] solutions = performBackSubstitution(matrix, constants);

        return formatSolution(solutions);
    }

    private void validateSystem() {
        if (equations.size() < getExpectedVariableCount()) {
            throw new IllegalArgumentException("Es müssen mindestens so viele Gleichungen wie Variablen vorhanden sein.");
        }
    }

    private int getExpectedVariableCount() {
        return equations.isEmpty() ? 0 : equations.get(0).getCoefficients().length;
    }

    private double[][] createMatrix() {
        int variableCount = getExpectedVariableCount();
        double[][] matrix = new double[equations.size()][variableCount];

        for (int i = 0; i < equations.size(); i++) {
            for (int j = 0; j < variableCount; j++) {
                matrix[i][j] = equations.get(i).getCoefficients()[j];
            }
        }

        return matrix;
    }

    private double[] createConstantsArray() {
        double[] constants = new double[equations.size()];
        for (int i = 0; i < equations.size(); i++) {
            constants[i] = equations.get(i).getConstant();
        }
        return constants;
    }

    private void performGaussianElimination(double[][] matrix, double[] constants) {
        int n = matrix.length;

        for (int i = 0; i < n; i++) {
            // Finde die Zeile mit dem größten Pivot und tausche sie nach oben
            int max = i;
            for (int j = i + 1; j < n; j++) {
                if (Math.abs(matrix[j][i]) > Math.abs(matrix[max][i])) {
                    max = j;
                }
            }
            swapRows(matrix, constants, i, max);

            // Setze die Pivot-Zeile auf 1 und reduziere die anderen Zeilen
            for (int j = i + 1; j < n; j++) {
                double factor = matrix[j][i] / matrix[i][i];
                constants[j] -= factor * constants[i];
                for (int k = i; k < n; k++) {
                    matrix[j][k] -= factor * matrix[i][k];
                }
            }
        }
    }

    private void swapRows(double[][] matrix, double[] constants, int i, int max) {
        double[] tempRow = matrix[i];
        matrix[i] = matrix[max];
        matrix[max] = tempRow;

        double temp = constants[i];
        constants[i] = constants[max];
        constants[max] = temp;
    }

    private void checkForSpecialCases(double[][] matrix, double[] constants) {
        int n = matrix.length;
        for (int i = 0; i < n; i++) {
            boolean allZeros = true;
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] != 0) {
                    allZeros = false;
                    break;
                }
            }
            if (allZeros && constants[i] != 0) {
                throw new ArithmeticException("Das Gleichungssystem hat keine Lösung.");
            }
            if (allZeros && constants[i] == 0) {
                throw new ArithmeticException("Das Gleichungssystem hat unendlich viele Lösungen.");
            }
        }
    }

    private double[] performBackSubstitution(double[][] matrix, double[] constants) {
        int n = matrix.length;
        double[] solutions = new double[n];

        for (int i = n - 1; i >= 0; i--) {
            double sum = 0.0;
            for (int j = i + 1; j < n; j++) {
                sum += matrix[i][j] * solutions[j];
            }
            solutions[i] = (constants[i] - sum) / matrix[i][i];
        }

        return solutions;
    }

    private String formatSolution(double[] solutions) {
        StringBuilder result = new StringBuilder("Lösung: \n");
        for (int i = 0; i < solutions.length; i++) {
            result.append((char)('x' + i)).append(" = ").append(solutions[i]).append("\n");
        }
        return result.toString();
    }
}