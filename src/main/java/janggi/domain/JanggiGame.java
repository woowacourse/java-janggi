package janggi.domain;

import janggi.domain.game.GameStatus;
import janggi.domain.piece.Piece;
import janggi.domain.turn.Turn;
import janggi.dto.GameDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class JanggiGame {

    private final Long id;
    private final List<Turn> turns;
    private final GameStatus gameStatus;

    private JanggiGame(Long id, List<Turn> turns, GameStatus gameStatus) {
        this.id = id;
        this.turns = new ArrayList<>(turns);
        this.gameStatus = gameStatus;
    }

    public static JanggiGame createInitialJanggiGame() {
        return new JanggiGame(IdGenerator.createId(), List.of(Turn.createInitialTurn()), GameStatus.IN_PROGRESS);
    }

    public static JanggiGame loadPreviousJanggiGame(JanggiGame janggiGame, Turn previousTurn) {
        return new JanggiGame(janggiGame.getId(), List.of(previousTurn), janggiGame.getGameStatus());
    }

    public static JanggiGame from(GameDto gameDto) {
        // 빈 리스트 수정 필요
        return new JanggiGame(gameDto.id(), List.of(), gameDto.gameStatus());
    }

    public long getId() {
        return id;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public boolean isRunning() {
        return gameStatus == GameStatus.IN_PROGRESS;
    }

    public Map<Position, Piece> makeCurrentTurnBoardSnapShot() {
        Turn lastTurn = getLastTurn();
        return lastTurn.makeBoardSnapShot();
    }

    public String getCurrentTurnTeamName() {
        Turn lastTurn = getLastTurn();
        return lastTurn.nextTurnTeamName();
    }

    public void validatePieceExists(Position position) {
        Turn lastTurn = getLastTurn();
        if (!lastTurn.isNextTurnTeamPieceExists(position)) {
            throw new IllegalArgumentException("기물이 존재하는 좌표가 아닙니다.");
        }
    }

    public String getPieceName(Position position) {
        return getLastTurn().getPieceName(position);
    }

    public void validateValidEndPosition(Position start, Position end) {
        Turn lastTurn = getLastTurn();
        lastTurn.validateCanMove(start, end);
    }

    public JanggiGame move(Position start, Position end) {
        Turn lastTurn = getLastTurn();
        Turn movedTurn = lastTurn.move(start, end);
        turns.add(movedTurn);
        if (!movedTurn.isRunning()) {
            new JanggiGame(id, turns, GameStatus.from(movedTurn.getTurnStatus().getFormat()));
        }
        return this;
    }

    public String winTeamName() {
        return getLastTurn().winTeamName();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        JanggiGame that = (JanggiGame) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public Turn getLastTurn() {
        return turns.getLast();
    }
}
