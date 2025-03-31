package janggi;

import janggi.board.Board;
import janggi.board.BoardInitializer;
import janggi.board.GameOverException;
import janggi.board.position.StartAndGoalPosition;
import janggi.board.position.Position;
import janggi.dao.BoardPieceDao;
import janggi.dao.GameDao;
import janggi.view.InputView;
import janggi.view.OutputView;

public class Application {
    private static final InputView inputView = new InputView();
    private static final OutputView outputView = new OutputView();
    private static final GameDao gameDao = new GameDao();
    private static final BoardPieceDao boardPieceDao = new BoardPieceDao();

    public static void main(String[] args) {
        Board board = initBoard();
        outputView.printGameStartMessage();
        while (true) {
            Team team = gameDao.readTurn();
            outputView.printBoard(board);
            if (!playTurn(board, team)) {
                break;
            }
            team = team.convertTeam();
            gameDao.updateTurn(team);
        }
    }

    private static Board initBoard() {
        BoardInitializer boardInitializer = new BoardInitializer();
        try {
            gameDao.create();
            Board board = boardInitializer.makeBoard();
            boardPieceDao.saveAll(board.getBoard());
            return board;
        } catch (AlreadyGameExistsException e) { // 진행 중이던 게임이 존재
            System.out.println("\n이전 게임을 불러옵니다.\n");
            return boardPieceDao.loadBoard();
        }
    }

    private static boolean playTurn(Board board, Team team) {
        try {
            StartAndGoalPosition startAndGoalPosition = inputView.readStartAndGoalPosition(team);
            Position startPosition = startAndGoalPosition.getStart();
            Position goalPosition = startAndGoalPosition.getGoal();
            board.movePiece(startPosition, goalPosition, team);
            boardPieceDao.delete(goalPosition);
            boardPieceDao.updatePiecePosition(startPosition, goalPosition);
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e);
            return playTurn(board, team);
        } catch (GameOverException e) {
            outputView.printBoard(board);
            outputView.printGameResult(board, team);
            boardPieceDao.deleteAll();
            gameDao.delete();
            return false;
        }
        return true;
    }
}
