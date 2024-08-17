import java.util.ArrayList;
import java.util.List;

public class EquationSystem {
	private List<Equation> equations;

	public EquationSystem() {
		this.equations = new ArrayList<>();
	}

	public void addEquation(Equation equation) {
		equations.add(equation);
	}

	public String solve() throws IllegalArgumentException {
		int n = equations.size();
		if (n < 2 || n > 3) {
			throw new IllegalArgumentException("Das System muss 2 bis 3 Gleichungen haben.");
		}

		double[][] matrix = new double[n][n + 1];

		// Matrix aus Gleichungen erstellen
		for (int i = 0; i < n; i++) {
			Equation eq = equations.get(i);
			int[] coefficients = eq.getCoefficients();
			for (int j = 0; j < n; j++) {
				matrix[i][j] = coefficients[j];
			}
			matrix[i][n] = eq.getConstant();
		}

		// Gaußsches Eliminationsverfahren
		for (int i = 0; i < n; i++) {
			// Pivotisierung
			for (int j = i + 1; j < n; j++) {
				if (Math.abs(matrix[j][i]) > Math.abs(matrix[i][i])) {
					double[] temp = matrix[i];
					matrix[i] = matrix[j];
					matrix[j] = temp;
				}
			}

			// Wenn der Pivot-Element 0 ist, prüfen wir auf unendlich viele Lösungen oder
			// keine Lösung
			if (matrix[i][i] == 0) {
				// Wenn die gesamte Zeile 0 ist und die letzte Spalte nicht 0 ist, gibt es keine
				// Lösung
				boolean allZeroes = true;
				for (int k = i; k < n; k++) {
					if (matrix[i][k] != 0) {
						allZeroes = false;
						break;
					}
				}

				if (allZeroes && matrix[i][n] != 0) {
					return "Das Gleichungssystem hat keine Lösung.";
				}

				// Wenn die gesamte Zeile und die letzte Spalte 0 sind, gibt es unendlich viele
				// Lösungen
				if (allZeroes && matrix[i][n] == 0) {
					return "Das Gleichungssystem hat unendlich viele Lösungen.";
				}
			}

			// Normalisierung der Gleichung
			for (int j = i + 1; j < n; j++) {
				double factor = matrix[j][i] / matrix[i][i];
				for (int k = i; k <= n; k++) {
					matrix[j][k] -= factor * matrix[i][k];
				}
			}
		}

		// Rücksubstitution
		double[] result = new double[n];
		for (int i = n - 1; i >= 0; i--) {
			result[i] = matrix[i][n];
			for (int j = i + 1; j < n; j++) {
				result[i] -= matrix[i][j] * result[j];
			}
			result[i] /= matrix[i][i];
		}

		// Ergebnisse formatieren und zurückgeben
		StringBuilder sb = new StringBuilder();
		char var = 'x';
		for (int i = 0; i < result.length; i++) {
			sb.append(String.format("%c = %.2f%n", var++, result[i]));
		}

		return sb.toString();
	}
}