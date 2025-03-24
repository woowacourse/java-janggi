package domain;

import domain.board.Board;
import domain.piece.Piece;
import domain.piece.noPathPiece.Byeong;
import domain.piece.noPathPiece.Goong;
import domain.piece.noPathPiece.Jol;
import domain.piece.noPathPiece.Sa;
import domain.piece.pathPiece.Cha;
import domain.piece.pathPiece.Ma;
import domain.piece.pathPiece.Sang;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.function.BiFunction;

public class BoardFixture {


    private static final Map<Integer, BiFunction<Integer, Integer, Piece>> randomMap
        = Map.of(
        1, (x, y) -> new Cha(Team.HAN, new Coordinate(x, y)),
        2, (x, y) -> new Cha(Team.CHO, new Coordinate(x, y)),
        3, (x, y) -> new Ma(Team.HAN, new Coordinate(x, y)),
        4, (x, y) -> new Ma(Team.CHO, new Coordinate(x, y)),
        5, (x, y) -> new Sang(Team.HAN, new Coordinate(x, y)),
        6, (x, y) -> new Sang(Team.CHO, new Coordinate(x, y)),
        7, (x, y) -> new Sa(Team.HAN, new Coordinate(x, y)),
        8, (x, y) -> new Sa(Team.CHO, new Coordinate(x, y)),
        9, (x, y) -> new Jol(new Coordinate(x, y)),
        10, (x, y) -> new Byeong(new Coordinate(x, y))
    );

    private final Set<Piece> pieces = new HashSet<>();

    public BoardFixture addPiece(Piece piece) {
        pieces.add(piece);
        return this;
    }

    public BoardFixture addPiece(int x, int y, Piece piece) {
        pieces.add(piece);
        return this;
    }

    public BoardFixture anyPiece(int x, int y) {
        int random = new Random().nextInt(10) + 1;
        Piece piece = randomMap.get(random).apply(x, y);
        pieces.add(piece);
        return this;
    }

    public BoardFixture teamPieceAt(int x, int y, Team team) {
        pieces.add(new Cha(team, new Coordinate(x, y)));
        return this;
    }

    public Board build() {
        return new Board(pieces);
    }

    public static Board emptyBoard() {
        return new Board(new HashSet<>());
    }
}
