package game.domain.board;

import game.domain.piece.Piece;
import java.util.List;

public interface PieceExtractor {

    List<Piece> extract(List<BoardLocation> boardLocations);
}
