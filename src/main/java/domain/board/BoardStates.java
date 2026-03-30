package domain.board;

import domain.Country;
import domain.Path;
import domain.Position;
import domain.TableSetting;
import domain.piece.PieceInfo;
import domain.state.State;
import java.util.LinkedHashMap;
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

    public boolean canMovePiece(Position from, Path path) {
        Map<Position, State> wayPointStates = getPathStates(path);
        return boardStates.get(from).getPiece().canMove(wayPointStates);
    }

    private Map<Position, State> getPathStates(Path path) {
        Map<Position, State> pathStates = new LinkedHashMap<>();
        for (Position position : path.getPath()) {
            pathStates.put(position, boardStates.get(position).copy());
        }
        return pathStates;
    }

    public Path getPiecePath(Position from, Position to) {
        return boardStates.get(from).getPiece().path(from, to);
    }

    public Country getPieceCountry(Position position) {
        return boardStates.get(position).getPieceCountry();
    }

    public Map<Position, PieceInfo> getPieceInfos() {
        Map<Position, PieceInfo> pieceInfos = new LinkedHashMap<>();
        for (Entry<Position, State> entry : boardStates.entrySet()) {
            adjustPieceInfo(pieceInfos, entry);
        }
        return pieceInfos;
    }

    private void adjustPieceInfo(Map<Position, PieceInfo> pieceInfos, Entry<Position, State> entry) {
        if (!entry.getValue().isEmpty()) {
            pieceInfos.put(entry.getKey(), entry.getValue().getPieceInfo());
        }
    }
}
