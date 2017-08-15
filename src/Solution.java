
import java.io.File;
import java.util.*;

public class Solution {
    public void solve() throws Exception{
        Scanner scanner = new Scanner(new File("map.txt"));
        int totalRows = scanner.nextInt();
        int totalCols = scanner.nextInt();

        int[][] map = new int[totalRows][totalCols];
        for(int row = 0; row < totalRows; row ++){
            for(int col = 0; col < totalCols; col ++){
                map[row][col] = scanner.nextInt();
            }
        }

        int maxLength = 0;
        int maxHeight = 0;
        Queue<Integer> queue = new LinkedList<Integer>();
        int[][] value = new int[totalRows][totalCols];
        boolean[][] hasLarger = new boolean[totalRows][totalCols];

        //init queue and value;
        for(int row = 0; row < totalRows; row ++) {
            for (int col = 0; col < totalCols; col++) {
                boolean hasSmallerAround = hasSmallerAround(row, col, map);
                boolean hasLargerAround = hasLargerAround(row, col, map);

                if(!hasSmallerAround){
                    queue.add(convertPointToIndex(row, col, totalRows, totalCols));
                }

                value[row][col] = 1;
                hasLarger[row][col] = hasLargerAround;
            }
        }


        while(!queue.isEmpty()){
            Integer index = queue.poll();
            Point p = convertIndexToPoint(index, totalRows, totalCols);
            int row = p.x;
            int col = p.y;

            //update result;
            if(!hasLarger[row][col] && value[row][col] > maxLength){
                maxLength = value[row][col];
                if(map[row][col] > maxHeight){
                    maxHeight = map[row][col];
                }
            }

            //if up is larger;
            if(row - 1 >= 0 && map[row - 1][col] > map[row][col]){
                if(value[row - 1][col] < value[row][col] + 1){
                    value[row - 1][col] = value[row][col] + 1;
                    queue.add(convertPointToIndex(row - 1, col, totalRows, totalCols));
                }
            }

            //if down is larger;
            if(row + 1 <= totalRows - 1 && map[row + 1][col] > map[row][col]){
                if(value[row + 1][col] < value[row][col] + 1){
                    value[row + 1][col] = value[row][col] + 1;
                    queue.add(convertPointToIndex(row + 1, col, totalRows, totalCols));
                }
            }

            //if left is larger;
            if(col - 1 >= 0 && map[row][col - 1] > map[row][col]){
                if(value[row][col - 1] < value[row][col] + 1){
                    value[row][col - 1] = value[row][col] + 1;
                    queue.add(convertPointToIndex(row, col - 1, totalRows, totalCols));
                }
            }

            //if right is larger;
            if(col + 1 <= totalCols - 1 && map[row][col + 1] > map[row][col]){
                if(value[row][col + 1] < value[row][col] + 1){
                    value[row][col + 1] = value[row][col] + 1;
                    queue.add(convertPointToIndex(row, col + 1, totalRows, totalCols));
                }
            }
        }

        System.out.println(maxLength);
        System.out.println(maxHeight);
    }

    private boolean hasSmallerAround(int row, int col, int[][] map){
        int totalRows = map.length;
        int totalCols = map[0].length;

        //up;
        if(row - 1 >= 0 && map[row - 1][col] < map[row][col]){
            return true;
        }
        //down;
        if(row + 1 <= totalRows - 1 && map[row + 1][col] < map[row][col]){
            return true;
        }
        //left;
        if(col - 1 >= 0 && map[row][col - 1] < map[row][col]){
            return true;
        }
        //right;
        if(col + 1 <= totalCols - 1 && map[row][col + 1] < map[row][col]){
            return true;
        }
        return false;
    }

    private boolean hasLargerAround(int row, int col, int[][] map){
        int totalRows = map.length;
        int totalCols = map[0].length;

        //up;
        if(row - 1 >= 0 && map[row - 1][col] > map[row][col]){
            return true;
        }
        //down;
        if(row + 1 <= totalRows - 1 && map[row + 1][col] > map[row][col]){
            return true;
        }
        //left;
        if(col - 1 >= 0 && map[row][col - 1] > map[row][col]){
            return true;
        }
        //right;
        if(col + 1 <= totalCols - 1 && map[row][col + 1] > map[row][col]){
            return true;
        }
        return false;
    }

    private int convertPointToIndex(int row, int col, int totalRows, int totalCols){
        return row * totalCols + col;
    }

    private Point convertIndexToPoint(int index, int totalRows, int totalCols){
        int row = index / totalCols;
        int col = index % totalCols;

        return new Point(row, col);
    }

    private class Point{
        int x;
        int y;

        Point(int x, int y){
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();

        try {
            solution.solve();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
