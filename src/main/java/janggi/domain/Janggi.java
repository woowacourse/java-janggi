package janggi.domain;

import janggi.domain.board.Board;
import janggi.domain.board.strategy.*;
import janggi.domain.piece.Piece;
import janggi.domain.state.ChoTurn;
import janggi.domain.state.GameState;

import java.util.List;
import java.util.Map;

public class Janggi {
    private static final List<FormationStrategy> FORMATIONS = List.of(
            new HorseElephantElephantHorse(),
            new HorseElephantHorseElephant(),
            new ElephantHorseHorseElephant(),
            new ElephantHorseElephantHorse()
    );

    private final Board board;
    private GameState gameState;

    private Janggi(Board board, GameState gameState) {
        this.board = board;
        this.gameState = gameState;
    }

    public static Janggi start(int choFormationNumber, int hanFormationNumber) {
        return new Janggi(Board.initializeToBoard(
                readFormation(choFormationNumber),
                readFormation(hanFormationNumber)),
                new ChoTurn());
    }

    public static Janggi reconstruct(Board board, GameState gameState) {
        return new Janggi(board, gameState);
    }

    private static FormationStrategy readFormation(int choice) {
        if (choice < 1 || choice > FORMATIONS.size()) {
            throw new IllegalArgumentException("1~4 중 선택해주세요.");
        }
        return FORMATIONS.get(choice - 1);
    }

    public void movePiece(JanggiPosition from, JanggiPosition to) {
        gameState = gameState.move(from, to, board);
    }

    public void validateCamp(JanggiPosition position) {
        gameState.validateCamp(position, board);
    }

    public boolean isOnGoing() {
        return gameState.isOngoing();
    }

    public void giveUpGame() {
        gameState = gameState.giveUp();
    }

    public void drawGame() {
        gameState = gameState.draw();
    }

    public Camp currentTurn() {
        return gameState.turn();
    }

    public Map<JanggiPosition, Piece> getBoardSnapshot() {
        return board.getPiecesSnapshot();
    }

    public int calculateTotalScore(Camp camp) {
        return board.calculateTotalScore(camp);
    }

    public String getStateType() {
        return gameState.getStateType();
    }
}
