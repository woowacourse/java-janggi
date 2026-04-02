package controller;

import controller.response.BoardView;
import domain.board.Board;
import domain.board.BoardInitializer;
import domain.board.ElephantSetup;
import domain.player.Player;
import domain.player.Team;
import view.InputView;
import view.OutputView;

public class JanggiController {

    private final InputView inputView;
    private final OutputView outputView;

    public JanggiController(final InputView inputView, final OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        Player choPlayer = generateChoPlayer();
        Player hanPlayer = generateHanPlayer();

        Board board = initializeBoard();

        // 턴 진행 (턴 전환)

        // 보드판 출력
        outputView.printBoard(BoardView.from(board));
        // 기물 선택
        // 기물이 이동 가능한 좌표 선택
        // 기물 이동
        // 종료 판단은 사이클2
    }


    private Player generateHanPlayer() {
        outputView.printEnterHanPlayerNamePrompt();
        String hanPlayerName = inputView.readPlayerName();
        return Player.of(hanPlayerName, Team.HAN);
    }

    private Player generateChoPlayer() {
        outputView.printEnterChoPlayerNamePrompt();
        String choPlayerName = inputView.readPlayerName();
        return Player.of(choPlayerName, Team.CHO);
    }


    private Board initializeBoard() {
        outputView.printChoiceChoElephantSetupPrompt();
        int choElephantSetupNumber = inputView.readElephantSetup();

        outputView.printChoiceHanElephantSetupPrompt();
        int hanElephantSetupNumber = inputView.readElephantSetup();

        return BoardInitializer.initialize(ElephantSetup.of(choElephantSetupNumber),
                ElephantSetup.of(hanElephantSetupNumber));
    }
}
