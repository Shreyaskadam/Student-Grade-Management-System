import java.util.Scanner;

/**
 * Basic Student Grade Management System using arrays.
 * Stores roll numbers, names, and marks; computes total, average, grade;
 * supports search by roll number.
 */
public class StudentGradeManagementSystem {

    private static final int MAX_STUDENTS = 50;
    private static final int SUBJECT_COUNT = 3;
    private static final String[] SUBJECT_NAMES = {"Math", "Science", "English"};

    private static int[] rollNumbers = new int[MAX_STUDENTS];
    private static String[] names = new String[MAX_STUDENTS];
    private static int[][] marks = new int[MAX_STUDENTS][SUBJECT_COUNT];
    private static int studentCount = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            printMenu();
            System.out.print("Enter choice: ");
            int choice = readInt(scanner);

            switch (choice) {
                case 1:
                    addStudent(scanner);
                    break;
                case 2:
                    displayAllStudents();
                    break;
                case 3:
                    searchByRollNumber(scanner);
                    break;
                case 4:
                    System.out.println("Exiting. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
            System.out.println();
        }
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("=== Student Grade Management System ===");
        System.out.println("1. Add student");
        System.out.println("2. Display all students");
        System.out.println("3. Search student by roll number");
        System.out.println("4. Exit");
    }

    private static void addStudent(Scanner scanner) {
        if (studentCount >= MAX_STUDENTS) {
            System.out.println("Cannot add more students. Limit reached (" + MAX_STUDENTS + ").");
            return;
        }

        System.out.print("Enter roll number: ");
        int roll = readInt(scanner);
        if (findStudentIndex(roll) != -1) {
            System.out.println("Student with roll number " + roll + " already exists.");
            return;
        }

        scanner.nextLine();
        System.out.print("Enter student name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }

        int[] studentMarks = new int[SUBJECT_COUNT];
        for (int i = 0; i < SUBJECT_COUNT; i++) {
            System.out.print("Enter marks for " + SUBJECT_NAMES[i] + " (0-100): ");
            int mark = readInt(scanner);
            if (mark < 0 || mark > 100) {
                System.out.println("Marks must be between 0 and 100.");
                return;
            }
            studentMarks[i] = mark;
        }

        rollNumbers[studentCount] = roll;
        names[studentCount] = name;
        marks[studentCount] = studentMarks;
        studentCount++;

        System.out.println("Student added successfully.");
        printStudentReport(studentCount - 1);
    }

    private static void displayAllStudents() {
        if (studentCount == 0) {
            System.out.println("No students recorded yet.");
            return;
        }
        System.out.println("--- All Students ---");
        for (int i = 0; i < studentCount; i++) {
            printStudentReport(i);
            System.out.println("--------------------");
        }
    }

    private static void searchByRollNumber(Scanner scanner) {
        if (studentCount == 0) {
            System.out.println("No students recorded yet.");
            return;
        }
        System.out.print("Enter roll number to search: ");
        int roll = readInt(scanner);
        int index = findStudentIndex(roll);
        if (index == -1) {
            System.out.println("No student found with roll number " + roll + ".");
        } else {
            System.out.println("--- Student Found ---");
            printStudentReport(index);
        }
    }

    private static int findStudentIndex(int roll) {
        for (int i = 0; i < studentCount; i++) {
            if (rollNumbers[i] == roll) {
                return i;
            }
        }
        return -1;
    }

    private static int calculateTotal(int studentIndex) {
        int total = 0;
        for (int i = 0; i < SUBJECT_COUNT; i++) {
            total += marks[studentIndex][i];
        }
        return total;
    }

    private static double calculateAverage(int studentIndex) {
        return (double) calculateTotal(studentIndex) / SUBJECT_COUNT;
    }

    private static char calculateGrade(double average) {
        if (average >= 80) {
            return 'A';
        } else if (average >= 60) {
            return 'B';
        } else if (average >= 40) {
            return 'C';
        }
        return 'F';
    }

    private static void printStudentReport(int index) {
        int total = calculateTotal(index);
        double average = calculateAverage(index);
        char grade = calculateGrade(average);

        System.out.println("Roll No : " + rollNumbers[index]);
        System.out.println("Name    : " + names[index]);
        for (int i = 0; i < SUBJECT_COUNT; i++) {
            System.out.println(SUBJECT_NAMES[i] + " : " + marks[index][i]);
        }
        System.out.println("Total   : " + total);
        System.out.printf("Average : %.2f%n", average);
        System.out.println("Grade   : " + grade);
    }

    private static int readInt(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.print("Please enter a valid number: ");
            scanner.next();
        }
        return scanner.nextInt();
    }
}
