package janggi.domain.turn;

import janggi.domain.PieceInfo;
import janggi.domain.Position;
import janggi.domain.ScoreStatus;
import janggi.domain.Side;

public interface PlayerTurn {
    PlayerTurn move(Position start, Position end);

    boolean isFinished();

    PieceInfo[][] getCurrentBoard();

    Side getCurrentSide();

    ScoreStatus getCurrentScoreStatus();
}