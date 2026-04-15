package domain.board;

import domain.board.formation.AbstractFormationFactory;
import domain.board.formation.FormationType;
import domain.game.Team;
import domain.piece.Piece;
import domain.position.Position;
import java.util.HashMap;
import java.util.Map;

public class BoardFactory {

    public static Board create(FormationType choFormation, FormationType hanFormation) {
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.putAll(AbstractFormationFactory.from(choFormation)
                .createFormation(Team.CHO));
        pieces.putAll(AbstractFormationFactory.from(hanFormation)
                .createFormation(Team.HAN));
        return new Board(pieces);
    }
}
