package janggi.domain.turn;

import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.SideScore;
import janggi.domain.piece.PieceAttribute;
import java.util.List;

public interface PlayerTurn {
    TurnState move(Position start, Position end);

    boolean isFinished();

    List<List<PieceAttribute>> getCurrentBoard();

    Side getCurrentSide();

    Side getWinnerSide();

    SideScore getCurrentScore();
}
