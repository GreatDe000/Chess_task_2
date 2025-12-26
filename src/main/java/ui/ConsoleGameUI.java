package ui;

import board.Board;
import board.Cell;
import move.Move;
import pieces.Piece;
import pieces.PieceColor;
import pieces.PieceType;

/**
 * Консольный интерфейс для режима наблюдения.
 * Выводит доску и сообщения в терминал.
 */
public class ConsoleGameUI implements GameUI {

    /**
     * Выводит доску (8x8). Верхняя строка соответствует 8-ой горизонтали.
     */
    @Override
/**
 * Выполняет действие: render.
 * @param board параметр метода
 */
    public void render(Board board) {
        for (int y = Board.SIZE - 1; y >= 0; y--) {
            System.out.print((y + 1) + "  ");
            for (int x = 0; x < Board.SIZE; x++) {
                Cell cell = board.getCell(x, y);
                System.out.print(symbol(cell.getPiece()) + " ");
            }
            System.out.println();
        }
        System.out.print("\n   ");
        for (int x = 0; x < Board.SIZE; x++) {
            System.out.print((char) ('a' + x) + " ");
        }
        System.out.println("\n");
    }

    /**
     * В режиме наблюдателя консоль не запрашивает ход.
     */
    @Override
/**
 * Возвращает значение move.
 * @return результат выполнения
 */
    public Move getMove() {
        return null;
    }

    /**
     * Печатает сообщение в консоль.
     *
     * @param message текст сообщения
     */
    @Override
/**
 * Выполняет действие: showMessage.
 * @param message параметр метода
 */
    public void showMessage(String message) {
        System.out.println(message);
    }

/**
 * Выполняет действие: symbol.
 * @param piece параметр метода
 * @return результат выполнения
 */
    private String symbol(Piece piece) {
        if (piece == null) return ".";

        PieceType t = piece.getType();
        PieceColor c = piece.getColor();
        // Используем простые буквы, чтобы было видно в терминале.
        String s;
        switch (t) {
            case KING: s = "K"; break;
            case QUEEN: s = "Q"; break;
            case ROOK: s = "R"; break;
            case BISHOP: s = "B"; break;
            case KNIGHT: s = "N"; break;
            case PAWN: s = "P"; break;
            default: s = "?";
        }
        return c == PieceColor.WHITE ? s : s.toLowerCase();
    }
}
