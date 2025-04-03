package janggi.domain;

import janggi.db.PieceDao;
import janggi.db.TurnDao;
import janggi.domain.piece.Piece;

public class BoardService {

    private final PieceDao pieceDao;
    private final TurnDao turnDao;

    public BoardService(final PieceDao pieceDao, final TurnDao turnDao) {
        this.pieceDao = pieceDao;
        this.turnDao = turnDao;
    }

    public Board readBoardFromDatabase() {
        Team currentTurn = turnDao.readTeam();
        Pieces currentPieces = new Pieces(pieceDao.readAllPiece());
        return new Board(currentPieces, currentTurn);
    }

    public void updateDatabase(final Board board) {
        pieceDao.deleteAllPiece();
        for (Piece piece : board.getPieces()) {
            pieceDao.addPiece(piece);
        }
        turnDao.updateTeam(board.getTurn());
    }
}
