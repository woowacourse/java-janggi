package board;

import dao.PieceDao;
import dao.TurnDao;
import piece.Pieces;
import team.Team;

public class GameBoard {
    private final PieceDao pieceDao;
    private final TurnDao turnDao;

    public GameBoard(PieceDao pieceDao, TurnDao turnDao) {
        this.pieceDao = pieceDao;
        this.turnDao = turnDao;
    }

    public boolean isGameExist() {
        return pieceDao.existPiece();
    }

    public Pieces loadPieces() {
        return new Pieces(pieceDao.findAllPieces());
    }

    public Pieces startNewGame(Team turn) {
        PiecesInitializer piecesInitializer = new PiecesInitializer();
        Pieces pieces = new Pieces(piecesInitializer.makeAllPieces());

        pieceDao.addAllPieces(pieces);
        turnDao.addTurn(turn);

        return pieces;
    }

    public void saveGame(Pieces pieces, Team turn) {
        pieceDao.removeAll();
        pieceDao.addAllPieces(pieces);
        turnDao.updateTurn(turn);
    }

    public Team loadCurrentTurn() {
        return Team.valueOf(turnDao.getTurn());
    }

    public void resetGame() {
        pieceDao.removeAll();
        turnDao.removeAll();
    }
}
