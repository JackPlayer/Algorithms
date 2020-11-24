import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import javax.sound.sampled.Line;


public class BruteCollinearPoints {
    private int size;
    private final LineSegment[] lineSegments;
    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();
        if (repeated(points)) throw new IllegalArgumentException();

        size = 0;
        lineSegments = new LineSegment[points.length];
        bruteForce(points);
    }

    /**
     * Brute force algorithm for finding collinear points. O(n^4)
     * @param points The list of points to find collinear points from
     */
    private void bruteForce(Point[] points) {
        int pointLength = points.length;
        System.out.println("Points" + points.length);


        for (int p1Idx = 0; p1Idx < pointLength; p1Idx++) {
            for (int p2Idx = 1; p2Idx < pointLength; p2Idx++) {
                if ( p1Idx == p2Idx) continue;
                for (int p3Idx = 2; p3Idx < pointLength; p3Idx++) {
                    if (p3Idx == p1Idx || p3Idx == p2Idx) continue;
                    for (int p4Idx = 3; p4Idx < pointLength; p4Idx++) {
                        if (p4Idx == p1Idx || p4Idx == p3Idx || p4Idx == p2Idx) continue;
                        // Any points are null
                        if (points[p1Idx] == null || points[p2Idx] == null || points[p3Idx] == null || points[p4Idx] == null) {
                            throw new IllegalArgumentException();
                        }


                        if (slopesEqual(points[p1Idx], points[p2Idx], points[p3Idx], points[p4Idx])) {
                            System.out.println("Equal");
                            LineSegment ls = new LineSegment(points[p1Idx], points[p4Idx]);
                            lineSegments[p1Idx] = ls;
                            size++;
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
    public int numSegments() {
        return size;
    }

    /**
     * Gets the list of collinear segments with 4 points
     * @return The collinear segments
     */
    public LineSegment[] segments() {
        int lsSize = 0;
        for (LineSegment lineSegment : lineSegments) {
            if (lineSegment == null) break;
            lsSize++;
        }

        LineSegment[] ls = new LineSegment[lsSize];

        if (lsSize >= 0) System.arraycopy(lineSegments, 0, ls, 0, lsSize);

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
    }


}
