package src;

public interface Player {
    void turn();

    void createGrid();

    void print_grid(boolean is_ocean_grid);

    boolean checkEnd();
}