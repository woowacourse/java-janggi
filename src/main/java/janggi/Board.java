package janggi;

import janggi.piece.Cannon;
import janggi.piece.Elephant;
import janggi.piece.Guard;
import janggi.piece.Horse;
import janggi.piece.King;
import janggi.piece.Piece;
import janggi.piece.Soldier;
import janggi.piece.Tank;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {

    private final Map<Position, Piece> board;

    public Board(final Map<Position, Piece> board) {
        this.board = new HashMap<>(board);
    }

    public static Board init() {
        Map<Position, Piece> board = new HashMap<>();

        board.put(new Position(1, 1), new Tank(Side.BLUE));
        board.put(new Position(2, 1), new Elephant(Side.BLUE));
        board.put(new Position(3, 1), new Horse(Side.BLUE));
        board.put(new Position(4, 1), new Guard(Side.BLUE));

        board.put(new Position(6, 1), new Guard(Side.BLUE));
        board.put(new Position(7, 1), new Horse(Side.BLUE));
        board.put(new Position(8, 1), new Elephant(Side.BLUE));
        board.put(new Position(9, 1), new Tank(Side.BLUE));

        board.put(new Position(5, 2), new King(Side.BLUE));

        board.put(new Position(2, 3), new Cannon(Side.BLUE));
        board.put(new Position(8, 3), new Cannon(Side.BLUE));

        board.put(new Position(1, 4), new Soldier(Side.BLUE));
        board.put(new Position(3, 4), new Soldier(Side.BLUE));
        board.put(new Position(5, 4), new Soldier(Side.BLUE));
        board.put(new Position(7, 4), new Soldier(Side.BLUE));
        board.put(new Position(9, 4), new Soldier(Side.BLUE));

        board.put(new Position(1, 10), new Tank(Side.RED));
        board.put(new Position(2, 10), new Elephant(Side.RED));
        board.put(new Position(3, 10), new Horse(Side.RED));
        board.put(new Position(4, 10), new Guard(Side.RED));

        board.put(new Position(6, 10), new Guard(Side.RED));
        board.put(new Position(7, 10), new Horse(Side.RED));
        board.put(new Position(8, 10), new Elephant(Side.RED));
        board.put(new Position(9, 10), new Tank(Side.RED));

        board.put(new Position(5, 9), new King(Side.RED));

        board.put(new Position(2, 8), new Cannon(Side.RED));
        board.put(new Position(8, 8), new Cannon(Side.RED));

        board.put(new Position(1, 7), new Soldier(Side.RED));
        board.put(new Position(3, 7), new Soldier(Side.RED));
        board.put(new Position(5, 7), new Soldier(Side.RED));
        board.put(new Position(7, 7), new Soldier(Side.RED));
        board.put(new Position(9, 7), new Soldier(Side.RED));
        return new Board(board);
    }

    public void move(final Position start, final Position end) {
        validatePieceExistsByPosition(start);
        Piece pickedPiece = board.get(start);
        if (board.containsKey(end)) {
            Piece targetPiece = board.get(end);
            validateTargetPiece(pickedPiece, targetPiece);
        }

        validatePieceOnPath(start, end, pickedPiece);

        board.remove(start);
        board.put(end, pickedPiece);
    }

    private void validatePieceOnPath(final Position start, final Position end, final Piece pickedPiece) {
        List<Position> path = pickedPiece.calculatePath(start, end);
        if (pickedPiece instanceof Cannon) {
            validateNotExistsCannonOnPath(path);
            validateOnePieceOnCannonPath(path);
        } else {
            boolean containsPieceOnPath = path.stream()
                    .anyMatch(board::containsKey);
            if (containsPieceOnPath) {
                throw new IllegalArgumentException("경로 상에 말이 존재합니다.");
            }
        }
    }

    private void validateOnePieceOnCannonPath(final List<Position> path) {
        long countPieceOnPath = path.stream()
                .filter(board::containsKey)
                .count();
        if (countPieceOnPath != 1) {
            throw new IllegalArgumentException("포가 이동할 수 없는 조건입니다.");
        }
    }

    private void validateNotExistsCannonOnPath(final List<Position> path) {
        boolean containsCannonOnPath = path.stream()
                .anyMatch(position -> board.containsKey(position) && board.get(position) instanceof Cannon);
        if (containsCannonOnPath) {
            throw new IllegalArgumentException("포의 이동 경로 상에 포가 존재합니다.");
        }
    }

    private static void validateTargetPiece(final Piece pickedPiece, final Piece targetPiece) {
        if (pickedPiece.isSameSide(targetPiece)) {
            throw new IllegalArgumentException("같은 팀이 있는 위치로는 이동할 수 없습니다.");
        }
        if (targetPiece instanceof Cannon && pickedPiece instanceof Cannon) {
            throw new IllegalArgumentException("포는 포를 잡을 수 없습니다.");
        }
    }

    private void validatePieceExistsByPosition(final Position position) {
        if (board.containsKey(position)) {
            return;
        }
        throw new IllegalArgumentException("선택된 좌표에 말이 없습니다.");
    }

    public Map<Position, Piece> getBoard() {
        return Collections.unmodifiableMap(board);
    }
}
