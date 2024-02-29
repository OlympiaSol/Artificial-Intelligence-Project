class minMax
{
    public minMax next;
    public int emptyCELLS; //How many cells are free-empty?
    public int[] cols_rows = new int[4];
    // We have columns and rows. For every column there is a row
    //that a symbol X OR 0 will be placed
    public char[][] board = new char[3][4]; //The board game
}
