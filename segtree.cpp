#include <iostream>
#include <cmath>
#include <string>

// helper function
int fastPow2(int n) {
    --n;
    n |= n >> 1;
    n |= n >> 2;
    n |= n >> 4;
    n |= n >> 8;
    n |= n >> 16;
    return n + 1;
}

// note type in constructor when building --> rmq, sum, etc.
class Segtree
{
    public:
        Segtree(int arr[], int n, std::string type);
        ~Segtree();
        int update(int idx, int val);
        int query(int lo, int hi);
    private:
        int *tree;
        int size; // length of range, not size of tree array
        std::string usage; // type of segtree: rmq, sum, etc.
};

Segtree::Segtree(int arr[], int n, std::string type) {
    usage = type;
    size = fastPow2(n);
    tree = new int[size << 1];
    // initializing leaf nodes
    int i;
    for (i = 0; i < n; ++i)
        tree[i + size] = arr[i];
    // filling in higher nodes
    if (usage == "sum") {
        memset(tree + (size+n), 0, sizeof(int) * (size-n));
        for (i = size - 1; i >= 1; --i)
            tree[i] = tree[i << 1] + tree[(i << 1) + 1];
    } else if (usage == "rmq") {
        memset(tree + (size+n), numeric_limits<int>::max(), sizeof(int) * (size-n));
        for (i = size - 1; i >= 1; --i)
            tree[i] = std::min(tree[i << 1], tree[(i << 1) + 1]);
    }
}

// returns new value of root node
int Segtree::update(int idx, int val) {
    idx += size;
    tree[idx] = val;
    if (usage == "sum") {
        for (int k = idx >> 1; k >= 1; k >>= 1)
            tree[k] = tree[k << 1] + tree[(k << 1) + 1];
    } else if (usage == "rmq") {
        for (int k = idx >> 1; k >= 1; k >>= 1)
            tree[k] = std::min(tree[k << 1], tree[(k << 1) + 1]);
    }
    return tree[1];
}

// find either sum/min within range [lo, hi] inclusive
int Segtree::query(int lo, int hi) {
    lo += size; hi += size;
    int ans;
    if (usage == "sum") {
        int sum = 0;
        while (lo <= hi) {
            if (lo % 2 == 1) 
                sum += tree[lo++];
            if (hi % 2 == 0) 
                sum += tree[hi--];
            lo >>= 1; hi >>= 1;
        }
        ans = sum;
    } else if (usage == "rmq") {
        int min = tree[lo];
        while (lo <= hi) {
            if (lo % 2 == 1) 
                min = std::min(min, tree[lo++]);
            if (hi % 2 == 0) 
                min = std::min(min, tree[hi--]);
            lo >>= 1; hi >>= 1;
        }
        ans = min;
    }
    return ans;
}

Segtree::~Segtree() {
    delete[] tree;
}

int main()
{
    int arr[] = {5, 8, 6, 3, 2, 7};
    Segtree sumTree(arr, 6, "sum");
    std::cout << sumTree.query(1, 5) << std::endl;
    Segtree minTree(arr, 6, "rmq");
    std::cout << minTree.query(1, 3) << std::endl;
}
