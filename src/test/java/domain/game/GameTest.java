package domain.game;

import static org.junit.jupiter.api.Assertions.assertEquals;

import domain.board.Board;
import domain.board.BoardFactory;
import domain.board.Formation;
import domain.piece.Cha;
import domain.piece.Jol;
import domain.piece.None;
import domain.piece.Piece;
import domain.piece.Po;
import domain.piece.Sa;
import domain.player.Player;
import domain.player.Players;
import domain.player.Team;
import domain.position.Position;
import domain.score.Score;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class GameTest {

    @Test
    void 게임_시작_시_점수는_초_72점_한_73_5점이다() {
        Players players = new Players(List.of(
                Player.of("cho", Team.CHO),
                Player.of("han", Team.HAN)
        ));
        Board board = BoardFactory.createWithFormation(
                Formation.SANG_MA_SANG_MA,
                Formation.SANG_MA_SANG_MA
        );
        Game game = new Game(players, board);

        Score score = game.getScore();

        assertEquals(72.0, score.getChoScore());
        assertEquals(73.5, score.getHanScore());
    }

    @Test
    void 보드에_남아있는_기물_기준으로_점수를_계산한다() {
        Players players = new Players(List.of(
                Player.of("cho", Team.CHO),
                Player.of("han", Team.HAN)
        ));
        Map<Position, Piece> boardMap = createEmptyBoard();
        boardMap.put(new Position(9, 0), new Cha(Team.CHO));
        boardMap.put(new Position(7, 1), new Po(Team.CHO));
        boardMap.put(new Position(6, 0), new Jol(Team.CHO));
        boardMap.put(new Position(0, 3), new Sa(Team.HAN));
        boardMap.put(new Position(3, 0), new Jol(Team.HAN));

        Game game = new Game(players, new Board(boardMap));

        Score score = game.getScore();

        assertEquals(22.0, score.getChoScore());
        assertEquals(6.5, score.getHanScore());
    }

    private Map<Position, Piece> createEmptyBoard() {
        Map<Position, Piece> boardMap = new HashMap<>();
        for (int row = Board.MIN_ROW; row <= Board.MAX_ROW; row++) {
            for (int column = Board.MIN_COLUMN; column <= Board.MAX_COLUMN; column++) {
                boardMap.put(new Position(row, column), new None());
            }
        }
        return boardMap;
    }
}
