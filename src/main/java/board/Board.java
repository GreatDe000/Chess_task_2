package board;

import move.Move;
import pieces.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс шахматной доски.
 * Хранит клетки, начальную расстановку фигур и методы проверки/выполнения ходов.
 */
public class Board {

    /** Размер доски (8x8). */
    public static final int SIZE = 8;

    /** Массив клеток доски. Индексация: [x][y]. */
    private final Cell[][] cells = new Cell[SIZE][SIZE];

    /**
     * Создаёт доску и выполняет стартовую расстановку фигур.
     */
    public Board() {
        for (int y = 0; y < SIZE; y++) {
            for (int x = 0; x < SIZE; x++) {
                cells[x][y] = new Cell(x, y);
            }
        }
        setupDefaultPosition();
    }

    /**
     * Возвращает клетку по координатам.
     *
     * @param x столбец (0..7)
     * @param y ряд (0..7)
     * @return клетка
     */
/**
 * Выполняет действие: getCell.
 * @param x параметр метода
 * @param y параметр метода
 * @return результат выполнения
 */
    public Cell getCell(int x, int y) {
        if (!isInside(x, y)) {
            throw new IllegalArgumentException("Координаты вне доски: " + x + "," + y);
        }
        return cells[x][y];
    }

    /**
     * Выполняет стартовую расстановку фигур.
     */
/**
 * Выполняет действие: setupDefaultPosition.
 */
    public final void setupDefaultPosition() {
        clear();

        // Чёрные фигуры
        getCell(0, 0).setPiece(new Rook(PieceColor.BLACK));
        getCell(1, 0).setPiece(new Knight(PieceColor.BLACK));
        getCell(2, 0).setPiece(new Bishop(PieceColor.BLACK));
        getCell(3, 0).setPiece(new Queen(PieceColor.BLACK));
        getCell(4, 0).setPiece(new King(PieceColor.BLACK));
        getCell(5, 0).setPiece(new Bishop(PieceColor.BLACK));
        getCell(6, 0).setPiece(new Knight(PieceColor.BLACK));
        getCell(7, 0).setPiece(new Rook(PieceColor.BLACK));
        for (int x = 0; x < SIZE; x++) {
            getCell(x, 1).setPiece(new Pawn(PieceColor.BLACK));
        }

        // Белые фигуры
        getCell(0, 7).setPiece(new Rook(PieceColor.WHITE));
        getCell(1, 7).setPiece(new Knight(PieceColor.WHITE));
        getCell(2, 7).setPiece(new Bishop(PieceColor.WHITE));
        getCell(3, 7).setPiece(new Queen(PieceColor.WHITE));
        getCell(4, 7).setPiece(new King(PieceColor.WHITE));
        getCell(5, 7).setPiece(new Bishop(PieceColor.WHITE));
        getCell(6, 7).setPiece(new Knight(PieceColor.WHITE));
        getCell(7, 7).setPiece(new Rook(PieceColor.WHITE));
        for (int x = 0; x < SIZE; x++) {
            getCell(x, 6).setPiece(new Pawn(PieceColor.WHITE));
        }
    }

    /**
     * Очищает доску (убирает все фигуры).
     */
/**
 * Выполняет действие: clear.
 */
    public void clear() {
        for (int y = 0; y < SIZE; y++) {
            for (int x = 0; x < SIZE; x++) {
                cells[x][y].setPiece(null);
            }
        }
    }

    /**
     * Проверяет, что координаты находятся внутри доски.
     *
     * @param x столбец
     * @param y ряд
     * @return true, если клетка существует
     */
/**
 * Выполняет действие: isInside.
 * @param x параметр метода
 * @param y параметр метода
 * @return результат выполнения
 */
    public boolean isInside(int x, int y) {
        return x >= 0 && x < SIZE && y >= 0 && y < SIZE;
    }

    /**
     * Проверяет, свободен ли путь между клетками (для ладьи/слона/ферзя).
     * Начальная и конечная клетки не проверяются.
     *
     * @param from начальная клетка
     * @param to конечная клетка
     * @return true, если между ними нет фигур
     */
/**
 * Выполняет действие: isPathClear.
 * @param from параметр метода
 * @param to параметр метода
 * @return результат выполнения
 */
    public boolean isPathClear(Cell from, Cell to) {
        int dx = Integer.compare(to.getX(), from.getX());
        int dy = Integer.compare(to.getY(), from.getY());

        int x = from.getX() + dx;
        int y = from.getY() + dy;

        while (x != to.getX() || y != to.getY()) {
            if (getCell(x, y).getPiece() != null) {
                return false;
            }
            x += dx;
            y += dy;
        }
        return true;
    }

