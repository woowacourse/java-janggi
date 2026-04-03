package view;

import domain.board.BoardState;
import domain.board.IntersectionState;
import domain.piece.PieceType;
import domain.point.Point;
import domain.team.Team;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class OutputView {

    private static final int MIN_INDEX = 0;
    private static final int MAX_ROW = 10;
    private static final int MAX_FILE = 9;
    private static final String FULL_SPACE = "　";
    private static final String HALF_SPACE = " ";
    private static final Map<PieceType, String> CHO_PIECE_CHINESE_CHARACTER_MAP = Map.of(
            PieceType.GENERAL, "楚",
            PieceType.CHARIOT, "車",
            PieceType.CANNON, "包",
            PieceType.HORSE, "馬",
            PieceType.ELEPHANT, "象",
            PieceType.GUARD, "士",
            PieceType.SOLDIER, "卒",
            PieceType.NONE, "＋"
    );
    private static final Map<PieceType, String> HAN_PIECE_CHINESE_CHARACTER_MAP = Map.of(
            PieceType.GENERAL, "漢",
            PieceType.CHARIOT, "車",
            PieceType.CANNON, "包",
            PieceType.HORSE, "馬",
            PieceType.ELEPHANT, "象",
            PieceType.GUARD, "士",
            PieceType.SOLDIER, "兵",
            PieceType.NONE, "＋"
    );

    public void printCurrentTurn(Team turn) {
        System.out.print(getTeamName(turn) + "의 차례입니다.\n");
    }

    public void printCurrentBoardStatus(final BoardState boardState) {
        final Map<Point, String> board = boardState.intersectionStates().stream()
                .collect(Collectors.toMap(
                        IntersectionState::point,
                        state -> getPieceChineseCharacter(state.pieceType(), state.team())
                ));

        final String header = IntStream.range(MIN_INDEX, MAX_FILE)
                .mapToObj(this::toFullWidthNumber)
                .collect(Collectors.joining(HALF_SPACE));

        final String rows = IntStream.range(MIN_INDEX, MAX_ROW)
                .mapToObj(row -> {
                    final String rowCells = IntStream.range(MIN_INDEX, MAX_FILE)
                            .mapToObj(file -> board.getOrDefault(new Point(row, file), "＋"))
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

    public void printErrorMessage(String message) {
        System.out.println(message);
    }

    public void printWinnerTeam(Team winnerTeam) {
        System.out.println("게임이 종료되었습니다.");
        System.out.println(getTeamName(winnerTeam) + "의 승리입니다.\n");
    }

    private String getTeamName(Team team){
        if(team == Team.CHO){
            return "초(楚)";
        }
        return "한(漢)";
    }

    private String getPieceChineseCharacter (PieceType pieceType, Team team){
        if (pieceType == PieceType.NONE || team == null) {
            return CHO_PIECE_CHINESE_CHARACTER_MAP.get(PieceType.NONE);
        }
        if (team == Team.CHO) {
            return CHO_PIECE_CHINESE_CHARACTER_MAP.get(pieceType);
        }
        return HAN_PIECE_CHINESE_CHARACTER_MAP.get(pieceType);
    }

}
