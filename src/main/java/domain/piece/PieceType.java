package domain.piece;

import domain.activePiece.Cannon;
import domain.activePiece.Chariot;
import domain.activePiece.Elephant;
import domain.activePiece.General;
import domain.activePiece.Guard;
import domain.activePiece.Horse;
import domain.activePiece.Soldier;
import java.util.List;
import java.util.function.Function;

public enum PieceType {
    CHA(13.0, "차", List.of(1, 9), Chariot::new),
    MA(5.0, "마", List.of(2, 3, 7, 8), Horse::new),
    SANG(3.0, "상", List.of(2, 3, 7, 8), Elephant::new),
    SA(3.0, "사", List.of(4, 6), Guard::new),
    GENERAL(0.0, "궁", List.of(5), General::new),
    PHO(7.0, "포", List.of(2, 8), Cannon::new),
    BYEONG(2.0, "병", List.of(1, 3, 5, 7, 9), Soldier::new),
    EMPTY(0.0, "ㅡ", List.of(), team -> new EmptyPiece());

    private final double score;
    private final String displayName;
    private final List<Integer> initialColumns;
    private final Function<Team, Piece> pieceFactory;

    PieceType(double score, String displayName, List<Integer> initialColumns, Function<Team, Piece> pieceFactory) {
        this.score = score;
        this.displayName = displayName;
        this.initialColumns = initialColumns;
        this.pieceFactory = pieceFactory;
    }

    public String getDisplayName() {
        return displayName;
    }

    public List<Integer> getInitialColumns() {
        return initialColumns;
    }

    public Piece createPiece(Team team) {
        return pieceFactory.apply(team);
    }
}
