import java.util.*;

/**
 * Problem 1: Course Registration Portal
 * HashMap<String, Course>  -> O(1) lookup by course code
 * HashSet<String>          -> unique enrolled student IDs
 * ArrayList<String>        -> ordered prerequisite codes
 * LinkedList<String>       -> FIFO waitlist queue
 */
public class CourseRegistrationPortal {

    // ---------------------------------------------------------------- Course
    static class Course {
        private final String code;
        private final String title;
        private final int capacity;
        private final Set<String> enrolled = new HashSet<>();
        private final List<String> prerequisites = new ArrayList<>();
        private final LinkedList<String> waitlist = new LinkedList<>();

        Course(String code, String title, int capacity, List<String> prerequisites) {
            this.code = code;
            this.title = title;
            this.capacity = capacity;
            if (prerequisites != null) this.prerequisites.addAll(prerequisites);
        }

        boolean isFull()                  { return enrolled.size() >= capacity; }
        boolean isEnrolled(String id)     { return enrolled.contains(id); }
        boolean isWaitlisted(String id)   { return waitlist.contains(id); }
        String getCode()                  { return code; }
        List<String> getPrerequisites()   { return prerequisites; }

        @Override
        public String toString() {
            return code + " - " + title + " [" + enrolled.size() + "/" + capacity
                    + " enrolled, waitlist: " + waitlist + ", prereqs: " + prerequisites + "]";
        }
    }

    // ---------------------------------------------------------------- Portal
    private final Map<String, Course> courses = new HashMap<>();
    // Tracks completed courses per student (needed for prerequisite checks)
    private final Map<String, Set<String>> completed = new HashMap<>();

    public void addCourse(Course c) {
        courses.put(c.getCode(), c);
    }

    public void markCompleted(String studentId, String courseCode) {
        completed.computeIfAbsent(studentId, k -> new HashSet<>()).add(courseCode);
    }

    /** Registers a student, or places them on the waitlist if the course is full. */
    public String register(String studentId, String courseCode) {
        Course c = courses.get(courseCode);
        if (c == null) return "Course " + courseCode + " does not exist.";

        if (c.isEnrolled(studentId))   return studentId + " is already enrolled in " + courseCode + ".";
        if (c.isWaitlisted(studentId)) return studentId + " is already on the waitlist for " + courseCode + ".";

        // Prerequisite check
        Set<String> done = completed.getOrDefault(studentId, Collections.emptySet());
        for (String pre : c.getPrerequisites()) {
            if (!done.contains(pre)) {
                return studentId + " cannot register for " + courseCode + ": missing prerequisite " + pre + ".";
            }
        }

        // Capacity check
        if (!c.isFull()) {
            c.enrolled.add(studentId);
            return studentId + " enrolled in " + courseCode + ".";
        }
        c.waitlist.addLast(studentId);                       // FIFO: join at the back
        return courseCode + " is full. " + studentId + " added to waitlist (position "
                + c.waitlist.size() + ").";
    }

    /** Drops a student; automatically promotes the next waitlisted student. */
    public String drop(String studentId, String courseCode) {
        Course c = courses.get(courseCode);
        if (c == null) return "Course " + courseCode + " does not exist.";

        // Student leaving the waitlist
        if (c.waitlist.remove(studentId)) {
            return studentId + " removed from waitlist of " + courseCode + ".";
        }
        if (!c.enrolled.remove(studentId)) {
            return studentId + " is not enrolled in " + courseCode + ".";
        }

        StringBuilder msg = new StringBuilder(studentId + " dropped " + courseCode + ".");
        // Automated waitlist promotion (first-come, first-served)
        while (!c.isFull() && !c.waitlist.isEmpty()) {
            String next = c.waitlist.pollFirst();
            c.enrolled.add(next);
            msg.append(" Promoted ").append(next).append(" from waitlist.");
        }
        return msg.toString();
    }

    public void printCourse(String code) {
        Course c = courses.get(code);
        System.out.println(c == null ? "No such course: " + code : c);
    }

    // ------------------------------------------------------------------ main
    public static void main(String[] args) {
        CourseRegistrationPortal portal = new CourseRegistrationPortal();

        portal.addCourse(new Course("CS101", "Intro to Programming", 2, List.of()));
        portal.addCourse(new Course("CS201", "Data Structures", 2, List.of("CS101")));

        portal.markCompleted("S1", "CS101");
        portal.markCompleted("S2", "CS101");
        portal.markCompleted("S3", "CS101");
        portal.markCompleted("S4", "CS101");

        System.out.println(portal.register("S1", "CS201"));
        System.out.println(portal.register("S2", "CS201"));
        System.out.println(portal.register("S3", "CS201"));   // waitlisted
        System.out.println(portal.register("S4", "CS201"));   // waitlisted
        System.out.println(portal.register("S1", "CS201"));   // duplicate
        System.out.println(portal.register("S9", "CS201"));   // prerequisite missing
        portal.printCourse("CS201");

        System.out.println(portal.drop("S1", "CS201"));       // S3 promoted
        portal.printCourse("CS201");

        System.out.println(portal.drop("S4", "CS201"));       // leaves waitlist
        System.out.println(portal.register("XYZ", "CS999"));  // unknown course
        portal.printCourse("CS201");
    }
}
