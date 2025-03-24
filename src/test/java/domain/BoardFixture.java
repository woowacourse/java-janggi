package domain;

import domain.board.Board;
import domain.piece.Piece;
import domain.piece.noPathPiece.Byeong;
import domain.piece.noPathPiece.Jol;
import domain.piece.noPathPiece.Sa;
import domain.piece.pathPiece.Cha;
import domain.piece.pathPiece.Ma;
import domain.piece.pathPiece.Sang;
import java.lang.reflect.Constructor;
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
        1, (team, x, y) -> new Cha(team, new Coordinate(x, y)),
        2, (team, x, y) -> new Cha(team, new Coordinate(x, y)),
        3, (team, x, y) -> new Ma(team, new Coordinate(x, y)),
        4, (team, x, y) -> new Ma(team, new Coordinate(x, y)),
        5, (team, x, y) -> new Sang(team, new Coordinate(x, y)),
        6, (team, x, y) -> new Sang(team, new Coordinate(x, y)),
        7, (team, x, y) -> new Sa(team, new Coordinate(x, y)),
        8, (team, x, y) -> new Sa(team, new Coordinate(x, y))
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

    public BoardFixture addPiece(int x, int y, Class<? extends Piece> pieceType, Team team) {
        final var createdPiece = createPiece(pieceType, team, x, y);
        addPiece(createdPiece);
        return this;
    }

    public BoardFixture addJol(int x, int y) {
        final var jol = new Jol(new Coordinate(x, y));
        addPiece(jol);
        return this;
    }

    public BoardFixture addByeong(int x, int y) {
        final var byeong = new Byeong(new Coordinate(x, y));
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

    private Piece createPiece(Class<? extends Piece> pieceType, Team team, int x, int y) {
        try {
            Constructor<? extends Piece> constructor =
                pieceType.getDeclaredConstructor(Team.class, Coordinate.class);
            return constructor.newInstance(team, new Coordinate(x, y));

        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("테스트 픽스쳐 : Piece 생성 중 예외 발생", e);
        }
    }
}
