package janggi.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.dao.game.GameDao;
import janggi.dao.piece.PieceDao;
import janggi.model.Janggi;
import janggi.model.Team;
import janggi.model.board.PlayingBoard;
import janggi.model.initializer.LeftSidedTableSetting;
import janggi.model.piece.Byeong;
import janggi.model.piece.Piece;
import janggi.model.piece.PieceType;
import janggi.model.piece.palace.Jang;
import janggi.model.position.absolute.Column;
import janggi.model.position.absolute.Position;
import janggi.model.position.absolute.Row;
import janggi.service.dto.LatestInProgressGameResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JanggiServiceTest {

    GameDao gameDao;
    PieceDao pieceDao;
    JanggiService janggiService;

    @BeforeEach
    void beforeEach() {
        gameDao = new TestGameDao();
        pieceDao = new TestPieceDao();

        janggiService = new JanggiService(
                gameDao,
                pieceDao,
                new TestTransactionExecutor(null)
        );
    }

    @DisplayName("진행 중이던 게임이 없으면 Optional.empty()를 반환한다.")
    @Test
    void loadGame_empty() {
        assertThat(janggiService.loadGame())
                .isEmpty();
    }

    @DisplayName("진행 중이던 게임을 조회한다.")
    @Test
    void loadGame_success() {
        //given
        Long gameId = gameDao.save(null, "CHO");

        pieceDao.save(null, gameId, "BYEONG", 1, 1, "CHO");
        pieceDao.save(null, gameId, "JANG", 1, 2, "HAN");
        pieceDao.save(null, gameId, "SA", 1, 3, "CHO");

        //when
        Optional<LatestInProgressGameResponse> janggiOpt =
                janggiService.loadGame();

        //then
        Janggi janggi = janggiOpt.get().janggi();
        assertThat(janggi.getCurrentTeam()).isEqualTo(Team.HAN);

        Map<Position, Piece> boardInfo = janggi.getBoard().getBoardInfo();

        assertThat(boardInfo.get(new Position(Row.ONE, Column.ONE))
                .getPieceType()).isEqualTo(PieceType.BYEONG);
        assertThat(boardInfo.get(new Position(Row.ONE, Column.TWO))
                .getPieceType()).isEqualTo(PieceType.JANG);
        assertThat(boardInfo.get(new Position(Row.ONE, Column.THREE))
                .getPieceType()).isEqualTo(PieceType.SA);
    }

    @DisplayName("진행 중이던 게임이 없으면 Optional.empty()를 반환한다.")
    @Test
    void loadGameByGameId_empty() {
        assertThat(janggiService.loadGameByGameId(100L))
                .isEmpty();
    }

    @DisplayName("진행 중이던 게임을 조회한다.")
    @Test
    void loadGameByGameId_success() {
        //given
        Long gameId1 = gameDao.save(null, "HAN");
        Long gameId2 = gameDao.save(null, "CHO");

        pieceDao.save(null, gameId2, "BYEONG", 1, 1, "CHO");
        pieceDao.save(null, gameId2, "JANG", 1, 2, "HAN");
        pieceDao.save(null, gameId2, "SA", 1, 3, "CHO");

        //when
        Optional<LatestInProgressGameResponse> janggiOpt =
                janggiService.loadGameByGameId(gameId2);

        //then
        Janggi janggi = janggiOpt.get().janggi();
        assertThat(janggi.getCurrentTeam()).isEqualTo(Team.HAN);

        Map<Position, Piece> boardInfo = janggi.getBoard().getBoardInfo();

        assertThat(boardInfo.get(new Position(Row.ONE, Column.ONE))
                .getPieceType()).isEqualTo(PieceType.BYEONG);
        assertThat(boardInfo.get(new Position(Row.ONE, Column.TWO))
                .getPieceType()).isEqualTo(PieceType.JANG);
        assertThat(boardInfo.get(new Position(Row.ONE, Column.THREE))
                .getPieceType()).isEqualTo(PieceType.SA);
    }


    @DisplayName("새로운 게임을 시작한다.")
    @Test
    void initGame() {
        //given
        PlayingBoard board = PlayingBoard.of(
                new LeftSidedTableSetting().init().getBoardInfo()
        );

        //when
        LatestInProgressGameResponse response = janggiService.initGame(board);

        //then
        assertThat(response.gameId())
                .isEqualTo(1L);
        assertThat(response.janggi().getCurrentTeam())
                .isEqualTo(Team.CHO);
    }

    @DisplayName("from에 기물이 없으면 예외가 발생한다.")
    @Test
    void updateBoardWith_fail() {
        //given
        gameDao.save(null, "CHO");

        //when & then
        assertThatThrownBy(() ->
                janggiService.updateBoardWith(
                        Janggi.of(PlayingBoard.of(Map.of())),
                        new Position(Row.ONE, Column.ONE),
                        new Position(Row.TWO, Column.ONE)
                )
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 위치에 기물이 존재하지 않습니다.");
    }

    @DisplayName("from에 있는 기물을 to로 이동시킨 것을 반영해 보드를 업데이트 한다.")
    @Test
    void updateBoardWith_success() {
        //given
        Map<Position, Piece> boardInfo = new HashMap<>();

        Position from = new Position(Row.NINE, Column.FIVE);
        Piece pieceAtFrom = new Jang(Team.CHO);

        boardInfo.put(from, pieceAtFrom);
        boardInfo.put(new Position(Row.TWO, Column.FIVE), new Jang(Team.HAN));

        Janggi janggi = Janggi.of(PlayingBoard.of(boardInfo));

        Long gameId = gameDao.save(null, "CHO");

        for (Entry<Position, Piece> entry : boardInfo.entrySet()) {
            Position position = entry.getKey();
            Piece piece = entry.getValue();

            pieceDao.save(
                    null,
                    gameId,
                    piece.getPieceType().name(),
                    position.row().getValue(),
                    position.column().getValue(),
                    Team.CHO.name()
            );
        }

        Position to = new Position(Row.NINE, Column.SIX);

        //when
       janggiService.updateBoardWith(
                janggi,
                from,
                to
        );

        //then
        Optional<LatestInProgressGameResponse> responseOpt = janggiService.loadGame();
        LatestInProgressGameResponse response = responseOpt.get();

        Janggi updated = response.janggi();

        assertThat(updated.getCurrentTeam()).isEqualTo(Team.HAN);
        assertThat(updated.getBoard().getBoardInfo().get(to).getPieceType())
                .isEqualTo(pieceAtFrom.getPieceType());
    }

    @DisplayName("원래 to에 있던 기물을 삭제한다.")
    @Test
    void updateBoardWith_success_to_exist() {
        //given
        Map<Position, Piece> boardInfo = new HashMap<>();

        Position from = new Position(Row.NINE, Column.FIVE);
        Piece pieceAtFrom = new Jang(Team.CHO);

        Position to = new Position(Row.NINE, Column.SIX);

        boardInfo.put(from, pieceAtFrom);
        boardInfo.put(new Position(Row.TWO, Column.FIVE), new Jang(Team.HAN));
        boardInfo.put(to, new Byeong(Team.HAN));

        Janggi janggi = Janggi.of(PlayingBoard.of(boardInfo));

        Long gameId = gameDao.save(null, "CHO");

        for (Entry<Position, Piece> entry : boardInfo.entrySet()) {
            Position position = entry.getKey();
            Piece piece = entry.getValue();

            pieceDao.save(
                    null,
                    gameId,
                    piece.getPieceType().name(),
                    position.row().getValue(),
                    position.column().getValue(),
                    Team.CHO.name()
            );
        }

        //when
        Janggi updated = janggiService.updateBoardWith(
                janggi,
                from,
                to
        );

        //then
        assertThat(updated.getBoard().getBoardInfo().get(to))
                .isEqualTo(pieceAtFrom);
    }

    @DisplayName("게임을 삭제한다.")
    @Test
    void removeGame() {
        //given
        Long gameId = gameDao.save(null, "CHO");

        //when
        janggiService.removeGame(gameId);

        //then
        assertThat(janggiService.loadGame())
                .isEmpty();
    }
}