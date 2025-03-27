package janggi.factory;

import janggi.domain.Team;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.behavior.Soldier;
import janggi.domain.piece.behavior.palace.General;
import janggi.domain.piece.behavior.palace.Guard;
import janggi.domain.piece.behavior.rotatemove.Elephant;
import janggi.domain.piece.behavior.rotatemove.Horse;
import janggi.domain.piece.behavior.straightmove.Cannon;
import janggi.domain.piece.behavior.straightmove.Chariot;
import java.util.Arrays;
import java.util.function.Function;

public enum PieceFactory {
    GENERAL(PieceType.GENERAL, side -> new Piece(side, new General())),
    GUARD(PieceType.GUARD, side -> new Piece(side, new Guard())),
    ELEPHANT(PieceType.ELEPHANT, side -> new Piece(side, new Elephant())),
    HORSE(PieceType.HORSE, side -> new Piece(side, new Horse())),
    CANNON(PieceType.CANNON, side -> new Piece(side, new Cannon())),
    CHARIOT(PieceType.CHARIOT, side -> new Piece(side, new Chariot())),
    SOLDIER(PieceType.SOLDIER, side -> new Piece(side, new Soldier())),
    ;

    private final PieceType pieceType;
    private final Function<Team, Piece> generate;

    PieceFactory(PieceType pieceType, Function<Team, Piece> generate) {
        this.pieceType = pieceType;
        this.generate = generate;
    }

    public static Piece create(PieceType pieceType, Team team) {
        return Arrays.stream(values())
                .filter(value -> value.pieceType.equals(pieceType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 기물명입니다."))
                .generate.apply(team);
    }
}
