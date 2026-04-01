package controller;

import domain.Board;
import domain.BoardFactory;
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
        String hanArrangement = inputView.readArrangement(Team.HAN);
        String chuArrangement = inputView.readArrangement(Team.CHU);

        board = applyArrangement(hanArrangement, board, Team.HAN);
        board = applyArrangement(chuArrangement, board, Team.CHU);

        outputView.printBoard(board.getBoard());

        int turnCount = 0;
        turnCount = movePosition(board, turnCount);

        while (inputView.readRetryCommand()) {
            turnCount = movePosition(board, turnCount);
        }
    }

    private static Board applyArrangement(String arrangement, Board board, Team team) {
        if (arrangement.equals("1")) {
            return BoardFactory.setUpLeftElephantFormation(board.getBoard(), team);
        }
        if (arrangement.equals("2")) {
            return BoardFactory.setUpRightElephantFormation(board.getBoard(), team);
        }
        if (arrangement.equals("3")) {
            return BoardFactory.setUpInnerElephantFormation(board.getBoard(), team);
        }
        if (arrangement.equals("4")) {
            return BoardFactory.setUpOuterElephantFormation(board.getBoard(), team);
        }
        return board;
    }

    private int movePosition(Board board, int turnCount) {
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
            return movePosition(board, turnCount);
        }
    }
}
