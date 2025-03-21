package view;

import domain.Player;
import domain.Position;
import domain.Team;
import domain.piece.Piece;
import java.util.List;
import java.util.Map;

public class OutputView {

    public void displayPlayerInfo(List<String> playerNames) {
        System.out.println("┌───────────────────────────┐");
        System.out.println("│        Korea Chase        │");
        System.out.println("└───────────────────────────┘");
        System.out.println();
        System.out.printf("%s님의 팀은 청팀 입니다.\n %s님의 팀은 홍팀 입니다.\n", playerNames.getFirst(), playerNames.getLast());
    }

    public void displayJanggiBoard(Map<Position, Piece> board) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 9; j++) {
                Piece piece = board.get(new Position(i + 1, j + 1));
                if (piece == null) {
                    System.out.print(TextColor.white + "ㅁ" + TextColor.exit);
                    continue;
                }
                if (piece.getTeam() == Team.RED) {
                    System.out.print(convertToString(TextColor.red, piece));
                }
                if (piece.getTeam() == Team.BLUE) {
                    System.out.print(convertToString(TextColor.blue, piece));
                }
            }
            System.out.println();
        }
    }

    private String convertToString(String color, Piece piece) {
        String result = switch (piece.getClass().getSimpleName()) {
            case "Pawn" -> "병";
            case "Chariot" -> "차";
            case "Elephant" -> "상";
            case "Guard" -> "사";
            case "Horse" -> "마";
            case "King" -> "궁";
            case "Cannon" -> "포";
            default -> "";
        };
        return color + result + TextColor.exit;
    }

    public void displayErrorMessage(String message) {
        System.out.println(message);
    }

    public void displayGameResult(Player thisTurnPlayer) {
        String result =
                """
                        왕이 죽었습니다. 게임을 종료합니다.
                        (%s)팀의 플레이어인 %s님이 게임을 승리하셨습니다.
                        """;
        System.out.printf(result, thisTurnPlayer.getTeam(), thisTurnPlayer.getName());
    }
}
