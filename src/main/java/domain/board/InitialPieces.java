package domain.board;

import domain.game.Side;
import domain.piece.AlivePieces;
import domain.piece.Cannon;
import domain.piece.Chariot;
import domain.piece.General;
import domain.piece.Guard;
import domain.piece.Piece;
import domain.piece.Soldier;
import domain.piece.StaticPositionedPiece;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InitialPieces {

    private static final List<StaticPositionedPiece> HAN_PIECES = List.of(
            new Cannon(Side.HAN),
            new Chariot(Side.HAN),
            new General(Side.HAN),
            new Guard(Side.HAN),
            new Soldier(Side.HAN)
    );
    private static final List<StaticPositionedPiece> CHO_PIECES = List.of(
            new Cannon(Side.CHO),
            new Chariot(Side.CHO),
            new General(Side.CHO),
            new Guard(Side.CHO),
            new Soldier(Side.CHO)
    );

    private final Map<Intersection, Piece> initialPieces = new HashMap<>();

    public InitialPieces(HanWings hanWings, ChoWings choWings) {
        initialPieces.putAll(hanWings.setUpPieces());
        initialPieces.putAll(choWings.setUpPieces());

        putFixedPieces(HAN_PIECES);
        putFixedPieces(CHO_PIECES);
    }

    private void putFixedPieces(List<StaticPositionedPiece> pieces) {
        for (StaticPositionedPiece piece : pieces) {
            piece.initAt()
                    .forEach(intersection -> initialPieces.put(intersection, piece));
        }
    }

    public AlivePieces get() {
        return new AlivePieces(initialPieces);
    }
}
