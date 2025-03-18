package janggi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JanggiGame {
    List<Piece> choPieces = new ArrayList<>();
    List<Piece> handPieces = new ArrayList<>();

    public JanggiGame(AssignType choAssignType, AssignType hanAssignType) {
        if(choAssignType == AssignType.LEFT_TOP) {
            choPieces.add(new Piece(PieceType.SANG, new Position(1,9)));
            choPieces.add(new Piece(PieceType.MA, new Position(2,9)));
            choPieces.add(new Piece(PieceType.SANG, new Position(6,9)));
            choPieces.add(new Piece(PieceType.MA, new Position(7,9)));
        }
        if(choAssignType == AssignType.RIGHT_TOP) {
            choPieces.add(new Piece(PieceType.MA, new Position(1,9)));
            choPieces.add(new Piece(PieceType.SANG, new Position(2,9)));
            choPieces.add(new Piece(PieceType.MA, new Position(6,9)));
            choPieces.add(new Piece(PieceType.SANG, new Position(7,9)));
        }
        if(choAssignType == AssignType.IN_TOP) {
            choPieces.add(new Piece(PieceType.MA, new Position(1,9)));
            choPieces.add(new Piece(PieceType.SANG, new Position(2,9)));
            choPieces.add(new Piece(PieceType.SANG, new Position(6,9)));
            choPieces.add(new Piece(PieceType.MA, new Position(7,9)));

        }
        if(choAssignType == AssignType.OUT_TOP) {
            choPieces.add(new Piece(PieceType.SANG, new Position(1,9)));
            choPieces.add(new Piece(PieceType.MA, new Position(2,9)));
            choPieces.add(new Piece(PieceType.MA, new Position(6,9)));
            choPieces.add(new Piece(PieceType.SANG, new Position(7,9)));
        }
    }

    public List<Piece> getChoPieces() {
        return Collections.unmodifiableList(choPieces);
    }

    public List<Piece> getHandPieces() {
        return Collections.unmodifiableList(handPieces);
    }
}
