package janggi.view;

import janggi.domain.Team;
import janggi.domain.piece.Cannon;
import janggi.domain.piece.Chariot;
import janggi.domain.piece.Elephant;
import janggi.domain.piece.General;
import janggi.domain.piece.Guard;
import janggi.domain.piece.Horse;
import janggi.domain.piece.Piece;
import janggi.domain.piece.direction.Position;
import janggi.domain.piece.direction.Route;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class OutputView {

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_BLUE = "\u001B[34m";

    public void printBoard(final List<Piece> pieces) {
        final int startX = 0;
        final int startY = 9;

        for (int i = startY; i >= 0; i--) {
            for (int j = startX; j <= 8; j++) {
                boolean check = true;
                for (final Piece piece : pieces) {
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

    public void printPossibleRoutes(final Set<Route> possibleRoutes) {
        System.out.println("이 기물은 ");

        final List<Route> sortedRoutes = new ArrayList<>(possibleRoutes);
        sortedRoutes.sort((route1, route2) -> {
            final Position pos1 = route1.getDestination();
            final Position pos2 = route2.getDestination();

            if (pos1.x() != pos2.x()) {
                return Integer.compare(pos1.x(), pos2.x());
            }
            return Integer.compare(pos1.y(), pos2.y());
        });
        for (final Route route : sortedRoutes) {
            final Position destination = route.getDestination();
            System.out.printf("%d %d%n", destination.x(), destination.y());
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

    private String colorPiece(final Piece piece) {
        if (piece.isSameTeam(Team.RED)) {
            return String.format(ANSI_RED + getPieceName(piece) + ANSI_RESET);
        }
        return String.format(ANSI_BLUE + getPieceName(piece) + ANSI_RESET);
    }

    private String getPieceName(final Piece piece) {
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

    public void printErrorMessage(final String message) {
        System.out.println(message);
    }
}
