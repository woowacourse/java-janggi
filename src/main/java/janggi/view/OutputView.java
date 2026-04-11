package janggi.view;

import janggi.dto.BoardDto;
import janggi.dto.DestinationDto;
import janggi.dto.GameDto;
import janggi.dto.PieceDto;
import janggi.dto.PositionDto;
import janggi.dto.WinnerDto;
import java.util.List;
import java.util.stream.IntStream;

public class OutputView {

    private static final String EMPTY = "\uFF0B";
    private static final String SPACE = "\u3000";
    private static final List<String> NUMBERS = List.of(
            "\uFF10", "\uFF11", "\uFF12", "\uFF13", "\uFF14",
            "\uFF15", "\uFF16", "\uFF17", "\uFF18", "\uFF19"
    );

    public void printGameList(List<GameDto> games) {
        System.out.println("\n[저장된 게임 목록]");
        for (GameDto game : games) {
            System.out.printf("ID: %d | %s vs %s | 현재 턴: %s\n",
                    game.id(), game.choName(), game.hanName(), game.currentTurn());
        }
        System.out.println("-------------------------");
    }

    public void printBoard(BoardDto boardDto) {
        System.out.println();
        for (int y = 9; y >= 0; y--) {
            System.out.println(buildRow(boardDto, y));
        }
        System.out.println(buildHeader());
    }

    private String buildHeader() {
        return SPACE.repeat(3) + String.join(SPACE, IntStream.range(0, 9)
                .mapToObj(NUMBERS::get)
                .toList()) + SPACE;
    }

    private String buildRow(BoardDto boardDto, int y) {
        return SPACE + NUMBERS.get(y) + SPACE + IntStream.rangeClosed(0, 8)
                .mapToObj(x -> formatCell(boardDto.board().get(new PositionDto(x, y))))
                .reduce("", String::concat);
    }

    private String formatCell(PieceDto pieceDto) {
        if (pieceDto == null) {
            return EMPTY + SPACE;
        }
        return PieceFormatter.format(pieceDto) + SPACE;
    }

    public void printError(String message) {
        System.out.println("[ERROR] " + message);
    }

    public void printDestinations(DestinationDto destinations) {
        String result = destinations.positions().stream()
                .map(positionDto -> String.format("(%d, %d)", positionDto.x(), positionDto.y()))
                .reduce((a, b) -> a + ", " + b)
                .orElse("");
        System.out.println(result);
    }

    public void printWinner(WinnerDto winnerDto) {
        System.out.printf("%s(이/가) 승리했습니다.%n", winnerDto.name());
    }
}
