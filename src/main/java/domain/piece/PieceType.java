package domain.piece;

import domain.activePiece.Cannon;
import domain.activePiece.Chariot;
import domain.activePiece.General;
import domain.activePiece.Guard;
import domain.activePiece.Soldier;
import java.util.List;
import java.util.function.Function;

public enum PieceType {
    CHA(13.0, List.of(1, 9), Chariot::new),
    MA(5.0, List.of(2, 3, 7, 8), null),
    SANG(3.0, List.of(2, 3, 7, 8), null),
    SA(3.0, List.of(4, 6), Guard::new),
    GENERAL(0.0, List.of(5), General::new),
    PHO(7.0, List.of(2, 8), Cannon::new),
    BYEONG(2.0, List.of(1, 3, 5, 7, 9), Soldier::new),
    EMPTY(0.0, List.of(), null);

    private final double score;
    private final List<Integer> initialColumns;
    private final Function<Team, Piece> pieceFactory;

    PieceType(double score, List<Integer> initialColumns, Function<Team, Piece> pieceFactory) {
        this.score = score;
        this.initialColumns = initialColumns;
        this.pieceFactory = pieceFactory;
    }

    public List<Integer> getInitialColumns() {
        return initialColumns;
    }

    public Piece createPiece(Team team) {
        return pieceFactory.apply(team);
    }
}
