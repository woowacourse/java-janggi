package controller;

import domain.Board;
import domain.BoardFactory;
import domain.Formation;
import domain.JanggiGame;
import domain.Team;
import domain.vo.Position;
import view.InputView;
import view.OutputView;

public class JanggiController {

    private final InputView inputView;
    private final OutputView outputView;

    public JanggiController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        Board board = BoardFactory.setUp();
        board = readHanFormation(board);
        board = readChuFormation(board);

        outputView.printBoard(board.getBoard());

        JanggiGame janggiGame = JanggiGame.of(board);
        movePosition(janggiGame);

        while (true) {
            movePosition(janggiGame);
            if (janggiGame.isFinished()) {
                outputView.printGameFinishMessage();
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

                Position position = inputView.readPosition();
                Position targetPosition = inputView.readTargetPosition();

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
