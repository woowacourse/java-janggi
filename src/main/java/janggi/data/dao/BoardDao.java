package janggi.data.dao;

import janggi.board.point.Point;
import janggi.piece.Camp;
import janggi.piece.Piece;
import java.time.LocalDateTime;
import java.util.Map;

public interface BoardDao {

    void create();

    void endGame();

    boolean existsActiveGame();

    int findCurrentBoardId();

    LocalDateTime findCurrentBoardCreatedAt();

    Camp findCurrentTurnCamp();

    void turnChange();

    Map<Point, Piece> findCurrentBoardPieces();
}
