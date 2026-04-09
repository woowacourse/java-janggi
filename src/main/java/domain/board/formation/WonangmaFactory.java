package domain.board.formation;

import domain.position.Position;
import domain.piece.Elephant;
import domain.piece.Horse;
import domain.piece.Piece;
import domain.game.Team;
import java.util.Map;

public class WonangmaFactory extends AbstractFormationFactory {
    @Override
    protected void setVariablePieces(Map<Position, Piece> pieces, Team team) {
        int backRow = TeamLayout.of(team).backRow();
        pieces.put(new Position(backRow, 2), new Elephant(team));
        pieces.put(new Position(backRow, 3), new Horse(team));
        pieces.put(new Position(backRow, 7), new Horse(team));
        pieces.put(new Position(backRow, 8), new Elephant(team));
    }
}
