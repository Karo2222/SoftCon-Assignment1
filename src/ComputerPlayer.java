package src;

import java.util.ArrayList;
import java.util.Random;

public class ComputerPlayer implements Player {

    private final Grid grid;
    private final InputManager input;
    private final OutputManager output;
    private final ArrayList<Integer> open_fields;
    private final Random rand = new Random();


    public ComputerPlayer(InputManager input, OutputManager output) {
        grid = new Grid();
        this.input = input;
        this.output = output;
        open_fields = new ArrayList<>(100);
        for (int i = 0; i < 100; i++) {
            open_fields.add(i);
        }
        createGrid();
    }

    /**
     * selects a starting field randomly for a ship then
     * tries to randomly place it either horizontally or vertically
     */
    public void createGrid() {
        ArrayList<ShipType> ships = grid.getShips();
        int endField;
        ArrayList<Integer> firstPair;
        ArrayList<Integer> secondPair;
        for (ShipType ship : ships) {
            boolean is_valid = false;
            while (!is_valid) {
                int startField = open_fields.get(rand.nextInt(open_fields.size()));
                firstPair = grid.formatToGrid(startField);
                //horizontal (if true is chosen try placing ship left or right)
                if (rand.nextBoolean()) {
                    // try right if true is chosen
                    if (rand.nextBoolean()) {
                        endField = startField + (ship.getSize() - 1);
                    }
                    // try left
                    else {
                        endField = startField - (ship.getSize() - 1);
                    }
                    secondPair = grid.formatToGrid(endField);
                    if (grid.valid_placement(firstPair.get(0), firstPair.get(1), secondPair.get(0), secondPair.get(1), ship.getSize())) {
                        removeFromOpen(startField, endField, true);
                        is_valid = true;
                    }
                }
                //vertical (try placing ship up or down)
                else {
                    if (rand.nextBoolean()) {
                        endField = startField + (ship.getSize() - 1) * 10;
                    } else {
                        endField = startField - (ship.getSize() - 1) * 10;
                    }
                    secondPair = grid.formatToGrid(endField);
                    if (grid.valid_placement(firstPair.get(0), firstPair.get(1), secondPair.get(0), secondPair.get(1), ship.getSize())) {
                        removeFromOpen(startField, endField, false);
                        is_valid = true;
                    }
                }
            }
        }
        System.out.println("Finished placing all ships for the Computer");
    }

    /**
     * when it's hP's turn, this method shoots at the grid of cP by calling the method getNextMove() which manages
     * the user input
     */
    public void turn() {
        input.getNextMove(grid);
    }

    public boolean checkEnd() {
        return grid.checkEnd();
    }

    /**
     * prints the Target Grid
     */
    public void print_grid(boolean is_ocean_grid) {
        System.out.println("===== TARGET GRID =====");
        output.printGrid(grid, is_ocean_grid);
    }

    /**
     * removes fields on which a ship was just placed from OPEN-FIELDS (OPEN-FIELDS stores
     * all the fields on which no ship has been placed yet)
     */
    private void removeFromOpen(int start, int end, boolean horizontal) {
        int startlow = Math.min(start, end);
        int endhigh = Math.max(start, end);

        if (horizontal) {
            while (startlow != endhigh) {
                open_fields.remove((Integer) startlow);
                startlow += 1;
            }
        } else {
            while (startlow != endhigh) {
                open_fields.remove((Integer) startlow);
                startlow += 10;
            }
        }
        open_fields.remove((Integer) startlow);
    }

}