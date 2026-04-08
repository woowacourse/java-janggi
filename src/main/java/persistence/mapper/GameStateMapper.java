package persistence.mapper;

import domain.Board;
import domain.JanggiGame;
import domain.Piece;
import domain.Position;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import persistence.entity.GameState;
import persistence.entity.PieceState;

public final class GameStateMapper {

    private final PieceStateMapper pieceStateMapper;

    public GameStateMapper(PieceStateMapper pieceStateMapper) {
        this.pieceStateMapper = pieceStateMapper;
    }
    public GameState mapFrom(JanggiGame janggiGame) {
        return new GameState(pieceStates(janggiGame), janggiGame.gameStatus());
    }

    private List<PieceState> pieceStates(JanggiGame janggiGame) {
        List<PieceState> pieceStates = new ArrayList<>();
        for (Entry<Position, Piece> entry : boardOf(janggiGame).entrySet()) {
            pieceStates.add(toPieceState(entry));
        }
        return pieceStates;
    }

    private PieceState toPieceState(Entry<Position, Piece> entry) {
        return pieceStateMapper.mapFrom(entry.getValue().pieceProperty(), entry.getKey());
    }

    private Map<Position, Piece> boardOf(JanggiGame janggiGame) {
        return janggiGame.board().board();
    }

    public JanggiGame mapToJanggiGame(GameState gameState) {
        Map<Position, Piece> restoredBoard = new HashMap<>();
        for (PieceState pieceState : gameState.pieceStates()) {
            restoredBoard.put(pieceState.position(), pieceStateMapper.mapToPiece(pieceState));
        }
        return JanggiGame.of(Board.of(restoredBoard), gameState.gameStatus());
    }
}
