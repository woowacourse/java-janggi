package application.persistence;

import domain.piece.coordiante.Coordinate;
import domain.game.Game;
import domain.piece.Piece;
import java.util.Map;

public interface PieceRepository {

    void savePieces(Map<Coordinate, Piece> pieces, Game game);

    void deleteByGame(Game game);

    Map<Coordinate, Piece> findByGame(Game game);
}
