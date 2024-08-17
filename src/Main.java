import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		String command;

		while (true) {
			System.out.println(
					"Geben Sie 'neu' ein, um ein neues Gleichungssystem zu lösen, oder 'ende', um das Programm zu beenden:");
			command = scanner.nextLine().trim().toLowerCase();

			if (command.equals("ende")) {
				System.out.println("Programm beendet.");
				break;
			} else if (command.equals("neu")) {

				EquationSystem system = new EquationSystem();

				System.out.println("Bitte geben Sie die Anzahl der Variablen (und Gleichungen) ein (2 oder 3):");
				int variableCount = scanner.nextInt();

				if (variableCount < 2 || variableCount > 3) {
					System.out.println("Fehler: Die Anzahl der Variablen und Gleichungen muss 2 oder 3 sein.");
					continue;
				}

				for (int i = 0; i < variableCount; i++) {
					int[] coefficients = new int[variableCount];
					System.out
							.println("Geben Sie die ganzzahligen Koeffizienten für die Gleichung " + (i + 1) + " ein:");
					for (int j = 0; j < variableCount; j++) {
						coefficients[j] = scanner.nextInt();
					}

					System.out.println("Geben Sie den ganzzahligen konstanten Term ein:");
					int constant = scanner.nextInt();

					system.addEquation(new Equation(coefficients, constant));
				}

				// Leere Zeile nach dem Integer-Eingaben überspringen
				scanner.nextLine();

				try {
					String solution = system.solve();
					System.out.println(solution);
				} catch (IllegalArgumentException e) {
					System.out.println("Fehler: " + e.getMessage());
				} catch (ArithmeticException e) {
					System.out.println("Fehler: Das Gleichungssystem hat keine eindeutige Lösung.");
				}

			} else {
				System.out.println("Ungültiger Befehl. Bitte 'neu' oder 'ende' eingeben.");
			}
		}

		scanner.close();
	}
}