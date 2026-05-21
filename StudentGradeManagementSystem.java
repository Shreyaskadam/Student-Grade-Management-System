import java.util.Scanner;

/**
 * Student Grade Management System
 *
 * This program stores student data in arrays (not a database).
 * For each student we save: roll number, name, and marks in 3 subjects.
 * We can add students, show all records, search by roll number,
 * and automatically calculate total, average, and letter grade.
 */
public class StudentGradeManagementSystem {

    // Maximum number of students the program can store at once
    private static final int MAX_STUDENTS = 50;

    // Each student has marks in exactly 3 subjects
    private static final int SUBJECT_COUNT = 3;

    // Names of subjects (used when asking for marks and when printing reports)
    private static final String[] SUBJECT_NAMES = {"Math", "Science", "English"};

    // --- Arrays to store all student data ---
    // We use "parallel arrays": same index = same student in every array.
    // Example: rollNumbers[0], names[0], and marks[0] all belong to the first student.

    private static int[] rollNumbers = new int[MAX_STUDENTS];       // roll number of each student
    private static String[] names = new String[MAX_STUDENTS];       // name of each student
    private static int[][] marks = new int[MAX_STUDENTS][SUBJECT_COUNT]; // marks[row][col] = one subject mark

    // How many students are actually stored (rest of array slots are empty/unused)
    private static int studentCount = 0;

    /**
     * Program starts here. Shows a menu in a loop until the user chooses Exit.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // reads input from keyboard
        boolean running = true;                 // controls the main menu loop

        while (running) {
            printMenu();
            System.out.print("Enter choice: ");
            int choice = readInt(scanner); // read menu option as a number

            // Run the action matching the user's menu choice
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
                    running = false; // stops the while loop
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
            System.out.println(); // blank line between menu rounds
        }
        scanner.close();
    }

    /** Prints the main menu options on the screen. */
    private static void printMenu() {
        System.out.println("=== Student Grade Management System ===");
        System.out.println("1. Add student");
        System.out.println("2. Display all students");
        System.out.println("3. Search student by roll number");
        System.out.println("4. Exit");
    }

    /**
     * Adds one new student: asks for roll number, name, and marks,
     * then saves them into the arrays at position studentCount.
     */
    private static void addStudent(Scanner scanner) {
        // Check if we still have free space in our arrays
        if (studentCount >= MAX_STUDENTS) {
            System.out.println("Cannot add more students. Limit reached (" + MAX_STUDENTS + ").");
            return;
        }

        System.out.print("Enter roll number: ");
        int roll = readInt(scanner);

        // Roll number must be unique — search returns -1 if not found
        if (findStudentIndex(roll) != -1) {
            System.out.println("Student with roll number " + roll + " already exists.");
            return;
        }

        // Clear leftover newline after reading the roll number (int)
        scanner.nextLine();
        System.out.print("Enter student name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }

        // Temporary array to hold this student's 3 subject marks before storing
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

        // Save student at the next free index, then increase the count
        rollNumbers[studentCount] = roll;
        names[studentCount] = name;
        marks[studentCount] = studentMarks;
        studentCount++;

        System.out.println("Student added successfully.");
        // Show report for the student we just added (last index = studentCount - 1)
        printStudentReport(studentCount - 1);
    }

    /** Prints details of every student currently stored. */
    private static void displayAllStudents() {
        if (studentCount == 0) {
            System.out.println("No students recorded yet.");
            return;
        }
        System.out.println("--- All Students ---");
        // Loop only up to studentCount — not the full MAX_STUDENTS size
        for (int i = 0; i < studentCount; i++) {
            printStudentReport(i);
            System.out.println("--------------------");
        }
    }

    /**
     * Asks for a roll number and prints that student's report if found.
     */
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

    /**
     * Linear search: checks each stored student until roll number matches.
     * @return array index of the student, or -1 if not found
     */
    private static int findStudentIndex(int roll) {
        for (int i = 0; i < studentCount; i++) {
            if (rollNumbers[i] == roll) {
                return i;
            }
        }
        return -1; // convention: -1 means "not found"
    }

    /** Adds up all subject marks for one student. */
    private static int calculateTotal(int studentIndex) {
        int total = 0;
        for (int i = 0; i < SUBJECT_COUNT; i++) {
            total += marks[studentIndex][i];
        }
        return total;
    }

    /** Average = total marks divided by number of subjects. */
    private static double calculateAverage(int studentIndex) {
        return (double) calculateTotal(studentIndex) / SUBJECT_COUNT;
    }

    /**
     * Letter grade based on average marks:
     * A = 80 and above, B = 60–79, C = 40–59, F = below 40
     */
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

    /**
     * Prints one student's roll number, name, subject marks,
     * total, average, and grade.
     * @param index position of the student in the arrays
     */
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
        System.out.printf("Average : %.2f%n", average); // %.2f = show 2 decimal places
        System.out.println("Grade   : " + grade);
    }

    /**
     * Safely reads an integer from the user.
     * Keeps asking until the input is a valid number (not text).
     */
    private static int readInt(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.print("Please enter a valid number: ");
            scanner.next(); // throw away invalid input
        }
        return scanner.nextInt();
    }
}
