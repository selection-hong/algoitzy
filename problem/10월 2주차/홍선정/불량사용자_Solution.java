package 

10월 2주차.홍선정;

//hashset api 호출 처리 누락 주의
import java.util.*;

class 불량사용자_Solution {

    //불량 사용자 -> 사용자 리스트 중에 밴 당할 조합 dfs 완탐
    //중복되는 조합을 hashset으로 자동 제거 
    //전역 변수 -> 
    Set<String> result = new HashSet<>();
    boolean[] v;

    public int solution(String[] user_id, String[] banned_id) {
        v = new boolean[user_id.length];
        dfs(0, user_id, banned_id);
        return result.size();
    }

    void dfs(int idx, String[] user_id, String[] banned_id) {
        if (idx == banned_id.length) {
            //String의 경우 작은 따옴표가 아닌 큰 따옴표로 감싸기... 
            String key = "";
            for (int i = 0; i < v.length; i++) {
                if (v[i]) {
                    key += i;
                }
            }
            result.add(key);
            return;
        }
        for (int i = 0; i < user_id.length; i++) {
            if (v[i] || !isMatch(user_id[i], banned_id[idx])) {
                continue;
            }
            v[i] = true;
            dfs(idx + 1, user_id, banned_id);
            v[i] = false;
        }

    }

    //배열일 때 length()가 아니라 length
    //String의 경우 length()
    boolean isMatch(String user_id, String banned_id) {
        if (user_id.length() != banned_id.length()) {
            return false;
        }

        //밴 아이디 목록에서 *가 포함되어있을 때
        for (int i = 0; i < banned_id.length(); i++) {
            if (banned_id.charAt(i) == '*') {
                continue;
            }
            if (banned_id.charAt(i) != user_id.charAt(i)) {
                return false;
            }
        }
        return true;
    }

}
