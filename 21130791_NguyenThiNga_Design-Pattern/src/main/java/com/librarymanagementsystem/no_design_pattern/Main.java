package com.librarymanagementsystem.no_design_pattern;

public class Main {
    public static void main(String[] args) {
        Employee emp1 = new TeamLeader("Alice");
        Employee emp3 = new OfficeStaff("Charlie");
        Employee emp4 = new FactoryWorker("David");
        Employee emp5 = new ChiefAccountant("Eva");

        // Perform duties
        emp1.attendMeetings();
        emp1.performDuties();
        emp1.submitReports();

        System.out.println();

        emp3.attendMeetings();
        emp3.performDuties();
        emp3.submitReports();

        System.out.println();

        emp4.attendMeetings();
        emp4.performDuties();
        emp4.submitReports();

        System.out.println();

        emp5.attendMeetings();
        emp5.performDuties();
        emp5.submitReports();
    }
}
