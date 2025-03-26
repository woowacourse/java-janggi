package janggi.domain;

import janggi.domain.board.Board;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class BoardFixture {

    private interface TriFunction<T, U, V, R> {

        R apply(T t, U u, V v);
    }

    private static final Map<Integer, TriFunction<Team, Integer, Integer, Piece>> randomMap
        = Map.of(
        1, (team, x, y) -> new Piece(team, new Coordinate(x, y), PieceType.CHA),
        2, (team, x, y) -> new Piece(team, new Coordinate(x, y), PieceType.CHA),
        3, (team, x, y) -> new Piece(team, new Coordinate(x, y), PieceType.MA),
        4, (team, x, y) -> new Piece(team, new Coordinate(x, y), PieceType.MA),
        5, (team, x, y) -> new Piece(team, new Coordinate(x, y), PieceType.SANG),
        6, (team, x, y) -> new Piece(team, new Coordinate(x, y), PieceType.SANG),
        7, (team, x, y) -> new Piece(team, new Coordinate(x, y), PieceType.SA),
        8, (team, x, y) -> new Piece(team, new Coordinate(x, y), PieceType.SA)
    );

    private final Set<Piece> pieces = new HashSet<>();

    // 테스트 메서드 가독성을 위해 명시할 수 있도록 x, y를 받고, 무시합니다.
    public BoardFixture addPiece(int x, int y, Piece piece) {
        addPiece(piece);
        return this;
    }

    private BoardFixture addPiece(Piece piece) {
        pieces.add(piece);
        return this;
    }

    public BoardFixture addPiece(int x, int y, PieceType pieceType, Team team) {
        final var createdPiece = createPiece(pieceType, team, x, y);
        addPiece(createdPiece);
        return this;
    }

    public BoardFixture addJol(int x, int y) {
        final var jol = new Piece(Team.CHO, new Coordinate(x, y), PieceType.JOL);
        addPiece(jol);
        return this;
    }

    public BoardFixture addByeong(int x, int y) {
        final var byeong = new Piece(Team.HAN, new Coordinate(x, y), PieceType.BYEONG);
        addPiece(byeong);
        return this;
    }

    public BoardFixture anyPieceNotPo(int x, int y) {
        return anyPieceNotPo(x, y, randomTeam());
    }

    public BoardFixture anyPieceNotPo(int x, int y, Team team) {
        int random = new Random().nextInt(8) + 1;
        Piece piece = randomMap.get(random).apply(team, x, y);
        addPiece(piece);
        return this;
    }

    private Team randomTeam() {
        boolean isRandomTeamHAN = new Random().nextBoolean();
        if (isRandomTeamHAN) {
            return Team.HAN;
        }
        return Team.CHO;
    }

    public Board build() {
        return new Board(pieces);
    }

    public static Board emptyBoard() {
        return new Board(new HashSet<>());
    }

    private Piece createPiece(PieceType pieceType, Team team, int x, int y) {
        return new Piece(team, new Coordinate(x, y), pieceType);
    }
}
