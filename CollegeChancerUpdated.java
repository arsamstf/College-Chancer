import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Main {

    // Represents the admission criteria for a college.
    static class CollegeStats {
        double minGPA;
        double maxGPA;
        int minSAT;
        int maxSAT;
        int minACT;
        int maxACT;
        boolean requiresExtracurriculars;
        int minExtraScore; // Minimum extracurricular score required (if applicable)

        CollegeStats(double minGPA, double maxGPA, int minSAT, int maxSAT, int minACT, int maxACT,
                     boolean requiresExtracurriculars, int minExtraScore) {
            this.minGPA = minGPA;
            this.maxGPA = maxGPA;
            this.minSAT = minSAT;
            this.maxSAT = maxSAT;
            this.minACT = minACT;
            this.maxACT = maxACT;
            this.requiresExtracurriculars = requiresExtracurriculars;
            this.minExtraScore = minExtraScore;
        }

        /**
         * Determines if the student's profile meets the college's admission criteria.
         *
         * @param gpa        the student's GPA
         * @param sat        the student's SAT score
         * @param act        the student's ACT score
         * @param extraScore the computed extracurricular score
         * @return true if the student's credentials are within the accepted ranges; false otherwise
         */
        boolean isWithinRange(double gpa, int sat, int act, int extraScore) {
            boolean withinGPA = gpa >= minGPA && gpa <= maxGPA;
            boolean withinSAT = sat >= minSAT && sat <= maxSAT;
            boolean withinACT = act >= minACT && act <= maxACT;
            boolean meetsExtracurriculars = !requiresExtracurriculars || extraScore >= minExtraScore;
            // Accept if GPA is within range, at least one test (SAT or ACT) meets criteria, and extracurricular requirements are met.
            return withinGPA && (withinSAT || withinACT) && meetsExtracurriculars;
        }
    }

    /**
     * Simulates fetching college statistics from an online source.
     * Assumes the external service returns an HTML document with the following element IDs:
     * "gpaMin", "gpaMax", "satMin", "satMax", "actMin", "actMax", "requiresExtracurriculars",
     * and if extracurriculars are required, "minExtraScore".
     *
     * @param collegeName the name of the college
     * @return a CollegeStats object built from the fetched data, or fallback values if the fetch fails.
     */
    static CollegeStats fetchCollegeStatsFromWeb(String collegeName) {
        try {
            String encodedCollegeName = URLEncoder.encode(collegeName, StandardCharsets.UTF_8);
            String url = "https://example.com/college-stats?college=" + encodedCollegeName;
            System.out.println("Connecting to " + url);

            Document doc = Jsoup.connect(url).get();

            Element gpaMinElem = doc.getElementById("gpaMin");
            Element gpaMaxElem = doc.getElementById("gpaMax");
            Element satMinElem = doc.getElementById("satMin");
            Element satMaxElem = doc.getElementById("satMax");
            Element actMinElem = doc.getElementById("actMin");
            Element actMaxElem = doc.getElementById("actMax");
            Element extraReqElem = doc.getElementById("requiresExtracurriculars");

            double minGPA = Double.parseDouble(gpaMinElem.text());
            double maxGPA = Double.parseDouble(gpaMaxElem.text());
            int minSAT = Integer.parseInt(satMinElem.text());
            int maxSAT = Integer.parseInt(satMaxElem.text());
            int minACT = Integer.parseInt(actMinElem.text());
            int maxACT = Integer.parseInt(actMaxElem.text());
            boolean requiresExtracurriculars = extraReqElem.text().equalsIgnoreCase("yes");

            int minExtraScore = 0;
            if (requiresExtracurriculars) {
                Element extraScoreElem = doc.getElementById("minExtraScore");
                minExtraScore = Integer.parseInt(extraScoreElem.text());
            }

            return new CollegeStats(minGPA, maxGPA, minSAT, maxSAT, minACT, maxACT, requiresExtracurriculars, minExtraScore);
        } catch (IOException | NumberFormatException e) {
            System.out.println("Failed to fetch or parse online data for " + collegeName + ". Using fallback default values.");
            // Fallback default values (adjust as necessary).
            return new CollegeStats(3.0, 4.0, 1000, 1600, 20, 36, false, 0);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Collect academic details.
        System.out.print("Enter your GPA: ");
        double userGPA = scanner.nextDouble();

        System.out.print("Enter your SAT score: ");
        int userSAT = scanner.nextInt();

        System.out.print("Enter your ACT score: ");
        int userACT = scanner.nextInt();

        // Collect detailed extracurricular information.
        System.out.println("\nEnter details for your extracurricular activities:");
        System.out.print("Number of extracurricular activities you are involved in: ");
        int numActivities = scanner.nextInt();

        System.out.print("Number of leadership positions held: ");
        int leadershipPositions = scanner.nextInt();

        System.out.print("Number of awards or honors received: ");
        int awards = scanner.nextInt();

        // Calculate an extracurricular score. (This is just one way to compute it.)
        int extraScore = numActivities + (2 * leadershipPositions) + (3 * awards);
        System.out.println("Your computed extracurricular score is: " + extraScore);

        // Let the user select a college.
        scanner.nextLine();  // Consume the newline.
        System.out.print("\nEnter the college name you are interested in: ");
        String collegeName = scanner.nextLine();

        System.out.println("\nSearching online for stats for " + collegeName + "...");
        CollegeStats stats = fetchCollegeStatsFromWeb(collegeName);

        // Evaluate the student's profile against the college's criteria.
        if (stats.isWithinRange(userGPA, userSAT, userACT, extraScore)) {
            System.out.println("Based on the current stats, you have a good chance at " + collegeName + "!");
        } else {
            System.out.println("Based on the current stats, " + collegeName + " might be a reach for you.");
        }

        scanner.close();
    }
}
