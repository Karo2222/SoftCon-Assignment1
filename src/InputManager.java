package src;

import java.util.*;

public class InputManager {
    private static final InputManager INSTANCE = new InputManager();

    private InputManager() {}

    public static InputManager getInstance() {
        return INSTANCE;
    }

    private final OutputManager output = OutputManager.getInstance();

    /**
     * checks if the shot of the user is valid
     * @return true if it is valid, else false
     */
    public static boolean checkValidInput(String input) {

        if (input.length() != 2) return false;

        int first = (int) input.charAt(0) % 65;

        String number = input.substring(1, 2);
        if (!number.matches("\\d")) return false;

        return first < 10;
    }

    /**
     * handles the input of the user, checks if a valid input has been made and shoots if shot was valid
     */
    public void getNextMove(Grid grid) {

        System.out.println("Please enter a field for the next shot:");

        // Input of the User
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            if (!checkValidInput(input)) {
                System.out.println("Please enter a valid field. Example: B1");
                continue;
            }
            int first = (int) input.charAt(0) % 65; //converts the char in an int
            int second = Integer.parseInt(input.substring(1, 2)); //converts the number in an int
            //"second" represents row, "first" represents column
            GridType hit = grid.shoot(second, first);
            if (hit == GridType.HIT_SHIP || hit == GridType.MISS) {
                System.out.println("Position already shot, please enter another field.");
                continue;
            } else {
                System.out.println("You shot at " + input + ", " + (hit == GridType.SHIP ? "hit!" : "water."));
            }
            break;
        }
    }

    private void printPositionText(Map.Entry<String, Integer> entry) {
        System.out.println("Please enter the position of your " + entry.getKey() + " of length " + entry.getValue() + ": ");
    }

    /**
     * handles the actual placement of the ships of the user, checks if input is valid and places ship
     * by calling the method valid_placement if ship can be placed on input position
     */
    public void init(Grid grid) {
        print_starting_string(grid);
        Map<String, Integer> ship_hash = create_Hash(grid);
        Scanner scanner = new Scanner(System.in);
        Iterator<Map.Entry<String, Integer>> iter = ship_hash.entrySet().iterator();
        Map.Entry<String, Integer> entry = iter.next();
        printPositionText(entry);
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            String[] input_of_user = input.split(",");
            if (input_of_user.length != 2) {
                System.out.println("Please enter a valid position (Example: B2,B4).");
                continue;
            }
            if (!checkValidInput(input_of_user[0]) || !checkValidInput(input_of_user[1])) {
                System.out.println("Please enter a valid input (Example: B2,B4).");
                continue;
            }

            int first_y = (int) input_of_user[0].charAt(0) % 65;
            int first_x = Integer.parseInt(input_of_user[0].substring(1, 2));
            int second_y = (int) input_of_user[1].charAt(0) % 65;
            int second_x = Integer.parseInt(input_of_user[1].substring(1, 2));

            if (!grid.valid_placement(first_x, first_y, second_x, second_y, entry.getValue())) {
                System.out.println("Please enter a valid position.");
                continue;
            }
            output.printGrid(grid, true);
            if (!iter.hasNext()) {
                break;
            }

            entry = iter.next();
            printPositionText(entry);
        }
        System.out.println("Finished placing all ships for Player");
    }

    private void print_starting_string(Grid grid) {
        System.out.println("There are "+grid.number_of_ships()+" ships in total that you have to place. The length varies from ship to ship.");
        System.out.println("Both you and your opponent have one ship with length 6, two ships with length 4, three ships with length 3 and four ships with length 2.");
        System.out.println("After placing your ships, either ComputerPlayer or you will start shooting at the opponent's grid.");
        System.out.println("You can shoot by simply entering a field on the grid (Example: H5).");
        System.out.println("First, please enter two fields separated by a comma indicating the start & end of a ship to place it (Example: B2,B4). \n");
    }

    private Map<String, Integer> create_Hash(Grid grid) {
        Map<String, Integer> hashMap = new LinkedHashMap<>();
        ShipType previousShipType = ShipType.CARRIER;
        int shipTypeCounter = 0;
        for (ShipType shipType : grid.getShips()) {
            if (previousShipType != shipType) {
                shipTypeCounter = 1;
                previousShipType = shipType;
            } else {
                shipTypeCounter++;
            }
            hashMap.put(""+shipType+" "+shipTypeCounter, shipType.getSize());
        }
        return hashMap;
    }
}
