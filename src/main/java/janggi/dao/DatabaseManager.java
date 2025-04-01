package janggi.dao;

import janggi.board.Board;
import janggi.board.Pieces;
import janggi.piece.Team;
import janggi.piece.pieces.Piece;
import janggi.position.Position;
import java.util.Map;
import java.util.Map.Entry;

public class DatabaseManager {
    private final BoardDao boardDao;
    private final PieceDao pieceDao;

    public DatabaseManager(BoardDao boardDao, PieceDao pieceDao) {
        this.boardDao = boardDao;
        this.pieceDao = pieceDao;
    }

    public Board loadOrCreateBoard() {
        if (boardDao.countBoard() >= 1) {
            Map<Position, Piece> pieces = pieceDao.findAll();
            return boardDao.find(new Pieces(pieces));
        }
        return new Board(new Pieces(), Team.CHO);
    }

    public void saveGame(Board board) {
        deleteAll();
        String boardId = boardDao.addBoard(board);
        for (Entry<Position, Piece> entry : board.getPieces().entrySet()) {
            pieceDao.addPiece(entry.getValue(), entry.getKey().getColumn(), entry.getKey().getRow(), boardId);
        }
    }

    public void deleteAll() {
        pieceDao.deleteAllPieces();
        boardDao.deleteAllBoards();
    }
}
