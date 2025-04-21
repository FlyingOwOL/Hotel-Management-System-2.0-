package MainProgram;

import ObjectElements.Objects;
import ObjectElements.Objects.constants;

import java.util.ArrayList;
import java.util.Scanner;

public class AssistMethods {
    // Helper method to convert MM/DD/YYYY to an integer YYYYMMDD for easy comparison
    public static int convertToComparableDate(String date) {
        String[] parts = date.split("/");
        int month = Integer.parseInt(parts[0]);
        int day = Integer.parseInt(parts[1]);
        int year = Integer.parseInt(parts[2]);
        return year * 10000 + month * 100 + day;
    }
    public static boolean isExistingHotel (ArrayList <Objects.Hotel> hotels, String name) {
        boolean isFound = false;
        for (int i = 0; i < hotels.size() && !isFound; i++) {
            if (name == null){
                isFound = false;
            } else if (hotels.get(i).getName().equals(name)) {
                isFound = true;
            }
        }
        return isFound;
    }
    public static boolean isValidDate (String date){
        boolean isValid = false;
        String[] parts = date.split("/");
        if (parts.length == 3) {
            int month = Integer.parseInt(parts[0]);
            int day = Integer.parseInt(parts[1]);
            int year = Integer.parseInt(parts[2]);
            if (month >= 1 && month <= 12 && day >= 1 && day <= 31 && year > 0) {
                isValid = true;
            } else {
                System.out.println("Invalid date, please enter a valid date in MM/DD/YYYY format");
            }
        } else {
            System.out.println("Invalid date format, please enter a date in MM/DD/YYYY format");
        }
        return isValid;
    }
    public static boolean isValidPrefix (String prefix){
        boolean isValid = false;
        if (prefix.length() == 1) {
            char cPrefix = prefix.charAt(0);
            if (Character.isLetter(cPrefix)) {
                isValid = true;
            } else {
                System.out.println("Invalid prefix, please enter a letter");
            }
        } else {
            System.out.println("Only first character was taken");
        }
        return isValid;
    }
    public static boolean isExistingRoom (Objects.Hotel hotel, String roomNumber){
        boolean isValid = false;
        for (int x = 0; x < hotel.getTotalRooms() && !isValid; x++){
            if (roomNumber == null){
                isValid = false;
            } else if (hotel.getRoomNumber(x).equals(roomNumber)) {
                isValid = true;
            }
        }
        return isValid;
    }

    public static void hotelSelection (Objects.Hotel hotel, Scanner userInput ){
        int dChoice = 0;
        do{
            System.out.printf ("Hotel %s menu:\n", hotel.getName());
            System.out.printf ("%s\n%s\n%s\n%s\n%s",
            "1. Total number of available rooms for selected date",
            "2. View room information",
            "3. View Reservation information",
            "0. return to view hotel menu",
            "Your choice: ");
            dChoice = userInput.nextInt();
            userInput.nextLine(); // Consume the newline character left by nextInt()  
            switch (dChoice){
                case 1:
                    System.out.print("Enter start date (MM/DD/YYYY): ");
                    String checkInDate = userInput.nextLine();
                    System.out.print("Enter end date (MM/DD/YYYY): ");
                    String checkOutDate = userInput.nextLine();
                    System.out.printf("Total number of available rooms: %d\n", hotel.getTotaldAvailableRooms());
                    System.out.printf("Total number of booked rooms: %d\n", hotel.dateBookedRooms(checkInDate, checkOutDate));
                    break;
                case 2:
                    System.out.printf ("Hotel %s room:\n", hotel.getName());
                    for (int i = 0; i < hotel.getTotalRooms(); i++) {
                        System.out.printf ("%d. %s\n", i + 1, hotel.getRoomNumber(i));
                    }
                    System.out.print("Enter room number (e.g., A001): ");
                    String roomNumber = userInput.nextLine();
                    System.out.print("Enter month (e.g., January): ");
                    String month = userInput.nextLine(); //Spelled out
                    hotel.viewRoomInfo(roomNumber, month);
                    break;
                case 3:
                    System.out.printf ("Hotel %s reservation:\n", hotel.getName());
                    if (hotel.getTotaldReservations() == 0) {
                        System.out.println("No reservations found.");
                    } else {
                        for (int i = 0; i < hotel.getTotaldReservations(); i++) {
                            System.out.printf ("%d. %s\n", i + 1, hotel.viewReservation(i));
                        }
                        System.out.print ("Enter room number (e.g., A001): ");
                        String room = userInput.nextLine();
                        System.out.print ("Enter name:");
                        String name = userInput.nextLine();
                        hotel.viewReservationInfo(room, name);
                    }
                    break;
                case 0:
                    System.out.println("Returning to view hotel menu");
                    break;
                default:
            }  
        } while (dChoice != 0);
    }

