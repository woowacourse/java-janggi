package view;

import model.Piece;

import java.util.Optional;

public class OutputView {

    public void printJanggiStart() {
        System.out.println("장기 시작");
    }

    public void printPieceOrHyphen(Optional<Piece> piece) {
        if (piece.isPresent()) {
            System.out.print(piece.get());
        } else {
            System.out.print("－");
        }
    }

    public void printBlankLine() {
        System.out.println();
    }
}
