import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Ticket {
    private String row;
    private int seat;
    private int price;
    private Person person;

    // constructor
    public Ticket(String row, int seat, int price, Person person) {
        this.row = row;
        this.seat = seat;
        this.price = price;
        this.person = person;
    }

    // getters and setters
    public String getRow() {
        return row;
    }
    public void setRow(String row) {
        this.row = row;
    }
    public int getSeat() {
        return seat;
    }
    public void setSeat(int seat) {
        this.seat = seat;
    }
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public Person getPerson() {
        return person;
    }
    public void setPerson(Person person) {
        this.person = person;
    }

    // save to file (Triggered from buy seat method)
    public void save() {
        String filename = row + seat + ".txt";
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(print_tickets_info());
            System.out.println("Ticket information saved to file: " + filename);
        } catch (IOException e) {
            System.out.println("Error saving ticket information: " + e.getMessage());
        }
    }

    // delete saved files (Triggered from cancel seat method)
    public static void delete(String row, int seat) {
        String filename = row + seat + ".txt";
        File file = new File(filename);
        if (file.delete()) {
            System.out.println("Ticket information deleted from file: " + filename);
        } else {
            System.out.println("Error deleting ticket information file: " + filename);
        }
    }


    // Triggered from search ticket method
    public void printInfo() {
        System.out.println("Row: " + row);
        System.out.println("Seat: " + seat);
        System.out.println("Price: " + price);
        System.out.println("----- Passenger Information -----");
        person.printPersonInfo();
    }

    public String print_tickets_info() {
        return "-----------------TICKET--------------------\n"
                + "Row : " + this.row
                + "\nSeat : " + this.seat
                + "\nPrice :  £" + this.price
                + "\nName : " + this.person.getName()
                + "\nSurname : " + this.person.getSurname()
                + "\nEmail : " + this.person.getEmail()
                + "\n-----------------------------------------------";
    }


}
