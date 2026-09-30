#include <iostream>
#include <stdlib.h>
 
using namespace std;
 
int solve(int* date, int n, int p) {
    int left = 0, right = 0, res = p + 1;
 
    while(right < n) {
        int day = date[right] - date[left] + 1 + p;
        if(day > res) res = day;
        right++;
 
        p -= date[right] - (date[right - 1] + 1);
 
        while(p < 0 && left < right) {
            p += date[left + 1] - (date[left] + 1);
            left++;
        }        
    }
    return res;
}
 
int main(int argc, char** argv) {
    ios::sync_with_stdio(NULL);
    cin.tie(NULL);
 
    int T, n, p;
    cin >> T;
    for(int t = 1; t <= T; t++) {
        cin >> n >> p;
 
        int* date = (int*)malloc(sizeof(int) * n);
        for(int i = 0; i < n; i++) cin >> date[i];
 
        cout << '#' << t << ' ' << solve(date, n, p) << '\n';
    }
     
    return 0;
}