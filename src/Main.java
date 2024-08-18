import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
    	
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String command = promptUserCommand(scanner);
            if (command.equals("ende")) {
                System.out.println("Programm beendet.");
                break;
            } else if (command.equals("neu")) {
                processEquationSystem(scanner);
            } else {
                System.out.println("Ungültiger Befehl. Bitte 'neu' oder 'ende' eingeben.");
            }
        }
        
        scanner.close();
    }

    
    private static String promptUserCommand(Scanner scanner) {
        System.out.println("Geben Sie 'neu' ein, um ein neues Gleichungssystem zu lösen, oder 'ende', um das Programm zu beenden:");
        return scanner.nextLine().trim().toLowerCase();
    }

    
    private static void processEquationSystem(Scanner scanner) {
        int variableCount = promptVariableCount(scanner);
        EquationSystem system = new EquationSystem();

        for (int i = 0; i < variableCount; i++) {
            int[] coefficients = promptCoefficients(scanner, variableCount, i);
            int constant = promptConstant(scanner, i);
            system.addEquation(new Equation(coefficients, constant));
        }

        scanner.nextLine(); // Leere Zeile nach den Integer-Eingaben überspringen

        solveAndDisplaySystem(system);
    }
    

    private static int promptVariableCount(Scanner scanner) {
        int variableCount = 0;
        while (true) {
            System.out.println("Bitte geben Sie die Anzahl der Variablen (und Gleichungen) ein (2 oder 3):");
            try {
                variableCount = scanner.nextInt();
                if (variableCount < 2 || variableCount > 3) {
                    System.out.println("Fehler: Die Anzahl der Variablen und Gleichungen muss 2 oder 3 sein.");
                } else {
                    break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Fehler: Bitte eine gültige ganze Zahl (2 oder 3) eingeben.");
                scanner.next(); // Ungültige Eingabe aus dem Puffer entfernen
            }
        }
        return variableCount;
    }

    
    private static int[] promptCoefficients(Scanner scanner, int variableCount, int equationIndex) {
        int[] coefficients = new int[variableCount];
        for (int j = 0; j < variableCount; j++) {
            while (true) {
                System.out.println("Geben Sie den ganzzahligen Koeffizienten für Variable " + (char)('x' + j) + " in Gleichung " + (equationIndex + 1) + " ein:");
                try {
                    coefficients[j] = scanner.nextInt();
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Fehler: Bitte eine gültige ganze Zahl eingeben.");
                    scanner.next(); // Ungültige Eingabe aus dem Puffer entfernen
                }
            }
        }
        return coefficients;
    }

    
    private static int promptConstant(Scanner scanner, int equationIndex) {
        int constant = 0;
        while (true) {
            System.out.println("Geben Sie den ganzzahligen konstanten Term für Gleichung " + (equationIndex + 1) + " ein:");
            try {
                constant = scanner.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Fehler: Bitte eine gültige ganze Zahl eingeben.");
                scanner.next(); // Ungültige Eingabe aus dem Puffer entfernen
            }
        }
        return constant;
    }
    

    private static void solveAndDisplaySystem(EquationSystem system) {
        try {
            String solution = system.solve();
            System.out.println(solution);
        } catch (IllegalArgumentException e) {
            System.out.println("Fehler: " + e.getMessage());
        } catch (ArithmeticException e) {
            System.out.println("Hinweis: "+e.getMessage());
        }
    }
    
}