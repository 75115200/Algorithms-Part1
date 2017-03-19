import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private static final int OPENDED = 1;
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF visualEnd;

    private int[] opened;

    private int n;

    private int first = -1;
    private int end = -1;
    private int openCount = 0;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n搴旇澶т簬0:" + n);
        }
        this.n = n;
        this.opened = new int[n * n + n];
        this.uf = new WeightedQuickUnionUF(n * n + n);
        this.visualEnd = new WeightedQuickUnionUF(n * n + n);
    }

    /**
     * 妫�鏌ョ綉绔欐槸鍚﹀紑鏀�
     * 
     * @param x
     * @param y
     * @return boolean
     */
    public boolean isOpen(int x, int y) {
        checkIndex(x, y);
        return opened[xyTo1D(x - 1, y - 1)] == OPENDED;
    }

    /**
     * 寮�鏀剧綉绔�
     * 
     * @param i
     * @param j
     */
    public void open(int i, int j) {
        checkIndex(i, j);
        i = i - 1;
        j = j - 1;
        int index = xyTo1D(i, j);
        if (opened[index] != OPENDED) {
            opened[index] = OPENDED;
            openCount++;
        }
        if (i == 0) {
            if (first == -1) {
                first = index;
            }
            uf.union(first, index);
            visualEnd.union(first, index);
        }
        if (i == n - 1) {
            if (end == -1) {
                end = xyTo1D(i, j);
            } else {
                visualEnd.union(end, xyTo1D(i, j));
            }
        }
        unionArround(i, j);
    }

    /**
     * 妫�鏌ユ槸鍚﹀彲浠ヨ繛閫�
     * 
     * @param row
     * @param col
     * @return
     */
    public boolean isFull(int row, int col) {
        checkIndex(row, col);
        if (first == -1) {
            return false;
        }
        return uf.connected(first, xyTo1D(row - 1, col - 1));
    }

    /**
     * 妫�鏌ユ槸鍚︽笚閫�
     * 
     * @return
     */
    public boolean percolates() {
        return end == -1 || first == -1 ? false : visualEnd.connected(first, end);
    }

    public int numberOfOpenSites() {
        return openCount;
    }

    private void checkIndex(int i, int j) {
        if (i <= 0 || j <= 0 || i > n || j > n) {
            throw new IndexOutOfBoundsException("the arg should > 0 :" + i + "," + j);
        }
    }

    private int xyTo1D(int x, int y) {
        return x * n + y;
    }

    private void unionArround(int x, int y) {
        int lastIndex = n - 1;
        if (x != 0 && opened[xyTo1D(x - 1, y)] == OPENDED) {
            uf.union(xyTo1D(x - 1, y), xyTo1D(x, y));
            visualEnd.union(xyTo1D(x - 1, y), xyTo1D(x, y));
        }
        if (x != lastIndex && opened[xyTo1D(x + 1, y)] == OPENDED) {
            uf.union(xyTo1D(x + 1, y), xyTo1D(x, y));
            visualEnd.union(xyTo1D(x + 1, y), xyTo1D(x, y));
        }
        if (y != 0 && opened[xyTo1D(x, y - 1)] == OPENDED) {
            uf.union(xyTo1D(x, y - 1), xyTo1D(x, y));
            visualEnd.union(xyTo1D(x, y - 1), xyTo1D(x, y));
        }
        if (y != lastIndex && opened[xyTo1D(x, y + 1)] == OPENDED) {
            uf.union(xyTo1D(x, y + 1), xyTo1D(x, y));
            visualEnd.union(xyTo1D(x, y + 1), xyTo1D(x, y));
        }
    }

    public static void main(String[] args) {
        Percolation percolation = new Percolation(3);
        percolation.open(1, 3);// 2
        percolation.open(2, 3);// 5
        percolation.open(3, 3);// 6+2=8
        percolation.open(3, 1);// 6
        System.out.println(percolation.isFull(3, 1));
    }
}
