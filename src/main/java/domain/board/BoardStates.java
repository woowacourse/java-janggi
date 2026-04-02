package domain.board;

import domain.Country;
import domain.CountryType;
import domain.Path;
import domain.Position;
import domain.piece.PieceInfo;
import domain.state.EmptyState;
import domain.state.State;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class BoardStates {
    private final Map<Position, State> boardStates;

    public BoardStates(Map<Position, State> boardStates) {
        this.boardStates = new HashMap<>(boardStates);
    }

    public void changeState(Position from, Position to) {
        State fromState = boardStates.get(from);
        boardStates.put(from, new EmptyState());
        boardStates.put(to, fromState);
    }

    public void adjustScore(Position to, Country country) {
        if (boardStates.get(to).isEmpty()) {
            return;
        }
        country.minusScore(boardStates.get(to).getPieceScore());
    }

    public boolean isEmpty(Position position) {
        return boardStates.get(position).isEmpty();
    }

    public void validatePieceMove(Position from, Path path) {
        Map<Position, State> wayPointStates = getPathStates(path);
        boardStates.get(from).getPiece().validateMove(wayPointStates);
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

    public CountryType getPieceCountryType(Position position) {
        return boardStates.get(position).getPieceCountryType();
    }

    public Map<Position, PieceInfo> getPieceInfos() {
        Map<Position, PieceInfo> pieceInfos = new HashMap<>();
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
