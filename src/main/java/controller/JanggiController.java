package controller;

import dao.mongodb.BoardDao;
import domain.Board;
import domain.BoardFactory;
import domain.Formation;
import domain.JanggiGame;
import domain.Team;
import domain.vo.Position;
import dto.PieceDto;
import java.util.List;
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
        String gameId = boardDao.save(PieceDto.fromBoard(board.getBoard()), janggiGame.getTurnCount());

        playGame(gameId, janggiGame);
    }

    private void resumeGame() {
        while (true) {
            try {
                String gameId = inputView.readGameId();
                List<PieceDto> pieceDtos = boardDao.findPiecesByGameId(gameId);

                int turnCount = boardDao.findTurnCountByGameId(gameId);
                Board board = PieceDto.toBoard(pieceDtos);
                JanggiGame janggiGame = JanggiGame.of(board, turnCount);

                playGame(gameId, janggiGame);
                return;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                System.out.println();
            }
        }
    }

    private void playGame(String gameId, JanggiGame janggiGame) {
        outputView.printCurrentGameId(gameId);
        outputView.printBoard(janggiGame.getBoardStatus());

        while (true) {
            movePosition(janggiGame);
            boardDao.update(gameId, PieceDto.fromBoard(janggiGame.getBoardStatus()), janggiGame.getTurnCount());

            if (janggiGame.isFinished()) {
                boardDao.finish(gameId);
                outputView.printGameFinishMessage();
                outputView.printScore(janggiGame.calculateScore(Team.HAN), janggiGame.calculateScore(Team.CHU));
                break;
            }

            if (janggiGame.isSurrendered()) {
                boardDao.finish(gameId);
                outputView.printGameSurrenderMessage();
                outputView.printScore(janggiGame.calculateScore(Team.HAN), janggiGame.calculateScore(Team.CHU));
                break;
            }
        }
    }

    private Board readChuFormation(Board board) {
        while (true) {
            try {
                String chuArrangement = inputView.readArrangement(Team.CHU);
                board = Formation.from(chuArrangement, board, Team.CHU);
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
                board = Formation.from(hanArrangement, board, Team.HAN);

                return board;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println();
            }
        }
    }

    private void movePosition(JanggiGame janggiGame) {
        while (true) {
            try {
                outputView.printCurrentTurn(janggiGame.currentTurn());

                String input = inputView.readPosition();
                if (input.equals(SURRENDER)) {
                    janggiGame.surrender();
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
