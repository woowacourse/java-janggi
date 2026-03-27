package domain.board;

import domain.Country;
import domain.Position;
import domain.TableSetting;
import domain.piece.Piece;
import domain.piece.PieceInfo;
import domain.piece.PieceType;
import domain.state.EmptyState;
import domain.state.FullState;
import domain.state.State;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Board {
    private static final String NOT_MY_PIECE = "[ERROR] 본인 진영의 기물이 아닙니다.";
    private static final String CAN_NOT_MOVE_TO_POSITION = "[ERROR] 해당 경로로 기물을 이동시킬 수 없습니다.";

    private static final int CANNON_JUMP_PIECE_COUNT = 1;

    private final Map<Position, State> board;

    public Board(TableSetting choTableSetting, TableSetting hanTableSetting) {
        this.board = BoardInitializer.initialize(choTableSetting, hanTableSetting);
    }

    public void validateFromPosition(Position from, Country country) {
        State fromState = board.get(from);
        if (fromState.getPiece().getPieceCountry() != country) {
            throw new IllegalArgumentException(NOT_MY_PIECE);
        }
    }

    public void move(Position from, Position to) {
        Piece piece = board.get(from).getPiece();
        List<Position> paths = piece.path(from, to);

        if (!board.get(to).isEmpty()) {
            PieceType fromPieceType = board.get(from).getPiece().getPieceType();
            PieceType toPieceType = board.get(to).getPiece().getPieceInfo().pieceType();
            if (fromPieceType != toPieceType) {
                throw new IllegalArgumentException(CAN_NOT_MOVE_TO_POSITION);
            }
        }

        PieceType pieceType = piece.getPieceInfo().pieceType();
        if (pieceType == PieceType.CANNON) {
            checkCannonPath(paths);
        }
        if (pieceType != PieceType.CANNON) {
            checkPathExceptCannon(paths);
        }
        board.put(to, new FullState(piece));
        board.put(from, new EmptyState());
    }

    private void checkPathExceptCannon(List<Position> paths) {
        for (int index = 0; index < paths.size() - 1; index++) {
            if (!board.get(paths.get(index)).isEmpty()) {
                throw new IllegalArgumentException(CAN_NOT_MOVE_TO_POSITION);
            }
        }
    }

    private void checkCannonPath(List<Position> paths) {
        int pieceCount = 0;
        for (int index = 0; index < paths.size() - 1; index++) {
            State state = board.get(paths.get(index));
            if (!state.isEmpty()) {
                PieceType pieceType = state.getPiece().getPieceType();
                if (pieceType == PieceType.CANNON) {
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
        Map<Position, PieceInfo> pieceInfos = new LinkedHashMap<>();
        for (Entry<Position, State> entry : board.entrySet()) {
            if (!entry.getValue().isEmpty()) {
                pieceInfos.put(entry.getKey(), entry.getValue().getPiece().getPieceInfo());
            }
        }
        return pieceInfos;
    }
}
