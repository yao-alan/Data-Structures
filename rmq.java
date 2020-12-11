public class rmq
{
    static class SparseTableRMQ
    {
        // table[i][j] holds min[i, i + 2^j - 1]
        int[][] table;
        public SparseTableRMQ(int[] arr) {
            int size = Integer.numberOfTrailingZeros(Integer.highestOneBit(arr.length));
            table = new int[arr.length][size+1];
            for (int i = 0; i < arr.length; ++i)
                table[i][0] = arr[i];
            for (int j = 1; j <= size; ++j)
                for (int i = 0, w = (1 << j); i + w <= arr.length; ++i)
                    table[i][j] = Math.min(table[i][j-1], table[i + (w >> 1)][j-1]);
        }
        public int query(int lo, int hi) {
            int j = Integer.numberOfTrailingZeros(Integer.highestOneBit(hi - lo + 1));
            return Math.min(table[lo][j], table[hi - (1 << j) + 1][j]);
        }
    }
    public static void main(String[] args) {
        int[] arr = {1, 3, 4, 8, 6, 1, 4, 10, 9};
        SparseTableRMQ st = new SparseTableRMQ(arr);
        System.out.println(st.query(7, 8));
    }
}