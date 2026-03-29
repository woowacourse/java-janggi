package view;

import domain.team.Team;
import dto.BoardStatusDTO;
import dto.PointInfoDTO;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class OutputView {

    private static final int MIN_INDEX = 0;
    private static final int MAX_ROW = 10;
    private static final int MAX_FILE = 9;
    private static final String FULL_SPACE = "　";
    private static final String HALF_SPACE = " ";

    public void printCurrentTurn(Team turn){
        System.out.print(turn.getKoreanTeamName() + "(" + turn.getChineseTeamName() + ")의 차례입니다.\n");
    }

    public void printCurrentBoardStatus(final BoardStatusDTO boardStatus) {
        final List<PointInfoDTO> pointInfos = boardStatus.boardStatus();
        final String header = IntStream.range(MIN_INDEX, MAX_FILE)
                .mapToObj(this::toFullWidthNumber)
                .collect(Collectors.joining(HALF_SPACE));
        final String rows = IntStream.range(MIN_INDEX, MAX_ROW)
                .mapToObj(row -> {
                    final String rowCells = IntStream.range(MIN_INDEX, MAX_FILE)
                            .mapToObj(file -> pointInfos.get(row * MAX_FILE + file).pointInfo())
                            .collect(Collectors.joining(HALF_SPACE));
                    return toFullWidthNumber(row) + HALF_SPACE + rowCells;
                })
                .collect(Collectors.joining(System.lineSeparator()));
        System.out.println(FULL_SPACE + HALF_SPACE + header);
        System.out.println(rows);
    }

    private String toFullWidthNumber(int number) {
        return String.valueOf(number)
                .chars()
                .mapToObj(ch -> String.valueOf((char) ('０' + (ch - '0'))))
                .collect(Collectors.joining());
    }

    public void printErrorMessage(String message){
        System.out.println(message);
    }

}
