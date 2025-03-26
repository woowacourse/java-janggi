package janggi.domain.piece;

import janggi.domain.Country;
import janggi.domain.gung.Gung;
import janggi.domain.path.path_filter.*;
import janggi.domain.path.path_provider.CrossLinePathProvider;
import janggi.domain.path.path_provider.GungOneStepPathProvider;
import janggi.domain.path.path_provider.GungPathProvider;
import janggi.domain.path.path_provider.movement_path_provider.*;
import janggi.domain.position.Position;

import java.util.List;

public class PieceFactory {

    public static Piece create(final PieceType pieceType, final Country country, final Position position) {
        return switch (pieceType) {
            case 차 -> createCha(country, position);
            case 마 -> createMa(position);
            case 상 -> createSang(position);
            case 사 -> createSa(country, position);
            case 장 -> createJang(country, position);
            case 포 -> createPo(country, position);
            case 졸 -> createJol(position);
            case 병 -> createByeong(position);
        };
    }

    public static Piece createSang(final Position position) {
        return new Piece(
                PieceType.상,
                List.of(new StraightDiagonalDiagonalPathProvider()),
                List.of(new NonMiddleBlockPathFilter(), new NonStopAtAllyPathFilter()),
                position
        );
    }

    public static Piece createMa(final Position position) {
        return new Piece(
                PieceType.마,
                List.of(new StraightDiagonalPathProvider()),
                List.of(new NonMiddleBlockPathFilter(), new NonStopAtAllyPathFilter()),
                position
        );
    }

    public static Piece createJang(final Country country, final Position position) {
        final Gung gung = Gung.of(country);
        return new Piece(
                PieceType.장,
                List.of(new CrossPathProvider(), new GungOneStepPathProvider(gung)),
                List.of(new InGungPathFilter(gung), new NonMiddleBlockPathFilter(), new NonStopAtAllyPathFilter()),
                position
        );
    }

    public static Piece createSa(final Country country, final Position position) {
        final Gung gung = Gung.of(country);
        return new Piece(
                PieceType.사,
                List.of(new CrossPathProvider(), new GungOneStepPathProvider(gung)),
                List.of(new InGungPathFilter(gung), new NonMiddleBlockPathFilter(), new NonStopAtAllyPathFilter()),
                position
        );
    }

    public static Piece createCha(final Country country, final Position position) {
        final Gung gung = Gung.of(country);
        return new Piece(
                PieceType.차,
                List.of(new CrossLinePathProvider(), new GungPathProvider(gung)),
                List.of(new NonMiddleBlockPathFilter(), new NonStopAtAllyPathFilter()),
                position
        );
    }

    public static Piece createPo(final Country country, final Position position) {
        final Gung gung = Gung.of(country);
        return new Piece(
                PieceType.포,
                List.of(new CrossLinePathProvider(), new GungPathProvider(gung)),
                List.of(new BlockSameTypePathFilter(), new JumpPathFilter(1), new NonStopAtAllyPathFilter(), new NonStopAtSameTypePathFilter()),
                position
        );
    }

    public static Piece createJol(final Position position) {
        final Gung gung = Gung.of(Country.HAN);
        return new Piece(
                PieceType.졸,
                List.of(new UpLeftRightPathProvider(), new GungOneStepPathProvider(gung)),
                List.of(new NonStopAtAllyPathFilter()),
                position
        );
    }

    public static Piece createByeong(final Position position) {
        final Gung gung = Gung.of(Country.CHO);
        return new Piece(
                PieceType.병,
                List.of(new DownLeftRightPathProvider(), new GungOneStepPathProvider(gung)),
                List.of(new NonStopAtAllyPathFilter()),
                position
        );
    }
}
