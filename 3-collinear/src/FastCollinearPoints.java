import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * Finds Collinear Points with a faster algorithm
 * For Coursera Algorithms course
 */
public class FastCollinearPoints {
    private final ArrayList<LineSegment> lineSegments;

    /**
     * Finds the set of all line segments that contain 4 points
     * @param points The points to find colinear segments from
     */
    public FastCollinearPoints(Point[] points) {
        lineSegments = new ArrayList<>(1);
        ArrayList<Double> slopeList = new ArrayList<>(1);

        // Sort into normal order
        Arrays.sort(points);
        Point[] pointsCopy = points.clone();

        for (Point p : pointsCopy) {

            // Sort into normal order first to maintain stability
            Arrays.sort(points);

            // Sort based off slope order
            Arrays.sort(points, p.slopeOrder());

            int numCollinear = 0;
            double currSlope = p.slopeTo(points[1]);
            Point endElement = null;

            int index = 0;
            for (Point q : points) {
                index++;
                if (q == p) continue;
                if (currSlope != p.slopeTo(q) || index == points.length - 1) {
                    if (currSlope == p.slopeTo(q)) {
                        numCollinear++;
                        endElement = q;
                    }
                    if (numCollinear >= 3 && !slopeList.contains(currSlope)) {
                        lineSegments.add(new LineSegment(p, endElement));
                        slopeList.add(currSlope);
                    }
                    currSlope = p.slopeTo(q);
                    numCollinear = 1;
                } else {
                    numCollinear++;
                    endElement = q;
                }
            }
        }
    }

    /**
     * Gets the number of line segments
     * @return The number of line segments in the points array
     */
    public int numSegments() {
        return lineSegments.size();
    }

    /**
     * The line segments
     * @return An array of line segments
     */
    public LineSegment[] segments() {
        LineSegment[] ls = new LineSegment[lineSegments.size()];
        ls = lineSegments.toArray(ls);
        return ls;
    }


//    private void printPointArr(Point[] arr) {
//        for (Point p : arr) {
//            System.out.println(p.toString());
//        }
//    }

    /**
     * Testing suite
     * @param args text file with line 1 being the number of points and subsequent lines being points x y
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();

        // Print out the number of points
        StdOut.println("Num Segments:" + collinear.numSegments());
    }
}
