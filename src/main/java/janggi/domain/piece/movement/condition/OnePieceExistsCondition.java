package janggi.domain.piece.movement.condition;

import janggi.domain.board.BoardChecker;
import janggi.domain.board.Position;
import janggi.domain.piece.Camp;
import janggi.domain.piece.PieceType;
import java.util.List;

public class OnePieceExistsCondition implements MoveCondition {

    private static final String SAME_PIECE_TYPE_AT_DESTINATION = "[ERROR] 목적지에 같은 종류의 기물이 존재합니다.";
    private static final String SAME_CAMP_PIECE_AT_DESTINATION = "[ERROR] 목적지에 같은 진영의 기물이 존재합니다.";

    private static final int PASS_PIECE_COUNT = 1;
    private static final String SAME_PIECE_TYPE_IN_PATH = "[ERROR] 경로상에 같은 종류의 기물이 존재합니다.";
    private static final String INVALID_JUMPED_PIECE_COUNT = String.format(
            "[ERROR] 해당 기물은 정확히 %d개의 기물만 뛰어넘을 수 있습니다.",
            PASS_PIECE_COUNT
    );

    private final PieceType unabledPieceType;

    public OnePieceExistsCondition(PieceType unabledPieceType) {
        this.unabledPieceType = unabledPieceType;
    }

    @Override
    public void checkPath(List<Position> path, Camp camp, BoardChecker board) {
        int countOfPiece = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            Position position = path.get(i);
            countOfPiece += countPieceAt(board, position);
        }

        validateExactPieceCount(countOfPiece);
        validateDestination(path.getLast(), camp, board);
    }

    private int countPieceAt(BoardChecker board, Position position) {
        if (board.hasPieceAt(position)) {
            validateSamePieceRule(board, position);
            return 1;
        }
        return 0;
    }

    private void validateSamePieceRule(BoardChecker board, Position position) {
        if (board.hasSamePieceTypeAt(position, unabledPieceType)) {
            throw new IllegalArgumentException(SAME_PIECE_TYPE_IN_PATH);
        }
    }

    private void validateExactPieceCount(int countOfPiece) {
        if (countOfPiece != PASS_PIECE_COUNT) {
            throw new IllegalArgumentException(INVALID_JUMPED_PIECE_COUNT);
        }
    }

    private void validateDestination(Position destination, Camp camp, BoardChecker board) {
        if (board.isSameCampPieceAt(destination, camp)) {
            throw new IllegalArgumentException(SAME_CAMP_PIECE_AT_DESTINATION);
        }

        if (board.hasSamePieceTypeAt(destination, unabledPieceType)) {
            throw new IllegalArgumentException(SAME_PIECE_TYPE_AT_DESTINATION);
        }
    }
}
