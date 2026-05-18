import java.util.*;

public class Main {

	public static void main(String[] args) throws Exception {
		
		int[][] line = {{2, -1, 4}, {-2, -1, 4},
				{0, -1, 1}, {5, -8, -12}, {5, 8, 12}};
		String[] result = solution(line);
		for (int i = 0; i < result.length; i++) {
			System.out.println(result[i]);
		}

	}
	
    public static String[] solution(int[][] line) {
    	
    	// [1] 교점 구하기
        HashSet<String> set = new HashSet<>();
        
        for (int i = 0; i < line.length - 1; i++) {
        	for (int j = i + 1; j < line.length; j++) {
        		
        		// long
        		long a = line[i][0];
        		long b = line[i][1];
        		long e = line[i][2];
        		long c = line[j][0];
        		long d = line[j][1];
        		long f = line[j][2];
        		
        		// 분모
        		long z = a * d - b * c;
        		if (z == 0) continue;
        		
        		// 분자
        		long x = (b * f - e * d);
        		long y = (e * c - a * f);
        		if (x % z != 0 || y % z != 0) continue;
        		
        		// 교점
        		set.add((int) x / z + " " + (int) y / z);
        		
        	}
        }
        
        // [2] 교점 최대값 & 최소값 계산
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;
        int[][] list = new int[set.size()][2];
        
        int idx = 0;
        StringTokenizer st;
        
        for (String str : set) {
        	st = new StringTokenizer(str);
        	list[idx][0] = Integer.parseInt(st.nextToken());
        	list[idx][1] = Integer.parseInt(st.nextToken());
        	minX = Math.min(minX, list[idx][0]);
        	minY = Math.min(minY, list[idx][1]);
        	maxX = Math.max(maxX, list[idx][0]);
        	maxY = Math.max(maxY, list[idx][1]);
        	idx++;
        }
        
        // [3] map 크기 계산
        int row = maxY - minY + 1;
        int col = maxX - minX + 1;
        char[][] map = new char[row][col];
        
        for (int i = 0; i < row; i++) {
        	Arrays.fill(map[i], '.');
        }
        
        // [4] 별 그리기
        for (int i = 0; i < list.length; i++) {
        	int r = maxY - list[i][1]; // 음수 조정
        	int c = list[i][0] - minX; // 방향 반대
        	map[r][c] = '*';
        }
        
        String[] answer = new String[row];
        for (int i = 0; i < row; i++) {
        	answer[i] = new String(map[i]);
        }
        return answer;
        
    }

}
