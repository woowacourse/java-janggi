import domain.InitialGameState;
import domain.Piece;
import domain.Position;
import domain.TeamColor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import strategy.formation.InitialFormationStrategy;

public class Initializer {

    private InitialFormationStrategy choStrategy;
    private InitialFormationStrategy hanStrategy;

    public Initializer(InitialFormationStrategy choStrategy, InitialFormationStrategy hanStrategy) {
        this.choStrategy = choStrategy;
        this.hanStrategy = hanStrategy;
    }

    public InitialGameState initialize() {
        Map<Position, Piece> board = new HashMap<>();
        board.putAll(choStrategy.setUpPieces(TeamColor.CHO));
        board.putAll(hanStrategy.setUpPieces(TeamColor.HAN));

        List<Piece> choPieces = board.values().stream()
                .filter(piece -> piece.getTeamColor() == TeamColor.CHO)
                .toList();

        List<Piece> hanPieces = board.values().stream()
                .filter(piece -> piece.getTeamColor() == TeamColor.HAN)
                .toList();

        return new InitialGameState(board, choPieces, hanPieces);
    }
}
