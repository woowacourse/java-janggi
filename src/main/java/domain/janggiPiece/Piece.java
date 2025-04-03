package domain.janggiPiece;

import domain.score.Score;
import domain.type.JanggiTeam;

import java.util.Arrays;
import java.util.function.Function;

public enum Piece {

    CHARIOT("CHARIOT", new Score(13), Chariot::new),
    HORSE("HORSE", new Score(5), Horse::new),
    ELEPHANT("ELEPHANT", new Score(3), Elephant::new),
    CANNON("CANNON", new Score(7), Cannon::new),
    GUARD("GUARD", new Score(3), Guard::new),
    PAWN("PAWN", new Score(2), Pawn::new),
    KING("KING", new Score(0), King::new);

    public final String name;
    public final Score score;
    private final Function<JanggiTeam, JanggiChessPiece> create;

    Piece(String name, Score score, Function<JanggiTeam, JanggiChessPiece> create) {
        this.name = name;
        this.score = score;
        this.create = create;
    }

    public static Piece from(String name) {
        return Arrays.stream(values())
                .filter(v -> v.name.equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 기물입니다."));
    }

    public JanggiChessPiece create(JanggiTeam team) {
        return create.apply(team);
    }
}
