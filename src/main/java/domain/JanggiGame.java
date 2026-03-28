package domain;

import domain.piece.Team;
import domain.position.Position;
import domain.settingType.SettingType;

public class JanggiGame {
    private final Board board;
    private Team turn;

    private JanggiGame(Board board) {
        this.board = board;
        turn = Team.CHO;
    }

    public static JanggiGame init(SettingType choSetting, SettingType hanSetting) {
        return new JanggiGame(Board.of(choSetting, hanSetting));
    }

    public void executeMove(Position start, Position destination) {
        board.move(turn, start, destination);
        passTurn();
    }

    public void passTurn() {
        if (Team.CHO == turn) {
            turn = Team.HAN;
            return;
        }
        turn = Team.CHO;
    }

    public BoardStatus getJanggiGameStatus() {
        return board.getBoardStatus();
    }

    public Team getTurn() {
        return turn;
    }
}
