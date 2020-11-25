import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * BruteCollinearPoints
 * Finds collinear points with brute force algorithm
 * For coursera algorithms course
 */
public class BruteCollinearPoints {
    private final ArrayList<LineSegment> lineSegments;
    public BruteCollinearPoints(Point[] points) {
        // Exception cases
        if (points == null) throw new IllegalArgumentException("Points array is null");
        for (Point point : points) {
            if (point == null) {
                throw new IllegalArgumentException("An is null");
            }
        }
        if (repeated(points)) throw new IllegalArgumentException("A point is repeated");

        lineSegments = new ArrayList<>(1);
        bruteForce(points);
    }

    /**
     * Brute force algorithm for finding collinear points. O(n^4)
     * @param points The list of points to find collinear points from
     */
    private void bruteForce(Point[] points) {
        int pointLength = points.length;

        //Arrays.sort(points);
        for (int p1Idx = 0; p1Idx < pointLength; p1Idx++) {

            for (int p2Idx = p1Idx + 1; p2Idx < pointLength; p2Idx++) {
                if ( p1Idx == p2Idx) continue;

                for (int p3Idx = p2Idx + 1; p3Idx < pointLength; p3Idx++) {
                    if (p3Idx == p1Idx || p3Idx == p2Idx) continue;

                    for (int p4Idx = p3Idx + 1; p4Idx < pointLength; p4Idx++) {
                        if (p4Idx == p1Idx || p4Idx == p3Idx || p4Idx == p2Idx) continue;
                        // p1 -> p4 are now distinct

                        // Are the slopes connecting these 4 points equal
                        if (slopesEqual(points[p1Idx], points[p2Idx], points[p3Idx], points[p4Idx])) {
                            Point[] fourPointList = {points[p1Idx], points[p2Idx], points[p3Idx], points[p4Idx]};

                            // Arrange them in order
                            Arrays.sort(fourPointList);

                            // Create a line segment from the smallest to the largest
                            LineSegment ls = new LineSegment(fourPointList[0], fourPointList[3]);
                            lineSegments.add(ls);
                        }
                    }
                }
            }
        }
    }

    /**
     *
     * @param points Array of points
     * @return true if there are repeated points
     */
    private boolean repeated(Point[] points) {
        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < points.length; j++) {
                if (j == i) continue;
                if (points[i].compareTo(points[j]) == 0) return true;
            }
        }
        return false;
    }

    private boolean slopesEqual(Point p1, Point p2, Point p3, Point p4) {
        return p1.slopeTo(p2) == p1.slopeTo(p3)
                && p1.slopeTo(p3) == p1.slopeTo(p4);
    }

    /**
     * The number of line segments of 4
     * @return The number of line segments
     */
    public int numberOfSegments() {
        return lineSegments.size();
    }

    /**
     * Gets the list of collinear segments with 4 points
     * @return The collinear segments
     */
    public LineSegment[] segments() {
        LineSegment[] ls = new LineSegment[lineSegments.size()];

        ls = (lineSegments.toArray(ls));
        return ls;
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();

        //  Print out the number of points
        StdOut.println("Num Segments:" + collinear.numberOfSegments());
    }
}
