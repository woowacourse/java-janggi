package domain;

import domain.piece.Team;
import domain.position.Position;

public class JanggiGame {
    private final Board board;
    private Turn turn;
    private GameState gameState;

    private JanggiGame(Board board) {
        this.board = board;
        turn = new Turn(Team.CHO);
        gameState = GameState.PLAYING;
    }

    public static JanggiGame init(SettingType choSetting, SettingType hanSetting) {
        return new JanggiGame(Board.of(choSetting, hanSetting));
    }

    public void executeMove(Position start, Position destination) {
        validateGameIsNotFinished();
        board.move(turn, start, destination);
        checkGameTermination();
        if (gameState == GameState.PLAYING) {
            passTurn();
        }
    }

    private void validateGameIsNotFinished() {
        if (gameState == GameState.END) {
            throw new IllegalStateException(JanggiGameErrorMessage.ALREADY_END.getMessage());
        }
    }

    private void checkGameTermination() {
        if (board.isKingCaptured(Team.CHO) || board.isKingCaptured(Team.HAN)) {
            gameState = GameState.END;
        }
    }

    public void passTurn() {
        turn = turn.passTurn();
    }

    public boolean isFinished() {
        return gameState == GameState.END;
    }

    public Team getWinner() {
        if (gameState != GameState.END) {
            throw new IllegalStateException(JanggiGameErrorMessage.NOW_ON_PLAYING.getMessage());
        }
        if (board.isKingCaptured(Team.CHO)) {
            return Team.HAN;
        }
        return Team.CHO;
    }

    public BoardStatus getJanggiGameStatus() {
        return board.getBoardStatus();
    }

    public Team getTurnOwnTeam() {
        return turn.turnOwnTeam();
    }
}
