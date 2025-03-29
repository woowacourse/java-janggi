package janggi.domain.piece;

import janggi.domain.Team;
import java.util.Arrays;
import java.util.function.Function;

public enum PieceFactory {
    CHARIOT(PieceType.CHARIOT, Chariot::new),
    CANNON(PieceType.CANNON, Cannon::new),
    HORSE(PieceType.HORSE, Horse::new),
    ELEPHANT(PieceType.ELEPHANT, Elephant::new),
    GUARD(PieceType.GUARD, Guard::new),
    SOLDIER(PieceType.SOLDIER, Soldier::new),
    GENERAL(PieceType.GENERAL, General::new),
    ;

    private final PieceType pieceType;
    private final Function<Team, Piece> initFunction;

    PieceFactory(final PieceType pieceType, final Function<Team, Piece> initFunction) {
        this.pieceType = pieceType;
        this.initFunction = initFunction;
    }

    public static Piece create(PieceType pieceType, Team team) {
        return Arrays.stream(PieceFactory.values())
                .filter(pieceFactory -> pieceFactory.pieceType == pieceType)
                .map(pieceFactory -> pieceFactory.initFunction.apply(team))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 pieceType입니다: " + pieceType));
    }
}
