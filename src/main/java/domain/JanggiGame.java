package domain;

import domain.piece.Team;
import domain.position.Position;

public class JanggiGame {
    private final Board board;
    private Turn turn;

    private JanggiGame(Board board) {
        this.board = board;
        turn = new Turn(Team.CHO);
    }

    public static JanggiGame init(SettingType choSetting, SettingType hanSetting) {
        return new JanggiGame(Board.of(choSetting, hanSetting));
    }

    public void executeMove(Position start, Position destination) {
        board.move(turn, start, destination);
        passTurn();
    }

    public void passTurn() {
        turn = turn.passTurn();
    }

    public BoardStatus getJanggiGameStatus() {
        return board.getBoardStatus();
    }

    public Team getTurnOwnTeam() {
        return turn.turnOwnTeam();
    }
}
