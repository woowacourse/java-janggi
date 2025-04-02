package view;

import janggiGame.piece.Dynasty;
import janggiGame.piece.Piece;
import janggiGame.piece.Type;
import janggiGame.position.Palace;
import janggiGame.position.Position;
import janggiGame.state.GameResult;
import janggiGame.state.GameScore;
import java.util.Map;

public class OutputView {
    private static final String BLANK = "＿";

    public void printBoard(Map<Position, Piece> pieces) {
        for (Position position : Position.getDots()) {
            if (position.getX() == 0) {
                System.out.println();
                System.out.printf("%d", position.getY());
            }

            if (!pieces.containsKey(position)) {
                if (Palace.isInPalace(position)) {
                    System.out.print("\u001B[38;5;226m" + " " + BLANK + "\u001B[0m");
                    continue;
                }
                System.out.printf("%2s", BLANK);
                continue;
            }

            Piece piece = pieces.get(position);

            if (piece.getDynasty() == Dynasty.CHO) {
                System.out.print("\u001B[32m" + " " + getName(piece) + "\u001B[0m");
                continue;
            }

            System.out.print("\u001B[31m" + " " + getName(piece) + "\u001B[0m");
        }
        System.out.println();

        System.out.println("  ０ １ ２ ３ ４ ５ ６ ７ 8");
    }

    private String getName(Piece piece) {
        Type type = piece.getType();

        switch (type) {
            case KING -> {
                return "장";
            }
            case PAWN -> {
                return "병";
            }
            case HORSE -> {
                return "마";
            }
            case CANNON -> {
                return "포";
            }
            case ADVISOR -> {
                return "사";
            }
            case CHARIOT -> {
                return "차";
            }
            case ELEPHANT -> {
                return "상";
            }
            case null, default -> throw new RuntimeException();
        }
    }

    public void printGameScore(GameScore gameScore) {
        System.out.printf("한나라 점수: %.1f%n", gameScore.hanScore());
        System.out.printf("초나라 점수: %d%n", (int) gameScore.choScore());

    }

    public void printGameResult(GameResult gameResult) {
        System.out.print("\n게임 결과: ");
        switch (gameResult) {
            case CHO_WIN -> System.out.println("초나라 승리!!");
            case HAN_WIN -> System.out.println("한나라 승리!!");
            case DRAW -> System.out.println("무승부");
            case null, default -> throw new RuntimeException();
        }
    }

    public void printErrorMessage(Exception e) {
        System.out.println(e.getMessage());
    }
}
