package janggi.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import janggi.dto.GameDto;
import janggi.dto.PiecesOnBoardDto;
import janggi.entity.PieceEntity;
import janggi.exception.GameNotDeletedException;
import janggi.game.Board;
import janggi.game.Game;
import janggi.game.Team;
import janggi.movement.target.AttackedPiece;
import janggi.piece.Byeong;
import janggi.piece.Piece;
import janggi.point.Point;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PieceDaoTest {
    private Piece piece;
    private static final PieceDao pieceDao = new PieceDao();
    private static final GameDao gameDao = new GameDao();

    private Game createdGame;

    @BeforeEach
    void setUp() {
        piece = new Byeong(Team.HAN, new Point(5, 5));
        createdGame = new Game(new Board(List.of(piece)));
        gameDao.createGame(createdGame);
        pieceDao.createPiece(piece, createdGame);
    }

    @AfterEach
    void cleanUp() {
        try {
            gameDao.deleteGame(createdGame);
        } catch (GameNotDeletedException ignore) {
        }
    }

    @Test
    @DisplayName("기물 투플을 생성할 수 있다.")
    void createPieceTuple() {
        assertThatCode(() -> PieceEntity.findByPiece(piece)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("기물 투플의 위치를 수정할 수 있다.")
    void updatePieceTuplePoint() {
        Piece movedPiece = piece.updatePoint(new Point(4, 4));

        pieceDao.updatePointFrom(piece, movedPiece);

        assertThatCode(() -> PieceEntity.findByPiece(movedPiece)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("기물이 공격받았을 때 수정할 수 있다.")
    void updatePieceTupleIfAttacked() {
        GameDto lastCreated = gameDao.findLastCreated();

        pieceDao.updateToAttacked(new AttackedPiece(piece));
        PiecesOnBoardDto piecesDto = pieceDao.findPieceDataBy(lastCreated);

        assertThat(piecesDto.getRunningPieces()).hasSize(0);
        assertThat(piecesDto.getAttackedPieces()).hasSize(1);
    }

    @Test
    @DisplayName("조회된 기물 데이터에 맞게 기물 객체를 생성한다.")
    void createPieceObjectFromTuple() {
        GameDto lastCreated = gameDao.findLastCreated();
        PiecesOnBoardDto piecesDto = pieceDao.findPieceDataBy(lastCreated);

        assertThat(piecesDto.getRunningPieces()).hasSize(1);
        assertThat(piecesDto.getAttackedPieces()).hasSize(0);
    }
}