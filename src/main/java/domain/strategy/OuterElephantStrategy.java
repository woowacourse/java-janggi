package domain.strategy;

import domain.Player;
import domain.Team;
import domain.direction.PieceDirection;
import domain.piece.Piece;
import domain.piece.Pieces;
import domain.piece.category.Cannon;
import domain.piece.category.Chariot;
import domain.piece.category.Elephant;
import domain.piece.category.Guard;
import domain.piece.category.Horse;
import domain.piece.category.King;
import domain.piece.category.Soldier;
import domain.spatial.Position;
import java.util.ArrayList;
import java.util.List;

public class OuterElephantStrategy implements SettingUpStrategy {

    @Override
    public Pieces initPieces(final Player player) {
        if (player.team().equals(Team.HAN)) {
            return initHanPieces();
        }
        return initChoPieces();
    }

    private Pieces initHanPieces() {
        List<Piece> hanPieces = new ArrayList<>();
        hanPieces.add(new King(new Position(5, 2), PieceDirection.KING.get()));

        hanPieces.add(new Chariot(new Position(1, 1), PieceDirection.CHARIOT.get()));
        hanPieces.add(new Chariot(new Position(9, 1), PieceDirection.CHARIOT.get()));

        hanPieces.add(new Cannon(new Position(2, 3), PieceDirection.CANNON.get()));
        hanPieces.add(new Cannon(new Position(8, 3), PieceDirection.CANNON.get()));

        hanPieces.add(new Horse(new Position(3, 1), PieceDirection.HORSE.get()));
        hanPieces.add(new Horse(new Position(7, 1), PieceDirection.HORSE.get()));

        hanPieces.add(new Elephant(new Position(2, 1), PieceDirection.ELEPHANT.get()));
        hanPieces.add(new Elephant(new Position(8, 1), PieceDirection.ELEPHANT.get()));

        hanPieces.add(new Guard(new Position(4, 1), PieceDirection.GUARD.get()));
        hanPieces.add(new Guard(new Position(6, 1), PieceDirection.GUARD.get()));

        hanPieces.add(new Soldier(new Position(1, 4), PieceDirection.HAN_SOLDIER.get()));
        hanPieces.add(new Soldier(new Position(3, 4), PieceDirection.HAN_SOLDIER.get()));
        hanPieces.add(new Soldier(new Position(5, 4), PieceDirection.HAN_SOLDIER.get()));
        hanPieces.add(new Soldier(new Position(7, 4), PieceDirection.HAN_SOLDIER.get()));
        hanPieces.add(new Soldier(new Position(9, 4), PieceDirection.HAN_SOLDIER.get()));
        return new Pieces(hanPieces);
    }

    private Pieces initChoPieces() {
        List<Piece> choPieces = new ArrayList<>();
        choPieces.add(new King(new Position(5, 9), PieceDirection.KING.get()));

        choPieces.add(new Chariot(new Position(1, 10), PieceDirection.CHARIOT.get()));
        choPieces.add(new Chariot(new Position(9, 10), PieceDirection.CHARIOT.get()));

        choPieces.add(new Cannon(new Position(2, 8), PieceDirection.CANNON.get()));
        choPieces.add(new Cannon(new Position(8, 8), PieceDirection.CANNON.get()));

        choPieces.add(new Horse(new Position(3, 10), PieceDirection.HORSE.get()));
        choPieces.add(new Horse(new Position(7, 10), PieceDirection.HORSE.get()));

        choPieces.add(new Elephant(new Position(2, 10), PieceDirection.ELEPHANT.get()));
        choPieces.add(new Elephant(new Position(8, 10), PieceDirection.ELEPHANT.get()));

        choPieces.add(new Guard(new Position(4, 10), PieceDirection.GUARD.get()));
        choPieces.add(new Guard(new Position(6, 10), PieceDirection.GUARD.get()));

        choPieces.add(new Soldier(new Position(1, 7), PieceDirection.CHO_SOLDIER.get()));
        choPieces.add(new Soldier(new Position(3, 7), PieceDirection.CHO_SOLDIER.get()));
        choPieces.add(new Soldier(new Position(5, 7), PieceDirection.CHO_SOLDIER.get()));
        choPieces.add(new Soldier(new Position(7, 7), PieceDirection.CHO_SOLDIER.get()));
        choPieces.add(new Soldier(new Position(9, 7), PieceDirection.CHO_SOLDIER.get()));

        return new Pieces(choPieces);
    }
}
