package janggi.piece;

public class PieceFactory {

    public static Piece createPiece(String symbol, String side) {
        Symbol pieceSymbol = Symbol.valueOf(symbol);
        Side pieceSide = Side.valueOf(side);

        if (Symbol.SOLDIER == pieceSymbol) {
            return new Soldier(pieceSide);
        }
        if (Symbol.GUARD == pieceSymbol) {
            return new Guard(pieceSide);
        }
        if (Symbol.ELEPHANT == pieceSymbol) {
            return new Elephant(pieceSide);
        }
        if (Symbol.HORSE == pieceSymbol) {
            return new Horse(pieceSide);
        }
        if (Symbol.CANNON == pieceSymbol) {
            return new Cannon(pieceSide);
        }
        if (Symbol.CHARIOT == pieceSymbol) {
            return new Chariot(pieceSide);
        }
        if (Symbol.KING == pieceSymbol) {
            return new King(pieceSide);
        }
        if (Symbol.EMPTY == pieceSymbol) {
            return new Empty();
        }
        throw new IllegalArgumentException("[ERROR] 해당 기물을 생성할 수 없습니다.");
    }

}
