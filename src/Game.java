package src;

import java.util.Random;

public class Game {
    private final Player hP;
    private final Player cP;

    public Game() {
        print_welcome();
        InputManager input = InputManager.getInstance();
        OutputManager output = OutputManager.getInstance();
        hP = new HumanPlayer(input, output);
        cP = new ComputerPlayer(input, output);
    }

    private void print_welcome() {
        System.out.println("Welcome to the Terminal Based Battleship Game!");
        System.out.println("The following grid named Ocean Grid is the one where you'll place your ships on.");
        System.out.println("The other grid named Target Grid is your opponent's grid on which you'll see the hits and misses you've made.");
    }

    /**
     * checks if all ships of either HumanPlayer or ComputerPlayer sank
     * @return true if game ended, else false.
     */
    public boolean checkEnd() {
        boolean hP_all_sunken = hP.checkEnd();
        boolean cP_all_sunken = cP.checkEnd();
        return hP_all_sunken || cP_all_sunken;
    }


    public void start() {
        System.out.println("Starting BattleShip Game!");
        Random rand = new Random();
        //randomize which player starts
        Player shootingPlayer = rand.nextBoolean() ? hP : cP;
        Player receivingPlayer = (shootingPlayer == cP) ? hP : cP;
        do {
            System.out.println(shootingPlayer.getClass().getSimpleName() + "'s turn:");
            receivingPlayer.turn();
            //printing the target grid (computer grid)
            cP.print_grid(false);
            //printing the ocean grid (human grid)
            hP.print_grid(true);

            // change turns by exchanging shooting and receiving role
            shootingPlayer = (shootingPlayer == cP) ? hP : cP;
            receivingPlayer = (shootingPlayer == cP) ? hP : cP;
        } while (!checkEnd());
        Player winner = receivingPlayer;
        Player loser = shootingPlayer;
        if (winner == cP) {
            //if ComputerPlayer wins, print updated Target Grid and show remaining ships as CHARS
            System.out.println("=== Remaining Ships ===");
            cP.print_grid(true);
            System.out.println("You've LOST! Try again next time.");
        } else {
            //if HumanPlayer wins, print both grids again
            cP.print_grid(false);
            hP.print_grid(true);
            System.out.println("You've WON! Congratulations!");
        }
        System.out.println(loser.getClass().getSimpleName() + " has lost.");
    }
}