package janggi.view;

import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.Team;
import janggi.domain.vo.position.Position;

import java.util.HashMap;
import java.util.Map;

public class OutputView {
    private static final int ROW_SIZE = 10;
    private static final int COL_SIZE = 9;

    public void printIntroduce() {
        System.out.println("장기 게임에 오신 걸 환영합니다.");
    }

    public void printBoard(Map<Position, Piece> pieces) {
        printHeader();

        for (int r = 0; r < ROW_SIZE; r++) {
            System.out.print(r + " ");
            for (int c = 0; c < COL_SIZE; c++) {
                Piece piece = pieces.getOrDefault(new Position(r, c), null); // 혹은 EmptyPiece
                System.out.print(formatCell(piece));
            }
            System.out.println();
        }
    }

    public void skipTurn() {
        System.out.println("움직임 없이 턴을 넘깁니다.");
    }

    public void printWinner(Team team) {
        System.out.println(getTeamName(team) + "나라가 이겼습니다.");
    }

    private void printHeader() {
        System.out.print("    ");

        for (int i = 0; i < COL_SIZE; i++) {
            System.out.print(i + "     ");
        }
        System.out.println("(Col)");
    }

    private String formatCell(Piece piece) {
        if (piece == null || piece.isEmpty()) {
            return "[ . ] ";
        }
        String team = getTeamName(piece.getTeam());
        String symbol = getSymbol(piece.pieceType());

        return String.format("[%s%s] ", team, symbol);
    }

    private String getSymbol(PieceType type) {
        return switch (type) {
            case KING -> "將";
            case ADVISOR -> "士";
            case TANK -> "車";
            case HORSE -> "馬";
            case ELEPHANT -> "象";
            case CANNON -> "包";
            case SOLDIER -> "卒";
            default -> "？";
        };
    }

    private String getTeamName(Team team) {
        Map<Team, String> names = new HashMap<>(){
            {
                put(Team.CHO, "초");
                put(Team.HAN, "한");
            }
        };

        return names.get(team);
    }

    public void printResign(Team team) {
        System.out.println(getTeamName(team) + "나라가 기권을 선언했습니다.");
    }

    public void printResumed() {
        System.out.println("끝나지 않은 기존 게임 이력이 존재합니다...\n" +
                "기존 게임을 불러옵니다.");
    }
}
