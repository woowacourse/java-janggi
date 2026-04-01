package domain.board;

import domain.board.wing.Wings;
import domain.direction.MoveAmount;
import domain.game.Side;
import domain.piece.Piece;
import domain.piece.factory.CannonFactory;
import domain.piece.factory.ChariotFactory;
import domain.piece.factory.GeneralFactory;
import domain.piece.factory.GuardFactory;
import domain.piece.factory.SoldierFactory;
import java.util.HashMap;
import java.util.Map;

public class InitialPieces {

    private final Map<Intersection, Piece> initialPieces = new HashMap<>();

    public InitialPieces(Wings hanWings, Wings choWings) {
        initialPieces.putAll(hanWings.setUpPieces());
        initialPieces.putAll(choWings.setUpPieces());

        putFixedPieces(Side.HAN);
        putFixedPieces(Side.CHO);
    }

    private void putFixedPieces(Side side) {
        initialPieces.putAll(
                new InitialPosition(side, new MoveAmount(3), 1, 3, 5, 7, 9).placePiece(new SoldierFactory())
        );
        initialPieces.putAll(
                new InitialPosition(side, new MoveAmount(2), 2, 8).placePiece(new CannonFactory())
        );
        initialPieces.putAll(
                new InitialPosition(side, new MoveAmount(0), 1, 9).placePiece(new ChariotFactory())
        );
        initialPieces.putAll(
                new InitialPosition(side, new MoveAmount(0), 4, 6).placePiece(new GuardFactory())
        );
        initialPieces.putAll(
                new InitialPosition(side, new MoveAmount(1), 5).placePiece(new GeneralFactory())
        );
    }

    public int size() {
        return initialPieces.size();
    }

    public Map<Intersection, Piece> toMap() {
        return Map.copyOf(initialPieces);
    }
}
