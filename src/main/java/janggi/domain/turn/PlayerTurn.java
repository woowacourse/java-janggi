package janggi.domain.turn;

import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.piece.PieceInfo;
import java.util.List;

public interface PlayerTurn {
    PlayerTurn move(Position start, Position end);

    boolean isFinished();

    List<List<PieceInfo>> getCurrentBoard();

    Side getCurrentSide();

    Side getWinnerSide();
}
