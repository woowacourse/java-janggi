package domain.strategy;

import domain.Player;
import domain.direction.PieceDirection;
import domain.piece.Piece;
import domain.piece.Pieces;
import domain.piece.category.Cannon;
import domain.piece.category.Chariot;
import domain.piece.category.Guard;
import domain.piece.category.King;
import domain.piece.category.Soldier;
import domain.spatial.Position;
import java.util.ArrayList;
import java.util.List;

public abstract class SettingUpInitializer {

    public abstract Pieces initPieces(final Player current);

    protected List<Piece> initHanBasicPieces() {
        List<Piece> pieces = new ArrayList<>();

        pieces.add(new King(new Position(5, 2), PieceDirection.KING.get()));

        pieces.add(new Chariot(new Position(1, 1), PieceDirection.CHARIOT.get()));
        pieces.add(new Chariot(new Position(9, 1), PieceDirection.CHARIOT.get()));

        pieces.add(new Cannon(new Position(2, 3), PieceDirection.CANNON.get()));
        pieces.add(new Cannon(new Position(8, 3), PieceDirection.CANNON.get()));

        pieces.add(new Guard(new Position(4, 1), PieceDirection.GUARD.get()));
        pieces.add(new Guard(new Position(6, 1), PieceDirection.GUARD.get()));

        pieces.add(new Soldier(new Position(1, 4), PieceDirection.HAN_SOLDIER.get()));
        pieces.add(new Soldier(new Position(3, 4), PieceDirection.HAN_SOLDIER.get()));
        pieces.add(new Soldier(new Position(5, 4), PieceDirection.HAN_SOLDIER.get()));
        pieces.add(new Soldier(new Position(7, 4), PieceDirection.HAN_SOLDIER.get()));
        pieces.add(new Soldier(new Position(9, 4), PieceDirection.HAN_SOLDIER.get()));

        return pieces;
    }

    protected List<Piece> initChoBasicPieces() {
        List<Piece> pieces = new ArrayList<>();

        pieces.add(new King(new Position(5, 9), PieceDirection.KING.get()));

        pieces.add(new Chariot(new Position(1, 10), PieceDirection.CHARIOT.get()));
        pieces.add(new Chariot(new Position(9, 10), PieceDirection.CHARIOT.get()));

        pieces.add(new Cannon(new Position(2, 8), PieceDirection.CANNON.get()));
        pieces.add(new Cannon(new Position(8, 8), PieceDirection.CANNON.get()));

        pieces.add(new Guard(new Position(4, 10), PieceDirection.GUARD.get()));
        pieces.add(new Guard(new Position(6, 10), PieceDirection.GUARD.get()));

        pieces.add(new Soldier(new Position(1, 7), PieceDirection.CHO_SOLDIER.get()));
        pieces.add(new Soldier(new Position(3, 7), PieceDirection.CHO_SOLDIER.get()));
        pieces.add(new Soldier(new Position(5, 7), PieceDirection.CHO_SOLDIER.get()));
        pieces.add(new Soldier(new Position(7, 7), PieceDirection.CHO_SOLDIER.get()));
        pieces.add(new Soldier(new Position(9, 7), PieceDirection.CHO_SOLDIER.get()));

        return pieces;
    }
}
