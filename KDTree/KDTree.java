/** Created on 6/28/20 by Alan Yao.
 * Working 2D k-d tree, though poorly optimized (takes up to 3 times as long as naive check)
 */

import java.io.*;
import java.util.*;

public class KDTree implements PointSet
{
    Node root;

    private class AABB /** Axis-Aligned Bounding Box */
    {
        Point bottomLeft;
        Point topRight;
        
        private AABB() {
            bottomLeft = new Point(-Double.MAX_VALUE, -Double.MAX_VALUE);
            topRight = new Point(Double.MAX_VALUE, Double.MAX_VALUE);
        }
        private AABB(Point bottomLeft, Point topRight) {
            this.bottomLeft = bottomLeft;
            this.topRight = topRight;
        }
    }
    private class Node
    {
        Point coord;
        /** left & right refer to both halves of a dimension, not just left/right */
        Node left;
        Node right;
        AABB boundBox;
        boolean vertical; /** whether node splits vertically (true) / horizontally (false) */

        private Node(Point p) {
            coord = p;
            boundBox = new AABB();
            vertical = true;
        }
        private Node(Point p, AABB bound, boolean vertical) {
            coord = p;
            boundBox = bound;
            this.vertical = vertical;
        }
    }

    public KDTree(List<Point> points) {
        root = new Node(points.get(0));
        for (int i = 1; i < points.size(); i++)
            insert(points.get(i));
    }

    /** Working */
    public void insert(Point p) {
        root = insert(p, root, new AABB(), true);
    }
    /** Working */
    private Node insert(Point p, Node current, AABB bound, boolean orientation) {
        if (current == null)
            return new Node(p, bound, orientation);

        current.boundBox = bound;

        int cmp = 0;
        if (current.vertical)
            cmp = compare(p.getX(), current.coord.getX());
        else /** horizontal line */
            cmp = compare(p.getY(), current.coord.getY());
        
        if (cmp == -1) { /** left branch */
            Point nTR;
            if (current.vertical) /** new node is left, horizontal */
                nTR = new Point(current.coord.getX(), bound.topRight.getY());
            else /** new node is down, vertical */
                nTR = new Point(bound.topRight.getX(), current.coord.getY());
            AABB newBound = new AABB(bound.bottomLeft, nTR);
            current.left = insert(p, current.left, newBound, !orientation);
        }
        else { /** If the coordinates of p and current tie, p is still moved right */
            Point nBL;
            if (current.vertical) /** new node is left and horizontal */
                nBL = new Point(current.coord.getX(), bound.bottomLeft.getY());
            else
                nBL = new Point(bound.bottomLeft.getX(), current.coord.getY());
            AABB newBound = new AABB(nBL, bound.topRight);
            current.right = insert(p, current.right, newBound, !orientation);
        }

        return current; 
    }
    /** Working */
    private int compare(double c1, double c2) {
        if (c1 < c2)
            return -1;
        else if (c2 > c1)
            return 1;
        return 0;
    }

    /** O(log N) on average */
    public Point nearest(double x, double y) {
        Point target = new Point(x, y);
        Node best = nearest(target, root, root);
        return best.coord;
    }
    private Node nearest(Point target, Node current, Node bestNode) {
        if (current == null)
            return bestNode;
        
        System.out.println(current.coord.getX() + ", " + current.coord.getY());
        if (current.coord.distance(target) < bestNode.coord.distance(target)) {
            bestNode = current;
            //System.out.println(current.coord.distance(target));
        }

        Point delta = new Point(target.getX() - current.coord.getX(),
                                target.getY() - current.coord.getY()); 

        if (current.vertical) {
            if (delta.getX() < 0) { /** target point lies to the left */
                bestNode = nearest(target, current.left, bestNode);
                if (smallestDelta(current, target) < bestNode.coord.distance(target))
                    bestNode = nearest(target, current.right, bestNode);
            }
            else { /** target point lies to the right */
                bestNode = nearest(target, current.right, bestNode);
                if (smallestDelta(current, target) < bestNode.coord.distance(target))
                    bestNode = nearest(target, current.left, bestNode);
            }
        }
        else {
            if (delta.getY() < 0) { /** target lies below */
                bestNode = nearest(target, current.left, bestNode);
                if (smallestDelta(current, target) < bestNode.coord.distance(target))
                    bestNode = nearest(target, current.right, bestNode);
            }
            else { /** target lies above */
                bestNode = nearest(target, current.right, bestNode);
                if (smallestDelta(current, target) < bestNode.coord.distance(target))
                    bestNode = nearest(target, current.left, bestNode);
            }
        }

        return bestNode;
    }
    /** Working */
    private double smallestDelta(Node p, Point target) {
        if (p == null)
            return Double.MAX_VALUE; 
        if (p.vertical) {
            Point endpoint1 = new Point(p.coord.getX(), p.boundBox.bottomLeft.getY());
            Point endpoint2 = new Point(p.coord.getX(), p.boundBox.topRight.getY());

            if (target.getY() <= endpoint1.getY()) {
                return target.distance(endpoint1);
            }
            else if (target.getY() >= endpoint2.getY()) {
                return target.distance(endpoint2);
            }
            else { /** between the two endpoints; closest distance is perpendicular */
                Point x = new Point(p.coord.getX(), target.getY());
                return target.distance(x);
            }
        }
        else {
            Point endpoint1 = new Point(p.boundBox.bottomLeft.getX(), p.coord.getY());
            Point endpoint2 = new Point(p.boundBox.topRight.getX(), p.coord.getY());

            if (target.getX() <= endpoint1.getX()) {
                return target.distance(endpoint1);
            }
            else if (target.getX() >= endpoint2.getX()) {
                return target.distance(endpoint2);
            }
            else {
                Point x = new Point(target.getX(), p.coord.getY());
                return target.distance(x);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("DataPoints.in"));

        StringTokenizer st = new StringTokenizer(in.readLine());
        int N = Integer.parseInt(st.nextToken());
        ArrayList<Point> points = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(in.readLine());
            double x = Double.parseDouble(st.nextToken());
            double y = Double.parseDouble(st.nextToken());
            points.add(new Point(x, y));
        }
        KDTree tree = new KDTree(points);
        Point ret = tree.nearest(0.81, 0.30);
        System.out.println();
        System.out.print(ret.getX() + ", ");
        System.out.println(ret.getY());

        in.close();
    }
}