package domain;

import static domain.Position.INITIAL_POSITION;
import static domain.Position.X_MAXIMUM_POSITION;
import static domain.Position.Y_MAXIMUM_POSITION;

import domain.piece.Cannon;
import domain.piece.Chariot;
import domain.piece.Elephant;
import domain.piece.General;
import domain.piece.Guard;
import domain.piece.Horse;
import domain.piece.Piece;
import domain.piece.PieceInfo;
import domain.piece.PieceType;
import domain.piece.Soldier;
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

    private final Map<Position, State> board = new LinkedHashMap<>();

    public Board(TableSetting choTableSetting, TableSetting hanTableSetting) {
        initialize(choTableSetting, hanTableSetting);
    }

    private void initialize(TableSetting choTableSetting, TableSetting hanTableSetting) {
        initializeSettings(choTableSetting, hanTableSetting);

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

    private void initializeSettings(TableSetting choTableSetting, TableSetting hanTableSetting) {
        initializeSoldierPosition();
        initializeGuardPosition();
        initializeCannonPosition();
        initializeChariotPosition();
        initializeGeneralPosition();
        initializeTableSettings(choTableSetting, hanTableSetting);
    }

    private void initializeSoldierPosition() {
        for (Position choPosition : InitialPosition.SOLDIER.getChoPositions()) {
            board.put(choPosition, new FullState(new Soldier(Country.CHO)));
        }
        for (Position hanPosition : InitialPosition.SOLDIER.getHanPositions()) {
            board.put(hanPosition, new FullState(new Soldier(Country.HAN)));
        }
    }

    private void initializeGuardPosition() {
        for (Position choPosition : InitialPosition.GUARD.getChoPositions()) {
            board.put(choPosition, new FullState(new Guard(Country.CHO)));
        }
        for (Position hanPosition : InitialPosition.GUARD.getHanPositions()) {
            board.put(hanPosition, new FullState(new Guard(Country.HAN)));
        }
    }

    private void initializeCannonPosition() {
        for (Position choPosition : InitialPosition.CANNON.getChoPositions()) {
            board.put(choPosition, new FullState(new Cannon(Country.CHO)));
        }
        for (Position hanPosition : InitialPosition.CANNON.getHanPositions()) {
            board.put(hanPosition, new FullState(new Cannon(Country.HAN)));
        }
    }

    private void initializeChariotPosition() {
        for (Position choPosition : InitialPosition.CHARIOT.getChoPositions()) {
            board.put(choPosition, new FullState(new Chariot(Country.CHO)));
        }
        for (Position hanPosition : InitialPosition.CHARIOT.getHanPositions()) {
            board.put(hanPosition, new FullState(new Chariot(Country.HAN)));
        }
    }

    private void initializeGeneralPosition() {
        for (Position choPosition : InitialPosition.GENERAL.getChoPositions()) {
            board.put(choPosition, new FullState(new General(Country.CHO)));
        }
        for (Position hanPosition : InitialPosition.GENERAL.getHanPositions()) {
            board.put(hanPosition, new FullState(new General(Country.HAN)));
        }
    }

    private void initializeTableSettings(TableSetting choTableSetting, TableSetting hanTableSetting) {
        initializeChoTableSetting(choTableSetting);
        initializeHanTableSetting(hanTableSetting);
    }

    private void initializeChoTableSetting(TableSetting choTableSetting) {
        for (int index = 0; index < 4; index++) {
            PieceType pieceType = choTableSetting.getFormation(Country.CHO).get(index);
            if (pieceType == PieceType.HORSE) {
                board.put(InitialPosition.ELEPHANT_AND_HORSE.getChoPositions().get(index),
                        new FullState(new Horse(Country.CHO)));
                continue;
            }
            board.put(InitialPosition.ELEPHANT_AND_HORSE.getChoPositions().get(index),
                    new FullState(new Elephant(Country.CHO)));
        }
    }

    private void initializeHanTableSetting(TableSetting hanTableSetting) {
        for (int index = 0; index < 4; index++) {
            PieceType pieceType = hanTableSetting.getFormation(Country.HAN).get(index);
            if (pieceType == PieceType.HORSE) {
                board.put(InitialPosition.ELEPHANT_AND_HORSE.getHanPositions().get(index),
                        new FullState(new Horse(Country.HAN)));
                continue;
            }
            board.put(InitialPosition.ELEPHANT_AND_HORSE.getHanPositions().get(index),
                    new FullState(new Elephant(Country.HAN)));
        }
    }

    public void validateFromPosition(Position from, Country country) {
        State fromState = board.get(from);
        if (fromState.getPiece().getPieceInfo().country() != country) {
            throw new IllegalArgumentException(NOT_MY_PIECE);
        }
    }

    public void move(Position from, Position to) {
        Piece piece = board.get(from).getPiece();
        List<Position> paths = piece.path(from, to);

        if (!board.get(to).isEmpty()) {
            PieceType fromPieceType = board.get(from).getPiece().getPieceInfo().pieceType();
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
            domain.state.State state = board.get(paths.get(index));
            if (!state.isEmpty()) {
                PieceType pieceType = state.getPiece().getPieceInfo().pieceType();
                if (pieceType == PieceType.CANNON) {
                    throw new IllegalArgumentException(CAN_NOT_MOVE_TO_POSITION);
                }
                pieceCount++;
            }
        }

        if (pieceCount != 1) {
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
