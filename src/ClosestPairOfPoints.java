import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by jo930_000 on 2016-10-26.
 */
public class ClosestPairOfPoints {
    ArrayList<Point> list = new ArrayList<>();

    public void ClosestPairOfPoints() throws IOException {

        FileInputStream input = new FileInputStream("C:/Users/jo930_000/IdeaProjects/Algorithm_hw06/src/data07_closest.txt");
        InputStreamReader reader = new InputStreamReader(input);
        StreamTokenizer token = new StreamTokenizer(reader);

        while((token.nextToken() != StreamTokenizer.TT_EOF)) {
            switch(token.ttype){
                case StreamTokenizer.TT_NUMBER :
                    Point temp_point = new Point();
                    temp_point.x = token.nval;
                    token.nextToken();
                    token.nextToken();
                    temp_point.y = token.nval;
                    list.add(temp_point);
                    break;
            }
        }

        input.close();

        // closest pair perform
        this.sortArr_about_x(list);
        System.out.printf("output data : %.4f", this.closest_Pair(list));
    }

    private class Point {
        double x,y;

        private Point() {
            this.x = Double.MAX_VALUE;
            this.y = Double.MAX_VALUE;
        }

        private Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    public void sortArr_about_x(ArrayList<Point> list) {
        Collections.sort(list, new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                return (o1.x < o2.x)? -1 : (o1.x > o2.x)? 1:0;
            }
        }); // ASC sort by x coordinate
    }

    public void sortArr_about_y(ArrayList<Point> list) {
        Collections.sort(list, new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                return (o1.y < o2.y)? -1 : (o1.y > o2.y)? 1:0;
            }
        }); // ASC sort by y coordinate
    }

    public double closest_Pair(ArrayList<Point> list) {
        if(list.size() <= 3) {     // if point count <= 3, calculate by bruteForce
            return bruteForce(list);
        }
        int mid = list.size()/2;
        double L = (list.get(mid-1).x + list.get(mid).x)/2;

        ArrayList<Point> left_half = new ArrayList<>();
        ArrayList<Point> right_half = new ArrayList<>();

        for(int i = 0; i <mid; i++)  left_half.add(list.get(i));
        for(int i = mid; i < list.size(); i++)  right_half.add(list.get(i));

        double window_Left = closest_Pair(left_half);
        double window_Right = closest_Pair(right_half);
        double window = min(window_Left, window_Right);

        // Delete all points further than window from separation line L
        ArrayList<Point> newlist = new ArrayList<>();
        for(int i = 0; i <mid; i++) {
            if(list.get(i).x > L - window) newlist.add(list.get(i));
        }
        for(int i = mid; i < list.size(); i++) {
            if(list.get(i).x < L + window) newlist.add(list.get(i));
        }

        // newlist sorting by y-coordinate
        this.sortArr_about_y(newlist);

        // the minimum distance in window
        double min = Double.MAX_VALUE;
        for(int i = 0; i < newlist.size(); i++) {
            for(int j = i + 1; j < newlist.size(); j++) {
                if ((newlist.get(j).y-newlist.get(i).y) < window) {
                    min = min(dist(newlist.get(i), newlist.get(j)), min);
                }
                else break;
            }
        }
        window = min(min, window);

        return window;
    }

    public double min(double x, double y) {
        if (x > y) return y;
        return x;
    }

    public double dist(Point p1, Point p2) {
        return Math.sqrt((p1.x-p2.x)*(p1.x-p2.x) + (p1.y-p2.y)*(p1.y-p2.y));
    }

    public double bruteForce(ArrayList<Point> list) {
        double min = Double.MAX_VALUE;
        for(int i = 0; i < list.size(); i++){
            for (int j = i+1; j < list.size(); j++)
                if(dist(list.get(i), list.get(j)) < min) min = dist(list.get(i), list.get(j));
        }
        return min;
    }
}