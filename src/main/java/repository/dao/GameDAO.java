package repository.dao;

import janggi.board.Position;
import janggi.piece.Piece;
import janggi.piece.Team;

import java.util.Map;

public class GameDAO {
    private final BoardDAO boardDAO;
    private final AttackTurnDAO attackTurnDAO;

    public GameDAO(BoardDAO boardDAO, AttackTurnDAO attackTurnDAO) {
        this.boardDAO = boardDAO;
        this.attackTurnDAO = attackTurnDAO;
    }

    public void saveInitialBoard(Map<Position, Piece> initialBoard) {
        boardDAO.saveInitialBoard(initialBoard);
    }

    public Map<Position, Piece> findAllPiecesOnBoard() {
        return boardDAO.findAllPiecesOnBoard();
    }

    public void updatePiecePosition(Position start, Position goal) {
        boardDAO.updatePiecePosition(start, goal);
    }

    public void resetPieces() {
        boardDAO.resetPieces();
    }

    public void saveTurn(Team team) {
        attackTurnDAO.saveTurn(team);
    }

    public Team loadAttackTeam() {
        return attackTurnDAO.loadAttackTeam();
    }

    public void updateTurn(Team team) {
        attackTurnDAO.updateTurn(team);
    }

    public void resetTurn() {
        attackTurnDAO.resetTurn();
    }

    public BoardDAO getBoardDAO() {
        return boardDAO;
    }
}