    public static void manipulateHotel (ArrayList <Objects.Hotel> hotels, Objects.Hotel hotel, Scanner userInput) {
        int dChoice = 0;
        do{
            System.out.printf ("Hotel %s menu:\n", hotel.getName());
            System.out.printf ("%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s",
            "1. Change hotel name",
            "2. Add room",
            "3. Remove room",
            "4. Update base price for a room",
            "5. Remove reservation",
            "6. remove hotel",
            "0. return to manage hotel menu",
            "Your choice: ");
            dChoice = userInput.nextInt();
            userInput.nextLine(); // Consume the newline character left by nextInt()  
            switch (dChoice){
                case 1:
                    boolean isNotValid = false;
                    do{
                        System.out.print ("Enter new hotel name: ");
                        String name = userInput.nextLine();
                        isNotValid = isExistingHotel(hotels, name);
                        if (hotel.getName().equals(name)) {
                            System.out.println("Hotel name is the same, please enter a different name.");
                        } else if (isNotValid) {
                            System.out.println("Hotel name already exists, please enter a different name.");
                        } else if (name.length() > 20) {
                            System.out.println ("Hotel name is too long, please enter a name with less than 20 characters");
                            isNotValid = true;
                        }else {
                            hotel.changeName(name);
                            System.out.printf ("Hotel name has been changed to %s\n", hotel.getName());
                        }
                    } while (isNotValid);
                    break;
                case 2: 
                    boolean isValidLocation = false;
                    int dIndex = 0;
                    do{
                        System.out.println ("Current rooms:");
                        for (int i = 0; i < constants.MAX_ROOMS; i++) {
                            if (AssistMethods.isExistingRoom(hotel, hotel.getRoomNumber(i))){
                                System.out.printf ("%d. %s\n", i + 1, hotel.getRoomNumber(i));
                            } else {
                                System.out.printf ("%d. Empty\n", i + 1);
                            }
                        }
                        System.out.printf("Select location to add room (1 - 50): ", hotel.getTotalRooms() + 1);
                        dIndex = userInput.nextInt();
                        if (dIndex < 1 || dIndex > 50) {
                            System.out.println("Invalid location, please select a number between 1 and 50.");
                        } else if (hotel.getRoomNumber(dIndex - 1) != null) {
                            System.out.println("Room already exists in this location, please select another location.");
                        } else {
                            isValidLocation = true;
                        }
                    } while (!isValidLocation);
                    hotel.addRoom(dIndex - 1);
                    break;
                case 3:
                    System.out.printf ("Hotel %s room:\n", hotel.getName());
                    for (int i = 0; i < hotel.getTotalRooms(); i++) {
                        System.out.printf ("%d. %s\n", i + 1, hotel.getRoomNumber(i));
                    }
                    System.out.print("Enter number of room to remove (e.g., A001): ");
                    String roomNumber = userInput.nextLine();
                    hotel.removeRoom(roomNumber);
                    break;
                case 4:
                    System.out.printf ("Hotel %s room:\n", hotel.getName());
                    for (int i = 0; i < hotel.getTotalRooms(); i++) {
                        if (AssistMethods.isExistingRoom(hotel, hotel.getRoomNumber(i))){
                            System.out.printf ("%d. %s\n", i + 1, hotel.getRoomNumber(i));
                        }
                    }
                    System.out.print("Enter room number (e.g., A001): ");
                    String room = userInput.nextLine();
                    System.out.print("Enter new base price: ");
                    double basePrice = userInput.nextDouble();
                    userInput.nextLine(); // Consume the newline character left by nextInt()
                    hotel.changeRoomPrice(room, basePrice); 
                    break;
                case 5:
                    System.out.printf ("Hotel %s reservation:\n", hotel.getName());
                    for (int i = 0; i < hotel.getTotaldReservations(); i++) {
                        System.out.printf ("%d. %s\n", i + 1, hotel.viewReservation(i));
                    }
                    System.out.print ("Enter room number (e.g., A001): ");
                    String roomNumber2 = userInput.nextLine();
                    System.out.print ("Enter name:");
                    String name = userInput.nextLine();
                    hotel.removeReservation(name, roomNumber2);
                    break;
                case 6:
                    if (hotel.getTotaldBookedRooms() > 0){
                        System.out.println ("There are still booked rooms in this hotel");
                    } else {
                        hotels.remove(hotel);
                        System.out.printf ("Hotel %s has been removed\n", hotel.getName());
                    }
                    break;
                default:
            }  
        } while (dChoice != 0 && AssistMethods.isExistingHotel(hotels, hotel.getName()) != false);
    }
    
    public static void makeReservation (Objects.Hotel hotel, Scanner userInput) {
        String name = null;
        String roomNumber = null;
        String checkInDate = null;
        String checkOutDate = null;
        boolean isNotValid = false;
        System.out.print ("Enter your name: ");
        name = userInput.nextLine();
        do{
            System.out.println ("Current rooms:");
            for (int i = 0; i < hotel.getTotalRooms(); i++) {
                if (AssistMethods.isExistingRoom(hotel, hotel.getRoomNumber(i))){
                    System.out.printf ("%d. %s\n", i + 1, hotel.getRoomNumber(i));
                } else {
                    System.out.printf ("%d. Empty\n", i + 1);
                }
            }
            System.out.print ("Enter room number (e.g., A001): ");
            roomNumber = userInput.nextLine();
            isNotValid = isExistingRoom(hotel, roomNumber);
            if (!isNotValid) {
                System.out.println("Room does not exist");
            } else if (!hotel.isRoomAvailable(roomNumber)){
                System.out.println ("Room is booked, please select another room");
                isNotValid = false;
            }
        } while (!isNotValid);
        do{
            System.out.print ("Enter start date (MM/DD/YYYY): ");
            checkInDate = userInput.nextLine();
            isNotValid = isValidDate(checkInDate);
        } while (!isNotValid);
        do{
            System.out.print ("Enter end date (MM/DD/YYYY): ");
            checkOutDate = userInput.nextLine();
            isNotValid = isValidDate(checkOutDate);
        } while (!isNotValid);
        hotel.addReservation(name, roomNumber, checkInDate, checkOutDate);
    }
}
