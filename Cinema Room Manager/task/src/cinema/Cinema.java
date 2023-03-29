package cinema;
import java.util.Scanner;

public class Cinema {
    private int rows = 0;
    private int cols = 0;
    private int currIncome = 0;
    private int totalIncome = 0;
    private int soldTickets = 0;
    private int firstHalf = 0;
    private char[][] theaterArr = new char[rows][cols];
    public static Scanner scanner = new Scanner(System.in);
    public static final int PRICE_10 = 10;
    public static final int PRICE_8 = 8;

    public int getRows() {
        return this.rows;
    }

    public int getCols() {
        return this.cols;
    }

    public int getCurrIncome() {
        return this.currIncome;
    }

    public int getTotIncome() {
        return this.totalIncome;
    }

    public int getSoldTickets() {
        return this.soldTickets;
    }
    
    public int getFirstHalf() {
        return this.firstHalf;
    }


    public char[][] getTheaterArr() {
        return this.theaterArr;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }

    public void setFirstHalf(int rows) {
        this.firstHalf = rows;
    }


    public void addToCurrIncome(int total) {
        this.currIncome += total;
    }
    
    public void setTotIncome(int total) {
        this.totalIncome = total;
    }

    public void addToSoldTickets() {
        this.soldTickets += 1;
    }

    public void setTheaterArr(int rows, int cols) {
        this.theaterArr = new char[rows][cols];
    }
    
    public void promptTheaterSize() {
        System.out.println("Enter the number of rows:");
        this.setRows(scanner.nextInt());
        System.out.println("Enter the number of seats in each row:");
        this.setCols(scanner.nextInt());
        this.setTheaterArr(this.getRows(), this.getCols());
        this.calcTotIncome();
        
    }

    public void buyTicketPrompt() {
        char[][] theaterArr = this.getTheaterArr();
        while (true) {
            System.out.println();
            System.out.println("Enter a row number:");
            int row = scanner.nextInt() - 1;
            System.out.println("Enter a seat number in that row:");
            int col = scanner.nextInt() - 1;
            System.out.println();
            
            // Error Handling if row and column is taken
            try {
                // first if is to is if occupied but the else if after words function as such
                // the first & second half variables are based aren't based on zero based ordering
                // I need to add back a 1 to the row variable to test accurately
                if (theaterArr[row][col] == 'B'){
                    System.out.println("That ticket has already been purchased!");
                } else if (row + 1 <= this.getFirstHalf()) {
                    System.out.println("Ticket price: $" + PRICE_10);
                    theaterArr[row][col] = 'B';
                    this.addToSoldTickets();
                    this.addToCurrIncome(PRICE_10);
                    break;
                } else {
                    System.out.println("Ticket price: $" + PRICE_8);
                    theaterArr[row][col] = 'B';
                    this.addToSoldTickets();
                    this.addToCurrIncome(PRICE_8);
                    break;
                }  
            }   catch(Exception e) {
                    System.out.println("Wrong input!");
            } 
        }

    }

    public void calcTotIncome() {
        int numRows = this.getRows();
        int numCols = this.getCols();
        int totalSeats = numRows * numCols;
        
        // 60 is the seat limit until the price structures changes
        if (totalSeats <= 60) {
            this.setTotIncome(totalSeats * PRICE_10);
            this.setFirstHalf(numRows);
        } else {
            int firstHalf = numRows / 2 ;
            int secondHalf = numRows - firstHalf;
            this.setTotIncome((firstHalf * cols * PRICE_10) + (secondHalf * cols * PRICE_8));
            this.setFirstHalf(firstHalf);
        }
    }
    
    public void printTheater() {
        int rows = this.getRows();
        int cols = this.getCols();
        char[][] tempArr = this.getTheaterArr();
        System.out.println();
        System.out.println("Cinema:");
        System.out.print("  ");

        // prints the numbers of cols
        for (int i = 1; i <= cols; i++){
            System.out.print(i + " ");
        }
        
        System.out.println();

        // prints the values in the theater seat array
        for (int row = 0; row < rows; row++) {
            System.out.print(row + 1 + " ");
            for(int col = 0; col < cols; col++) {
                if (tempArr[row][col] == 0 ) {
                    System.out.print("S ");
                } else {
                    System.out.print( tempArr[row][col] + " ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }
    
    public void printStats() {
        int soldTickets = this.getSoldTickets();
        int currIncome = this.getCurrIncome();
        int totalIncome = this.getTotIncome();
        int totalSeats = this.getRows() * this.getCols();
        float percentageSold = soldTickets != 0 ? ( (float) soldTickets / totalSeats ) * 100F : 0;
        System.out.println();
        System.out.printf("Number of purchased tickets: %d\n", soldTickets);
        System.out.printf("Percentage: %.2f%s\n", percentageSold, "%");
        System.out.printf("Current income: $%d\n", currIncome);
        System.out.printf("Total income: $%d\n", totalIncome);
        System.out.println();
        System.out.println();
    }
    
    public boolean actionPrompts() {
        System.out.println();
        System.out.println("1. Show the seats");
        System.out.println("2. Buy a ticket");
        System.out.println("3. Statistics");
        System.out.println("0. Exit");
        System.out.println();
        int action = scanner.nextInt();
    
        if (action == 0) {
            return true;
        } else if (action == 1) {
            this.printTheater();
        } else if (action == 2) {
            this.buyTicketPrompt();
        } else if (action == 3) {
            this.printStats();
        } else {
             // throw new RuntimeException(String.format("unknown menu command %d", action));
            return true;
        }
        return false;
    }


    public void startPrompts() {
            this.promptTheaterSize();
            while(true) {
                boolean exitAction = this.actionPrompts();
                if (exitAction) {
                    break;
                }
            }
            
    }
    
    public static void main(String[] args) {
        Cinema cinema = new Cinema();
        cinema.startPrompts();
    }
}