package janggi.dao;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.dto.GameDto;
import janggi.dto.PieceDtos;
import janggi.game.Board;
import janggi.game.Game;
import janggi.game.Team;
import janggi.movement.target.AttackedPiece;
import janggi.piece.Byeong;
import janggi.piece.Cha;
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

    @Test
    @DisplayName("기물 투플의 위치를 수정할 수 있다.")
    void updatePieceTuplePoint() {
        Piece piece = new Byeong(Team.HAN, new Point(5, 5));
        GameDao gameDao = GameDao.createGame(new Game(new Board(List.of(piece))));
        Piece movedPiece = piece.updatePoint(new Point(4, 4));

        PieceDao beforePiece = PieceDao.createPiece(piece, gameDao);
        PieceDao.updatePointFrom(piece, movedPiece);

        assertThat(beforePiece.getPiece().getPoint()).isEqualTo(new Point(4, 4));
    }

    @Test
    @DisplayName("기물이 공격받았을 때 수정할 수 있다.")
    void updatePieceTupleIfAttacked() {
        Piece piece = new Byeong(Team.HAN, new Point(5, 5));
        Game game = new Game(new Board(List.of(piece)));
        GameDao gameDao = GameDao.createGame(game);

        PieceDao.createPiece(piece, gameDao);
        PieceDao.updateToAttacked(new AttackedPiece(piece));
        //TODO 검증가능?
    }

    @Test
    @DisplayName("조회된 기물 데이터에 맞게 기물 객체를 생성한다.")
    void createPieceObjectFromTuple() {
        Piece piece1 = new Byeong(Team.HAN, new Point(5, 5));
        Piece piece2 = new Cha(Team.CHO, new Point(4, 4));
        GameDao gameDao = GameDao.createGame(new Game(new Board(List.of(piece1, piece2))));
        PieceDao.createPiece(piece1, gameDao);
        PieceDao.createPiece(piece2, gameDao);
        PieceDao.updateToAttacked(new AttackedPiece(piece2));
        GameDto lastCreated = GameDao.findLastCreated();

        PieceDtos pieceDtos = PieceDao.findPiecesBy(lastCreated);

        assertThat(pieceDtos.getRunningPieces()).hasSize(1);
        assertThat(pieceDtos.getAttackedPieces()).hasSize(1);
    }
}