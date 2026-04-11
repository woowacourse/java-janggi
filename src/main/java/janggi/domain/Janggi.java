package janggi.domain;

import janggi.domain.board.Board;
import janggi.domain.board.strategy.*;
import janggi.domain.piece.Piece;
import janggi.domain.state.ChoTurn;
import janggi.domain.state.Draw;
import janggi.domain.state.GameState;
import janggi.domain.state.GiveUp;
import janggi.view.dto.PieceStatus;
import janggi.view.dto.GameResult;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    public void movePiece(Position from, Position to) {
        gameState = gameState.move(from, to, board);
    }

    public void validateCamp(Position position) {
        gameState.validateCamp(position, board);
    }

    public boolean isOnGoing() {
        return gameState.isOngoing();
    }

    public void giveUpGame() {
        gameState = new GiveUp(gameState.turn());
    }

    public void drawGame() {
        gameState = new Draw(gameState.turn());
    }

    public Camp currentTurn() {
        return gameState.turn();
    }

    public Map<Position, Piece> getBoardSnapshot() {
        return board.getPiecesSnapshot();
    }

    public int calculateTotalScore(Camp camp) {
        return board.calculateTotalScore(camp);
    }
}
