package janggi.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.model.Janggi;
import janggi.model.Team;
import janggi.model.piece.Piece;
import janggi.model.piece.PieceType;
import janggi.model.position.absolute.Column;
import janggi.model.position.absolute.Position;
import janggi.model.position.absolute.Row;
import janggi.repository.dao.GameEntityDao;
import janggi.repository.dao.PieceEntityDao;
import janggi.repository.dto.LatestInProgressGameResponse;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JdbcGameRepositoryTest extends DatabaseTest{

    gameRepository gameRepository = new JdbcGameRepository(
            new GameEntityDao(),
            new PieceEntityDao()
    );

    @DisplayName("진행 중인 장기 게임을 조회한다.")
    @Test
    void findLatestInProgressGame() {
        //given
        Long gameId = gameEntityDao.save(con, "CHO");

        pieceEntityDao.save(con, gameId, "BYEONG", 1, 1, "CHO");
        pieceEntityDao.save(con, gameId, "JANG", 1, 2, "HAN");
        pieceEntityDao.save(con, gameId, "SA", 1, 3, "CHO");

        //when
        Optional<LatestInProgressGameResponse> result =
                gameRepository.findLatestInProgressGame(con);

        //then
        Janggi found = result.get().janggi();
        assertThat(found.getCurrentTeam()).isEqualTo(Team.HAN);

        Map<Position, Piece> boardInfo = found.getBoard().getBoardInfo();
        Piece first = boardInfo.get(new Position(Row.ONE, Column.ONE));
        Piece second = boardInfo.get(new Position(Row.ONE, Column.TWO));
        Piece third = boardInfo.get(new Position(Row.ONE, Column.THREE));

        assertThat(first.getPieceType()).isEqualTo(PieceType.BYEONG);
        assertThat(second.getPieceType()).isEqualTo(PieceType.JANG);
        assertThat(third.getPieceType()).isEqualTo(PieceType.SA);
    }

    @DisplayName("from에 있는 기물을 to로 이동시킨 것을 반영해 보드를 업데이트 한다.")
    @Test
    void updateBoardWith_success() {
        //given
        Long gameId = gameEntityDao.save(con, "CHO");

        pieceEntityDao.save(con, gameId, "BYEONG", 1, 1, "CHO");

        //when
        gameRepository.updateBoardWith(
                con,
                new Position(Row.ONE, Column.ONE),
                new Position(Row.TWO, Column.ONE)
        );

        //then
        Janggi game = gameRepository.findLatestInProgressGame(con).get().janggi();

        Map<Position, Piece> boardInfo = game.getBoard().getBoardInfo();
        assertThat(boardInfo.get(new Position(Row.TWO, Column.ONE)).getPieceType())
                .isEqualTo(PieceType.BYEONG);
    }

    @DisplayName("원래 to에 있던 기물을 삭제한다..")
    @Test
    void updateBoardWith_success_to_exist() {
        //given
        Long gameId = gameEntityDao.save(con, "CHO");

        pieceEntityDao.save(con, gameId, "BYEONG", 1, 1, "CHO");
        pieceEntityDao.save(con, gameId, "BYEONG", 2, 1, "HAN");

        //when
        gameRepository.updateBoardWith(
                con,
                new Position(Row.ONE, Column.ONE),
                new Position(Row.TWO, Column.ONE)
        );

        //then
        Janggi game = gameRepository.findLatestInProgressGame(con).get().janggi();

        Map<Position, Piece> boardInfo = game.getBoard().getBoardInfo();
        assertThat(boardInfo.size()).isEqualTo(1);
    }

    @DisplayName("from에 기물이 없으면 예외가 발생한다.")
    @Test
    void updateBoardWith_fail() {
        //given
        gameEntityDao.save(con, "CHO");

        //when & then

        assertThatThrownBy(() ->
                        gameRepository.updateBoardWith(
                                con,
                                new Position(Row.ONE, Column.ONE),
                                new Position(Row.TWO, Column.ONE)
                        )
        ).isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("해당 위치에 기물이 존재하지 않습니다.");
    }
}