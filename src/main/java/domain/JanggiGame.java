package domain;

import domain.piece.Piece;
import domain.piece.Team;
import domain.position.Position;
import domain.settingType.SettingType;
import java.util.Map;

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
        board.validateIsAlly(turn, start);

        board.validateIsMovable(start, destination);

        board.move(start, destination);
        passTurn();
    }

    public void passTurn() {
        if (Team.CHO == turn) {
            turn = Team.HAN;
            return;
        }
        turn = Team.CHO;
    }

    public Map<Position, Piece> getJanggiGameStatus() {
        return board.getPieces();
    }

    public Team getTurn() {
        return turn;
    }
}
