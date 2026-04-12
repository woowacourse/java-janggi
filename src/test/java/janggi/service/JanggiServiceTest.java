package janggi.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.dao.game.GameDao;
import janggi.dao.piece.PieceDao;
import janggi.model.Janggi;
import janggi.model.Team;
import janggi.model.board.PlayingBoard;
import janggi.model.piece.Byeong;
import janggi.model.piece.Piece;
import janggi.model.piece.PieceType;
import janggi.model.piece.palace.Jang;
import janggi.model.piece.palace.Sa;
import janggi.model.position.absolute.Column;
import janggi.model.position.absolute.Position;
import janggi.model.position.absolute.Row;
import janggi.service.dto.GameDetailResponse;
import janggi.service.dto.GameOptionResponse;
import janggi.view.mapping.BoardType;
import java.util.List;
import java.util.Map;
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

    @DisplayName("진행 중이던 게임이 없으면 예외가 발생한다.")
    @Test
    void loadGameByGameId_empty() {
        assertThatThrownBy(() -> janggiService.loadGameByGameId(100L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 게임이 존재하지 않습니다.");
    }

    @DisplayName("gameId로 게임을 조회한다.")
    @Test
    void loadGameByGameId_success() {
        //given
        gameDao.saveGame(null, "HAN");
        Long gameId = gameDao.saveGame(null, "CHO");

        Map<Position, Piece> boardInfo = Map.of(
                new Position(Row.ONE, Column.ONE), new Byeong(Team.CHO),
                new Position(Row.ONE, Column.TWO), new Jang(Team.HAN),
                new Position(Row.ONE, Column.THREE), new Sa(Team.CHO)
        );

        pieceDao.saveBoard(null, boardInfo, gameId);

        //when
        GameDetailResponse response = janggiService.loadGameByGameId(gameId);

        //then
        Janggi janggi = response.janggi();
        assertThat(janggi.getCurrentTeam()).isEqualTo(Team.HAN);

        Map<Position, Piece> result = janggi.getBoard().getBoardInfo();

        assertThat(result.get(new Position(Row.ONE, Column.ONE))
                .getPieceType()).isEqualTo(PieceType.BYEONG);
        assertThat(result.get(new Position(Row.ONE, Column.TWO))
                .getPieceType()).isEqualTo(PieceType.JANG);
        assertThat(result.get(new Position(Row.ONE, Column.THREE))
                .getPieceType()).isEqualTo(PieceType.SA);
    }


    @DisplayName("새로운 게임을 시작한다.")
    @Test
    void initGame() {
        //given
        BoardType boardType = BoardType.FIRST;

        //when
        GameDetailResponse response = janggiService.initGame(boardType.getBoard());

        //then
        assertThat(response.gameId())
                .isEqualTo(1L);
        assertThat(response.janggi().getCurrentTeam())
                .isEqualTo(Team.CHO);
    }

    @DisplayName("from에 기물이 없으면 예외가 발생한다.")
    @Test
    void playTurn_fail() {
        //given
        gameDao.saveGame(null, "CHO");

        //when & then
        assertThatThrownBy(() ->
                janggiService.playTurn(
                        Janggi.of(PlayingBoard.of(Map.of())),
                        new Position(Row.ONE, Column.ONE),
                        new Position(Row.TWO, Column.ONE)
                )
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 위치에 기물이 존재하지 않습니다.");
    }

    @DisplayName("from에 있는 기물을 to로 이동시킨 것을 반영해 보드를 업데이트 한다.")
    @Test
    void playTurn_success() {
        //given
        Position from = new Position(Row.NINE, Column.FIVE);
        Piece pieceAtFrom = new Jang(Team.CHO);

        Map<Position, Piece> boardInfo = Map.of(
                from, pieceAtFrom,
                new Position(Row.TWO, Column.FIVE), new Jang(Team.HAN)
        );

        Janggi janggi = Janggi.of(PlayingBoard.of(boardInfo));

        Long gameId = gameDao.saveGame(null, "CHO");
        pieceDao.saveBoard(null, boardInfo, gameId);

        Position to = new Position(Row.NINE, Column.SIX);

        //when
        janggiService.playTurn(
                janggi,
                from,
                to
        );

        //then

        GameDetailResponse response = janggiService.loadGameByGameId(gameId);

        Janggi updated = response.janggi();

        assertThat(updated.getCurrentTeam()).isEqualTo(Team.HAN);
        assertThat(updated.getBoard().getBoardInfo().get(to).getPieceType())
                .isEqualTo(pieceAtFrom.getPieceType());
    }

    @DisplayName("원래 to에 있던 기물을 삭제한다.")
    @Test
    void playTurn_success_to_exist() {
        //given
        Position from = new Position(Row.NINE, Column.FIVE);
        Piece pieceAtFrom = new Jang(Team.CHO);

        Position to = new Position(Row.NINE, Column.SIX);

        Map<Position, Piece> boardInfo = Map.of(
                from, pieceAtFrom,
                new Position(Row.TWO, Column.FIVE), new Jang(Team.HAN),
                to, new Byeong(Team.HAN)
        );

        Janggi janggi = Janggi.of(PlayingBoard.of(boardInfo));

        Long gameId = gameDao.saveGame(null, "CHO");
        pieceDao.saveBoard(null, boardInfo, gameId);

        //when
        Janggi updated = janggiService.playTurn(
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
        Long gameId = gameDao.saveGame(null, "CHO");

        //when
        janggiService.removeGame(gameId);

        //then
        assertThatThrownBy(() -> janggiService.loadGameByGameId(gameId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 게임이 존재하지 않습니다.");
    }

    @DisplayName("모든 게임을 조회한다.")
    @Test
    void loadAllGames() {
        //given
        Long gameId1 = gameDao.saveGame(null, "CHO");
        Long gameId2 = gameDao.saveGame(null, "HAN");
        Long gameId3 = gameDao.saveGame(null, "CHO");

        //when
        List<GameOptionResponse> responses = janggiService.loadAllGames();

        //then
        assertThat(responses).containsExactly(
                new GameOptionResponse(gameId1, Team.CHO),
                new GameOptionResponse(gameId2, Team.HAN),
                new GameOptionResponse(gameId3, Team.CHO)
        );
    }
}
