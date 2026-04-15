package domain.piece;

import domain.game.Score;
import domain.game.Team;
import java.util.List;
import java.util.function.Function;

public enum PieceDefinition {
    CHA(new Score(13.0), List.of(1, 9), Chariot::new),
    MA(new Score(5.0), List.of(2, 3, 7, 8), Horse::new),
    SANG(new Score(3.0), List.of(2, 3, 7, 8), Elephant::new),
    SA(new Score(3.0), List.of(4, 6), Guard::new),
    GENERAL(new Score(0.0), List.of(5), General::new),
    PHO(new Score(7.0), List.of(2, 8), Cannon::new),
    BYEONG(new Score(2.0), List.of(1, 3, 5, 7, 9), Soldier::new);

    final Score score;
    private final List<Integer> initialColumns;
    private final Function<Team, Piece> pieceFactory;

    PieceDefinition(Score score, List<Integer> initialColumns, Function<Team, Piece> pieceFactory) {
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

    public Score getScore() {
        return score;
    }
}
