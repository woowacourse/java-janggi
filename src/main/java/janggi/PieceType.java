package janggi;

import janggi.piece.Canon;
import janggi.piece.Chariot;
import janggi.piece.Elephant;
import janggi.piece.General;
import janggi.piece.Guard;
import janggi.piece.Horse;
import janggi.piece.Piece;
import janggi.piece.Soldier;
import java.util.function.Function;
import java.util.function.Predicate;

public enum PieceType {
    GENERAL(Piece::isGeneral, General::new),
    GUARD(Piece::isGuard, Guard::new),
    HORSE(Piece::isHorse, Horse::new),
    ELEPHANT(Piece::isElephant, Elephant::new),
    CANON(Piece::isCanon, Canon::new),
    CHARIOT(Piece::isChariot, Chariot::new),
    SOLDIER(Piece::isSoldier, Soldier::new);

    private final Predicate<Piece> typeCondition;
    private final Function<Team, Piece> pieceCreator;

    PieceType(Predicate<Piece> typeCondition, Function<Team, Piece> pieceCreator) {
        this.typeCondition = typeCondition;
        this.pieceCreator = pieceCreator;
    }

    public static PieceType from(Piece piece) {
        for (PieceType pieceType : values()) {
            Predicate<Piece> condition = pieceType.typeCondition;
            if (condition.test(piece)) {
                return pieceType;
            }
        }
        throw new IllegalArgumentException("[ERROR] 존재하지 않는 타입의 기물입니다.");
    }

    public Piece toPiece(Team team) {
        return pieceCreator.apply(team);
    }
}
