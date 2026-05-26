import java.io.*;
import java.util.*;

public class Main { // 프로그래머스 풍선 터트리기

	public static void main(String[] args) throws IOException {
		
		int[] a = {9, -1, -5};
		int[] b = {-16, 27, 65, -2, 58, -92, -71, -68, -61, -33};
		
		System.out.println(solution(a)); // 3 : {9, -1, -5}
		System.out.println(solution(b)); // 6 : {-16, -92, -71, -68, -61, -33}

	}
	
    public static int solution(int[] a) {
        
    	int answer = a.length;
        
    	TreeSet<Integer> left = new TreeSet<>();
    	TreeSet<Integer> right = new TreeSet<>();
    
    	for (int i = 1; i < a.length; i++) {
    		right.add(a[i]);
    	}
    	
    	for (int i = 1; i < a.length - 1; i++) {
    		left.add(a[i-1]);
    		right.remove(a[i]);
    		if (a[i] > left.first() && a[i] > right.first()) answer--;
    	}
    	
        return answer;
    
    }

}
