package MainMethods;

import ObjectElements.Objects;
import java.util.Scanner;
import java.util.ArrayList;
import MainProgram.AssistMethods;

public class MainMethods {

    public void createHotel (ArrayList <Objects.Hotel> hotels, Scanner userInput){
        String name;
        char cPrefix;
        boolean isNotValid = false;
        do{
            System.out.print ("Enter Hotel name: ");
            name = userInput.nextLine();
            isNotValid = AssistMethods.isExistingHotel(hotels, name);
            if (isNotValid) {
                System.out.println ("Hotel name already exists, please enter a different name");
            }
            if (name.length() > 20) {
                System.out.println ("Hotel name is too long, please enter a name with less than 20 characters");
                isNotValid = true;
            }
        } while (isNotValid);
        do{            
            System.out.print ("Enter Hotel room prefix (e.g A, B, C): ");
            cPrefix = userInput.nextLine().charAt(0);
            isNotValid = AssistMethods.isValidPrefix(String.valueOf(cPrefix));
        } while (!isNotValid);

        hotels.add(new Objects.Hotel(name, cPrefix));

        System.out.println ("Hotel " + name + " has been created with room prefix " + cPrefix);
    }

    public void viewHotel (ArrayList <Objects.Hotel> hotels, Scanner userInput){
        int dChoice = 0;
        do{
            int dTotalRooms = 0;
            double dTotalEarning = 0;
            System.out.println ("Select a hotel to view more details:");
            System.out.printf ("%3s +----- %10s -----+ %5s + %17s\n", "No.", "Hotel Name", "Rooms", "Estimated Earning");
            for (int i = 0; i < hotels.size(); i++){
                System.out.printf ("%2d. | %20s | %3d   | %.2f\n", 
                i + 1,  
                hotels.get(i).getName(), 
                hotels.get(i).getTotalRooms(),
                hotels.get(i).getEstimateEarning());
                dTotalEarning += hotels.get(i).getEstimateEarning();
                dTotalRooms += hotels.get(i).getTotalRooms();
            }
            System.out.println ("----+----------------------+-------+-----------------");
            System.out.printf ("Total:       %3d           | %3d   | %.2f\n",hotels.size(), dTotalRooms, dTotalEarning);
            System.out.println ("----+----------------------+-------+-----------------");
            System.out.println (" 0. Exit to main menu");
            System.out.print ("Enter your choice: ");
            dChoice = userInput.nextInt();
            userInput.nextLine(); // Consume the newline character
            if (dChoice > 0 && dChoice <= hotels.size()){
                AssistMethods.hotelSelection(hotels.get(dChoice - 1), userInput);
            } else {
                System.out.println ("Out of range option");
            }
        } while (dChoice != 0);
        System.out.println ("Returning to main menu");
    }

    public void manageHotel (ArrayList <Objects.Hotel> hotels, Scanner userInput){
        int dChoice = 0;
        do{
            System.out.println ("Select a hotel to manage:");
            System.out.printf ("%3s +----- %10s -----+\n", "No.", "Hotel Name");
            for (int i = 0; i < hotels.size(); i++){
                System.out.printf ("%2d. | %20s |\n", 
                i + 1,  
                hotels.get(i).getName());
            }
            System.out.println ("----+----------------------+");
            System.out.println (" 0. Exit to main menu");
            System.out.print ("Enter your choice: ");
            dChoice = userInput.nextInt();
            userInput.nextLine(); // Consume the newline character
            if (dChoice > 0 && dChoice <= hotels.size()){
                AssistMethods.manipulateHotel (hotels, hotels.get(dChoice - 1), userInput);
            } else {
                System.out.println ("Out of range option");
            }
        } while (dChoice != 0);
        System.out.println ("Returning to main menu");
    }

    public void bookRoom (ArrayList <Objects.Hotel> hotels, Scanner userInput){
        int dChoice = 0;
        do{
            System.out.println ("Select a hotel to book a room:");
            System.out.printf ("%3s +----- %10s -----+\n", "No.", "Hotel Name");
            for (int i = 0; i < hotels.size(); i++){
                System.out.printf ("%2d. | %20s |\n", 
                i + 1,  
                hotels.get(i).getName());
            }
            System.out.println ("----+----------------------+");
            System.out.println (" 0. Exit to main menu");
            System.out.print ("Enter your choice: ");
            dChoice = userInput.nextInt();
            userInput.nextLine(); // Consume the newline character
            if (dChoice > 0 && dChoice <= hotels.size()){
                if (hotels.get(dChoice - 1).getTotaldReservations() < Objects.constants.MAX_RESERVATIONS) {
                    AssistMethods.makeReservation(hotels.get(dChoice - 1), userInput);
                } else {
                    System.out.println ("Hotel " + hotels.get(dChoice - 1).getName() + " has reached the maximum number of reservations.");
                }
            } else {
                System.out.println ("Out of range option");
            }
        } while (dChoice != 0);
        System.out.println ("Returning to main menu");
    }
}