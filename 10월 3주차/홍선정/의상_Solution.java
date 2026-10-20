//프로그래머스_의상
//https://school.programmers.co.kr/learn/courses/30/lessons/42578?gad_source=1&gad_campaignid=23037984604&gbraid=0AAAAAC_c4nCtKFZfnf9P-dC8FEmMdVJYG&gclid=CjwKCAjwotzWBhBFEiwA1JhPoC_l4_GqyExpsZYPMLQP4Tte0-r5UqakzJPWXU-6kjVN3tFme6uLxBoCTnMQAvD_BwE

/*
각 의상 카테고리별 의상 개수를 세고, 
해당 의상을 입지 않는 경우를 카테고리 별로 1개씩 추가 후 곱해 조합 생성.
 */


//DP 활용 버전 구현해보세요 여러분
import java.util.HashMap;
import java.util.Map;

class Solution {

    public int solution(String[][] clothes) {
        Map<String, Integer> map = new HashMap<>();

        //의상 종류별 개수 카운트
        for (String[] cloth : clothes) {
            String type = cloth[1];

            //getOrDefault 사용 버전
            //map.put(type, map.getOrDefault(type, 0) + 1);
            Integer count = map.get(type);
            if (count == null) {
                count = 0;
            }
            map.put(type, count + 1);
        }
        int answer = 1;
        for (int count : map.values()) {
            answer *= (count + 1);
        }
        return answer - 1;
    }
