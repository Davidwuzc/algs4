//import edu.princeton.cs.algs4.StdRandom;
//import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
public class Percolation {
    private WeightedQuickUnionUF WQuf;
    private WeightedQuickUnionUF WQuf_backwash;
    private int N;
    private boolean[] status;
    public Percolation(int n) {
        if(N <= 0) 
            throw new IllegalArgumentException("N is <= 0");
        N=n;
        int i;
        WQuf = new WeightedQuickUnionUF(N*N+1);
        WQuf_backwash = new WeightedQuickUnionUF(N*N+2);
        status=new boolean[N*N+1];
        status[0]=true;
        for(i=1;i<(N*N+1);i++) 
        {
            status[i]=false;
        }    
    }
    private int xytrans(int i, int j){
        return ((i-1)*N+j);
    }
    public void open(int row, int col) {
        if(row < 1 || row > this.N || col < 1 || col > this.N)
        {
            throw new IllegalArgumentException("row or col is out of range");
        }
        if(status[xytrans(row, col)] == false)
        {
            status[xytrans(row, col)] = true;
            if(row==1)
            {
                WQuf.union(0, xytrans(row,col));
                WQuf_backwash.union(0, xytrans(row,col));
            }
            if(row == N)
            {
                WQuf_backwash.union(N*N + 1, xytrans(row,col));
            }
            if(row > 1 && status[xytrans((row-1), col)])
            {
                WQuf.union(xytrans(row,col), xytrans((row-1), col));
                WQuf_backwash.union(xytrans(row,col), xytrans((row-1), col));
            }
            if(row < N && status[xytrans((row+1), col)])
            {
                WQuf.union(xytrans(row,col), xytrans((row+1), col));
                WQuf_backwash.union(xytrans(row,col), xytrans((row+1), col));
            }
            if( col > 1 && status[xytrans(row,col-1)])
            {
                WQuf.union(xytrans(row,col), xytrans(row, (col-1)));
                WQuf_backwash.union(xytrans(row,col), xytrans(row, (col-1)));                 
            }
            if( col < N && status[xytrans(row,col+1)])
            {
                WQuf.union(xytrans(row,col), xytrans(row, (col+1)));
                WQuf_backwash.union(xytrans(row,col), xytrans(row, (col+1)));                 
            }
            
        }           
            
    }
    
    
    public boolean isOpen(int row, int col) 
    {
        if(row < 1 || row > this.N || col < 1 || col > this.N)
        {
            throw new IllegalArgumentException("row or col is out of range");
        }
        return status[xytrans(row, col)];
    }
    
    public boolean isFull(int row, int col) 
    {
        if(row < 1 || row > this.N || col < 1 || col > this.N)
        {
            throw new IllegalArgumentException("row or col is out of range");
        }
        return WQuf.connected(0,xytrans(row, col));
    }
    
    public boolean percolates()
    {
        return WQuf_backwash.connected(0, N*N+1);
    }
    
}
