package view;

import domain.Position;
import domain.Team;
import domain.piece.Piece;
import java.util.List;
import java.util.Map;

public class OutputView {

    public static final String red = "\u001B[31m";
    public static final String blue = "\u001B[34m";
    public static final String white = "\u001B[37m";

    public static final String exit = "\u001B[0m";

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
                    System.out.print(white + "ㅁ" + exit);
                    continue;
                }
                if (piece.getTeam() == Team.RED) {
                    System.out.print(convertToString(red, piece));
                }
                if (piece.getTeam() == Team.BLUE) {
                    System.out.print(convertToString(blue, piece));
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

    public void displayErrorMessage(String message) {
        System.out.println(message);
    }
}
