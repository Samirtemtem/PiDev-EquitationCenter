package testconnection;

import Entities.Activity;
import Service.ServiceActivity;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;


public class test {


    public static void main(String[] args) {
        try {
            ServiceActivity service = new ServiceActivity();

            Activity newActivity = new Activity();
            newActivity.setDate(new Date());
            newActivity.setTypeActivity("activity type");
            newActivity.setTitle("activity title");
            newActivity.setDescription("activity description");
            newActivity.setPrice(9.99);

            // Add the activity
            service.add(newActivity);
            System.out.println("Activity added.");

            // Find the activity by ID
            List<Activity> allActivities = service.ReadAll();
            if (allActivities.isEmpty()) {
                System.out.println("No activities found.");
            } else {
                // Modify the first activity in the list
                Activity firstActivity = allActivities.get(0);
                firstActivity.setTypeActivity("Updated activity type");

                // Update the modified activity
                service.update(firstActivity);
                System.out.println("Activity updated successfully.");

                // Output all activities
                System.out.println("All activities:");
                for (Activity activity : allActivities) {
                    System.out.println("ID: " + activity.getId());
                    System.out.println("Date: " + activity.getDate());
                    System.out.println("Type: " + activity.getTypeActivity());
                    System.out.println("Title: " + activity.getTitle());
                    System.out.println("Description: " + activity.getDescription());
                    System.out.println("Price: " + activity.getPrice());
                    System.out.println("------------------");
                }
            }
            // Close the database connection
            service.closeConnection();
        } catch (SQLException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
}
