public class Point
{
    private double xPos;
    private double yPos;
    public Point(double x, double y) {
        xPos = x;
        yPos = y;
    }
    public double getX() {
        return xPos;
    }
    public double getY() {
        return yPos;
    }
    /** Returns squared Euclidean distance from target */
    public double distance(Point o) {
        return Math.pow(xPos - o.xPos, 2) + Math.pow(yPos - o.yPos, 2);
    }
}