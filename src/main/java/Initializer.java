import java.util.HashMap;
import java.util.Map;

import domain.Board;
import domain.Piece;
import domain.Position;
import domain.TeamColor;
import strategy.formation.InitialFormationStrategy;

public class Initializer {

    private InitialFormationStrategy choStrategy;
    private InitialFormationStrategy hanStrategy;

    public Initializer(InitialFormationStrategy choStrategy, InitialFormationStrategy hanStrategy) {
        this.choStrategy = choStrategy;
        this.hanStrategy = hanStrategy;
    }

    public Board initialize() {
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.putAll(choStrategy.setUpPieces(TeamColor.CHO));
        pieces.putAll(hanStrategy.setUpPieces(TeamColor.HAN));
        return new Board(pieces);
    }
}
