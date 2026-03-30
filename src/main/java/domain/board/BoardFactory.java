package domain.board;

import domain.game.Team;
import domain.piece.Piece;
import domain.position.Position;
import java.util.Map;

public interface BoardFactory {
    Map<Position, Piece> createFormation(Team team);
}
