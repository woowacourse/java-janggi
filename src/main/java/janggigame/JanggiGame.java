package janggigame;

import domain.piece.Side;
import domain.players.Players;
import domain.position.Position;
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
        playersPlacement();
        gameStart();
    }

    private void selectSide() {
        while (true) {
            try {
                String input = InputView.inputSideChoice();
                int sideCode = Parser.parseToSideCode(input);
                Side side = generateSide(sideCode);
                OutputView.printSideChoiceResult(side);
                return;
            } catch (Exception e) {
                OutputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private Side generateSide(int sideCode) {
        List<Side> sides = Arrays.asList(Side.values());
        Collections.shuffle(sides);

        return sides.get(sideCode - 1);
    }

    private void playersPlacement() {
        initPlayersPlacement(Side.CHO);
        initPlayersPlacement(Side.HAN);
    }

    private void initPlayersPlacement(Side side) {
        while (true) {
            try {
                String input = inputPlacementCode(side);
                int code = Parser.parseToPlacementCode(input);
                players.initPlacementBySide(side, code);
                printBoard();
                return;
            } catch (Exception e) {
                OutputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private String inputPlacementCode(Side side) {
        if (side == Side.HAN) return InputView.inputHanPlacementCode();
        return InputView.inputChoPlacementCode();
    }

    private void gameStart() {
        Side attackerSide = Side.HAN;
        while (true) {
            // TODO: 궁성을 구현하지 않아 다음 사이클에서 종료조건을 구현할 예정...
            try {
                printBoard();
                OutputView.printSide(attackerSide);
                players.move(selectFromPosition(), selectToPosition(), attackerSide);
                attackerSide = changeSide(attackerSide);
            } catch (Exception e) {
                OutputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private void printBoard() {
        BoardResponseDto nowBoardState = BoardResponseDto.from(players.findBoardState());
        OutputView.printBoard(nowBoardState);
    }

    private Position selectFromPosition() {
        String input = InputView.inputFromPosition();
        return Parser.parseToPosition(input);
    }

    private Position selectToPosition() {
        String input = InputView.inputToPosition();
        return Parser.parseToPosition(input);
    }

    private Side changeSide(Side attackerSide) {
        if (attackerSide == Side.HAN) return Side.CHO;
        return Side.HAN;
    }
}
