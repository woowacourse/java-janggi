package player;

public class Player {
    private final Pieces pieces;
    private final Nation nation;

    public Player(Pieces pieces, Nation nation) {
        this.pieces = pieces;
        this.nation = nation;
    }

    public boolean isKingDie() {
        return pieces.isKingDie();
    }
}
