package domain;

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
    private static final String NOT_FOUND_PIECE_FROM_POSITION = "[ERROR] 해당 좌표에 기물이 존재하지 않습니다.";
    private static final String NOT_MY_PIECE = "[ERROR] 본인 진영의 기물이 아닙니다.";
    private static final String CAN_NOT_MOVE_TO_POSITION = "[ERROR] 해당 경로로 기물을 이동시킬 수 없습니다.";

    private final Map<Position, State> board;

    private Board(Map<Position, State> board) {
        this.board = board;
    }

    public static Board create(TableSetting choSetting, TableSetting hanSetting) {
        Map<Position, State> board = new BoardInitializer().initialize(choSetting, hanSetting);
        return new Board(board);
    }

    public void validateFromPosition(Position from, Country country) {
        State fromState = board.get(from);
        if (fromState.isEmpty()) {
            throw new IllegalArgumentException(NOT_FOUND_PIECE_FROM_POSITION);
        }
        if (fromState.getPiece().getPieceCountry() != country) {
            throw new IllegalArgumentException(NOT_MY_PIECE);
        }
    }

    public void move(Position from, Position to) {
        if (isEmpty(from)) {
            throw new IllegalArgumentException(NOT_FOUND_PIECE_FROM_POSITION);
        }
        Piece piece = board.get(from).getPiece();
        List<Position> paths = piece.findPaths(from, to);

        if (!board.get(to).isEmpty()) {
            Country fromCountry = board.get(from).getPiece().getPieceCountry();
            Country toCountry = board.get(to).getPiece().getPieceCountry();
            if (fromCountry == toCountry) {
                throw new IllegalArgumentException(CAN_NOT_MOVE_TO_POSITION);
            }
        }

        Map<Position, PieceType> piecesOnPath = new LinkedHashMap<>();
        for (Position position : paths) {
            if (!isEmpty(position)) {
                piecesOnPath.put(position, board.get(position).getPiece().getPieceType());
            }
        }
        boolean isDestinationEmpty = board.get(paths.getLast()).isEmpty();
        piece.validateClearPath(piecesOnPath, isDestinationEmpty);

        board.put(to, new FullState(piece));
        board.put(from, new EmptyState());
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

    public boolean isEmpty(Position position) {
        return board.get(position).isEmpty();
    }
}
