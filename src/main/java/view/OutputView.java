package view;

import domain.JanggiBoard;
import domain.JanggiGame;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Position;
import domain.piece.Team;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class OutputView {

    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String WHITE = "\u001B[37m";
    public static final String EXIT = "\u001B[0m";

    public void printJanggiBoard(JanggiGame game) {
        JanggiBoard board = game.getBoard();
        StringBuilder stringBuilder = new StringBuilder("\n");
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 9; j++) {
                Optional<Piece> optionalPiece = board.findPiece(new Position(i + 1, j + 1));
                if (optionalPiece.isEmpty()) {
                    stringBuilder.append(WHITE + "ㅁ" + EXIT);
                    continue;
                }
                Piece piece = optionalPiece.get();
                if (piece.getTeam() == Team.HAN) {
                    stringBuilder.append(convertToString(RED, piece));
                }
                if (piece.getTeam() == Team.CHO) {
                    stringBuilder.append(convertToString(GREEN, piece));
                }
            }
            stringBuilder.append("\n");
        }
        System.out.print(stringBuilder);
    }

    private String convertToString(String color, Piece piece) {
        String result = "";
        switch (piece.getType()) {
            case PieceType.PAWN:
                if (piece.getTeam() == Team.CHO) {
                    result = "졸";
                    break;
                }
                result = "병";
                break;
            case PieceType.CHA:
                result = "차";
                break;
            case PieceType.SANG:
                result = "상";
                break;
            case PieceType.SA:
                result = "사";
                break;
            case PieceType.MA:
                result = "마";
                break;
            case PieceType.GUNG:
                result = "궁";
                break;
            case PieceType.PO:
                result = "포";
                break;
        }
        return color + result + EXIT;
    }

    public void printErrorMessage(Exception exception) {
        System.out.printf("[ERROR] %s%n", exception.getMessage());
    }

    public void printScore(Map<Team, Double> scores) {
        DecimalFormat df = new DecimalFormat("#.##");

        System.out.printf("%s %s점%n", convertToCountry(Team.CHO), df.format(scores.get(Team.CHO)));
        System.out.printf("%s %s점%n", convertToCountry(Team.HAN), df.format(scores.get(Team.HAN)));
    }

    private String convertToCountry(Team team) {
        return switch (team) {
            case Team.HAN -> "한나라";
            case Team.CHO -> "초나라";
        };
    }

    public void printGameEnd() {
        System.out.println("궁이 사망해서 게임이 종료되었습니다.");
    }

    public void printAllRoomNames(List<String> allRoomNames) {
        StringBuilder sb = new StringBuilder();
        sb.append("장기 게임방 목록입니다. (").append(allRoomNames.size()).append("개)\n");
        for (String name : allRoomNames) {
            sb.append("-").append(name).append("\n");
        }

        System.out.println(sb);
    }
}
