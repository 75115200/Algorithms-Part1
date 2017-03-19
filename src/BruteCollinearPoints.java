import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
    private static final int DEFAULT_CAPACITY = 10;
    private int number;
    private LineSegment[] segments;

    public BruteCollinearPoints(Point[] ps) {
        Point[] points = ps.clone();
        // finds all line segments containing 4 points
        number = 0;
        segments = new LineSegment[DEFAULT_CAPACITY];
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new NullPointerException("the point can not be null");
            }
            for (int j = i; j > 0 && points[j].compareTo(points[j - 1]) <= 0; j--) {
                if (points[j].compareTo(points[j - 1]) == 0) {
                    throw new IllegalArgumentException("repeated point.");
                }
                Point temp = points[j];
                points[j] = points[j - 1];
                points[j - 1] = temp;
            }
        }
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                for (int k = j + 1; k < points.length; k++) {
                    if (points[i].slopeTo(points[j]) != points[i].slopeTo(points[k])) {
                        continue;
                    }
                    for (int z = k + 1; z < points.length; z++) {
                        if (Double.compare(points[i].slopeTo(points[j]), points[i].slopeTo(points[z])) == 0) {
                            addSegment(points[i], points[z]);
                        }
                    }
                }
            }
        }
    }

    public int numberOfSegments() {
        // the number of line segments
        return number;
    }

    /**
     * The method segments() should include each line segment containing 4
     * points exactly once. If 4 points appear on a line segment in the order
     * p→q→r→s, then you should include either the line segment p→s or s→p (but
     * not both) and you should not include subsegments such as p→r or q→r. For
     * simplicity, we will not supply any input to BruteCollinearPoints that has
     * 5 or more collinear points.
     * 
     * @return
     */
    public LineSegment[] segments() {
        // the line segments
        return ensureMin();
    }

    private void addSegment(Point a, Point b) {
        ensureCapacity(number + 1);
        segments[number++] = new LineSegment(a, b);
    }

    private LineSegment[] ensureMin() {
        LineSegment[] newSegments = new LineSegment[number];
        for (int i = 0; i < number; i++) {
            newSegments[i] = segments[i];
        }
        return newSegments;
    }

    private void ensureCapacity(int size) {
        if (size > segments.length) {
            LineSegment[] newSegments = new LineSegment[segments.length * 2];
            for (int i = 0; i < segments.length; i++) {
                newSegments[i] = segments[i];
            }
            segments = newSegments;
        }
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        // FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
