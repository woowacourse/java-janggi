package controller;

import dao.mongodb.BoardDao;
import domain.Board;
import domain.BoardFactory;
import domain.Formation;
import domain.JanggiGame;
import domain.Team;
import domain.vo.Position;
import presentation.PositionCommand;
import view.InputView;
import view.OutputView;

public class JanggiController {

    private static final String RESUME_GAME_NUMBER = "2";
    private static final String SURRENDER = "항복";

    private final InputView inputView;
    private final OutputView outputView;
    private final BoardDao boardDao;

    public JanggiController(InputView inputView, OutputView outputView, BoardDao boardDao) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.boardDao = boardDao;
    }

    public void run() {
        String command = inputView.readCommand();

        if (command.equals(RESUME_GAME_NUMBER)) {
            resumeGame();
            return;
        }

        startNewGame();
    }

    private void startNewGame() {
        Board board = BoardFactory.setUp();
        board = readHanFormation(board);
        board = readChuFormation(board);

        JanggiGame janggiGame = JanggiGame.of(board);
        String gameId = boardDao.save(board, janggiGame.getTurnCount());

        playGame(gameId, janggiGame);
    }

    private void resumeGame() {
        String gameId = inputView.readGameId();
        Board board = boardDao.findBoardByGameId(gameId);
        int turnCount = boardDao.findTurnCountByGameId(gameId);
        JanggiGame janggiGame = JanggiGame.of(board, turnCount);

        playGame(gameId, janggiGame);
    }

    private void playGame(String gameId, JanggiGame janggiGame) {
        outputView.printCurrentGameId(gameId);
        outputView.printBoard(janggiGame.getBoardStatus());

        while (true) {
            movePosition(janggiGame);
            boardDao.update(gameId, Board.of(janggiGame.getBoardStatus()), janggiGame.getTurnCount());

            if (janggiGame.isFinished()) {
                outputView.printGameFinishMessage();
                outputView.printScore(janggiGame.calculateScore(Team.HAN), janggiGame.calculateScore(Team.CHU));
                break;
            }
        }
    }

    private Board readChuFormation(Board board) {
        while (true) {
            try {
                String chuArrangement = inputView.readArrangement(Team.CHU);
                board = applyArrangement(chuArrangement, board, Team.CHU);
                return board;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println();
            }
        }
    }

    private Board readHanFormation(Board board) {
        while (true) {
            try {
                String hanArrangement = inputView.readArrangement(Team.HAN);
                board = applyArrangement(hanArrangement, board, Team.HAN);

                return board;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println();
            }
        }
    }

    private static Board applyArrangement(String arrangement, Board board, Team team) {
        if (Formation.from(arrangement) == Formation.SANG_MA_SANG_MA) {
            return BoardFactory.setUpLeftElephantFormation(board.getBoard(), team);
        }
        if (Formation.from(arrangement) == Formation.MA_SANG_MA_SANG) {
            return BoardFactory.setUpRightElephantFormation(board.getBoard(), team);
        }
        if (Formation.from(arrangement) == Formation.MA_SANG_SANG_MA) {
            return BoardFactory.setUpInnerElephantFormation(board.getBoard(), team);
        }
        if (Formation.from(arrangement) == Formation.SANG_MA_MA_SANG) {
            return BoardFactory.setUpOuterElephantFormation(board.getBoard(), team);
        }

        return board;
    }

    private void movePosition(JanggiGame janggiGame) {
        while (true) {
            try {
                outputView.printCurrentTurn(janggiGame.currentTurn());

                String input = inputView.readPosition();
                if (input.equals(SURRENDER)) {
                    outputView.printGameFinishMessage();
                    return;
                }

                String targetInput = inputView.readTargetPosition();

                Position position = PositionCommand.from(input).toPosition();
                Position targetPosition = PositionCommand.from(targetInput).toPosition();

                janggiGame.move(position, targetPosition);
                outputView.printBoard(janggiGame.getBoardStatus());
                janggiGame.passTheTurn();

                return;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println();
            }
        }
    }
}
