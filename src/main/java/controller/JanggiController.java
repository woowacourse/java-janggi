package controller;

import domain.Board;
import domain.BoardFactory;
import domain.Formation;
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

        int turnCount = 0;
        turnCount = movePosition(board, turnCount);

        while (true) {
            turnCount = movePosition(board, turnCount);
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

    private int movePosition(Board board, int turnCount) {
        while (true) {
            try {
                outputView.printCurrentTurn(turnCount);

                Position position = inputView.readPosition();
                Position targetPosition = inputView.readTargetPosition();

                Team currentTeam = Team.from(turnCount);
                board.move(position, targetPosition, currentTeam);
                outputView.printBoard(board.getBoard());
                return turnCount += 1;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println();
            }
        }
    }
}
