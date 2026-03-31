package domain.piece;

import domain.game.Team;
import java.util.List;
import java.util.function.Function;

public enum PieceDefinition {
    CHA(List.of(1, 9), StraightMovePiece::chariot),
    MA(List.of(2, 3, 7, 8), Horse::new),
    SANG(List.of(2, 3, 7, 8), Elephant::new),
    SA(List.of(4, 6), PalacePiece::guard),
    GENERAL(List.of(5), PalacePiece::general),
    PHO(List.of(2, 8), StraightMovePiece::cannon),
    BYEONG(List.of(1, 3, 5, 7, 9), Soldier::new),
    EMPTY(List.of(), team -> new EmptyPiece());

    private final List<Integer> initialColumns;
    private final Function<Team, Piece> pieceFactory;

    PieceDefinition(List<Integer> initialColumns, Function<Team, Piece> pieceFactory) {
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
