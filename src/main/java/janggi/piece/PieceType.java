package janggi.piece;

import janggi.piece.pieces.Cannon;
import janggi.piece.pieces.Chariot;
import janggi.piece.pieces.Elephant;
import janggi.piece.pieces.General;
import janggi.piece.pieces.Horse;
import janggi.piece.pieces.None;
import janggi.piece.pieces.Piece;
import janggi.piece.pieces.Scholar;
import janggi.piece.pieces.Soldier;
import java.util.Arrays;
import java.util.function.Function;

public enum PieceType {
    CHARIOT(13, "C", Chariot::new),
    CANNON(7, "B", Cannon::new),
    HORSE(5, "H", Horse::new),
    ELEPHANT(3, "E", Elephant::new),
    SCHOLAR(3, "S", Scholar::new),
    SOLDIER(2, "J", Soldier::new),
    GENERAL(0, "K", General::new),
    NONE(0, "N", None::new),
    ;

    private final int score;
    private final String name;
    private final Function<Team, Piece> rule;

    PieceType(int score, String name, Function<Team, Piece> rule) {
        this.score = score;
        this.name = name;
        this.rule = rule;
    }

    public static PieceType fromString(String pieceType) {
        return Arrays.stream(values())
                .filter(type -> type.name.equalsIgnoreCase(pieceType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("기물 타입이 존재하지 않습니다."));
    }

    public Piece createPiece(Team team) {
        return this.rule.apply(team);
    }

    public double getScore() {
        return this.score;
    }

    public String getName() {
        return name;
    }
}
