package janggi.view;

import janggi.camp.Camp;
import janggi.piece.PieceType;

public enum PieceSymbol {

    CANNON(PieceType.CANNON) {
        @Override
        public String getDisplayAttributes(Camp camp) {
            if (camp == Camp.CHU) {
                return colorChuAttribute("포");
            }
            return colorHanAttribute("포");
        }
    },
    CHARIOT(PieceType.CHARIOT) {
        @Override
        public String getDisplayAttributes(Camp camp) {
            if (camp == Camp.CHU) {
                return colorChuAttribute("차");
            }
            return colorHanAttribute("차");
        }
    },
    ELEPHANT(PieceType.ELEPHANT) {
        @Override
        public String getDisplayAttributes(Camp camp) {
            if (camp == Camp.CHU) {
                return colorChuAttribute("상");
            }
            return colorHanAttribute("상");
        }
    },
    GENERAL(PieceType.GENERAL) {
        @Override
        public String getDisplayAttributes(Camp camp) {
            if (camp == Camp.CHU) {
                return colorChuAttribute("왕");
            }
            return colorHanAttribute("왕");
        }
    },
    GUARD(PieceType.GUARD) {
        @Override
        public String getDisplayAttributes(Camp camp) {
            if (camp == Camp.CHU) {
                return colorChuAttribute("사");
            }
            return colorHanAttribute("사");
        }
    },
    HORSE(PieceType.HORSE) {
        @Override
        public String getDisplayAttributes(Camp camp) {
            if (camp == Camp.CHU) {
                return colorChuAttribute("마");
            }
            return colorHanAttribute("마");
        }
    },
    SOLDIER(PieceType.SOLDIER) {
        @Override
        public String getDisplayAttributes(Camp camp) {
            if (camp == Camp.CHU) {
                return colorChuAttribute("졸");
            }
            return colorHanAttribute("병");
        }
    },
    ;

    private static final String RED_COLOR_CODE = "\u001B[31m";
    private static final String GREEN_COLOR_CODE = "\u001B[32m";
    private static final String EXIT_CODE = "\u001B[0m";

    private final PieceType pieceType;

    PieceSymbol(PieceType pieceType) {
        this.pieceType = pieceType;
    }

    public abstract String getDisplayAttributes(Camp camp);

    public String colorChuAttribute(String value) {
        return GREEN_COLOR_CODE + value + EXIT_CODE;
    }

    public String colorHanAttribute(String value) {
        return RED_COLOR_CODE + value + EXIT_CODE;
    }

    public PieceType getPieceCategory() {
        return pieceType;
    }
}
