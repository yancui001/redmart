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
        int resultIndex = 0;
        Queue<Integer> queue = new LinkedList<>();
        int[][] pathLengths = new int[totalRows][totalCols];
        boolean[][] hasLarger = new boolean[totalRows][totalCols];
        int[][] parentIndex = new int[totalRows][totalCols];

        //init queue and pathLengths;
        for(int row = 0; row < totalRows; row ++) {
            for (int col = 0; col < totalCols; col++) {
                boolean hasSmallerAround = hasSmallerAround(row, col, map);
                boolean hasLargerAround = hasLargerAround(row, col, map);

                if(!hasSmallerAround){
                    queue.add(convertPointToIndex(row, col, totalCols));
                }

                pathLengths[row][col] = 1;
                hasLarger[row][col] = hasLargerAround;
                parentIndex[row][col] = -1;
            }
        }

        //main loop;
        while(!queue.isEmpty()){
            Integer index = queue.poll();
            Point p = convertIndexToPoint(index, totalCols);
            int row = p.x;
            int col = p.y;

            //update result;
            if(!hasLarger[row][col] && pathLengths[row][col] >= maxLength){
                if(pathLengths[row][col] > maxLength){
                    maxLength = pathLengths[row][col];
                    maxHeight = map[row][col];
                    resultIndex = convertPointToIndex(row, col, totalCols);
                }else if(map[row][col] > maxHeight){
                    maxHeight = map[row][col];
                    resultIndex = convertPointToIndex(row, col, totalCols);
                }
            }

            //if up is larger;
            if(row - 1 >= 0 && map[row - 1][col] > map[row][col]){
                if(pathLengths[row - 1][col] < pathLengths[row][col] + 1){
                    pathLengths[row - 1][col] = pathLengths[row][col] + 1;
                    parentIndex[row - 1][col] = convertPointToIndex(row, col, totalCols);
                    queue.add(convertPointToIndex(row - 1, col, totalCols));
                }
            }

            //if down is larger;
            if(row + 1 <= totalRows - 1 && map[row + 1][col] > map[row][col]){
                if(pathLengths[row + 1][col] < pathLengths[row][col] + 1){
                    pathLengths[row + 1][col] = pathLengths[row][col] + 1;
                    parentIndex[row + 1][col] = convertPointToIndex(row, col, totalCols);
                    queue.add(convertPointToIndex(row + 1, col, totalCols));
                }
            }

            //if left is larger;
            if(col - 1 >= 0 && map[row][col - 1] > map[row][col]){
                if(pathLengths[row][col - 1] < pathLengths[row][col] + 1){
                    pathLengths[row][col - 1] = pathLengths[row][col] + 1;
                    parentIndex[row][col - 1] = convertPointToIndex(row, col, totalCols);
                    queue.add(convertPointToIndex(row, col - 1, totalCols));
                }
            }

            //if right is larger;
            if(col + 1 <= totalCols - 1 && map[row][col + 1] > map[row][col]){
                if(pathLengths[row][col + 1] < pathLengths[row][col] + 1){
                    pathLengths[row][col + 1] = pathLengths[row][col] + 1;
                    parentIndex[row][col + 1] = convertPointToIndex(row, col, totalCols);
                    queue.add(convertPointToIndex(row, col + 1, totalCols));
                }
            }
        }

        System.out.println(maxLength);
        System.out.println(maxHeight);

        System.out.println("Path:");
        Point p = convertIndexToPoint(resultIndex, totalCols);
        System.out.println(p + " - " + map[p.x][p.y]);
        int parent = parentIndex[p.x][p.y];
        while(parent != -1){
            p = convertIndexToPoint(parent, totalCols);
            System.out.println(p + " - " + map[p.x][p.y]);
            parent = parentIndex[p.x][p.y];
        }
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

    private int convertPointToIndex(int row, int col, int totalCols){
        return row * totalCols + col;
    }

    private Point convertIndexToPoint(int index, int totalCols){
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

        public String toString() {
            return "(" + x + ", " + y + ")";
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
