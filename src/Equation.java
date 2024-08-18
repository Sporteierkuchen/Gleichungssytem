public class Equation {
	private int[] coefficients;
	private int constant;

	public Equation(int[] coefficients, int constant) {
		if (coefficients.length < 2 || coefficients.length > 3) {
			throw new IllegalArgumentException("Die Anzahl der Koeffizienten muss zwischen 2 und 3 liegen.");
		}
		this.coefficients = coefficients;
		this.constant = constant;
	}

	public int[] getCoefficients() {
		return coefficients;
	}

	public int getConstant() {
		return constant;
	}

//	@Override
//	public String toString() {
//		StringBuilder sb = new StringBuilder();
//		char var = 'x';
//		for (int i = 0; i < coefficients.length; i++) {
//			sb.append(String.format("%d%c + ", coefficients[i], var++));
//		}
//		sb.append(String.format("= %d", constant));
//		return sb.toString();
//	}
}