    /**
     * Выполняет ход, если он корректен по правилам и не оставляет своего короля под шахом.
     *
     * @param from начальная клетка
     * @param to конечная клетка
     * @param moverColor цвет игрока, который делает ход
     * @return true, если ход выполнен
     */
/**
 * Выполняет действие: move.
 * @param from параметр метода
 * @param to параметр метода
 * @param moverColor параметр метода
 * @return результат выполнения
 */
    public boolean move(Cell from, Cell to, PieceColor moverColor) {
        if (from == null || to == null) return false;
        if (from == to) return false;

        Piece piece = from.getPiece();
        if (piece == null) return false;
        if (piece.getColor() != moverColor) return false;

        Piece target = to.getPiece();
        if (target != null && target.getColor() == moverColor) {
            return false;
        }

        if (!piece.isValidMove(from, to, this)) {
            return false;
        }

        // Симуляция: нельзя оставить своего короля под шахом
        Piece captured = to.getPiece();
        to.setPiece(piece);
        from.setPiece(null);
        boolean illegal = isKingInCheck(moverColor);
        // откат
        from.setPiece(piece);
        to.setPiece(captured);

        if (illegal) {
            return false;
        }

        // реальное выполнение
        to.setPiece(piece);
        from.setPiece(null);
        return true;
    }

    /**
     * Выполняет ход без указания цвета (используйте, если цвет уже проверен снаружи).
     *
     * @param from начальная клетка
     * @param to конечная клетка
     * @return true, если ход выполнен
     */
/**
 * Выполняет действие: move.
 * @param from параметр метода
 * @param to параметр метода
 * @return результат выполнения
 */
    public boolean move(Cell from, Cell to) {
        Piece piece = (from == null) ? null : from.getPiece();
        if (piece == null) return false;
        return move(from, to, piece.getColor());
    }

    /**
     * Проверяет, находится ли король заданного цвета под шахом.
     *
     * @param color цвет короля
     * @return true, если король атакован
     */
/**
 * Выполняет действие: isKingInCheck.
 * @param color параметр метода
 * @return результат выполнения
 */
    public boolean isKingInCheck(PieceColor color) {
        Cell kingCell = findKing(color);
        if (kingCell == null) return false;

        // может ли любая фигура соперника атаковать клетку короля
        for (int y = 0; y < SIZE; y++) {
            for (int x = 0; x < SIZE; x++) {
                Cell from = getCell(x, y);
                Piece p = from.getPiece();
                if (p != null && p.getColor() != color) {
                    if (p.isValidMove(from, kingCell, this)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Проверяет мат указанному цвету.
     * Мат = шах + нет ни одного легального хода.
     *
     * @param color цвет, которому проверяем мат
     * @return true, если мат
     */
/**
 * Выполняет действие: isCheckmate.
 * @param color параметр метода
 * @return результат выполнения
 */
    public boolean isCheckmate(PieceColor color) {
        return isKingInCheck(color) && getLegalMoves(color).isEmpty();
    }

    /**
     * Проверяет пат указанному цвету.
     * Пат = нет легальных ходов, но шаха нет.
     *
     * @param color цвет, которому проверяем пат
     * @return true, если пат
     */
/**
 * Выполняет действие: isStalemate.
 * @param color параметр метода
 * @return результат выполнения
 */
    public boolean isStalemate(PieceColor color) {
        return !isKingInCheck(color) && getLegalMoves(color).isEmpty();
    }

    /**
     * Возвращает все легальные ходы цвета.
     *
     * @param color цвет игрока
     * @return список ходов
     */
/**
 * Выполняет действие: getLegalMoves.
 * @param color параметр метода
 * @return результат выполнения
 */
    public List<Move> getLegalMoves(PieceColor color) {
        List<Move> moves = new ArrayList<>();
        for (int y = 0; y < SIZE; y++) {
            for (int x = 0; x < SIZE; x++) {
                Cell from = getCell(x, y);
                Piece piece = from.getPiece();
                if (piece == null || piece.getColor() != color) continue;

                for (int ty = 0; ty < SIZE; ty++) {
                    for (int tx = 0; tx < SIZE; tx++) {
                        Cell to = getCell(tx, ty);
                        if (from == to) continue;
                        if (to.getPiece() != null && to.getPiece().getColor() == color) continue;
                        if (!piece.isValidMove(from, to, this)) continue;

                        // симуляция на шах
                        Piece captured = to.getPiece();
                        to.setPiece(piece);
                        from.setPiece(null);
                        boolean illegal = isKingInCheck(color);
                        from.setPiece(piece);
                        to.setPiece(captured);

                        if (!illegal) {
                            moves.add(new Move(from.getX(), from.getY(), to.getX(), to.getY()));
                        }
                    }
                }
            }
        }
        return moves;
    }

/**
 * Выполняет действие: findKing.
 * @param color параметр метода
 * @return результат выполнения
 */
    private Cell findKing(PieceColor color) {
        for (int y = 0; y < SIZE; y++) {
            for (int x = 0; x < SIZE; x++) {
                Cell cell = getCell(x, y);
                Piece p = cell.getPiece();
                if (p != null && p.getColor() == color && p.getType() == PieceType.KING) {
                    return cell;
                }
            }
        }
        return null;
    }
}
