package domain.janggigame;

import domain.board.Board;
import domain.piece.Side;
import domain.players.Players;
import dto.BoardResponseDto;
import util.Parser;
import view.InputView;
import view.OutputView;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class JanggiGame {
    private final Players players;
    private final Board board;

    public JanggiGame(Players players, Board board) {
        this.players = players;
        this.board = board;
    }

    public void run() {
        selectSide();
        hanPlayerPlacement();
        choPlayerPlacement();

        // 턴마다 번갈아가면서 기물 배치
    }

    private void selectSide() {
        String input = InputView.inputSideChoice();
        int sideCode = Parser.parseToSideCode(input);
        Side side = generateSide(sideCode);
        OutputView.printSideChoiceResult(side);
    }

    private Side generateSide(int sideCode) {
        List<Side> sides = Arrays.asList(Side.values());
        Collections.shuffle(sides);

        return sides.get(sideCode - 1);
    }

    private void hanPlayerPlacement() {
        String input = InputView.inputHanPlacementCode();
        int code = Parser.parseToPlacementCode(input);
        players.initPlacementBySide(Side.HAN, code, board);
        OutputView.printBoard(board.findState());
    }

    private void choPlayerPlacement() {
        String input = InputView.inputChoPlacementCode();
        int code = Parser.parseToPlacementCode(input);
        players.initPlacementBySide(Side.CHO, code, board);
        OutputView.printBoard(board.findState());
    }
}
