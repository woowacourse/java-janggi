package fixture;

import domain.coordination.Coordination;
import domain.piece.Piece;
import java.util.List;
import java.util.Map;

public class PiecePathFinder {

    public static List<Piece> piecesOnPath(
            Piece piece,
            Coordination from,
            Coordination to,
            Map<Coordination, Piece> board
    ) {
        return piece.resolvePath(MoveContextFactory.create(from, to)).stream()
                .map(board::get)
                .filter(pathPiece -> !pathPiece.isEmpty())
                .toList();
    }
}
