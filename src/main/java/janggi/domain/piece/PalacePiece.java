package janggi.domain.piece;

import java.util.function.Consumer;

public abstract class PalacePiece extends Piece {

    public PalacePiece(final String name, final Team team) {
        super(name, team);
    }

    @Override
    public abstract Consumer<Pieces> getMovableValidator(final Position beforePosition, final Position afterPosition);

    @Override
    public abstract Consumer<Pieces> getPalaceMovableValidator(final Position beforePosition,
                                                               final Position afterPosition);

    @Override
    public boolean isPalacePiece() {
        return true;
    }
}
