package janggigame;

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

    public JanggiGame(Players players) {
        this.players = players;
    }

    public void run() {
        selectSide();
        HanPlayerPlacement();
        ChoPlayerPlacement();
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

    private void HanPlayerPlacement() {
        String input = InputView.inputHanPlacementCode();
        int code = Parser.parseToPlacementCode(input);
        players.initPlacementBySide(Side.HAN, code);
        BoardResponseDto nowBoardState = BoardResponseDto.from(players.findBoardState());
        OutputView.printBoard(nowBoardState);
    }

    private void ChoPlayerPlacement() {
        String input = InputView.inputChoPlacementCode();
        int code = Parser.parseToPlacementCode(input);
        players.initPlacementBySide(Side.CHO, code);
        BoardResponseDto nowBoardState = BoardResponseDto.from(players.findBoardState());
        OutputView.printBoard(nowBoardState);
    }
}
