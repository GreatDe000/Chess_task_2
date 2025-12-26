package pieces;

import board.Board;
import board.Cell;

/**
 * Класс пешки.
 * Реализует правила движения пешки в шахматах.
 */
public class Pawn extends Piece {

    /**
     * Конструктор пешки.
     * @param color Цвет пешки (белый или черный)
     */
    public Pawn(PieceColor color) {
        super(color, PieceType.PAWN);
    }

    /**
     * Проверка корректности хода пешки.
     * @param from Клетка, с которой движется пешка
     * @param to Клетка, на которую пешка хочет сходить
     * @param board Шахматная доска
     * @return true, если ход разрешен, false — если запрещен
     */
    @Override
/**
 * Выполняет действие: isValidMove.
 * @param from параметр метода
 * @param to параметр метода
 * @param board параметр метода
 * @return результат выполнения
 */
    public boolean isValidMove(Cell from, Cell to, Board board) {
        int dir = (this.color == PieceColor.WHITE) ? -1 : 1; // белые идут вверх, черные вниз
        int startRow = (this.color == PieceColor.WHITE) ? 6 : 1; // начальная строка для двойного хода

        int dx = to.getX() - from.getX();
        int dy = to.getY() - from.getY();

        // Ход вперёд на 1 клетку, если клетка пуста
        if (dx == 0 && dy == dir && to.getPiece() == null) {
            return true;
        }

        // Первый ход: можно на 2 клетки вперед, если обе клетки пусты
        if (dx == 0 && dy == 2 * dir && from.getY() == startRow
                && to.getPiece() == null
                && board.getCell(from.getX(), from.getY() + dir).getPiece() == null) {
            return true;
        }

        // Съедание по диагонали (1 клетка по диагонали, если стоит фигура противника)
        if (Math.abs(dx) == 1 && dy == dir && to.getPiece() != null
                && to.getPiece().getColor() != this.color) {
            return true;
        }

        // Все остальные ходы запрещены
        return false;
    }
}
