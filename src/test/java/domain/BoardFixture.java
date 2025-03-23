package domain;

import domain.board.Board;
import domain.piece.pathPiece.Cha;
import domain.piece.noPathPiece.Goong;
import domain.piece.Piece;
import java.util.HashMap;
import java.util.Map;

public class BoardFixture {

    private final Map<Coordinate, Piece> pieces;

    public BoardFixture() {
        pieces = new HashMap<>();
    }

    public BoardFixture addPiece(Piece piece) {
        pieces.put(piece.getCoordinate(), piece);
        return this;
    }

    public BoardFixture addPiece(int x, int y, Piece piece) {
        pieces.put(new Coordinate(x, y), piece);
        return this;
    }

    public BoardFixture anyPiece(int x, int y) {
        pieces.put(new Coordinate(x, y), new Goong(Team.HAN, new Coordinate(x, y)));
        return this;
    }

    public BoardFixture teamPieceAt(int x, int y, Team team) {
        pieces.put(new Coordinate(x, y), new Cha(team, new Coordinate(x, y)));
        return this;
    }

    public Board build() {
        return new Board(pieces);
    }

    public static Board emptyBoard() {
        return new Board(new HashMap<>());
    }
}
