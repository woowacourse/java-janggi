package domain.board;

import domain.Country;
import domain.Position;
import domain.TableSetting;
import domain.piece.PieceInfo;
import domain.piece.PieceType;
import java.util.List;
import java.util.Map;

public class Board {
    private static final String NOT_MY_PIECE = "[ERROR] 본인 진영의 기물이 아닙니다.";
    private static final String CAN_NOT_MOVE_TO_POSITION = "[ERROR] 해당 경로로 기물을 이동시킬 수 없습니다.";

    private static final int CANNON_JUMP_PIECE_COUNT = 1;

    private final BoardStates boardStates;

    public Board(TableSetting choTableSetting, TableSetting hanTableSetting) {
        this.boardStates = new BoardStates(choTableSetting, hanTableSetting);
    }

    public void validateFromPosition(Position from, Country country) {
        if (boardStates.getPieceCountry(from) != country) {
            throw new IllegalArgumentException(NOT_MY_PIECE);
        }
    }

    public void move(Position from, Position to) {
        List<Position> paths = boardStates.getPiecePath(from, to);

        if (!boardStates.isEmpty(to)) {
            if (boardStates.isSameCountry(from, to)) {
                throw new IllegalArgumentException(CAN_NOT_MOVE_TO_POSITION);
            }
        }

        if (boardStates.getPieceType(from) == PieceType.CANNON) {
            checkCannonPath(paths, to);
        }
        if (boardStates.getPieceType(from) != PieceType.CANNON) {
            checkPathExceptCannon(paths);
        }
        boardStates.changeState(to, from);
    }

    private void checkPathExceptCannon(List<Position> paths) {
        for (int index = 0; index < paths.size() - 1; index++) {
            if (!boardStates.isEmpty(paths.get(index))) {
                throw new IllegalArgumentException(CAN_NOT_MOVE_TO_POSITION);
            }
        }
    }

    private void checkCannonPath(List<Position> paths, Position to) {
        if (!boardStates.isEmpty(to)) {
            if (boardStates.getPieceType(to) == PieceType.CANNON) {
                throw new IllegalArgumentException(CAN_NOT_MOVE_TO_POSITION);
            }
        }
        int pieceCount = 0;
        for (int index = 0; index < paths.size() - 1; index++) {
            if (!boardStates.isEmpty(paths.get(index))) {
                if (boardStates.getPieceType(paths.get(index)) == PieceType.CANNON) {
                    throw new IllegalArgumentException(CAN_NOT_MOVE_TO_POSITION);
                }
                pieceCount++;
            }
        }

        if (pieceCount != CANNON_JUMP_PIECE_COUNT) {
            throw new IllegalArgumentException(CAN_NOT_MOVE_TO_POSITION);
        }
    }

    public Map<Position, PieceInfo> getPieceInfos() {
        return boardStates.getPieceInfos();
    }
}
