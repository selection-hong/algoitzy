class Solution {
    public long solution(int[] sequence) {
        long answer = Long.MIN_VALUE;

        long current1 = 0;
        long current2 = 0;
        int pulse=1;
        
        for (int i = 0; i < sequence.length; i++) {
            long value1;
            long value2;

            if (i % 2 == 0) {
                value1 = sequence[i];
                value2 = -sequence[i];
            } else {
                value1 = -sequence[i];
                value2 = sequence[i];
            }
            
            current1 = Math.max(value1, current1 + value1);
            current2 = Math.max(value2, current2 + value2);

            answer = Math.max(answer, Math.max(current1, current2));
        }

        return answer;
    }
}
