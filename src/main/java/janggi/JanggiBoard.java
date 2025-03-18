package janggi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JanggiBoard {

    private final List<Piece> choPieces = new ArrayList<>();
    private final List<Piece> hanPieces = new ArrayList<>();

    public JanggiBoard(AssignType choAssignType, AssignType hanAssignType) {
        choPieces.addAll(choAssignType.makeAssign(CampType.CHO));
        hanPieces.addAll(hanAssignType.makeAssign(CampType.HAN));
    }

    public List<Piece> getChoPieces() {
        return Collections.unmodifiableList(choPieces);
    }

    public List<Piece> getHanPieces() {
        return Collections.unmodifiableList(hanPieces);
    }
}
