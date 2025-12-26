package ui;

import pieces.Piece;
import pieces.PieceColor;
import pieces.PieceType;

/**
 * Утилитный класс для получения Unicode-символа шахматной фигуры.
 */
public final class UnicodePieceSymbols {

    private UnicodePieceSymbols() {
    }

    /**
     * Возвращает Unicode-символ фигуры.
     *
     * @param piece фигура
     * @return символ ("♙", "♟" и т. п.), либо пустая строка
     */
/**
 * Выполняет действие: getSymbol.
 * @param piece параметр метода
 * @return результат выполнения
 */
    public static String getSymbol(Piece piece) {
        if (piece == null) return "";

        PieceType type = piece.getType();
        PieceColor color = piece.getColor();

        switch (type) {
            case PAWN:
                return color == PieceColor.WHITE ? "♙" : "♟";
            case ROOK:
                return color == PieceColor.WHITE ? "♖" : "♜";
            case KNIGHT:
                return color == PieceColor.WHITE ? "♘" : "♞";
            case BISHOP:
                return color == PieceColor.WHITE ? "♗" : "♝";
            case QUEEN:
                return color == PieceColor.WHITE ? "♕" : "♛";
            case KING:
                return color == PieceColor.WHITE ? "♔" : "♚";
            default:
                return "";
        }
    }
}
