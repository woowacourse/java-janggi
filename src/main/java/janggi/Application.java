package janggi;

import janggi.board.Board;
import janggi.board.Position;
import janggi.board.strategy.NormalPlaceStrategy;
import janggi.board.strategy.SavedPlaceStrategy;
import janggi.exception.GameOverException;
import janggi.piece.Piece;
import janggi.piece.Team;
import janggi.view.InputParser;
import janggi.view.InputView;
import janggi.view.OutputView;
import repository.dao.AttackTurnDAO;
import repository.dao.BoardDAO;

import java.util.Map;

public class Application {
    private static final InputView inputView = new InputView();
    private static final OutputView outputView = new OutputView();
    private static final InputParser parser = new InputParser();
    private static final BoardDAO BOARD_DAO = new BoardDAO();
    private static final AttackTurnDAO ATTACK_TURN_DAO = new AttackTurnDAO();

    public static void main(String[] args) {
        Board board;
        Team attackTeam;

        Map<Position, Piece> savedBoard = BOARD_DAO.findAllPiecesOnBoard();
        if (savedBoard.isEmpty()) {
            board = new Board(new NormalPlaceStrategy());
            BOARD_DAO.saveInitialBoard(board.getBoard());
            attackTeam = Team.GREEN;
            ATTACK_TURN_DAO.saveTurn(attackTeam);
        } else {
            board = new Board(new SavedPlaceStrategy(BOARD_DAO));
            attackTeam = ATTACK_TURN_DAO.loadAttackTeam();
        }

        outputView.printGameStartMessage(attackTeam);
        while (true) {
            outputView.printBoard(board);
            outputView.printGameScore(board);
            try {
                playTurn(board, attackTeam);
            } catch (GameOverException e) {
                endGame(board, attackTeam);
                break;
            }
            attackTeam = attackTeam.convertTeam();
            ATTACK_TURN_DAO.updateTurn(attackTeam);
        }
    }

    private static void playTurn(Board board, Team team) throws GameOverException {
        try {
            String startAndGoal = inputView.readStartAndGoalPosition(team);
            Position startPosition = parser.splitStartPosition(startAndGoal);
            Position goalPosition = parser.splitGoalPosition(startAndGoal);
            board.movePiece(startPosition, goalPosition, team);
            BOARD_DAO.updatePiecePosition(startPosition, goalPosition);
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e);
            playTurn(board, team);
        }
    }

    private static void endGame(Board board, Team team) {
        outputView.printBoard(board);
        outputView.printGameOver(team);
        BOARD_DAO.deleteAll();
        ATTACK_TURN_DAO.resetTurn();
    }
}
