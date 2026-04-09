package janggi.service;

import janggi.domain.Game;
import janggi.domain.board.Position;
import janggi.domain.piece.Camp;
import janggi.domain.piece.Piece;
import java.util.Map;

public record GameStatus(
        Map<Position, Piece> boardSnapshot,
        Map<Camp, Double> score,
        Camp currentTurn,
        boolean gameEnded
) {
    public static GameStatus from(Game game, boolean gameEnded) {
        return new GameStatus(
                game.boardSnapshot(),
                game.calculateScore(),
                game.currentTurn(),
                gameEnded
        );
    }
}
