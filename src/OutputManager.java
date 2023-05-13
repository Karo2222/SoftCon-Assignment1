package src;

public class OutputManager {

    private static final OutputManager INSTANCE = new OutputManager();

    private OutputManager() {
    }

    public static OutputManager getInstance() {
        return INSTANCE;
    }

    /**
     * Prints all letters from A to J which are on top and on the bottom of the grid.
     * ABC = Alphabet
     */
    private void printABC() {
        System.out.print(" ");
        for (int i = 65; i < 75; i++) {
            System.out.print(" " + (char) i);
        }
        System.out.print("\n");
    }

    /**
     * Prints the line +-+-+-+-+-, which is the top and bottom border of the grid.
     * PM = PlusMinus
     */
    private void printPM() {
        System.out.print(" ");
        for (int i = 0; i < 21; i++) {
            System.out.print(i % 2 == 0 ? '+' : '-');
        }
        System.out.print(" \n");
    }

    /**
     * Prints a line with only = symbols.
     * EQ = Equivalence symbols
     */
    private void printEQ() {
        for (int i = 0; i < 23; i++) {
            System.out.print('=');
        }
        System.out.print("\n");
    }

    /**
     * Prints the whole grid. Depending on the input parameter is_ocean_grid, either the Ocean Grid or
     * Target Grid of a Player will be printed
     */
    public void printGrid(Grid grid, boolean is_ocean_grid) {
        printABC();
        printPM();
        for (int i = 0; i < 10; i++) {
            System.out.print(i);
            for (int j = 0; j < 10; j++) {
                System.out.print("|");
                System.out.print(grid.type_field_to_print(i, j, is_ocean_grid));
            }
            System.out.print("|" + i + "\n");
        }
        printPM();

        printABC();

        printEQ();

    }
}

