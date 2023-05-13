package src;

import java.util.ArrayList;
import java.util.Random;

public class HumanPlayer implements Player {

    private final Grid grid;
    private final InputManager input;

    private final OutputManager output;

    private final ArrayList<Integer> possible_shots;

    private final Random rand = new Random();

    public HumanPlayer(InputManager input, OutputManager output) {
        grid = new Grid();
        this.input = input;
        this.output = output;
        possible_shots = new ArrayList<>(100);
        for (int i = 0; i < 100; i++) {
            possible_shots.add(i);
        }
        createGrid();
    }

    /**
     * prints an empty grid named Ocean and Target Grid first before the user starts placing ships in input.init(grid)
     */
    public void createGrid() {
        System.out.println("===== TARGET GRID =====");
        output.printGrid(grid,false);
        System.out.println("===== OCEAN  GRID =====");
        output.printGrid(grid, false);
        input.init(grid);
    }

    /**
     * when it's cP's turn, this method shoots randomly at the grid of hP
     */
    public void turn() {
        int random_index = rand.nextInt(possible_shots.size());
        ArrayList<Integer> coordinates = grid.formatToGrid(possible_shots.remove(random_index));
        int x = coordinates.get(0);
        int y = coordinates.get(1);
        GridType hit = grid.shoot(x, y);
        System.out.println("Computer shot at " + (char) (y%9 + 65) + x + ", " + (hit == GridType.SHIP ? "hit!" : "water."));
    }

    /**
     * prints the Ocean Grid
     */
    public void print_grid(boolean is_ocean_grid) {
        System.out.println("===== OCEAN  GRID =====");
        output.printGrid(grid, is_ocean_grid);
    }

    public boolean checkEnd() {
        return grid.checkEnd();
    }
}