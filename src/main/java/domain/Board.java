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
        // 해당 위치의 타겟 가져오기
        Piece piece = board.get(player).findByPosition(startPosition);

        // 지나가는 경로 좌표들 가져오기
        List<Position> path = piece.getPath(targetPosition);

        /* 검사 했을 때 이동할 수 없는 조건일 경우
        // 포 : 중간 기물 개수가 1개가 아닐 경우 (0개 또는 1개 초과)
        나머지 : 중간 기물이 1개 이상일 경우
         */

        if (!canMove(path)) {
            throw new IllegalArgumentException();
        }

        // 우리팀 도착지 검증
        if (board.get(player).existByPosition(targetPosition)) {
            throw new IllegalArgumentException("[ERROR] 도착 위치에 아군의 기물이 존재해 이동할 수 없습니다.");
        }
//
        //타겟 위치로 기물 이동시키기
        board.get(player).updatePosition(piece, targetPosition);
//
//        // 해당 타겟 위치에 상대편 기물이 존재하면
//        if (existsOppositePiece(piece)) {
//            // 해당 위치의 상대 기물 잡기
//            catchPiece(targetPosition);
//        }
    }

    private boolean canMove(final List<Position> path) {
        int containedPiecesCount = board.values().stream()
                .mapToInt(pieces -> pieces.countPiecesInPositions(path))
                .sum();
        return containedPiecesCount == 0;
    }

    public Map<Player, Pieces> getBoard() {
        return board;
    }
}
