package domain.movement;

import domain.game.Board;
import domain.game.Piece;
import domain.game.Position;
import java.util.List;

public final class MovementValidator {
    private final Board board;

    public MovementValidator(Board board) {
        this.board = board;
    }

    // 기물의 이동이 유효한지 전체적으로 검증한다.
    // 1) 목적지에 아군이 있으면 무조건 이동 불가
    // 2) 기물 종류에 따라 세부 검증 위임
    public boolean isValid(Piece piece, List<Path> candidatePaths, Position to) {
        if (board.hasFriend(to, piece.getTeam())) {
            return false;
        }

        return switch (piece.getPieceType()) {
            case CHARIOT -> isValidChariot(piece, candidatePaths, to);
            case CANNON -> isValidCannon(piece, candidatePaths, to);
            case HORSE, ELEPHANT -> isValidStepPiece(piece, candidatePaths, to);
            case GENERAL, GUARD -> isValidPalacePiece(piece, candidatePaths, to);
            case SOLDIER -> isValidPalacePiece(piece, candidatePaths, to);
        };
    }

    // 차 이동 검증: 목적지까지의 경로에 중간에 막힌 기물이 없어야 한다.
    // 목적지는 빈 칸이거나 적군이어야 한다.

    private boolean isValidChariot(Piece piece, List<Path> candidatePaths, Position to) {
        for (Path path : candidatePaths) {
            if (!path.contains(to)) {
                continue;
            }
            Path subPath = path.subPathTo(to);
            List<Position> intermediates = subPath.intermediates();

            boolean allIntermediatesEmpty = intermediates.stream()
                    .allMatch(board::isEmpty);

            if (!allIntermediatesEmpty) {
                continue;
            }

            if (board.isEmpty(to) || board.hasEnemy(to, piece.getTeam())) {
                return true;
            }
        }
        return false;
    }
    // 포 이동 검증: 경로 중간에 포가 아닌 기물이 정확히 1개 있어야 한다.
    // 포끼리는 서로 뛰어넘거나 잡을 수 없다.
    // 목적지가 빈 칸이거나 포가 아닌 적군이어야 한다.

    private boolean isValidCannon(Piece piece, List<Path> candidatePaths, Position to) {
        for (Path path : candidatePaths) {
            if (!path.contains(to)) {
                continue;
            }
            Path subPath = path.subPathTo(to);
            List<Position> intermediates = subPath.intermediates();

            long nonCannonCount = intermediates.stream()
                    .filter(board::hasAnyPiece)
                    .filter(pos -> board.pieceAt(pos).map(p -> !p.isCannon()).orElse(false))
                    .count();

            long cannonCount = intermediates.stream()
                    .filter(board::hasAnyPiece)
                    .filter(pos -> board.pieceAt(pos).map(Piece::isCannon).orElse(false))
                    .count();

            if (nonCannonCount != 1 || cannonCount != 0) {
                continue;
            }

            if (board.isEmpty(to)) {
                return true;
            }

            if (board.hasEnemy(to, piece.getTeam())) {
                boolean destIsCannon = board.pieceAt(to).map(Piece::isCannon).orElse(false);
                if (!destIsCannon) {
                    return true;
                }
            }
        }
        return false;
    }
    // 마, 상 이동 검증: 경로 중간 발판 위치가 모두 비어 있어야 한다.
    // 목적지는 빈 칸이거나 적군이어야 한다.

    private boolean isValidStepPiece(Piece piece, List<Path> candidatePaths, Position to) {
        for (Path path : candidatePaths) {
            if (!path.endsAt(to)) {
                continue;
            }

            //이동 경로 중의 로직
            if (!path.intermediates().stream()
                    .allMatch(board::isEmpty)) {
                continue;
            }

            //종점 로직
            if (board.isEmpty(to) || board.hasEnemy(to, piece.getTeam())) {
                return true;
            }
        }
        return false;
    }

    // 궁, 사, 졸 이동 검증: 중간 경로 제약 없이,
    // 목적지가 후보 경로의 끝이고 빈 칸이거나 적군이면 이동 가능하다.
    private boolean isValidPalacePiece(Piece piece, List<Path> candidatePaths, Position to) {
        //candidatePaths: 보드 밖으로 나가지 않는 경로들
        for (Path path : candidatePaths) {
            //to: 도착지점
            if (!path.endsAt(to)) {
                continue;
            }
            //찾아온 경로들 중에 도착지점이 명령으로입력한도착지점 인 것을 찾음

            if (board.isEmpty(to) || board.hasEnemy(to, piece.getTeam())) {
                //그 지점에 기물이 없거나, 적기물이 있으면 이동 가능함
                return true;
            }
        }
        //해당 지점에 아군 기물이 있으면
        return false;
    }
}
