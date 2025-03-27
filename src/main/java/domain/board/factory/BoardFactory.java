package domain.board.factory;

import domain.board.Board;
import domain.board.Point;
import domain.board.factory.elephantLocators.ElephantLocator;
import domain.board.factory.elephantLocators.InnerElephantLocator;
import domain.board.factory.elephantLocators.LeftElephantLocator;
import domain.board.factory.elephantLocators.OuterElephantLocator;
import domain.board.factory.elephantLocators.RightElephantLocator;
import domain.pieces.Cannon;
import domain.pieces.Chariot;
import domain.pieces.General;
import domain.pieces.Guard;
import domain.pieces.Piece;
import domain.pieces.Soldier;
import domain.player.TeamType;
import exceptions.JanggiGameRuleWarningException;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class BoardFactory {
    private static final int MAX_SOLDIER_COUNT = 5;

    public static Board generateBoard(final EnumMap<TeamType, Integer> setups) {
        final Map<Point, Piece> locations = new HashMap<>();
        for (final Entry<TeamType, Integer> setup : setups.entrySet()) {
            final TeamType teamType = setup.getKey();
            final ElephantLocator locator = createFromChoice(setup.getValue(), teamType);
            locations.putAll(setupSoldierLocations(teamType));
            locations.putAll(setupDefaultLocations(teamType));
            locations.putAll(locator.setupHorse(teamType));
            locations.putAll(locator.setupElephant(teamType));
        }
        return new Board(locations);
    }

    private static ElephantLocator createFromChoice(final int choice, final TeamType teamType) {
        return switch (choice) {
            case 1 -> new OuterElephantLocator();
            case 2 -> new InnerElephantLocator();
            case 3 -> new LeftElephantLocator();
            case 4 -> new RightElephantLocator();
            default ->
                    throw new JanggiGameRuleWarningException(teamType.toString() + "가 등록되지 않은 배치를 선택했습니다: " + choice);
        };
    }

    private static Map<Point, Piece> setupDefaultLocations(final TeamType teamType) {
        final Map<Point, Piece> locations = new HashMap<>();
        locations.put(new Point(teamType.calculateRowForPiece(0), 0), new Chariot(teamType));
        locations.put(new Point(teamType.calculateRowForPiece(0), 8), new Chariot(teamType));
        locations.put(new Point(teamType.calculateRowForPiece(2), 1), new Cannon(teamType));
        locations.put(new Point(teamType.calculateRowForPiece(2), 7), new Cannon(teamType));
        locations.put(new Point(teamType.calculateRowForPiece(0), 3), new Guard(teamType));
        locations.put(new Point(teamType.calculateRowForPiece(0), 5), new Guard(teamType));
        locations.put(new Point(teamType.calculateRowForPiece(1), 4), new General(teamType));
        return locations;
    }

    private static Map<Point, Piece> setupSoldierLocations(final TeamType teamType) {
        return IntStream.range(0, MAX_SOLDIER_COUNT)
                .boxed()
                .collect(Collectors.toMap(
                        column -> new Point(teamType.calculateRowForPiece(3), column * 2),
                        column -> new Soldier(teamType)
                ));
    }
}
