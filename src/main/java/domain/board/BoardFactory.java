package domain.board;

import domain.position.Position;
import domain.piece.Piece;
import domain.game.Team;
import java.util.Map;

public interface BoardFactory {
    Map<Position, Piece> createFormation(Team team);
}
