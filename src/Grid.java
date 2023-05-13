package src;

import java.util.ArrayList;
import java.util.Arrays;

public class Grid {
    private final GridType[][] grid;
    private final ArrayList<Ship> ship_list;
    private final ShipType[] ships = {  ShipType.CARRIER
            , ShipType.BATTLESHIP, ShipType.BATTLESHIP
            , ShipType.SUBMARINE, ShipType.SUBMARINE, ShipType.SUBMARINE
            , ShipType.PATROL_BOAT, ShipType.PATROL_BOAT, ShipType.PATROL_BOAT, ShipType.PATROL_BOAT};

    public Grid() {
        this.grid = new GridType[10][10];
        for (GridType[] row : grid) {
            Arrays.fill(row, GridType.WATER);
        }
        this.ship_list = new ArrayList<>();

        for (ShipType t : ships) {
            ship_list.add(new Ship(this, t));
        }
    }
    public int number_of_ships() {
        return ships.length;
    }

    public ArrayList<ShipType> getShips() {
        return new ArrayList<>(Arrays.asList(ships));
    }

    /**
     * checks if input coordinates are valid and places ship if they are indeed valid
     * @return true if coordinates are valid, else return false.
     */
    public boolean valid_placement(int first_x, int first_y, int second_x, int second_y, int supposed_length) {
        int length;
        if (isOutside(first_x) || isOutside(first_y) || isOutside(second_x) || isOutside(second_y)) {
            return false;
        }
        // returns false if user wants to place ship on !row or !column
        if ((first_x != second_x) && (first_y != second_y)) {
            return false;
        }
        //sort coordinates (xstart <= xend; ystart <= yend) to be able to iterate over the fields correctly.
        //this only works because the if statement above already checked if either x or y coordinates
        // of start and end block are the same.
        int ystart = Math.min(first_y, second_y);
        int yend = Math.max(first_y, second_y);
        int xstart = Math.min(first_x, second_x);
        int xend = Math.max(first_x, second_x);

        if (xstart == xend) {
            for (int i = ystart; i <= yend; i++) {
                if (!field_free(xstart, i)) {
                    return false;
                }
            }
            length = yend - ystart + 1;
        } else {
            for (int i = xstart; i <= xend; i++) {
                if (!field_free(i, ystart)) {
                    return false;
                }
            }
            length = xend - xstart + 1;
        }

        //returns false if the length of the ship the user wants to place doesn't match with the required length
        if (supposed_length != length) {
            return false;
        }
        for (Ship s : ship_list) {
            if (length == s.getLength()) {
                if (!s.getPlaced()) {
                    s.placeShip(xstart, ystart, xend, yend);
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * @return is false if SHIP on coordinates (x,y) on grid, else true (WATER)
     */
    private boolean field_free(int x, int y) {
        return grid[x][y] == GridType.WATER;
    }

    /**
     * method converts the GridType into a char
     * @return is the corresponding char to a GridType which will be printed
     */
    public char type_field_to_print(int x, int y, boolean is_ocean_grid) {
        GridType type = grid[x][y];
        if (is_ocean_grid) {
            if (type == GridType.SHIP) {
                for (Ship s : ship_list) {
                    if (s.isPart(x, y)) {
                        return s.getType().toString().charAt(0);
                    }
                }
            }
            if (type == GridType.HIT_SHIP) {
                return 'X';
            }
            //if type == GridType.WATER
        } else {
            if (type == GridType.HIT_SHIP) {
                for (Ship s : ship_list) {
                    if (s.isPart(x, y)) {
                        if (s.isSunken()) {
                            return s.getType().toString().charAt(0);
                        } else {
                            return 'X';
                        }
                    }
                }
            }
        }
        if (type == GridType.MISS) {
            return 'o';
        }
        //if type == GridType.SHIP (when ocean_grid == false) OR type == GridType.WATER
        return ' ';
    }

    /**
     * method to actually shoot at the Grid
     * @return the type of the grid which the player shot at (not the new value)
     */
    public GridType shoot(int x, int y) {
        GridType shot = grid[x][y];
        if (shot == GridType.HIT_SHIP || shot == GridType.MISS) {
            return shot;
        }
        if (shot == GridType.SHIP) {
            grid[x][y] = GridType.HIT_SHIP;
            for (Ship s : ship_list) {
                if (s.isPart(x, y)) {
                    s.shotAt();
                    break;
                }
            }
            return shot;
        }

        // unnecessary if-statement, but makes intentions clear
        if (shot == GridType.WATER) {
            grid[x][y] = GridType.MISS;
        }
        return shot;
    }

    /**
     * method to format an integer (0-99) into grid coordinates (0<=x<=9,0<=y<=9)
     * @return is an ArrayList containing two coordinates (ints) x,y
     */
    public ArrayList<Integer> formatToGrid(int coordinate) {
        ArrayList<Integer> arr = new ArrayList<>(2);
        // to calculate the row in the grid
        arr.add(coordinate / 10);
        // to calculate the column in the grid
        arr.add(coordinate % 10);
        return arr;
    }

    /**
     * method to see if a ship has been hit on this coordinate (used by Ship class)
     * @return true if a ship was hit at position x,y on grid, else false
     */
    public boolean is_hit(int xy) {
        ArrayList<Integer> grid_xy = formatToGrid(xy);
        return grid[grid_xy.get(0)][grid_xy.get(1)] == GridType.HIT_SHIP;
    }

    public void placeShipOnGrid(int x, int y) {
        grid[x][y] = GridType.SHIP;
    }

    /**
     * checks if all the ships have been shot
     * @return true if all the ships sank
     */
    public boolean checkEnd() {
        for (Ship s : ship_list) {
            if (!s.isSunken()) {
                return false;
            }
        }
        return true;
    }

    private boolean isOutside(int value) {
        return ((value < 0) || (value > 9));
    }

}