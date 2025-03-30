import board.Board;
import board.BoardFactory;
import board.Palace;
import piece.Country;
import position.Position;
import position.PositionFactory;
import view.InputView;
import view.LineSettingDto;
import view.OutputView;

import java.util.List;

// TODO 2025. 3. 29. 13:33: <전역>
// TODO 2025. 3. 29. 13:32: 테스트 코드 제네릭
// TODO 2025. 3. 29. 13:32: y up, down 방향 바꿀 때 한번에 바꿀 수 있도록 코드 결합도 고려
// TODO 2025. 3. 29. 13:33: row, column 처리
// TODO 2025. 3. 29. 13:34: turn 처리 좀 더 설계적으로

// TODO 2025. 3. 29. 13:34: <점수>
// TODO 2025. 3. 29. 13:34: player 생성?

public class Application {

    private static final int MAX_TRY_COUNT = 150;

    public static void main(String[] args) {
        initSetting();
        LineSettingDto settingDto = InputView.readLineSettingByCountry();
        final BoardFactory boardFactory = new BoardFactory(settingDto.country(), settingDto.direction());
        final Board board = boardFactory.generateBoard();
        Country type = Country.getDefaultTeam();

        int count = 0;
        while (++count < MAX_TRY_COUNT) {
            type = type.opposite();
            OutputView.printBoard(board, type);
            final List<Position> positions = InputView.readPositions();
            board.updatePosition(positions.get(0), positions.get(1), type);
        }
    }

    private static void initSetting() {
        OutputView.printIntroduce();
        PositionFactory positionFactory = new PositionFactory();
        positionFactory.basicSettingGraph();
        positionFactory.diagonalSettingGraph(Palace.getCenterPositions());
    }
}
