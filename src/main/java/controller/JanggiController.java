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
        // 보드 초기화
        int choFormationNumber = inputView.initialFormation(Team.CHO);
        int hanFormationNumber = inputView.initialFormation(Team.HAN);

        Map<Position, Piece> choBoard = initialBoard(choFormationNumber,Team.CHO);
        Map<Position, Piece> hanBoard = initialBoard(hanFormationNumber,Team.HAN);

        Map<Position, Piece> board = new HashMap<>();

        board.putAll(choBoard);
        board.putAll(hanBoard);
        // 초기화된 보드 출력
        outputView.printBoard(board);
        // 기물 이동 <-> 보드 출력 반복
        // 이동 위치 입력 받기

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
