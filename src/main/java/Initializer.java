import domain.Board;
import domain.Piece;
import domain.Position;
import domain.TeamColor;
import java.util.HashMap;
import java.util.Map;
import strategy.formation.InitialFormationStrategy;

public class Initializer {

    private final InitialFormationStrategy choStrategy;
    private final InitialFormationStrategy hanStrategy;

    public Initializer(InitialFormationStrategy choStrategy, InitialFormationStrategy hanStrategy) {
        this.choStrategy = choStrategy;
        this.hanStrategy = hanStrategy;
    }

    public Board initialize() {
        Map<Position, Piece> board = new HashMap<>();
        board.putAll(choStrategy.setUpPieces(TeamColor.CHO));
        board.putAll(hanStrategy.setUpPieces(TeamColor.HAN));
        return new Board(board);
    }
}
