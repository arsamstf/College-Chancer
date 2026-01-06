import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    static class CollegeStats {
        double minGPA;
        double maxGPA;
        int minSAT;
        int maxSAT;
        int minACT;
        int maxACT;
        boolean requiresExtracurriculars;

        CollegeStats(double minGPA, double maxGPA, int minSAT, int maxSAT, int minACT, int maxACT, boolean requiresExtracurriculars) {
            this.minGPA = minGPA;
            this.maxGPA = maxGPA;
            this.minSAT = minSAT;
            this.maxSAT = maxSAT;
            this.minACT = minACT;
            this.maxACT = maxACT;
            this.requiresExtracurriculars = requiresExtracurriculars;
        }

        boolean isWithinRange(double gpa, int sat, int act, boolean hasExtracurriculars) {
            boolean withinGPA = gpa >= minGPA && gpa <= maxGPA;
            boolean withinSAT = sat >= minSAT && sat <= maxSAT;
            boolean withinACT = act >= minACT && act <= maxACT;
            boolean meetsExtracurriculars = !requiresExtracurriculars || hasExtracurriculars;
            return withinGPA && (withinSAT || withinACT) && meetsExtracurriculars;
        }
    }

    public static void main(String[] args) {
        // Mock data for top 20 colleges
        Map<String, CollegeStats> collegeStatsMap = new HashMap<>();
        collegeStatsMap.put("Harvard University", new CollegeStats(3.9, 4.0, 1460, 1570, 33, 36, true));
        collegeStatsMap.put("Stanford University", new CollegeStats(3.9, 4.0, 1440, 1560, 32, 35, true));
        collegeStatsMap.put("MIT", new CollegeStats(3.9, 4.0, 1500, 1580, 34, 36, true));
        // Add more colleges as needed...

        // Get user information
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your GPA: ");
        double userGPA = scanner.nextDouble();

        System.out.print("Enter your SAT score: ");
        int userSAT = scanner.nextInt();

        System.out.print("Enter your ACT score: ");
        int userACT = scanner.nextInt();

        System.out.print("Do you have strong extracurriculars? (yes/no): ");
        String extracurricularInput = scanner.next().toLowerCase();
        boolean hasExtracurriculars = extracurricularInput.equals("yes");

        // Determine chances
        System.out.println("\nYour Chances for Top 20 Colleges:");
        for (Map.Entry<String, CollegeStats> entry : collegeStatsMap.entrySet()) {
            String collegeName = entry.getKey();
            CollegeStats stats = entry.getValue();

            if (stats.isWithinRange(userGPA, userSAT, userACT, hasExtracurriculars)) {
                System.out.println("You have a good chance at " + collegeName);
            } else {
                System.out.println("" + collegeName + " may be a reach for you.");
            }
        }

        scanner.close();
    }
}
