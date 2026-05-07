class Solution {
    static final int DIV = 10007;
    public int solution(int n, int[] tops) {
        int answer = 0;
        int none=1,up=tops[0],left=1,right=1;
        for(int i=1;i<n;i++){
            int bnone=none;
            int bup=up;
            int bleft=left;
            int bright=right;
            none=(bnone+bup+bleft+bright)%DIV;
            left=(bnone+bup+bleft)%DIV;
            right=(bnone+bup+bleft+bright)%DIV;
            if(tops[i]==1) up=(bnone+bup+bleft+bright)%DIV;
            else up=0;
        }
        answer=(none+up+left+right)%DIV;
        return answer;
    }
}
