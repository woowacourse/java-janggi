package model.piece;

import model.game.Team;

import java.util.function.Function;
import java.util.stream.Stream;

public enum PieceType {

    CANNON(7, Cannon::new),
    CHARIOT(13, Chariot::new),
    ELEPHANT(3, Elephant::new),
    GENERAL(0, General::new),
    GUARD(2, Guard::new),
    HORSE(5, Horse::new),
    SOLDIER(3, Soldier::new);

    private final double score;
    private final Function<Team, Piece> pieceFactory;

    PieceType(double score, Function<Team, Piece> pieceFactory) {
        this.score = score;
        this.pieceFactory = pieceFactory;
    }

    public static PieceType fromName(String name) {
        return Stream.of(values())
                .filter(pieceType -> pieceType.name().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 기물입니다."));
    }

    public double getScore() {
        return score;
    }

    public Piece createPiece(Team team) {
        return pieceFactory.apply(team);
    }
}
