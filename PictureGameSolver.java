import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.FilenameFilter;

public class PictureGameSolver {
    public static char[][] finalSolution;
    public static int r;
    public static int c;
    public static final int[] h = {-1,-1,-1,0,0,0,1,1,1};
    public static final int[] v = {-1,0,1,-1,0,1,-1,0,1};
    public static void main(String[] args) throws Exception {
        PictureGameSolver solver = new PictureGameSolver();
        char[][] numArray;
        char[][] arr;
        int count = 0;
        String dir = "/Users/yashpujara/cs180/extra java";
        Scanner s = new Scanner(System.in);
        
        System.out.println("choose a file number below"); 
        File folder = new File(dir);
        File[] listOfFiles = folder.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".txt");
            }
        });
        for(int i = 0; i < listOfFiles.length; i++) {
            System.out.printf("%d:   %s\n", i+1, listOfFiles[i].getName());
        }
            
        
       
        String file = listOfFiles[s.nextInt()-1].getName();
        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            c = Integer.parseInt(br.readLine());
            r = Integer.parseInt(br.readLine());
            numArray = new char[r][c];
            arr = new char[r][c];
            while(true) {
                String line = br.readLine();
                if(line == null) break;
                numArray[count++] = line.toCharArray();
            }
        }
        //solver.printArray(numArray);
        solver.solve(arr, numArray);
        //solver.printArray(arr);
        //printArray(numArray);
        
        
    }
    
    
    public void printArray(char[][] array) {
        //use 248
        for(int i = 0; i <array.length+2;i++) {
            System.out.print("- ");
        }
        System.out.println();
        for(int i = 0; i < array.length; i++) {
            System.out.printf("|");
            for(char z : array[i]) {
                if(z == 'x') {
                    z = ' ';
                }
                System.out.printf("%c ",z);
            }
            System.out.println("|");
        }
        for(int i = 0; i <array.length+2;i++) {
            System.out.print("- ");
        }
        System.out.println();
    }
    public void solve(char[][] arr, char[][] numArray) {
        int changed;
        do {
            changed = 0;
            for(int i = 0; i < numArray.length; i++) {
                for(int j = 0; j < numArray[0].length; j++) {
                    char current = numArray[i][j];
                    if(!Character.isDigit(current)) {
                        continue;
                    } else {
                        int shaded = 0;
                        int crossed = 0;
                        int blank = 0;
                        for(int k = 0; k < 9; k++) {
                            int x = i+h[k];
                            int y = j+v[k];
                            if (isValid(x,y)) {
                                
                                if(current == '0') {
                                    changed = 1;
                                    arr = cross(i, j, arr);
                                    numArray[i][j] = ' ';
                                    break;
                                } else if(current == '9') {
                                    changed = 2;
                                    arr = shade(i, j, arr);
                                    numArray[i][j] = ' ';
                                    break;
                                } else if (arr[x][y] == 'x') {
                                    crossed++;
                                } else if(arr[x][y] == 248) {
                                    shaded++;
                                } else {
                                    blank++;
                                }
                            }
                            
                        }
                        //System.out.printf("curr:%c\nshaded:%d\nblank%d\ncrossed%d\n", current, shaded, blank, crossed); DEBUG LINE
                        //got all counts
                        if(shaded == (current - '0')) {
                            //enough shaded rest are crossed
                            changed = 3;
                            arr = cross(i, j, arr);
                            numArray[i][j] = ' ';
                        }
                        if(crossed == 9 - (current - '0')) {
                            //enough crossed rest are shaded
                            changed = 4;
                            arr = shade(i, j, arr);
                            numArray[i][j] = ' ';
                        }
                        if(blank+shaded == (current - '0')) {
                            //similar to 0 or 9 but 6 and 4 for side and corner
                            changed = 5;
                            arr = shade(i, j, arr);
                            numArray[i][j] = ' ';
                        }
                        
                    }
                }
                
            }
            //printArray(arr);
            //System.out.println(changed); CHECKS FOR CHANGES
        } while(changed != 0);
        finalSolution = arr;
        
    }
    public boolean isCorner(int row, int col) {
        if(row + col == 0 || row + col == r+c-2) {
            return true;
        }
        if(row == 0 && col == c-1 || col == 0 && row == r-1) {
            return true;
        }
        return false;
    }
    public  boolean isSide(int row, int col) {
        //check for corner
        if(isCorner(row,col)) {
            return false;
        }
        //top side
        if(row == 0) {
            return true;
        }
        //bottom side
        if(row == r-1) {
            return true;
        }
        //left
        if(col==0) {
            return true;
        }
        //right
        if(col == c-1) {
            return true;
        }
        // else not
        return false;
    }
    public  boolean isValid(int row, int col) {
        if(row < 0 || col < 0) {
            return false;
        } else if(row >= r || col >= c) {
            return false;
        } else {
            return true;
        }
    }
    public char[][] shade(int row, int col, char[][] array) {
        char current = array[row][col];
        for(int i = 0; i < 9; i++) {
            int x = row + h[i];
            int y = col + v[i];
            if(isValid(x,y)) {
                if(array[x][y] != 'x') {
                    array[x][y] = 248;
                }
            }
        }
        return array;
    }
    public char[][] cross(int row, int col, char[][] array) {
        char current = array[row][col];
        for(int i = 0; i < 9; i++) {
            int x = row + h[i];
            int y = col + v[i];
            if(isValid(x,y)) {
                if(array[x][y] != 248) {
                    array[x][y] = 'x';
                }
            }
        }
        return array;
    }
    public boolean arrAreEqual(char[][] arr1, char[][] arr2) {
        for(int i = 0; i < arr1.length; i++) {
            for(int j = 0; j < arr1[0].length; j++) {
                if(arr1[i][j] != arr2[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
}
