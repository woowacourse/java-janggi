package view;

import domain.JanggiGame;
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

    public void printGameInfo(List<String> playerNames, JanggiGame janggiGame) {
        System.out.printf("%n%s: 초나라%n%s: 한나라%n", playerNames.getFirst(), playerNames.getLast());
        printJanggiBoard(janggiGame);
    }

    public void printJanggiBoard(JanggiGame game) {
        Map<Position, Piece> board = game.getBoardState();
        StringBuilder stringBuilder = new StringBuilder("\n");
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 9; j++) {
                Piece piece = board.get(new Position(i + 1, j + 1));
                if (piece == null) {
                    stringBuilder.append(white + "ㅁ" + exit);
                    continue;
                }
                if (piece.getTeam() == Team.HAN) {
                    stringBuilder.append(convertToString(red, piece));
                }
                if (piece.getTeam() == Team.CHO) {
                    stringBuilder.append(convertToString(green, piece));
                }
            }
            stringBuilder.append("\n");
        }
        System.out.print(stringBuilder);
    }

    private String convertToString(String color, Piece piece) {
        String result = "";
        switch (piece.getClass().getSimpleName()) {
            case "Pawn":
                if (piece.getTeam() == Team.CHO) {
                    result = "졸";
                    break;
                }
                result = "병";
                break;
            case "Jol":
                result = "졸";
                break;
            case "Cha":
                result = "차";
                break;
            case "Sang":
                result = "상";
                break;
            case "Sa":
                result = "사";
                break;
            case "Ma":
                result = "마";
                break;
            case "Gung":
                result = "궁";
                break;
            case "Po":
                result = "포";
                break;
        }
        return color + result + exit;
    }

    public void printErrorMessage(Exception exception) {
        System.out.printf("[ERROR] %s%n", exception.getMessage());
    }

    public void printGameEnd() {
        System.out.println("궁이 사망해서 게임이 종료되었습니다.");
    }
}
