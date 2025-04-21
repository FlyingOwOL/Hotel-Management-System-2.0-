package MainProgram;

import ObjectElements.Objects;
import MainMethods.MainMethods;
import java.util.Scanner;
import java.util.ArrayList;

public class Main
{
	public static void main(String[] args) 
	{
        ArrayList <Objects.Hotel> hotels = new ArrayList <Objects.Hotel>();
        MainMethods methods = new MainMethods();       
	    Scanner userInput = new Scanner (System.in);
        int dMenuInput;
	    do{
            System.out.printf("%s\n%s\n%s\n%s\n%s\n%s\n%s",
            "Select option:",
            "1.Create Hotel",
            "2.View Hotel",
            "3.Manage Hotel",
            "4.Book Room",
            "0.Exit Program",
            "Number of your choice:");
            dMenuInput = userInput.nextInt();
            userInput.nextLine(); // Consume the newline character left by nextInt()
            switch(dMenuInput)
            {
                case 1:
                    methods.createHotel(hotels, userInput);
                    break;
                case 2:
                    if (hotels.isEmpty()){
                        System.out.println("No hotels available to view, please create a hotel first");
                    } else {
                        methods.viewHotel(hotels, userInput);
                    }
                    break;
                case 3:
                    if (hotels.isEmpty()){
                        System.out.println("No hotels available to manage, please create a hotel first");
                    } else {
                        methods.manageHotel(hotels, userInput);
                    }
                    break;
                case 4:
                    if (hotels.isEmpty()){
                        System.out.println("No hotels available to book, please create a hotel first");
                    } else {
                        methods.bookRoom(hotels, userInput);
                    }
                    break;
                case 0:
                    System.out.println("Now exiting program");
                    break;
                default:
                    System.out.println("Out of range input");
                
            }
	    }while(dMenuInput!=0);
        userInput.close();
	}
}
