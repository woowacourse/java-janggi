package domain.strategy;

import domain.Player;
import domain.direction.PieceDirection;
import domain.piece.Piece;
import domain.piece.Pieces;
import domain.piece.category.Elephant;
import domain.piece.category.Horse;
import domain.spatial.Position;
import java.util.List;

public class RightElephantInitializer extends SettingUpInitializer {

    @Override
    public Pieces initPieces(final Player player) {
        if (player.isHanTeam()) {
            return initHanPieces();
        }
        return initChoPieces();
    }

    private Pieces initHanPieces() {
        List<Piece> pieces = initHanBasicPieces();

        pieces.add(new Horse(new Position(2, 1), PieceDirection.HORSE.get()));
        pieces.add(new Horse(new Position(7, 1), PieceDirection.HORSE.get()));

        pieces.add(new Elephant(new Position(3, 1), PieceDirection.ELEPHANT.get()));
        pieces.add(new Elephant(new Position(8, 1), PieceDirection.ELEPHANT.get()));

        return new Pieces(pieces);
    }

    private Pieces initChoPieces() {
        List<Piece> pieces = initChoBasicPieces();

        pieces.add(new Horse(new Position(2, 10), PieceDirection.HORSE.get()));
        pieces.add(new Horse(new Position(7, 10), PieceDirection.HORSE.get()));

        pieces.add(new Elephant(new Position(3, 10), PieceDirection.ELEPHANT.get()));
        pieces.add(new Elephant(new Position(8, 10), PieceDirection.ELEPHANT.get()));

        return new Pieces(pieces);
    }
}
