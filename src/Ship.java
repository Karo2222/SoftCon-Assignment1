package src;

import java.util.ArrayList;

public class Ship {
    private final Grid grid;
    private boolean sunken;
    private final ShipType type;
    private final int length;
    private final ArrayList<Integer> parts;
    private boolean placed;

    public Ship(Grid grid, ShipType type) {
        this.grid = grid;
        this.sunken = false;
        this.type = type;
        this.length = type.getSize();
        this.parts = new ArrayList<>();
        this.placed = false;
    }

    /**
     * method places the ship according to given input parameters (start and end coordinates)
     */
    public void placeShip(int startx, int starty, int endx, int endy) {
        // The ship is horizontal
        if (startx == endx) {
            for (int i = starty; i <= endy; i++) {
                ArrayList<Integer> part = new ArrayList<>(2);
                part.add(startx);
                part.add(i);
                grid.placeShipOnGrid(startx, i);
                parts.add(formatToShip(part));
            }
        }
        // The ship is vertical
        else {
            for (int i = startx; i <= endx; i++) {
                ArrayList<Integer> part = new ArrayList<>(2);
                part.add(i);
                part.add(starty);
                grid.placeShipOnGrid(i, starty);
                parts.add(formatToShip(part));
            }
        }
        placed = true;
    }

    public int getLength() {
        return length;
    }

    public boolean getPlaced() {
        return placed;
    }

    public boolean isSunken() {
        return sunken;
    }

    public ShipType getType() {
        return type;
    }


    /**
     * method goes through all parts of a ship and checks if all parts have been hit
     */
    public void shotAt() {
        boolean temp_sunken = true;
        for (Integer part : parts) {
            if (!grid.is_hit(part)) {
                temp_sunken = false;
            }
        }
        if (temp_sunken) {
            this.sunken = true;
        }
    }

    /**
     * checks if a part of a ship was placed on coordinates x,y on the grid
     * @return true if the ArrayList that stores all parts of a ship contains coordinates x,y, else false
     */
    public boolean isPart(int x, int y) {
        ArrayList<Integer> list = new ArrayList<>(2);
        list.add(x);
        list.add(y);
        int xy = formatToShip(list);
        return parts.contains(xy);
    }

    /**
     * @return is an integer 0-99 that represents coordinates x,y on grid
     */
    private int formatToShip(ArrayList<Integer> list) {
        int sum = list.get(0) * 10;
        sum += list.get(1);
        return sum;
    }

}