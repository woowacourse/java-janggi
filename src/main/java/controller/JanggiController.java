package controller;

import domain.Position;
import domain.board.LeftGwimaFactory;
import domain.board.RightGwimaFactory;
import domain.board.WonangmaFactory;
import domain.board.YanggwimaFactory;
import domain.piece.Piece;
import domain.piece.Team;
import java.util.HashMap;
import java.util.Map;
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
        int choFormationNumber = inputView.initialFormation(Team.CHO);
        int hanFormationNumber = inputView.initialFormation(Team.HAN);

        Map<Position, Piece> choBoard = initialBoard(choFormationNumber,Team.CHO);
        Map<Position, Piece> hanBoard = initialBoard(hanFormationNumber,Team.HAN);

        Map<Position, Piece> board = new HashMap<>();

        board.putAll(choBoard);
        board.putAll(hanBoard);

        outputView.printBoard(board);
    }

    private Map<Position, Piece> initialBoard(int input,Team team) {
        if (input == 1) {
            return new LeftGwimaFactory().createFormation(team);
        }

        if (input == 2) {
            return new RightGwimaFactory().createFormation(team);
        }

        if (input == 3) {
            return new WonangmaFactory().createFormation(team);
        }

        return new YanggwimaFactory().createFormation(team);
    }
}
