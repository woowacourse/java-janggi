package fixture;

import janggi.coordinate.Position;
import janggi.piece.Cannon;
import janggi.piece.Chariot;
import janggi.piece.Elephant;
import janggi.piece.General;
import janggi.piece.Guard;
import janggi.piece.Horse;
import janggi.piece.Piece;
import janggi.piece.PieceType;
import janggi.piece.Soldier;
import janggi.player.Team;

public class PieceFixture {

    public static Piece createPiece(final int row, final int column, final PieceType pieceType, final Team team) {
        return switch (pieceType) {
            case SOLDIER -> Soldier.of(Position.of(row, column), team);
            case GUARD -> Guard.of(Position.of(row, column), team);
            case HORSE -> Horse.of(Position.of(row, column), team);
            case ELEPHANT -> Elephant.of(Position.of(row, column), team);
            case CHARIOT -> Chariot.of(Position.of(row, column), team);
            case CANNON -> Cannon.of(Position.of(row, column), team);
            case GENERAL -> General.of(Position.of(row, column), team);
        };
    }
}
