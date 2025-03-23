package view;

import domain.Player;
import domain.Position;
import domain.Team;
import domain.piece.Piece;
import java.util.List;
import java.util.Map;

public class OutputView {

    public static final String red = "\u001B[31m";
    public static final String green = "\u001B[32m";
    public static final String white = "\u001B[37m";

    public static final String exit = "\u001B[0m";

    public void displayPlayerInfo(List<String> playerNames) {
        System.out.printf("%n%s: 초나라%n%s: 한나라%n%n", playerNames.getFirst(), playerNames.getLast());
    }

    public void printJanggiBoard(Map<Position, Piece> board) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 9; j++) {
                Piece piece = board.get(new Position(i + 1, j + 1));
                if (piece == null) {
                    System.out.print(white + "ㅁ" + exit);
                    continue;
                }
                if (piece.getTeam() == Team.HAN) {
                    System.out.print(convertToString(red, piece));
                }
                if (piece.getTeam() == Team.CHO) {
                    System.out.print(convertToString(green, piece));
                }
            }
            System.out.println();
        }
    }

    private String convertToString(String color, Piece piece) {
        String result = "";
        switch (piece.getClass().getSimpleName()) {
            case "Pawn":
                result = "병";
                break;
            case "Chariot":
                result = "차";
                break;
            case "Elephant":
                result = "상";
                break;
            case "Guard":
                result = "사";
                break;
            case "Horse":
                result = "마";
                break;
            case "King":
                result = "궁";
                break;
            case "Cannon":
                result = "포";
                break;

        }
        return color + result + exit;
    }

    public void printTurnMessage(Player player) {
        System.out.printf("%s의 차례입니다.%n", convertToCountry(player.getTeam()));
    }

    private String convertToCountry(Team team) {
        return switch (team) {
            case Team.HAN -> "한나라";
            case Team.CHO -> "초나라";
            default -> "알 수 없는 나라";
        };
    }
}
