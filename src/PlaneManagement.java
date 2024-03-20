import java.util.InputMismatchException;
import java.util.Scanner;

public class PlaneManagement{
    private static Scanner input = new Scanner(System.in);

    private static Ticket[][] ticketsSold = new Ticket[4][14];

    // menu (Triggered from the main method)
    public static void menu(){
        System.out.println("\n"+"-".repeat(45));
        System.out.println("-".repeat(10) + " ".repeat(5) + "MENU OPTIONS" + " ".repeat(8) + "-".repeat(10));
        System.out.println("-".repeat(45));
        System.out.println("\n1) Buy a seat");
        System.out.println("2) Cancel a seat");
        System.out.println("3) Find first available seat");
        System.out.println("4) Show seating plan");
        System.out.println("5) Print tickets information and total sales");
        System.out.println("6) Search ticket");
        System.out.println("0) Quit\n");
        System.out.println("-".repeat(45));
    }

    // task 3
    public static void buy_seat(int[][] seatsArray) {
        boolean SeatPurchased = false;
        do {
            try {
                System.out.print("Enter the row (A-D): ");
                String rowLetter = input.next().toUpperCase();
                System.out.print("Enter seat number to buy (1-" + (seatsArray[rowLetter.charAt(0) - 'A'].length) + "): ");
                int columnNumber = input.nextInt();

                int rowNumber = rowLetter.charAt(0) - 'A';
                if (rowNumber >= 0 && rowNumber < seatsArray.length && columnNumber > 0 && columnNumber <= seatsArray[rowNumber].length) {
                    if (seatsArray[rowNumber][columnNumber - 1] == 0) {
                        seatsArray[rowNumber][columnNumber - 1] = 1;
                        SeatPurchased = true;

                        // set passenger information
                        System.out.println("\nEnter passenger information:");

                        System.out.print("\nEnter passenger's first name: ");
                        String name = input.next();

                        System.out.print("Enter passenger's surname: ");
                        String surname = input.next();

                        System.out.print("Enter passenger's email: ");
                        String email = input.next();

                        System.out.println("\nSeat purchased successfully!");

                        int price = ticketsPriceCalculation(columnNumber);

                        Person person = new Person(name, surname, email);
                        Ticket ticket = new Ticket(rowLetter, columnNumber, price, person);
                        ticketsSold[rowNumber][columnNumber - 1] = ticket;
                        ticket.save();
                    } else {
                        System.out.println("\nSeat is already taken. Please try again.");
                    }
                } else {
                    System.out.println("\nInvalid seat number. Please try again.");
                }
            } catch (ArrayIndexOutOfBoundsException | InputMismatchException e) {
                System.out.println("\nInvalid input. Please try again");
                input.nextLine();
            }
        } while (!SeatPurchased);
    }

    // task 4
    public static void cancel_seat(int[][] seatsArray) {
        boolean seatCancelled = false;

        do {
            try {
                System.out.print("Enter the row (A-D) to cancel: ");
                String rowLetter = input.next().toUpperCase();
                System.out.print("Enter seat number to cancel (1-" + (seatsArray[rowLetter.charAt(0) - 'A'].length) + "): ");
                int columnNumber = input.nextInt();

                int rowIndex = rowLetter.charAt(0) - 'A';
                if (rowIndex >= 0 && rowIndex < seatsArray.length && columnNumber > 0 && columnNumber <= seatsArray[rowIndex].length) {
                    if (seatsArray[rowIndex][columnNumber - 1] == 1) {
                        seatsArray[rowIndex][columnNumber - 1] = 0;

                        ticketsSold[rowIndex][columnNumber - 1] = null;

                        Ticket.delete(rowLetter,columnNumber);

                        System.out.println("\nSeat cancelled successfully!");
                        seatCancelled = true;
                    } else {
                        System.out.println("\nSeat is already available. Enter '1' to try again or '0' to abort.");
                        int selection = input.nextInt();
                        if (selection == 0){
                            break;
                        }
                    }
                } else {
                    System.out.println("\nInvalid seat number. Please try again.");
                }
            }catch (ArrayIndexOutOfBoundsException | InputMismatchException e) {
                System.out.println("\nInvalid input. Please try again");
                input.nextLine();
            }
        } while (!seatCancelled);
    }

