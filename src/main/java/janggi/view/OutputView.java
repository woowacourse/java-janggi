package janggi.view;

import janggi.domain.Team;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.position.Position;
import java.util.List;
import java.util.Set;

public class OutputView {

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String HAN_DYNASTY = ANSI_RED + "한나라" + ANSI_RESET;
    private static final String CHU_DYNASTY = ANSI_BLUE + "초나라" + ANSI_RESET;

    public void printBoard(List<Piece> pieces) {
        int startX = 0;
        int startY = 9;

        System.out.print("  ");

        for (int j = startX; j <= 8; j++) {
            System.out.print(j + " ");
        }
        System.out.println();

        for (int i = startY; i >= 0; i--) {
            System.out.print(i + " ");

            for (int j = startX; j <= 8; j++) {
                boolean check = true;
                for (Piece piece : pieces) {
                    if (piece.isSamePosition(new Position(j, i))) {
                        System.out.print(colorPiece(piece));
                        check = false;
                    }
                }
                if (check) {
                    System.out.print("．");
                }
            }
            System.out.println();
        }

        System.out.print("  ");

        for (int j = startX; j <= 8; j++) {
            System.out.print(j + " ");
        }
        System.out.println();
    }

    public void printCannotMove() {
        System.out.println("해당 기물은 움직일 수 없습니다.");
    }

    public void printPossibleRoutes(Set<Position> possibleDestinations) {
        System.out.println("이 기물은 ");
        for (Position possibleDestination : possibleDestinations) {
            System.out.printf("%d %d%n", possibleDestination.x(), possibleDestination.y());
        }
        System.out.println("로 이동할 수 있습니다.");
    }

    public void printTurn(final Team currentTurn) {
        if (currentTurn == Team.RED) {
            System.out.println(HAN_DYNASTY + " 차례입니다.");
            return;
        }
        System.out.println(CHU_DYNASTY + " 차례입니다.");
    }

    private String colorPiece(Piece piece) {
        if (piece.isSameTeam(Team.RED)) {
            return String.format(ANSI_RED + getPieceName(piece) + ANSI_RESET);
        }
        return String.format(ANSI_BLUE + getPieceName(piece) + ANSI_RESET);
    }

    private String getPieceName(Piece piece) {
        if (piece.isSameType(PieceType.GENERAL)) {
            return "왕";
        }

        if (piece.isSameType(PieceType.CHARIOT)) {
            return "차";
        }

        if (piece.isSameType(PieceType.ELEPHANT)) {
            return "상";
        }

        if (piece.isSameType(PieceType.HORSE)) {
            return "마";
        }

        if (piece.isSameType(PieceType.CANNON)) {
            return "포";
        }

        if (piece.isSameType(PieceType.GUARD)) {
            return "사";
        }
        return "졸";
    }
}
