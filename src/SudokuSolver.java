import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class SudokuSolver {

    public static void main(String[] args) {

        int[][] playBoard = new int[9][9];
        int[][] hintBoard = new int[9][9];

        initializeBoard(playBoard, hintBoard);
        printBoard(playBoard, hintBoard);
        inputHints(playBoard, hintBoard);
        findSolution(playBoard, hintBoard);
        if (playBoard[8][8] == 0)
            System.out.println("This Sudoku has no solution!");

    }

    public static void initializeBoard(int[][] playBoard, int[][] hintBoard) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                playBoard[i][j] = 0;
                hintBoard[i][j] = 0;
            }
        }
    }

    public static void printBoard(int[][] playBoard, int[][] hintBoard) {
        final String YELLOW_BOLD_BRIGHT = "\033[1;93m";
        final String RESET = "\u001B[0m";
        System.out.println();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (j == 3 || j == 6) {
                    System.out.print("    ");
                }
                if (playBoard[i][j] != 0 && hintBoard[i][j] == 0)
                    System.out.printf(" %-3d ", playBoard[i][j]);
                else if (hintBoard[i][j] != 0)
                    System.out.printf(" %s%-3d%s ", YELLOW_BOLD_BRIGHT, playBoard[i][j], RESET);
                else
                    System.out.printf("%d:%-3d", i, j);
            }
            if (i == 2 || i == 5) {
                System.out.println();
                System.out.println();
            }
            System.out.println();
            System.out.println();
        }
    }

    public static void inputHints(int[][] playBoard, int[][] hintBoard) {
        final String BLUE_BOLD = "\033[1;34m";
        final String RESET = "\u001B[0m";
        System.out.printf("\nPlease input your hints. Input %sf%s at any time to finish.\nFormat: [n-th row element]:[n-th column element] [number]\nExample: 6:3 7\n", BLUE_BOLD, RESET);
        boolean choiceMade = false;
        while (!choiceMade) {
            System.out.print("Input hint: ");
            Scanner keyboard = new Scanner(System.in);
            String input = keyboard.nextLine();
            if (input.charAt(0) == 'f')
                choiceMade = true;
            else {
                playBoard[(int) input.charAt(0) - 48][(int) input.charAt(2) - 48] = (int) input.charAt(4) - 48;
                hintBoard[(int) input.charAt(0) - 48][(int) input.charAt(2) - 48] = (int) input.charAt(4) - 48;
            }
        }
        System.out.println();
    }

    public static boolean checkForDoubles(int[][] playBoard) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                for (int k = j + 1; k < 9; k++) {
                    if (playBoard[i][j] != 0 && playBoard[i][j] == playBoard[i][k]) {
                        return true;
                    }
                }
            }
        }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                for (int k = j + 1; k < 9; k++) {
                    if (playBoard[j][i] != 0 && playBoard[j][i] == playBoard[k][i]) {
                        return true;
                    }
                }
            }
        }
        for (int i = 0; i < 9; i += 3) {
            for (int j = 0; j < 9; j += 3) {
                Set<Integer> set = new HashSet<>();
                for (int k = i; k < i + 3; k++) {
                    for (int l = j; l < j + 3; l++) {
                        int num = playBoard[k][l];
                        if (playBoard[k][l] != 0 && set.contains(num)) {
                            return true;
                        }
                        set.add(num);
                    }
                }
            }
        }
        return false;
    }

    public static boolean findSolution(int[][] playBoard, int[][] hintBoard) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (playBoard[i][j] == 0) {
                    for (int k = 1; k <= 9; k++) {
                        if (!checkForDoubles(playBoard)) {
                            playBoard[i][j] = k;
                            if (findSolution(playBoard, hintBoard)) {
                                return true;
                            } else {
                                playBoard[i][j] = 0;
                            }
                        }
                    }
                    return false;
                }
            }
        }
        printBoard(playBoard, hintBoard);
        return true;
    }


}







