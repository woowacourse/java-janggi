package janggi.view;

import janggi.piece.Piece;
import janggi.rule.CampType;
import java.util.ArrayList;
import java.util.List;

public class OutputView {

    private static final char SPACE = '\u3000';
    private static final char SQUARE = '\u53E3';
    private static final List<Character> unicodes = List.of(
            '\uFF10', '\uFF11', '\uFF12', '\uFF13', '\uFF14',
            '\uFF15', '\uFF16', '\uFF17', '\uFF18', '\uFF19');

    public void writeStartMessage() {
        System.out.println("장기 게임을 시작하겠습니다!");
        System.out.println();
    }

    public void writeScore(double choScore, double hanScore) {
        System.out.println("--------------------");
        String choScoreContent = String.format("초의 점수 : %f점", choScore);
        String hanScoreContent = String.format("한의 점수 : %f점", hanScore);
        System.out.println(choScoreContent);
        System.out.println(hanScoreContent);
        System.out.println("--------------------");
    }

    public void writeJanggiBoard(List<Piece> choPieces, List<Piece> hanPieces) {
        writeBoardYHeader();

        List<Piece> allPieces = new ArrayList<>();
        allPieces.addAll(choPieces);
        allPieces.addAll(hanPieces);
        for (int y = 0; y < 10; y++) {
            int yPosition = y;
            List<Piece> pieces = allPieces.stream()
                    .filter(piece -> piece.getPosition().y() == yPosition)
                    .toList();
            writeOneLineInBoard(y, pieces);
        }
        System.out.println("--------------------");
        System.out.println();
    }

    public void writeTurn(CampType campType) {
        String turnContent = String.format("%s의 턴입니다.", campType.getName());
        System.out.println(turnContent);
    }

    public void writeGameEndMessage() {
        System.out.println("게임이 종료되었습니다!");
    }

    public void writeWinning(CampType campType) {
        String content = String.format("%s의 승리입니다.", campType.getName());
        System.out.println(content);
        System.out.println();
    }

    public void writeGameStopMessage(String gameTitle) {
        String content = String.format("'%s' 게임을 저장하고 종료합니다. 나중에 이어서 하실 수 있습니다.", gameTitle);
        System.out.println(content);
        System.out.println();
    }

    public void printExceptionMessage(String message) {
        System.out.println(message);
    }

    private void writeOneLineInBoard(int y, List<Piece> pieces) {
        System.out.print(unicodes.get(y));
        for (int x = 0; x < 9; x++) {
            int xPosition = x;
            pieces.stream()
                    .filter(piece -> piece.getPosition().x() == xPosition)
                    .findFirst()
                    .ifPresentOrElse(
                            piece -> System.out.print(piece.getType().getName()),
                            () -> System.out.print(SQUARE)
                    );
        }
        System.out.println();
    }

    private static void writeBoardYHeader() {
        System.out.print(SPACE);
        for (int i = 0; i < 9; i++) {
            System.out.print(unicodes.get(i));
        }
        System.out.println();
    }
}
