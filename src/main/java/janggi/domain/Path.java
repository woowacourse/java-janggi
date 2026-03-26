package janggi.domain;

import janggi.domain.piece.Piece;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Path {

    private final List<Position> path;

    public Path(List<Position> path) {
        this.path = path;
    }

    public List<Piece> getBlockedPieces(Map<Position, Space> piecesInfo) {
        List<Piece> pieces = new ArrayList<>();

        for (Position position : path) {
            Space routeSpace = piecesInfo.get(position);
            if (routeSpace.isBlank()) {
                continue;
            }
            pieces.add((Piece) routeSpace);
        }
        return pieces;
    }
}
