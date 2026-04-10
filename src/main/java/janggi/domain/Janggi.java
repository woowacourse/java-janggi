package janggi.domain;

import janggi.domain.board.Board;
import janggi.domain.board.strategy.*;
import janggi.domain.state.ChoTurn;
import janggi.domain.state.GameState;
import janggi.domain.state.GiveUp;
import janggi.view.dto.PieceStatus;
import janggi.view.dto.GameResult;

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

    public List<PieceStatus> piecesStatus() {
        Map<Position, String> displayBoard = board.displayBoard();
        return displayBoard.keySet()
                .stream()
                .map(position -> PieceStatus.from(position,
                        board.checkCampOfThePiece(position),
                        displayBoard.get(position)))
                .toList();
    }

    public boolean isOnGoing() {
        return gameState.isOngoing();
    }

    public void stopGame() {
        gameState = new GiveUp(gameState.turn());
    }

    public Camp currentTurn() {
        return gameState.turn();
    }

    public GameResult calculateGameResult() {
        if (gameState.isOngoing()) {
            throw new IllegalArgumentException("게임이 종료되지 않았습니다.");
        }
        return new GameResult(
                board.calculateTotalScore(Camp.CHO),
                board.calculateTotalScore(Camp.HAN)
        );
    }
}
