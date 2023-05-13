package src;

public enum ShipType {
      CARRIER(6, "Carrier")
    , BATTLESHIP(4, "Battleship")
    , SUBMARINE(3, "Submarine")
    , PATROL_BOAT(2, "Patrol boat")
    ;
    private final int size;
    private final String typeAsString;

    ShipType(int size, String typeAsString) {
        this.size = size;
        this.typeAsString = typeAsString;
    }

    public int getSize(){
        return size;
    }

    public String getTypeAsString(){
        return typeAsString;
    }

    public String toString() {
        return getTypeAsString();
    }
}
