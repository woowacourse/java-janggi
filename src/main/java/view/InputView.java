package view;

import domain.janggiboard.customstrategy.OuterBoardArrangementStrategy;
import domain.janggiboard.customstrategy.InnerBoardArrangementStrategy;
import domain.janggiboard.customstrategy.BoardArrangementStrategy;
import domain.janggiboard.customstrategy.RightBoardArrangementStrategy;
import domain.janggiboard.customstrategy.LeftBoardArrangementStrategy;
import domain.piece.JanggiSide;
import domain.position.JanggiPosition;
import java.util.List;
import java.util.Scanner;

public class InputView {

    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String CHO_COLOR_PREFIX = "\u001B[32m";
    private static final String HAN_COLOR_PREFIX = "\u001B[31m";
    private static final String COLOR_SUFFIX = "\u001B[0m";

    private static final Scanner scanner = new Scanner(System.in);

    public List<JanggiPosition> getMovePieceInput() {
        System.out.println("움직일 말의 위치와 원하는 도착지를 입력하세요. (ex. 01 45)");
        String position = scanner.nextLine();
        return List.of(
                parseToPosition(position.substring(0, 2)),
                parseToPosition(position.substring(3, 5))
        );
    }

    private JanggiPosition parseToPosition(String positionRaw) {
        try {
            int rank = Integer.parseInt(positionRaw.substring(0, 1));
            int file = Integer.parseInt(positionRaw.substring(1, 2));
            return new JanggiPosition(rank, file);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("위치는 숫자를 입력해야 합니다.");
        }
    }

    public BoardArrangementStrategy getBoardArrangementInput(JanggiSide janggiSide) {
        System.out.println(LINE_SEPARATOR +
                getMessageWithColorOfSide(janggiSide, JanggiSideDisplay.getJanggiSideDisplay(janggiSide)) + "의 상차림을 선택해주세요."
        );
        System.out.println("1. 왼상 차림");
        System.out.println("2. 오른상 차림");
        System.out.println("3. 안상 차림");
        System.out.println("4. 바깥상 차림");

        String strategyInput = scanner.nextLine();
        return parseStrategy(strategyInput, janggiSide);
    }

    public String getMessageWithColorOfSide(JanggiSide side, String message) {
        if (side == JanggiSide.CHO) {
            return CHO_COLOR_PREFIX + message + COLOR_SUFFIX;
        }
        if (side == JanggiSide.HAN) {
            return HAN_COLOR_PREFIX + message + COLOR_SUFFIX;
        }
        return message;
    }

    private BoardArrangementStrategy parseStrategy(String option, JanggiSide side) {
        if (option.equals("1")) {
            return new LeftBoardArrangementStrategy(side);
        }
        if (option.equals("2")) {
            return new RightBoardArrangementStrategy(side);
        }
        if (option.equals("3")) {
            return new InnerBoardArrangementStrategy(side);
        }
        if (option.equals("4")) {
            return new OuterBoardArrangementStrategy(side);
        }
        throw new IllegalArgumentException("상차림 입력이 올바르지 않습니다.");
    }

    public GameContinueOption getPreviousGameContinueSelectionInput() {
        System.out.println("이전에 진행하던 게임을 이어서 하시겠습니까? (y/n)");
        String optionInput = scanner.nextLine();
        return GameContinueOption.parseToOption(optionInput);
    }
}
