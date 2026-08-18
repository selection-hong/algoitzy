import java.util.*;

class Solution {
    public long[] solution(long k, long[] room_number) {
        int n = room_number.length;
        
        Map<Long, Integer> map = new HashMap<>();
        long[] answer = new long[n];
        long[] num = new long[n];
        
        for(int i = 0; i < n; i++) {
            long room = room_number[i];
            
            while(!map.isEmpty() && map.containsKey(room)) {
                room = find(map, num, room);
            }
            
            map.put(room, i);
            answer[i] = room;
            num[i] = room + 1;
        }
        
        
        return answer;
    }
    
    private long find(Map<Long, Integer> map, long[] num, long room) {
        if(!map.isEmpty() && map.containsKey(room)) {
            return num[map.get(room)] = find(map, num, num[map.get(room)]);    
        } else {
            return room;
        }
    }
}