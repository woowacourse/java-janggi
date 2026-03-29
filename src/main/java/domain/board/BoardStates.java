package domain.board;

import domain.Country;
import domain.Position;
import domain.TableSetting;
import domain.piece.PieceInfo;
import domain.piece.PieceType;
import domain.state.State;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class BoardStates {
    private final Map<Position, State> boardStates;

    public BoardStates(TableSetting choTableSetting, TableSetting hanTableSetting) {
        this.boardStates = BoardInitializer.initialize(choTableSetting, hanTableSetting);
    }

    public void changeState(Position from, Position to) {
        State fromState = boardStates.get(from);
        State toState = boardStates.get(to);
        boardStates.put(from, toState);
        boardStates.put(to, fromState);
    }

    public boolean isEmpty(Position position) {
        return boardStates.get(position).isEmpty();
    }

    public boolean isSameCountry(Position from, Position to) {
        return getPieceCountry(from) == getPieceCountry(to);
    }

    public List<Position> getPiecePath(Position from, Position to) {
        return boardStates.get(from).getPiece().path(from, to);
    }

    public PieceType getPieceType(Position position) {
        return boardStates.get(position).getPiece().getPieceType();
    }

    public Country getPieceCountry(Position position) {
        return boardStates.get(position).getPiece().getPieceCountry();
    }

    public Map<Position, PieceInfo> getPieceInfos() {
        Map<Position, PieceInfo> pieceInfos = new LinkedHashMap<>();
        for (Entry<Position, State> entry : boardStates.entrySet()) {
            if (!entry.getValue().isEmpty()) {
                pieceInfos.put(entry.getKey(), entry.getValue().getPiece().getPieceInfo());
            }
        }
        return pieceInfos;
    }
}
