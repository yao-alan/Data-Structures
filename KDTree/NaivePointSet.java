import java.io.*;
import java.util.*;

public class NaivePointSet implements PointSet
{
    List<Point> points;

    public NaivePointSet(List<Point> points) {
        this.points = points;
    }
    /** Naive O(N) variant of nearest */
    public Point nearest(double x, double y) {
        if (points == null)
            return null;
        Point target = new Point(x, y);
        Point nearest = points.get(0);
        double nearDist = points.get(0).distance(target);
        for (int i = 1; i < points.size(); i++) {
            double c;
            if ((c = points.get(i).distance(target)) < nearDist) {
                nearest = points.get(i);
                nearDist = c;
            }
        }
        return nearest;
    }

    /** just to test */
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
        NaivePointSet nn = new NaivePointSet(points);
        Point ret = nn.nearest(0.81, 0.30);
        System.out.print(ret.getX() + ", ");
        System.out.println(ret.getY());

        in.close();
    }
}