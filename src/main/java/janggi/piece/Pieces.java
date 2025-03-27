package janggi.piece;

import janggi.position.Position;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Pieces {
    private final Map<Position, Piece> pieces;

    public Pieces(final Map<Position, Piece> board) {
        this.pieces = new HashMap<>(board);
    }

    public static Pieces init() {
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.putAll(createRedSidePieces());
        pieces.putAll(createBlueSidePieces());
        return new Pieces(pieces);
    }

    private static Map<Position, Piece> createRedSidePieces() {
        Map<Position, Piece> board = new HashMap<>();
        board.put(new Position(1, 1), new Tank(Color.RED));
        board.put(new Position(2, 1), new Elephant(Color.RED));
        board.put(new Position(3, 1), new Horse(Color.RED));
        board.put(new Position(4, 1), new Guard(Color.RED));

        board.put(new Position(6, 1), new Guard(Color.RED));
        board.put(new Position(7, 1), new Horse(Color.RED));
        board.put(new Position(8, 1), new Elephant(Color.RED));
        board.put(new Position(9, 1), new Tank(Color.RED));

        board.put(new Position(5, 2), new King(Color.RED));

        board.put(new Position(2, 3), new Cannon(Color.RED));
        board.put(new Position(8, 3), new Cannon(Color.RED));

        board.put(new Position(1, 4), new Soldier(Color.RED));
        board.put(new Position(3, 4), new Soldier(Color.RED));
        board.put(new Position(5, 4), new Soldier(Color.RED));
        board.put(new Position(7, 4), new Soldier(Color.RED));
        board.put(new Position(9, 4), new Soldier(Color.RED));
        return board;
    }

    private static Map<Position, Piece> createBlueSidePieces() {
        Map<Position, Piece> board = new HashMap<>();
        board.put(new Position(1, 10), new Tank(Color.BLUE));
        board.put(new Position(2, 10), new Elephant(Color.BLUE));
        board.put(new Position(3, 10), new Horse(Color.BLUE));
        board.put(new Position(4, 10), new Guard(Color.BLUE));

        board.put(new Position(6, 10), new Guard(Color.BLUE));
        board.put(new Position(7, 10), new Horse(Color.BLUE));
        board.put(new Position(8, 10), new Elephant(Color.BLUE));
        board.put(new Position(9, 10), new Tank(Color.BLUE));

        board.put(new Position(5, 9), new King(Color.BLUE));

        board.put(new Position(2, 8), new Cannon(Color.BLUE));
        board.put(new Position(8, 8), new Cannon(Color.BLUE));

        board.put(new Position(1, 7), new Soldier(Color.BLUE));
        board.put(new Position(3, 7), new Soldier(Color.BLUE));
        board.put(new Position(5, 7), new Soldier(Color.BLUE));
        board.put(new Position(7, 7), new Soldier(Color.BLUE));
        board.put(new Position(9, 7), new Soldier(Color.BLUE));
        return board;
    }

    public Piece getPieceByPosition(final Position position) {
        Piece findPiece = pieces.get(position);
        if (findPiece == null) {
            throw new IllegalArgumentException("선택된 좌표에 말이 없습니다.");
        }
        return findPiece;
    }

    public boolean containsPiece(final Position position) {
        return pieces.containsKey(position);
    }

    public boolean containsPiece(final List<Position> positions) {
        return positions.stream()
                .anyMatch(pieces::containsKey);
    }

    // NOTE: 앞으로 진격하라는 의미.
    // 현재는 도착지에 적이 있다면 죽입니다.
    // 코드를 수정시 주석을 지워주세요.
    public void moveForward(final Position start, final Position end) {
        Piece startPositionPiece = getPieceByPosition(start);

        // NOTE: 컬렉션을 직접 사용하고 있습니다.
        // 검증이 필요하다면 메서드를 만들어 사용해주세요.
        pieces.remove(start);
        pieces.put(end, startPositionPiece);
    }

    public boolean isSameColorPiece(final Position start, final Position end) {
        if (!pieces.containsKey(start) || !pieces.containsKey(end)) {
            return false;
        }
        Piece startPiece = pieces.get(start);
        Piece endPiece = pieces.get(end);
        return startPiece.isSameColor(endPiece);
    }

    public Map<Position, Piece> getPieces() {
        return Collections.unmodifiableMap(pieces);
    }

    public boolean isEachCannonPiece(final Position start, final Position end) {
        if (!pieces.containsKey(start) || !pieces.containsKey(end)) {
            return false;
        }
        Piece startPiece = pieces.get(start);
        Piece endPiece = pieces.get(end);
        return startPiece.isCannon() && endPiece.isCannon();
    }

    public long countPieceOnPath(final List<Position> path) {
        return path.stream()
                .filter(pieces::containsKey)
                .count();
    }

    public boolean isCannonPieceOnPath(final List<Position> path) {
        return path.stream()
                .anyMatch(position -> pieces.containsKey(position) &&
                        pieces.get(position).isCannon());
    }

}
