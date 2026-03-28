package domain.board;

import domain.Position;
import domain.piece.Piece;
import domain.piece.Team;
import java.util.Map;

public interface BoardFactory {
    Map<Position, Piece> createFormation(Team team);
}
