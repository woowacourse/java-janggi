package janggi.service;


import janggi.dao.BoardDao;
import janggi.domain.board.Position;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.TeamColor;
import java.util.Map;

public class JanggiDBService {
    private final BoardDao boardDao = new BoardDao();

    public void saveInitialBoard(Map<Position, Piece> board) {
        boardDao.saveBoard(board);
    }
    public void updateMoveResult(Position source, Position destination, PieceType pieceType, TeamColor teamColor) {
        boardDao.updatePiecePosition(source, destination, pieceType, teamColor);
    }
}
