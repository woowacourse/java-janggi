package janggi.board;

import janggi.dao.JanggiDao;
import janggi.dto.MoveDto;
import janggi.piece.Cannon;
import janggi.piece.Chariot;
import janggi.piece.Elephant;
import janggi.piece.General;
import janggi.piece.Guard;
import janggi.piece.Horse;
import janggi.piece.Piece;
import janggi.piece.Soldier;
import janggi.piece.Team;
import janggi.position.Column;
import janggi.position.Position;
import janggi.position.Row;
import janggi.view.SetupOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class BoardGenerator {

    public static Board generate(final SetupOption setupOption, final JanggiDao janggiDao) {
        switch (setupOption) {
            case EXIST_SETUP:
                return generateExistSetup(janggiDao);
            case INNER_SETUP:
                return generateInnerSetup();
            case OUTER_SETUP:
                return generateOuterSetup();
            case RIGHT_SETUP:
                return generateRightSetup();
            case LEFT_SETUP:
                return generateLeftSetup();
            default:
                throw new IllegalStateException("[ERROR] 프로그램 로직이 잘못됐습니다.");
        }
    }

    private static Board generateExistSetup(final JanggiDao janggiDao) {
        if (janggiDao.existNotFinishedGame()) {
            final int notFinishedGameId = janggiDao.findNotFinishedGameId();
            final int notFinishedGameSetup = janggiDao.findNotFinishedGameSetup();
            final List<MoveDto> moveDtos = janggiDao.selectAllHistory(notFinishedGameId);
            final SetupOption setupOption = SetupOption.of(String.valueOf(notFinishedGameSetup));
            return moveByHistory(setupOption, moveDtos);
        }
        throw new IllegalArgumentException("[ERROR] 끝나지 않은 이전 게임 기록이 없습니다.");
    }

    private static Board moveByHistory(final SetupOption setupOption, final List<MoveDto> moveDtos) {
        final Board board = generateOriginalSetup(setupOption);
        for (MoveDto moveDto : moveDtos) {
            board.move(moveDto.getStartPosition(), moveDto.getEndPosition());
        }
        return board;
    }

    public static Board generateOriginalSetup(final SetupOption setupOption) {
        switch (setupOption) {
            case INNER_SETUP:
                return generateInnerSetup();
            case OUTER_SETUP:
                return generateOuterSetup();
            case RIGHT_SETUP:
                return generateRightSetup();
            case LEFT_SETUP:
                return generateLeftSetup();
            default:
                throw new IllegalStateException("[ERROR] 프로그램 로직이 잘못됐습니다.");
        }
    }

    private static Board generateInnerSetup() {
        return generateSetup(
                List.of(Horse.of(Team.HAN), Elephant.of(Team.HAN), Elephant.of(Team.HAN), Horse.of(Team.HAN),
                        Horse.of(Team.CHO), Elephant.of(Team.CHO), Elephant.of(Team.CHO), Horse.of(Team.CHO)),
                1
        );
    }

    private static Board generateOuterSetup() {
        return generateSetup(
                List.of(Elephant.of(Team.HAN), Horse.of(Team.HAN), Horse.of(Team.HAN), Elephant.of(Team.HAN),
                        Elephant.of(Team.CHO), Horse.of(Team.CHO), Horse.of(Team.CHO), Elephant.of(Team.CHO)),
                2
        );
    }

    private static Board generateRightSetup() {
        return generateSetup(
                List.of(Elephant.of(Team.HAN), Horse.of(Team.HAN), Elephant.of(Team.HAN), Horse.of(Team.HAN),
                        Elephant.of(Team.CHO), Horse.of(Team.CHO), Elephant.of(Team.CHO), Horse.of(Team.CHO)),
                3
        );
    }

    private static Board generateLeftSetup() {
        return generateSetup(
                List.of(Horse.of(Team.HAN), Elephant.of(Team.HAN), Horse.of(Team.HAN), Elephant.of(Team.HAN),
                        Horse.of(Team.CHO), Elephant.of(Team.CHO), Horse.of(Team.CHO), Elephant.of(Team.CHO)),
                4
        );
    }

    private static Board generateSetup(final List<? extends Piece> pieces, final int setupOption) {
        Map<Position, Piece> board = generateGeneralMap();
        board.put(new Position(Row.ZERO, Column.ONE), pieces.get(0));
        board.put(new Position(Row.ZERO, Column.TWO), pieces.get(1));
        board.put(new Position(Row.ZERO, Column.SIX), pieces.get(2));
        board.put(new Position(Row.ZERO, Column.SEVEN), pieces.get(3));
        board.put(new Position(Row.NINE, Column.ONE), pieces.get(4));
        board.put(new Position(Row.NINE, Column.TWO), pieces.get(5));
        board.put(new Position(Row.NINE, Column.SIX), pieces.get(6));
        board.put(new Position(Row.NINE, Column.SEVEN), pieces.get(7));
        return new Board(board, setupOption);
    }

    private static Map<Position, Piece> generateGeneralMap() {
        Map<Position, Piece> board = new HashMap<>();
        generateGeneralHanMap(board);
        generateGeneralChoMap(board);
        return board;
    }

    private static void generateGeneralHanMap(final Map<Position, Piece> board) {
        board.put(new Position(Row.ONE, Column.FOUR), General.of(Team.HAN));
        board.put(new Position(Row.ZERO, Column.ZERO), Chariot.of(Team.HAN));
        board.put(new Position(Row.ZERO, Column.EIGHT), Chariot.of(Team.HAN));
        board.put(new Position(Row.TWO, Column.ONE), Cannon.of(Team.HAN));
        board.put(new Position(Row.TWO, Column.SEVEN), Cannon.of(Team.HAN));
        board.put(new Position(Row.ZERO, Column.THREE), Guard.of(Team.HAN));
        board.put(new Position(Row.ZERO, Column.FIVE), Guard.of(Team.HAN));
        board.put(new Position(Row.THREE, Column.ZERO), Soldier.of(Team.HAN));
        board.put(new Position(Row.THREE, Column.TWO), Soldier.of(Team.HAN));
        board.put(new Position(Row.THREE, Column.FOUR), Soldier.of(Team.HAN));
        board.put(new Position(Row.THREE, Column.SIX), Soldier.of(Team.HAN));
        board.put(new Position(Row.THREE, Column.EIGHT), Soldier.of(Team.HAN));
    }

    private static void generateGeneralChoMap(final Map<Position, Piece> board) {
        board.put(new Position(Row.EIGHT, Column.FOUR), General.of(Team.CHO));
        board.put(new Position(Row.NINE, Column.ZERO), Chariot.of(Team.CHO));
        board.put(new Position(Row.NINE, Column.EIGHT), Chariot.of(Team.CHO));
        board.put(new Position(Row.SEVEN, Column.ONE), Cannon.of(Team.CHO));
        board.put(new Position(Row.SEVEN, Column.SEVEN), Cannon.of(Team.CHO));
        board.put(new Position(Row.NINE, Column.THREE), Guard.of(Team.CHO));
        board.put(new Position(Row.NINE, Column.FIVE), Guard.of(Team.CHO));
        board.put(new Position(Row.SIX, Column.ZERO), Soldier.of(Team.CHO));
        board.put(new Position(Row.SIX, Column.TWO), Soldier.of(Team.CHO));
        board.put(new Position(Row.SIX, Column.FOUR), Soldier.of(Team.CHO));
        board.put(new Position(Row.SIX, Column.SIX), Soldier.of(Team.CHO));
        board.put(new Position(Row.SIX, Column.EIGHT), Soldier.of(Team.CHO));
    }
}
