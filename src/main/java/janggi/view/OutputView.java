package janggi.view;

import janggi.piece.Piece;
import java.util.ArrayList;
import java.util.List;

public class OutputView {

    public void writeStartMessage() {
        System.out.println("장기 게임을 시작하겠습니다!");
    }

    public void writeJanggiBoard(final List<Piece> choPieces, final List<Piece> hanPieces) {
        List<Piece> allPieces = new ArrayList<>();
        allPieces.addAll(choPieces);
        allPieces.addAll(hanPieces);


    }

}
