package domain.piece;

import domain.TeamType;
import java.util.function.Function;

public enum PieceType {

    CANNON("C", 7, Cannon::new),
    CHARIOT("R", 13, Chariot::new),
    ELEPHANT("E", 3, Elephant::new),
    GUARD("G", 3, Guard::new),
    HORSE("H", 5, Horse::new),
    KING("K", 0, King::new),
    SOLDIER("S", 2, Soldier::new);

    private final String description;
    private final int score;
    private final Function<TeamType, Piece> createByTeam;

    PieceType(String description, int score, Function<TeamType, Piece> createByTeam) {
        this.description = description;
        this.score = score;
        this.createByTeam = createByTeam;
    }

    public String getDescription() {
        return description;
    }

    public int getScore() {
        return score;
    }

    public Piece createPiece(TeamType team) {
        return this.createByTeam.apply(team);
    }
}
