import board.Board;
import board.BoardFactory;
import position.Position;
import piece.Country;
import position.LineDirection;
import view.InputView;
import view.OutputView;

import java.util.List;

// TODO 2025. 3. 27. 20:53: 1. Board 내 필드 일급 컬렉션 처리 할 지
// TODO 2025. 3. 27. 20:53: 2. Board 양방향 매핑 처리 어떻게 할 건지
// TODO 2025. 3. 27. 20:54: 3. 테스트
// TODO 2025. 3. 27. 20:54: 4. 리뷰 처리


public class Application {

    private static final int MAX_TRY_COUNT = 150;

    public static void main(String[] args) {
        OutputView.printIntroduce();

        final BoardFactory boardFactory = new BoardFactory();
        final Board board = boardFactory.generateBoard(Country.HAN, LineDirection.UP);
        Country type = Country.getDefaultTeam();

        int count = 0;
        while (++count < MAX_TRY_COUNT) {
            type = type.opposite();
            OutputView.printBoard(board, type);
            final List<Position> positions = InputView.readPositions();
            board.updatePosition(positions.get(0), positions.get(1), type);
        }
    }
}
// Application - > 장기 관리자

// Board -> Janggi 관리자 Pieces, Team turn

// 1. Map<Position, Piece> -> Pieces 일급 컬랙션으로 만들기

// 2. Board 를 기물들 뿐만 아니라, 장기 전체적으로 관리하는 Janggi로 바꾸기

// 3. Janggi에서 turn, pieces 관리

// 4. Janggi에서 Piece로 경로 계산할 때 기물들 다 넘기지 말고 Route안에 있는 기물들만 Pieces로 넘기기
