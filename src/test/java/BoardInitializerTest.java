import domain.board.Board;
import domain.piece.PieceType;
import domain.board.Position;

import domain.piece.TeamColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;



import static org.assertj.core.api.Assertions.assertThat;
import strategy.formation.InitialFormationStrategy;
import strategy.formation.InnerFormationStrategy;

class BoardInitializerTest {

    InitialFormationStrategy choStrategy;
    InitialFormationStrategy hanStrategy;
    Board board;

    @BeforeEach
    public void setUp(){
        choStrategy = new InnerFormationStrategy();
        hanStrategy = new InnerFormationStrategy();
        BoardInitializer boardInitializer = new BoardInitializer(choStrategy, hanStrategy);
        board = boardInitializer.initialize();
    }


    @Nested
    class 초기화 {
        @Test
        void 초나라_한나라_전략을_주입받아_총_32개의_기물이_세팅된_초기_장기판을_반환한다() {
            assertThat(board.findPiecesByTeam(TeamColor.CHO)).hasSize(16);
            assertThat(board.findPiecesByTeam(TeamColor.HAN)).hasSize(16);
            assertThat(board.findPiece(Position.of(8, 4))).get().extracting("pieceType").isEqualTo(PieceType.KING);
            assertThat(board.findPiece(Position.of(0, 2))).get().extracting("pieceType").isEqualTo(PieceType.ELEPHANT);
        }
    }

    @Nested
    class 기물목록 {
        @Test
        void 초나라_기물_리스트를_생성한다() {
            assertThat(board.findPiecesByTeam(TeamColor.CHO)).hasSize(16);
            assertThat(
                    board.findPiecesByTeam(TeamColor.CHO).stream()
                            .map(piecePosition -> piecePosition.piece())
                            .filter(p -> p.getPieceType() == PieceType.ELEPHANT)
            ).hasSize(2);

            assertThat(
                    board.findPiecesByTeam(TeamColor.CHO).stream()
                            .map(piecePosition -> piecePosition.piece())
                            .filter(p -> p.getPieceType() == PieceType.HORSE)
            ).hasSize(2);

            assertThat(
                    board.findPiecesByTeam(TeamColor.CHO).stream()
                            .map(piecePosition -> piecePosition.piece())
                            .filter(p -> p.getPieceType() == PieceType.CANNON)
            ).hasSize(2);

            assertThat(
                    board.findPiecesByTeam(TeamColor.CHO).stream()
                            .map(piecePosition -> piecePosition.piece())
                            .filter(p -> p.getPieceType() == PieceType.ROOK)
            ).hasSize(2);

            assertThat(
                    board.findPiecesByTeam(TeamColor.CHO).stream()
                            .map(piecePosition -> piecePosition.piece())
                            .filter(p -> p.getPieceType() == PieceType.GUARD)
            ).hasSize(2);

            assertThat(
                    board.findPiecesByTeam(TeamColor.CHO).stream()
                            .map(piecePosition -> piecePosition.piece())
                            .filter(p -> p.getPieceType() == PieceType.PAWN)
            ).hasSize(5);

            assertThat(
                    board.findPiecesByTeam(TeamColor.CHO).stream()
                            .map(piecePosition -> piecePosition.piece())
                            .filter(p -> p.getPieceType() == PieceType.KING)
            ).hasSize(1);
        }
    }
}

