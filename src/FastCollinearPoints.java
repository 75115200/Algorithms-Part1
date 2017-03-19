import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
    private static final int DEFAULT_CAPACITY = 10;
    private LineSegment[] segments;
    private int number;

    public FastCollinearPoints(Point[] ps) {
        Point[] points = ps.clone();
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
        Point[] tmp = points.clone();
        for (int i = 0; i < tmp.length; i++) {
            findSegments(points[i], tmp);
        }
    }

    public int numberOfSegments() {
        return number;
    }

    public LineSegment[] segments() {
        return ensureMin();
    }

    private void findSegments(Point origin, Point[] sortPoints) {
        Arrays.sort(sortPoints, origin.slopeOrder());
        for (int i = 0; i < sortPoints.length; i++) {
            int k = i + 1;
            while (k < sortPoints.length && origin.slopeTo(sortPoints[i]) == origin.slopeTo(sortPoints[k])) {
                k++;
            }
            if (--k - i >= 2) {
                int minIndex = i;
                int maxIndex = i; // 注意打擂台要从相同的角标开始，不然有一个点会没有比较到
                for (int m = minIndex + 1; m <= k; m++) {
                    if (sortPoints[m].compareTo(sortPoints[minIndex]) < 0) {
                        minIndex = m;
                    }
                    if (sortPoints[m].compareTo(sortPoints[maxIndex]) > 0) {
                        maxIndex = m;
                    }
                }
                if (origin.compareTo(sortPoints[minIndex]) <= 0) {
                    // 只有当origin小于最小的点时，线段才是最长的，对每个点的遍历总会覆盖到这种情况
                    addSegment(origin, sortPoints[maxIndex]);
                }
                i = k;
            }
        }
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
