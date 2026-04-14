package janggi.view.output;

import janggi.domain.Position;
import janggi.domain.team.TeamType;
import janggi.dto.BoardSpot;
import janggi.dto.BoardSpots;
import java.util.Map;

public class ConsoleOutputView implements OutputView {

    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String RESET = "\u001B[0m";
    private static final String EMPTY = "\uFF0E";
    private static final String FULL_WIDTH_SPACE = "\u3000";
    private static final String FULL_WIDTH_ZERO = "\uFF10";
    private static final String FULL_WIDTH_ONE = "\uFF11";
    private static final String FULL_WIDTH_TWO = "\uFF12";
    private static final String FULL_WIDTH_THREE = "\uFF13";
    private static final String FULL_WIDTH_FOUR = "\uFF14";
    private static final String FULL_WIDTH_FIVE = "\uFF15";
    private static final String FULL_WIDTH_SIX = "\uFF16";
    private static final String FULL_WIDTH_SEVEN = "\uFF17";
    private static final String FULL_WIDTH_EIGHT = "\uFF18";
    private static final String FULL_WIDTH_NINE = "\uFF19";

    @Override
    public void printMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void printSameLine(String message) {
        System.out.print(message);
    }

    @Override
    public void printNewLine() {
        System.out.println();
    }

    @Override
    public void printStartMessage() {
        printMessage("장기 게임을 시작합니다.");
        printNewLine();
    }

    @Override
    public void printBoard(BoardSpots boardSpots) {
        Map<Position, BoardSpot> boardSpotsMap = boardSpots.value();
        printHeader();
        for (int y = 10; y >= 1; y--) {
            printRow(boardSpotsMap, y);
        }
    }

    @Override
    public void printTurnNotice(TeamType currentTurnTeam) {
        printMessage(formatTeamType(currentTurnTeam) + "의 차례입니다.");
    }

    @Override
    public void printAskPiecePosition() {
        printMessage("움직일 기물의 좌표를 입력해주세요. (ex. 1,3)");
    }

    @Override
    public void printAskMovePosition(String nickname) {
        printMessage(nickname + "의 목적 좌표를 입력해주세요. (ex. 1,3) /  말 선택을 취소하시려면 cancel을 입력해주세요.");
    }

    @Override
    public void printResult(BoardSpots boardSpots, TeamType winner, int winnerScore) {
        printBoard(boardSpots);
        printWinner(winner, winnerScore);
    }

    private void printWinner(TeamType winner, int winnerScore) {
        printMessage(formatTeamType(winner) + "의 승리입니다. 점수: " + winnerScore);
    }

    private void printHeader() {
        printSameLine("  ");
        printSameLine(FULL_WIDTH_SPACE.repeat(2));
        for (int x = 1; x <= 9; x++) {
            printSameLine(toFullWidthNumber(x) + FULL_WIDTH_SPACE);
        }
        printNewLine();
    }

    private void printRow(Map<Position, BoardSpot> boardSpotMap, int y) {
        printSameLine(formatRowNumber(y) + FULL_WIDTH_SPACE);
        for (int x = 1; x <= 9; x++) {
            printSameLine(makeCell(x, y, boardSpotMap) + FULL_WIDTH_SPACE);
        }
        printNewLine();
    }

    private String makeCell(int x, int y, Map<Position, BoardSpot> boardSpotMap) {
        Position position = new Position(x, y);
        if (boardSpotMap.containsKey(position)) {
            BoardSpot spot = boardSpotMap.get(position);
            return getColor(spot.teamType()) + spot.pieceName() + RESET;
        }
        return EMPTY;
    }

    private String formatRowNumber(int number) {
        if (number < 10) {
            return FULL_WIDTH_SPACE + toFullWidthNumber(number);
        }
        return toFullWidthNumber(number);
    }

    private String toFullWidthNumber(int number) {
        return String.valueOf(number)
            .replace("0", FULL_WIDTH_ZERO)
            .replace("1", FULL_WIDTH_ONE)
            .replace("2", FULL_WIDTH_TWO)
            .replace("3", FULL_WIDTH_THREE)
            .replace("4", FULL_WIDTH_FOUR)
            .replace("5", FULL_WIDTH_FIVE)
            .replace("6", FULL_WIDTH_SIX)
            .replace("7", FULL_WIDTH_SEVEN)
            .replace("8", FULL_WIDTH_EIGHT)
            .replace("9", FULL_WIDTH_NINE);
    }

    private String getColor(TeamType teamType) {
        if (teamType == TeamType.CHU) {
            return GREEN;
        }
        if (teamType == TeamType.HAN) {
            return RED;
        }
        return RESET;
    }

    private String formatTeamType(TeamType teamType) {
        if (teamType == TeamType.CHU) {
            return "초나라";
        }
        return "한나라";
    }
}
