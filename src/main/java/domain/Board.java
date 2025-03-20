package domain;

import domain.piece.Piece;
import domain.piece.Pieces;
import domain.piece.Position;

import java.util.List;
import java.util.Map;

public class Board {

    private final Map<Player, Pieces> board;

    public Board(final Map<Player, Pieces> board) {
        this.board = board;
    }

    public void move(final Player player, final Position startPosition, final Position targetPosition) {
        Piece piece = board.get(player).findByPosition(startPosition);

        List<Position> path = piece.getPath(targetPosition);

        /* 검사 했을 때 포가 이동할 수 없는 조건일 경우
        1. 중간 기물 개수가 1개가 아닐 경우 (0개 또는 1개 초과)
        2. 도착지에 포인 경우
        3. 중간 기물이 포인 경우
         */

        if (piece.isCannon()) {
            int count = getContainedPiecesCount(path);
            if (count != 1) {
                throw new IllegalArgumentException("[ERROR] 포는 중간에 기물이 1개여야 합니다.");
            }
        }

        if (!piece.isCannon() && !canMove(path)) {
            throw new IllegalArgumentException();
        }

        if (board.get(player).existByPosition(targetPosition)) {
            throw new IllegalArgumentException("[ERROR] 도착 위치에 아군의 기물이 존재해 이동할 수 없습니다.");
        }

        board.get(player).updatePosition(piece, targetPosition);

        Pieces oppositePieces = getOppositePieces(player);
        if (oppositePieces.existByPosition(targetPosition)) {
            oppositePieces.deleteByPosition(targetPosition);
        }
    }

    private boolean canMove(final List<Position> path) {
        int containedPiecesCount = getContainedPiecesCount(path);
        return containedPiecesCount == 0;
    }

    private int getContainedPiecesCount(final List<Position> path) {
        return board.values().stream()
                .mapToInt(pieces -> pieces.countPiecesInPositions(path))
                .sum();
    }

    private Pieces getOppositePieces(final Player player) {
        Player oppositePlayer = board.keySet()
                .stream()
                .filter(opposite -> !opposite.equals(player))
                .findFirst()
                .orElseThrow(RuntimeException::new);

        return board.get(oppositePlayer);
    }

    public Map<Player, Pieces> getBoard() {
        return board;
    }

    public boolean isFinish() {
        long kingCount = board.values().stream()
                .filter(Pieces::existKing)
                .count();

        return kingCount != 2;
    }

    public Player getWinner() {
        return board.keySet().stream()
                .filter(player -> board.get(player).existKing())
                .findAny()
                .orElseThrow(RuntimeException::new);
    }
}