    // task 5
    public static int[] find_first_available(int[][] seatsArray) {
        for (int i = 0; i < seatsArray.length; i++) {
            for (int j = 0; j < seatsArray[i].length; j++) {
                if (seatsArray[i][j] == 0) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    public static void printAvailable(int[][] seatsArray){
        int[] availableSeat = find_first_available(seatsArray);
        if (availableSeat != null) {
            System.out.println("First available seat is in row " + (char) ('A' + availableSeat[0]) + ", seat number " + (availableSeat[1] + 1));
        } else {
            System.out.println("No available seats found.");
        }
    }

    // task 6
    public static void show_seating_plan(int[][] seatsArray) {
        System.out.println("\n  1  2  3  4  5  6  7  8  9 10 11 12 13 14");
        for (int i = 0; i < seatsArray.length; i++) {
            System.out.printf("%c ", 'A' + i);
            for (int j = 0; j < seatsArray[i].length; j++) {
                if (seatsArray[i][j] == 0) {
                    System.out.print("O  ");
                } else {
                    System.out.print("X  ");
                }
            }
            System.out.println();
        }
    }

   // task 10
    public static void printAllTickets() {
        double price = 0;
        for(int i=0 ; i <= ticketsSold.length-1; i++) {
            for (int j = 0; j <= ticketsSold[i].length-1; j++) {
                if (ticketsSold[i][j] != null){
                    System.out.println(ticketsSold[i][j].print_tickets_info());
                    price = price + ticketsSold[i][j].getPrice();
                }
            }
        }
        System.out.println("Total Price : £"+ price);
    }

    // task 11
    public static void search_ticket(int[][] seatsArray) {
        try {
            System.out.print("Enter the row (A-D) to search: ");
            String rowLetter = input.next().toUpperCase();
            System.out.print("Enter seat number to search (1-" + (seatsArray[rowLetter.charAt(0) - 'A'].length) + "): ");
            int columnNumber = input.nextInt();

            int rowIndex = rowLetter.charAt(0) - 'A';
            if (rowIndex >= 0 && rowIndex < seatsArray.length && columnNumber > 0 && columnNumber <= seatsArray[rowIndex].length) {
                if (seatsArray[rowIndex][columnNumber - 1] == 1) {
                    Ticket ticket = ticketsSold[rowIndex][columnNumber - 1];
                    System.out.println("\nTicket Found!");
                    ticket.printInfo();
                } else {
                    System.out.println("\nThis seat is available.");
                }
            } else {
                System.out.println("\nInvalid seat number. Please try again.");
            }
        } catch (ArrayIndexOutOfBoundsException | InputMismatchException e) {
            System.out.println("\nInvalid input. Please try again");
            input.nextLine();
        }
    }

    // Triggered from buy seat method
    public static int ticketsPriceCalculation(int columnNumber) {
        int price;
        if (columnNumber < 6) {
            price = 200;
        } else if (columnNumber < 10) {
            price = 150;
        } else {
            price = 180;
        }
        return price;
    }

    // welcome and option menu
    public static void main(String[] args) {
        System.out.println("\nWelcome to the Plane Management Application");

        int option = -1;

        int[][] seatsArray;
        seatsArray = new int[4][];
        seatsArray[0] = new int[14]; // Row A
        seatsArray[1] = new int[12]; // Row B
        seatsArray[2] = new int[12]; // Row C
        seatsArray[3] = new int[14]; // Row D

        for (int i = 0; i < seatsArray.length; i++) {
            for (int j = 0; j < seatsArray[i].length; j++) {
                seatsArray[i][j] = 0;
            }
        }

        do {
            menu();
            System.out.print("\nPlease select an option: ");

            try {
                option = input.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please select an option between 0 and 6.");
                input.nextLine();
                continue;
            }

            switch (option) {
                case 1:
                    buy_seat(seatsArray);
                    break;
                case 2:
                    cancel_seat(seatsArray);
                    break;
                case 3:
                    printAvailable(seatsArray);
                    break;
                case 4:
                    show_seating_plan(seatsArray);
                    break;
                case 5:
                    printAllTickets();
                    break;
                case 6:
                    search_ticket(seatsArray);
                    break;
                case 0:
                    System.out.println("Exiting program...");
                    break;
                default:
                    System.out.println("Invalid option. Please select a number between 0 and 6.");
            }
        } while (option != 0);
        input.close();
    }
}