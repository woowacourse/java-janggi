package domain;

import static domain.Position.INITIAL_POSITION;
import static domain.Position.X_MAXIMUM_POSITION;
import static domain.Position.Y_MAXIMUM_POSITION;

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
    private static final List<Position> CHO_VARIABLE_POSITIONS = List.of(new Position(1, 0), new Position(2, 0),
            new Position(6, 0),
            new Position(7, 0));
    private static final List<Position> HAN_VARIABLE_POSITIONS = List.of(new Position(1, 9), new Position(2, 9),
            new Position(6, 9),
            new Position(7, 9));

    private final Map<Position, State> board = new LinkedHashMap<>();

    public Board(TableSetting choTableSetting, TableSetting hanTableSetting) {
        initialize(choTableSetting, hanTableSetting);
    }

    private void initialize(TableSetting choTableSetting, TableSetting hanTableSetting) {
        for (InitialPosition initialPosition : InitialPosition.values()) {
            initializeFixedSettings(initialPosition);
        }
        initializeTableSettings(choTableSetting, hanTableSetting);

        for (int y = INITIAL_POSITION; y <= Y_MAXIMUM_POSITION; y++) {
            EmptyState emptyState = new EmptyState();
            for (int x = INITIAL_POSITION; x <= X_MAXIMUM_POSITION; x++) {
                Position position = new Position(x, y);

                if (!board.containsKey(position)) {
                    board.put(position, emptyState);
                }
            }
        }
    }

    private void initializeFixedSettings(InitialPosition initialPosition) {
        for (Position choPosition : initialPosition.getChoPositions()) {
            board.put(choPosition,
                    new FullState(new Piece(new PieceInfo(initialPosition.getPieceType(), Country.CHO))));
        }

        for (Position hanPosition : initialPosition.getHanPositions()) {
            board.put(hanPosition,
                    new FullState(new Piece(new PieceInfo(initialPosition.getPieceType(), Country.HAN))));
        }
    }

    private void initializeTableSettings(TableSetting choTableSetting, TableSetting hanTableSetting) {
        for (int index = 0; index < 4; index++) {
            board.put(CHO_VARIABLE_POSITIONS.get(index), new FullState(
                    new Piece(new PieceInfo(choTableSetting.getFormation(Country.CHO).get(index), Country.CHO))
            ));
        }

        for (int index = 0; index < 4; index++) {
            board.put(HAN_VARIABLE_POSITIONS.get(index), new FullState(
                    new Piece(new PieceInfo(hanTableSetting.getFormation(Country.HAN).get(index), Country.HAN))
            ));
        }
    }


    public void validateFromPosition(Position from, Country country) {
        State fromState = board.get(from);
        if (fromState.isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 해당 좌표에 기물이 존재하지 않습니다.");
        }
        if (fromState.getPiece().getPieceInfo().getCountry() != country) {
            throw new IllegalArgumentException("[ERROR] 본인 진영의 기물이 아닙니다.");
        }
    }

    public void move(Position from, Position to) {
        Piece piece = board.get(from).getPiece();
        List<Position> paths = piece.path(from, to);

        if (!board.get(to).isEmpty()) {
            PieceType fromPieceType = board.get(from).getPiece().getPieceInfo().getPieceType();
            PieceType toPieceType = board.get(to).getPiece().getPieceInfo().getPieceType();
            if (fromPieceType != toPieceType) {
                throw new IllegalArgumentException("[ERROR] 해당 경로로 기물을 이동시킬 수 없습니다.");
            }
        }

        PieceType pieceType = piece.getPieceInfo().getPieceType();
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
                throw new IllegalArgumentException("[ERROR] 해당 경로로 기물을 이동시킬 수 없습니다.");
            }
        }
    }

    private void checkCannonPath(List<Position> paths) {
        int pieceCount = 0;
        for (int index = 0; index < paths.size() - 1; index++) {
            domain.state.State state = board.get(paths.get(index));
            if (!state.isEmpty()) {
                PieceType pieceType = state.getPiece().getPieceInfo().getPieceType();
                if (pieceType == PieceType.CANNON) {
                    throw new IllegalArgumentException("[ERROR] 해당 경로로 기물을 이동시킬 수 없습니다.");
                }
                pieceCount++;
            }
        }

        if (pieceCount != 1) {
            throw new IllegalArgumentException("[ERROR] 해당 경로로 기물을 이동시킬 수 없습니다.");
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
