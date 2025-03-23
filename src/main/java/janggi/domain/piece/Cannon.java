package janggi.domain.piece;

import janggi.domain.Board;
import janggi.domain.Team;
import janggi.domain.position.Position;
import janggi.domain.position.RawPosition;
import janggi.domain.position.RawRoute;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Cannon extends Piece {

    public Cannon(final Position position, final Team team) {
        super(position, team);
        this.pieceType = PieceType.CANNON;
    }

    protected Set<RawRoute> calculateRawRoutes() {
        Set<RawRoute> rawRoutes = new HashSet<>();

        for (int i = position.x() + 1; i <= Board.MAX_X_POSITION; i++) {
            List<RawPosition> rawPositions = new ArrayList<>();
            for (int j = position.x() + 1; j <= i; j++) {
                rawPositions.add(new RawPosition(j, position.y()));
            }
            rawRoutes.add(new RawRoute(rawPositions));
        }

        for (int i = position.x() - 1; i >= Board.MIN_POSITION; i--) {
            List<RawPosition> rawPositions = new ArrayList<>();
            for (int j = position.x() - 1; j >= i; j--) {
                rawPositions.add(new RawPosition(j, position.y()));
            }
            rawRoutes.add(new RawRoute(rawPositions));
        }

        for (int i = position.y() + 1; i <= Board.MAX_Y_POSITION; i++) {
            List<RawPosition> rawPositions = new ArrayList<>();
            for (int j = position.y() + 1; j <= i; j++) {
                rawPositions.add(new RawPosition(position.x(), j));
            }
            rawRoutes.add(new RawRoute(rawPositions));
        }

        for (int i = position.y() - 1; i >= Board.MIN_POSITION; i--) {
            List<RawPosition> rawPositions = new ArrayList<>();
            for (int j = position.y() - 1; j >= i; j--) {
                rawPositions.add(new RawPosition(position.x(), j));
            }
            rawRoutes.add(new RawRoute(rawPositions));
        }
        return rawRoutes;
    }
}
