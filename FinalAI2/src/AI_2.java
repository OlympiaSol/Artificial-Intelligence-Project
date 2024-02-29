import java.util.Scanner;

//Aikaterini Tsitsimikli 4821
//Soldatou Christina-Olympia 4001
//Pantazis Dimosthenis 4136

public class AI_2 {

    public static final int M = 3; //Number of rows
    public static final int N = 4; //Number of columns
    public static final int K = 3;
    //K = Number of 3 X OR 0 in a row(vertically, diagonally or //horizontally)
    public static final int CONTINUE_GAME = 100;
    //CONTINUE_GAME  = There is not a series of 3 X or 3 O (aka K)
    //yet!
    public static final int END_GAME = 50;
    //END_GAME = If we create a series of 3 X or 3 O (aka K)then
    //we have a winner!


    public static int testTheGame(char letter, char[][] board, int[] col_rows) {
        int i;
        int j;
        int z;
        int flag;

        //Check every column
        for (j = 0; j < N; j++) {
            for (i = M - 1; i > col_rows[j]; i--) {
                if (i >= K - 1) {
                    flag = 0;
                    for (z = 0; z < K; z++) {
                        if (board[i - z][j] != letter) {
                            flag = 1;
                            break;
                        }
                    }
                    if (flag == 0) {
                        return (END_GAME);
                    }
                }
            }
        }
        //Check every row
        for (j = 0; j < N; j++) {
            if (j + K <= N) {
                for (i = M - 1; i > col_rows[j]; i--) {
                    flag = 0;
                    for (z = 0; z < K; z++) {
                        if (board[i][j + z] != letter) {
                            flag = 1;
                            break;
                        }
                    }
                    if (flag == 0) {
                        return (END_GAME);
                    }
                }
            }
        }
        //check first diagonal
        for (j = 0; j < N; j++) {
            for (i = M - 1; i > col_rows[j]; i--) {
                if (i + K <= M && j + K <= N) {
                    flag = 0;
                    for (z = 0; z < K; z++) {
                        if (board[i + z][j + z] != letter) {
                            flag = 1;
                            break;
                        }
                    }
                    if (flag == 0) {
                        return (END_GAME);
                    }
                }
            }
        }

        //check second diagonal
        for (j = 0; j < N; j++) {
            for (i = M - 1; i > col_rows[j]; i--) {
                if (i >= K - 1 && j + K <= N) {
                    flag = 0;
                    for (z = 0; z < K; z++) {
                        if (board[i - z][j + z] != letter) {
                            flag = 1;
                            break;
                        }
                    }
                    if (flag == 0) {
                        return (END_GAME);
                    }
                }
            }
        }

        return (CONTINUE_GAME);
    }
    //Update the Board
    public static void copyTheBoard(int[] A, int[] B) {
        int i;
        for (i = 0; i < N; i++) {
            B[i] = A[i];
        }

    }

    public static void copyTheBoard2(char[][] X, char[][] Y) {
        int j;
        int i;
        for (j = 0; j < M; j++) {
            for (i = 0; i < N; i++) {
                Y[j][i] = X[j][i];
            }
        }

    }
    //Display the Board
    public static void printBoard(char[][] Z) {
        int j;
        int i;

        final String numDisplay = "1 2 3 4 ";
        final String hr = "-------";
        System.out.println(numDisplay);
        System.out.println(hr);


        for (j = 0; j < M; j++) {
            for (i = 0; i < N; i++) {
                System.out.printf("%c ", Z[j][i]);
            }
            System.out.println();
        }
        System.out.println();
    }

    public static final int MAX = 10;
    public static final int MIN = -10;
    public static final int TIE = 0;

    public static int emptyCELLS(minMax tree) {
        if (tree.emptyCELLS == 0) {
            return (TIE);
        }
        return 0;
    }

