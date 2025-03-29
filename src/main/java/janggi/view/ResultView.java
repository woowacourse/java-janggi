package janggi.view;

import janggi.direction.PieceMovement;
import janggi.piece.Piece;
import janggi.piece.Board;
import janggi.piece.Team;
import janggi.position.Position;
import java.util.List;
import java.util.Map;

public class ResultView {

    private static final String LINE = System.lineSeparator();
    private static final String BLANK = "ㅤ";
    private static final String HEADER = "   1    2    3    4    5    6   7    8    9%n";
    private static final String BOARD_LINE = "   |    |    |    |    |    |    |    |   |%n";
    private static final String DASH = " ㅡ ";
    private static final String TITLE_RESULT = """
            왕이 잡혔습니다.
            %s나라의 승리입니다!""";
    private static final String TITLE_ORDER = "%s나라의 순서입니다.";

    private static final String BLUE_CODE = "\u001B[34m";
    private static final String RED_CODE = "\u001B[31m";
    private static final String EXIT_CODE = "\u001B[0m";

    private static final Map<PieceMovement, List<String>> PIECE_TYPE_KOREAN = Map.of(
            PieceMovement.KING, List.of("漢", "楚"),
            PieceMovement.GUARD, List.of("士"),
            PieceMovement.HORSE, List.of("馬"),
            PieceMovement.ELEPHANT, List.of("象"),
            PieceMovement.CHARIOT, List.of("車"),
            PieceMovement.CANNON, List.of("包"),
            PieceMovement.CHO_SOLDIER, List.of("卒"),
            PieceMovement.HAN_SOLDIER, List.of("兵")
    );

    public void printBoard(final Board choBoard, final Board hanBoard) {
        System.out.printf(HEADER);
        for (int y = 1; y <= 10; y++) {
            // TODO: 리스트로 스트링 넣고 팀에 따라 색깔 조합 + 한자 받아오기
            final StringBuilder sb = new StringBuilder(String.format("%2d ", y));
            for (int x = 1; x <= 9; x++) {
                final Position currentPosition = Position.valueOf(y, x);
                if (x != 1) {
                    sb.append(DASH);
                }
                if (x == 5) {
                    sb.append(BLANK);
                }
                if (!hanBoard.hasPiece(currentPosition) && !choBoard.hasPiece(currentPosition)) {
                    sb.append(BLANK);
                    continue;
                }
                sb.append(makeTeamMessage(hanBoard, choBoard, currentPosition));
            }
            System.out.println(sb);
            if (y != 10) {
                System.out.printf(BOARD_LINE);
            }
        }
    }

    public String makeTeamMessage(final Board hanBoard, final Board choBoard, final Position currentPosition) {
        if (hanBoard.hasPiece(currentPosition)) {
            final Piece piece = hanBoard.findPieceByPosition(currentPosition);
            return getValue(piece.getPieceMovement(), Team.HAN);
        }
        final Piece piece = choBoard.findPieceByPosition(currentPosition);
        return getValue(piece.getPieceMovement(), Team.CHO);
    }

    public void printOrder(final Team team) {
        System.out.printf(LINE + TITLE_ORDER + LINE, team.getTitle());
    }

    public void printJanggiResult(final Team team) {
        System.out.printf(LINE + TITLE_RESULT, team.getTitle());
    }

    public String getValue(final PieceMovement pieceMovement, final Team team) {
        final List<String> values = PIECE_TYPE_KOREAN.get(pieceMovement);
        if (team == Team.HAN) {
            return RED_CODE + values.getFirst() + EXIT_CODE;
        }
        return BLUE_CODE + values.getLast() + EXIT_CODE;
    }
}
