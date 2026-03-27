package domain.board;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import domain.piece.Cha;
import domain.piece.Jol;
import domain.piece.None;
import domain.piece.Piece;
import domain.piece.Po;
import domain.piece.Sang;
import domain.player.Team;
import domain.position.Position;
import dto.BoardDTO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class BoardTest {

    private Map<Position, Piece> createEmptyBoard() {
        Map<Position, Piece> boardMap = new HashMap<>();
        for (int y = 0; y <= 9; y++) {
            for (int x = 0; x <= 8; x++) {
                boardMap.put(new Position(x, y), new None());
            }
        }

        return boardMap;
    }

    @Nested
    class 포_이외의_기물_이동_가능_판단_테스트 {
        @Test
        void 이동할_수_없는_경우는_canMove가_Exception_던진다() {
            Map<Position, Piece> boardMap = createEmptyBoard();
            boardMap.put(new Position(0, 0), new Cha(Team.CHO));
            boardMap.put(new Position(1, 0), new Sang(Team.CHO));

            Board board = new Board(boardMap);
            assertThrows(IllegalArgumentException.class, () -> board.canMove(new Position(0, 0), new Position(8, 0)));
        }

        @Test
        void 이동할_수_있는_경우는_canMove가_Exception_던지지_않는다() {
            Map<Position, Piece> boardMap = createEmptyBoard();
            boardMap.put(new Position(0, 3), new Jol(Team.CHO));

            Board board = new Board(boardMap);
            assertDoesNotThrow(() -> board.canMove(new Position(0, 3), new Position(0, 4)));
        }
    }

    @Nested
    class 포_기물_이동_가능_판단_테스트 {
        @Test
        void 포가_이동할_수_없는_경우는_canMove가_Exception_던진다() {
            Map<Position, Piece> boardMap = createEmptyBoard();
            boardMap.put(new Position(0, 0), new Po(Team.CHO));

            Board board = new Board(boardMap);
            assertThrows(IllegalArgumentException.class, () -> board.canMove(new Position(0, 0), new Position(0, 3)));
        }

        @Test
        void 포가_이동할_수_있는_경우는_canMove가_Exception_던지지_않는다() {
            Map<Position, Piece> boardMap = createEmptyBoard();
            boardMap.put(new Position(0, 0), new Po(Team.CHO));
            boardMap.put(new Position(0, 1), new Cha(Team.HAN));
            boardMap.put(new Position(0, 2), new None());

            Board board = new Board(boardMap);
            assertDoesNotThrow(() -> board.canMove(new Position(0, 0), new Position(0, 2)));
        }

        @Test
        void 포가_도착지에_상대편_포가_있으면_canMove가_Exception_던진다() {
            Map<Position, Piece> boardMap = createEmptyBoard();
            boardMap.put(new Position(0, 0), new Po(Team.CHO));
            boardMap.put(new Position(0, 1), new Cha(Team.HAN));
            boardMap.put(new Position(0, 2), new Po(Team.HAN));

            Board board = new Board(boardMap);
            assertThrows(IllegalArgumentException.class, () -> board.canMove(new Position(0, 0), new Position(0, 2)));
        }
    }

    @Nested
    class 기물_이동_테스트 {
        @Test
        void 도착_위치에_상대편_기물이_있는_경우_해당_기물을_반환한다() {
            Map<Position, Piece> boardMap = createEmptyBoard();
            boardMap.put(new Position(0, 0), new Cha(Team.CHO));
            boardMap.put(new Position(3, 0), new Cha(Team.HAN));

            Board board = new Board(boardMap);

            assertEquals(new Cha(Team.HAN), board.move(new Position(0, 0), new Position(3, 0)));
        }

        @Test
        void 도착_위치에_상대편_기물이_없는_경우_None_기물을_반환한다() {
            Map<Position, Piece> boardMap = createEmptyBoard();
            boardMap.put(new Position(0, 0), new Cha(Team.CHO));

            Board board = new Board(boardMap);

            assertEquals(new None(), board.move(new Position(0, 0), new Position(3, 0)));
        }
    }

    @Nested
    class BoardDTO_생성_테스트 {
        @Test
        void 빈_보드를_DTO로_변환하면_모든_칸이_None_기물이다() {
            Map<Position, Piece> boardMap = createEmptyBoard();
            Board board = new Board(boardMap);

            BoardDTO dto = board.createDTO();
            List<List<String>> data = dto.board();

            assertEquals(10, data.size());
            assertEquals(9, data.get(0).size());
            
            for (int y = 0; y < 10; y++) {
                for (int x = 0; x < 9; x++) {
                    assertEquals("  ", data.get(y).get(x));
                }
            }
        }

        @Test
        void 기물이_있는_보드를_DTO로_변환하면_기물의_문자열이_표시된다() {
            Map<Position, Piece> boardMap = createEmptyBoard();
            boardMap.put(new Position(0, 0), new Cha(Team.CHO));
            boardMap.put(new Position(4, 1), new Sang(Team.CHO));
            boardMap.put(new Position(3, 5), new Po(Team.HAN));

            Board board = new Board(boardMap);
            BoardDTO dto = board.createDTO();
            List<List<String>> data = dto.board();

            assertEquals("CH", data.get(0).get(0));
            assertEquals("SD", data.get(1).get(4));
            assertEquals("PO", data.get(5).get(3));
        }

        @Test
        void DTO는_정확한_크기의_2차원_리스트를_반환한다() {
            Map<Position, Piece> boardMap = createEmptyBoard();
            boardMap.put(new Position(0, 0), new Cha(Team.CHO));
            boardMap.put(new Position(8, 9), new Jol(Team.HAN));

            Board board = new Board(boardMap);
            BoardDTO dto = board.createDTO();
            List<List<String>> data = dto.board();

            assertEquals(10, data.size());
            for (List<String> row : data) {
                assertEquals(9, row.size());
            }
        }
    }
}