    public static int minMaxTree(int turn, minMax tree) { //DFS algorithm for the creation of the tree

        int pos = 0; //position
        int i;
        int nextOne;
        minMax[] pointers = new minMax[4];


        int[] test = new int[N];
        minMax[] n = new minMax[N];
        int bestMove;
        minMax best;
        int j;
        char symbol;

        if (turn == MAX && testTheGame('O', tree.board, tree.cols_rows) == END_GAME) { //It's Max's turn BUT Min created 3 "O" in a row already
            return (MIN); //Winner --> Min
        } else if (turn == MIN && testTheGame('X', tree.board, tree.cols_rows) == END_GAME) {//It's Min's turn BUT Max created 3 "X" in a row already
            return (MAX);//Winner --> Max
        } else {
            emptyCELLS(tree); //It's a TIE.. No one won or lost.
        }
        if (turn == MAX) {
            nextOne = MIN;
            symbol = 'X';
        } else {
            nextOne = MAX;
            symbol = 'O';
        }
        for (i = 0; i < N; i++) { //Placement of symbols (X OR O ) to create pointers
            if (tree.cols_rows[i] >= 0) {
                pointers[pos] = new minMax();
                pointers[pos].emptyCELLS = tree.emptyCELLS - 1; // Empty cells of the children
                copyTheBoard2(tree.board, pointers[pos].board);
                pointers[pos].board[tree.cols_rows[i]][i] = symbol; //Placement of X or O
                copyTheBoard(tree.cols_rows, pointers[pos].cols_rows);
                pointers[pos].cols_rows[i] = tree.cols_rows[i] - 1;
                test[pos] = minMaxTree(nextOne, pointers[pos]); //We call mixMaxTree with DFS to create their own children
                n[pos] = pointers[pos];
                pos++;
            }
        }

        if (turn == MAX) {//If we are in level MAX we save the maximum value
            bestMove = test[0];
            best = n[0];
            for (j = 0; j < pos; j++) {
                if (test[j] > bestMove) {
                    bestMove = test[j];
                    best = n[j];
                }
            }
        } else {
            bestMove = test[0];
            best = n[0];
            for (j = 0; j < pos; j++) {
                if (test[j] < bestMove) {
                    bestMove = test[j];
                    best = n[j];
                }
            }
        }
        tree.next = best;
        return bestMove;// Returns the best value
    }

    public static void FullCol() { //Method that checks if all the a column is full

        char[][] board = new char[3][4];
        int filled = 0;
        int i;
        int j = 0;

        for (i = 0; i < N; i++) {
            if (board[i][j] != ' ')
                filled++;
            if (filled == N) {
                System.out.println("Wrong. The column is full!!!");
            }
        }

        for (j = 0; j < M; j++) {
            if (board[i][j] != ' ')
                filled++;
            if (filled == M) {
                System.out.println("Wrong. The column is full!!!");
            }
        }

    }

    public static void check() {

        int j;
        int i;
        int column = 0;
        int turn = MAX;
        minMax state = new minMax();
        char[][] board = new char[3][4];


        Scanner input = new Scanner(System.in);
        state.emptyCELLS = M * N; //When the game begins we want all the cells to be empty
        for (j = 0; j < M; j++) {
            for (i = 0; i < N; i++) {
                state.board[j][i] = '-';//'-' ---> empty cell
            }
        }
        for (i = 0; i < N; i++) {
            state.cols_rows[i] = M - 1;
        }
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("GAME BEGINS!");
        System.out.println();
        printBoard(state.board);

        while (true) {

            if (turn == MIN && testTheGame('X', state.board, state.cols_rows) == END_GAME) {
                System.out.println("Winner is the computer");
                System.out.println("Game Over!");
                System.out.println();
                System.out.println();
                break;
            } else if (turn == MAX && testTheGame('O', state.board, state.cols_rows) == END_GAME) {
                System.out.println("Winner is the player");
                System.out.println("Game Over!");
                System.out.println();
                System.out.println();
                break;
            } else if (state.emptyCELLS == 0) {
                System.out.println("It's a tie");
                System.out.println("Game Over!");
                System.out.println();
                System.out.println();
                break;
            }


            if (turn == MAX) {// Max plays
                System.out.println("It's the computer's turn.");
                System.out.println();
                minMaxTree(MAX, state); //Update next
                state = state.next;// Makes the optimal move
                printBoard(state.board);
                turn = MIN;
                //boolean b = FullCol();
            }
            else { //Min is playing


                System.out.println("It's the player's turn.");

                do {    // We don't want the programm to stop everytime someone makes a wrong choice. So we make him choose again!
                    System.out.println("Please choose a column 1 to 4.");//Choose ONLY from 1 to 4
                    column = input.nextInt();

                    if ((column < 1) || (column > 4))
                        System.out.println("Wrong choice!!! ");

                } while ((column < 1) || (column > 4));
                System.out.println();

                input.nextLine();
                //FullCol();
                state.board[state.cols_rows[column - 1]][column - 1] = 'O';
                //FullCol();
                state.cols_rows[column - 1] = state.cols_rows[column - 1] - 1;
                state.emptyCELLS = state.emptyCELLS - 1;
                printBoard(state.board);
                turn = MAX;
            }
        }
    }

    public static void main(String[] args) {
        boolean stop = false; // We created a loop to start the program again if wanted.
        while (!stop) {

            check();

            Scanner input = new Scanner(System.in);
            System.out.println("Would you like to continue? (yes or no)");
            String s = input.nextLine();
            if (s.equals("no")) {
                stop = true;

            }

        }
    }
}