package janggi.domain.piece;

import janggi.domain.gung.Gung;
import janggi.domain.path.path_filter.*;
import janggi.domain.path.path_provider.CrossPathProvider;
import janggi.domain.path.path_provider.GungOneStepPathProvider;
import janggi.domain.path.path_provider.GungPathProvider;
import janggi.domain.path.path_provider.movement_path_provider.*;
import janggi.domain.position.Position;

import java.util.List;

public final class PieceFactory {

    public static Piece create(final PieceType pieceType, final Position position) {
        return switch (pieceType) {
            case 차 -> createCha(position);
            case 마 -> createMa(position);
            case 상 -> createSang(position);
            case 사 -> createSa(position);
            case 장 -> createJang(position);
            case 포 -> createPo(position);
            case 졸 -> createJol(position);
            case 병 -> createByeong(position);
        };
    }

    public static Piece createSang(final Position position) {
        return new Piece(
                PieceType.상,
                List.of(new StraightDiagonalDiagonalPathProvider()),
                List.of(new BlockPathFilter(), new LastPositionAllyPathFilter()),
                position
        );
    }

    public static Piece createMa(final Position position) {
        return new Piece(
                PieceType.마,
                List.of(new StraightDiagonalPathProvider()),
                List.of(new BlockPathFilter(), new LastPositionAllyPathFilter()),
                position
        );
    }

    public static Piece createJang(final Position position) {
        final Gung gung = new Gung();
        return new Piece(
                PieceType.장,
                List.of(new CrossOneStepPathProvider(), new GungOneStepPathProvider(gung)),
                List.of(new InGungPathFilter(gung), new BlockPathFilter(), new LastPositionAllyPathFilter()),
                position
        );
    }

    public static Piece createSa(final Position position) {
        final Gung gung = new Gung();
        return new Piece(
                PieceType.사,
                List.of(new CrossOneStepPathProvider(), new GungOneStepPathProvider(gung)),
                List.of(new InGungPathFilter(gung), new BlockPathFilter(), new LastPositionAllyPathFilter()),
                position
        );
    }

    public static Piece createCha(final Position position) {
        final Gung gung = new Gung();
        return new Piece(
                PieceType.차,
                List.of(new CrossPathProvider(), new GungPathProvider(gung)),
                List.of(new BlockPathFilter(), new LastPositionAllyPathFilter()),
                position
        );
    }

    public static Piece createPo(final Position position) {
        final Gung gung = new Gung();
        return new Piece(
                PieceType.포,
                List.of(new CrossPathProvider(), new GungPathProvider(gung)),
                List.of(new BlockSameTypePathFilter(), new JumpPathFilter(1), new LastPositionAllyPathFilter(), new LastPositionSameTypePathFilter()),
                position
        );
    }

    public static Piece createJol(final Position position) {
        final Gung gung = new Gung();
        return new Piece(
                PieceType.졸,
                List.of(new UpLeftRightPathProvider(), new GungOneStepPathProvider(gung)),
                List.of(new LastPositionAllyPathFilter()),
                position
        );
    }

    public static Piece createByeong(final Position position) {
        final Gung gung = new Gung();
        return new Piece(
                PieceType.병,
                List.of(new DownLeftRightPathProvider(), new GungOneStepPathProvider(gung)),
                List.of(new LastPositionAllyPathFilter()),
                position
        );
    }
}
