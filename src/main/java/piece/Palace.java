package piece;

import position.Position;
import route.Routes;

public class Palace extends Piece {

    public Palace(Position position) {
        super(position);
    }

    @Override
    public Routes routes() {
        return Routes.ofPalace();
    }
}
