package janggi.game;

import janggi.board.Board;
import janggi.board.Position;
import janggi.board.strategy.NormalPlaceStrategy;
import janggi.board.strategy.SavedPlaceStrategy;
import janggi.exception.GameOverException;
import janggi.piece.Piece;
import janggi.piece.Team;
import janggi.view.Console;
import repository.dao.GameDAO;

import java.util.Map;

public class JanggiGame {
    private final Console console;
    private final GameDAO gameDAO;

    public JanggiGame(Console console, GameDAO gameDAO) {
        this.console = console;
        this.gameDAO = gameDAO;
    }

    public void play() {
        Board board;
        Team attackTeam;

        Map<Position, Piece> savedBoard = gameDAO.findAllPiecesOnBoard();
        if (savedBoard.isEmpty()) {
            board = new Board(new NormalPlaceStrategy());
            gameDAO.saveInitialBoard(board.getBoard());
            attackTeam = Team.GREEN;
            gameDAO.saveTurn(attackTeam);
        } else {
            board = new Board(new SavedPlaceStrategy(gameDAO.getBoardDAO()));
            attackTeam = gameDAO.loadAttackTeam();
        }

        console.printGameStartMessage(attackTeam);
        while (true) {
            console.printBoard(board);
            console.printGameScore(board);
            try {
                playTurn(board, attackTeam);
            } catch (GameOverException e) {
                endGame(board, attackTeam);
                break;
            }
            attackTeam = attackTeam.convertTeam();
            gameDAO.updateTurn(attackTeam);
        }
    }

    private void playTurn(Board board, Team team) throws GameOverException {
        try {
            String startAndGoal = console.readStartAndGoalPosition(team);
            Position startPosition = console.splitStartPosition(startAndGoal);
            Position goalPosition = console.splitGoalPosition(startAndGoal);
            board.movePiece(startPosition, goalPosition, team);
            gameDAO.updatePiecePosition(startPosition, goalPosition);
        } catch (IllegalArgumentException e) {
            console.printErrorMessage(e);
            playTurn(board, team);
        }
    }

    private void endGame(Board board, Team team) {
        console.printBoard(board);
        console.printGameOver(team);
        gameDAO.resetPieces();
        gameDAO.resetTurn();
    }
}
