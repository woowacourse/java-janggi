package domain.board;

import domain.Country;
import domain.Path;
import domain.Position;
import domain.TableSetting;
import domain.piece.PieceInfo;
import java.util.Map;

public class Board {
    private static final String NOT_MY_PIECE = "[ERROR] 본인 진영의 기물이 아닙니다.";
    private static final String CANNOT_MOVE_SAME_POSITION = "[ERROR] 기물을 동일한 위치로 이동시킬 수 없습니다.";

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
        validateMoveSamePosition(from, to);
        Path path = boardStates.getPiecePath(from, to);

        boardStates.validatePieceMove(from, path);
        boardStates.changeState(from, to);
    }

    public void validateMoveSamePosition(Position from, Position to) {
        if (from.equals(to)) {
            throw new IllegalArgumentException(CANNOT_MOVE_SAME_POSITION);
        }
    }

    public Map<Position, PieceInfo> getPieceInfos() {
        return boardStates.getPieceInfos();
    }
}
