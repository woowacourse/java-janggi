package janggi.dao;

import static org.junit.jupiter.api.Assertions.*;

import janggi.game.Board;
import janggi.game.Game;
import janggi.game.Team;
import janggi.piece.Byeong;
import janggi.piece.Piece;
import janggi.point.Point;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PieceDaoTest {

    @AfterEach
    void cleanUp() {
        //TODO 자원정리
    }

    @Test
    @DisplayName("기물 투플을 생성할 수 있다.")
    void createPieceTuple() {
        Piece piece = new Byeong(Team.HAN, new Point(5, 5));
        GameDao gameDao = GameDao.createGame(new Game(new Board(List.of(piece))));

        PieceDao.createPiece(piece, gameDao);
    }

}