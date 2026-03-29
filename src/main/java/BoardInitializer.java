import domain.board.Board;
import domain.piece.Piece;
import domain.board.Position;
import domain.piece.TeamColor;
import java.util.HashMap;
import java.util.Map;
import strategy.formation.InitialFormationStrategy;

public class BoardInitializer {

    private final InitialFormationStrategy choStrategy;
    private final InitialFormationStrategy hanStrategy;

    public BoardInitializer(InitialFormationStrategy choStrategy, InitialFormationStrategy hanStrategy) {
        this.choStrategy = choStrategy;
        this.hanStrategy = hanStrategy;
    }

    public Board initialize() {
        final Map<Position, Piece> board = new HashMap<>();
        board.putAll(choStrategy.createInitialPieces(TeamColor.CHO));
        board.putAll(hanStrategy.createInitialPieces(TeamColor.HAN));
        return new Board(board);
    }
}


