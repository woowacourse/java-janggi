package janggi.view;

import janggi.domain.Route;
import janggi.domain.Team;
import janggi.domain.piece.Cannon;
import janggi.domain.piece.Chariot;
import janggi.domain.piece.Elephant;
import janggi.domain.piece.General;
import janggi.domain.piece.Guard;
import janggi.domain.piece.Horse;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Position;
import java.util.List;
import java.util.Set;

public class OutputView {

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_BLUE = "\u001B[34m";

    public void printBoard(List<Piece> pieces) {
        int startX = 0;
        int startY = 9;

        for (int i = startY; i >= 0; i--) {
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
    }

    public void printPossibleRoutes(Set<Route> possibleRoutes) {
        System.out.println("이 기물은 ");
        for (Route possibleRoute : possibleRoutes) {
            Position destination = possibleRoute.getDestination();
            System.out.println(String.format("%d %d", destination.x(), destination.y()));
        }
        System.out.println("로 이동할 수 있습니다.");
    }

    public void printTurn(final Team currentTurn) {
        if (currentTurn == Team.RED) {
            System.out.println("한나라 차례입니다.");
            return;
        }
        System.out.println("초나라 차례입니다.");
    }

    private String colorPiece(Piece piece) {
        if (piece.isSameTeam(Team.RED)) {
            return String.format(ANSI_RED + getPieceName(piece) + ANSI_RESET);
        }
        return String.format(ANSI_BLUE + getPieceName(piece) + ANSI_RESET);
    }

    private String getPieceName(Piece piece) {
        if (piece.getClass() == General.class) {
            return "왕";
        }

        if (piece.getClass() == Chariot.class) {
            return "차";
        }

        if (piece.getClass() == Elephant.class) {
            return "상";
        }

        if (piece.getClass() == Horse.class) {
            return "마";
        }

        if (piece.getClass() == Cannon.class) {
            return "포";
        }

        if (piece.getClass() == Guard.class) {
            return "사";
        }

        return "졸";

    }
}
