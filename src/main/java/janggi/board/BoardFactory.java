package janggi.board;

import janggi.piece.Byeong;
import janggi.piece.Cannon;
import janggi.piece.Chariot;
import janggi.piece.Elephant;
import janggi.piece.Guard;
import janggi.piece.Horse;
import janggi.piece.Jol;
import janggi.piece.King;
import janggi.piece.Piece;
import janggi.piece.Team;
import janggi.position.Position;
import java.util.HashMap;
import java.util.Map;

public class BoardFactory {

    public Board makeBoard() {
        Map<Position, Piece> pieces = new HashMap<>();
        makeHanPieces(pieces);
        makeChoPieces(pieces);
        return new Board(pieces);
    }

    private static void makeChoPieces(Map<Position, Piece> pieces) {
        pieces.put(new Position(10, 1), new Chariot(Team.CHO));
        pieces.put(new Position(10, 2), new Horse(Team.CHO));
        pieces.put(new Position(10, 3), new Elephant(Team.CHO));
        pieces.put(new Position(10, 4), new Guard(Team.CHO));
        pieces.put(new Position(10, 6), new Guard(Team.CHO));
        pieces.put(new Position(10, 7), new Elephant(Team.CHO));
        pieces.put(new Position(10, 8), new Horse(Team.CHO));
        pieces.put(new Position(10, 9), new Chariot(Team.CHO));

        pieces.put(new Position(9, 5), new King(Team.CHO));
        pieces.put(new Position(8, 2), new Cannon(Team.CHO));
        pieces.put(new Position(8, 8), new Cannon(Team.CHO));

        pieces.put(new Position(7, 1), new Jol());
        pieces.put(new Position(7, 3), new Jol());
        pieces.put(new Position(7, 5), new Jol());
        pieces.put(new Position(7, 7), new Jol());
        pieces.put(new Position(7, 9), new Jol());
    }

    private static void makeHanPieces(Map<Position, Piece> pieces) {
        pieces.put(new Position(1, 1), new Chariot(Team.HAN));
        pieces.put(new Position(1, 2), new Horse(Team.HAN));
        pieces.put(new Position(1, 3), new Elephant(Team.HAN));
        pieces.put(new Position(1, 4), new Guard(Team.HAN));
        pieces.put(new Position(1, 6), new Guard(Team.HAN));
        pieces.put(new Position(1, 7), new Elephant(Team.HAN));
        pieces.put(new Position(1, 8), new Horse(Team.HAN));
        pieces.put(new Position(1, 9), new Chariot(Team.HAN));

        pieces.put(new Position(2, 5), new King(Team.HAN));
        pieces.put(new Position(3, 2), new Cannon(Team.HAN));
        pieces.put(new Position(3, 8), new Cannon(Team.HAN));

        pieces.put(new Position(4, 1), new Byeong());
        pieces.put(new Position(4, 3), new Byeong());
        pieces.put(new Position(4, 5), new Byeong());
        pieces.put(new Position(4, 7), new Byeong());
        pieces.put(new Position(4, 9), new Byeong());
    }
}
