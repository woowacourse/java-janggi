package domain.board;

import domain.direction.MoveAmount;
import domain.game.Side;
import domain.piece.Piece;
import domain.piece.factory.PieceFactory;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record InitialPosition(
        Side side,
        MoveAmount farFromBaseRow,
        Integer... initialFiles
) {

    public Map<Intersection, Piece> placePiece(PieceFactory factory) {
        Map<Intersection, Piece> result = new HashMap<>();

        List<Intersection> intersections = intersections();
        for (Intersection intersection : intersections) {
            result.put(intersection, factory.create(side));
        }

        return result;
    }

    private List<Intersection> intersections() {
        int row = side.farTo(farFromBaseRow);

        return Arrays.stream(initialFiles)
                .map(file -> new Intersection(row, file))
                .toList();
    }
}
