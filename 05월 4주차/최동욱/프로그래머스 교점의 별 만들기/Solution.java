import java.util.*;

class Solution {
    
    static final int MAX_SIZE = 100_000;
    
    public String[] solution(int[][] line) {
        long minX = Long.MAX_VALUE, maxX = Long.MIN_VALUE;
        long minY = Long.MAX_VALUE, maxY = Long.MIN_VALUE;
        
        List<long[]> list = new ArrayList<>();
        
        for(int i = 0; i < line.length - 1; i++) {
            long a = line[i][0];
            long b = line[i][1];
            long e = line[i][2];
            for(int j = i + 1; j < line.length; j++) {
                long c = line[j][0];
                long d = line[j][1];
                long f = line[j][2];
                
                long denominator = (a * d) - (b * c);
                
                if(denominator == 0) continue;
                
                long moleculeX = (b * f) - (e * d);
                long moleculeY = (e * c) - (a * f);
                
                if(moleculeX % denominator != 0 || moleculeY % denominator != 0) continue;
                
                long dotX = moleculeX / denominator;
                long dotY = moleculeY / denominator;
                
                if(dotX > maxX) maxX = dotX;
                if(dotX < minX) minX = dotX;
                if(dotY > maxY) maxY = dotY;
                if(dotY < minY) minY = dotY;
                
                list.add(new long[]{dotY, dotX});
            }
        }
        
        int row = (int)(maxY - minY + 1);
        int col = (int)(maxX - minX + 1);
        char[][] res = new char[row][col];
        for(int i = 0; i < row; i++) {
            for(int j = 0; j < col; j++) {
                res[i][j] = '.';
            }
        }
        
        for(long[] dot : list) {
            int y = (int)(maxY - dot[0]);
            int x = (int)(dot[1] - minX);
            
            res[y][x] = '*';
        }
        
        StringBuilder sb = new StringBuilder();
        String[] answer = new String[row];
        for(int i = 0; i < row; i++) {
            for(int j = 0; j < col; j++) {
                sb.append(res[i][j]);
            }
            answer[i] = sb.toString();
            sb.setLength(0);
        }
        
        return answer;
    }
}