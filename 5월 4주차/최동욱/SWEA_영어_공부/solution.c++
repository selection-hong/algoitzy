#include <iostream>
#include <stdlib.h>

using namespace std;

int solve(int* date, int n, int p) {
    int left = 0, right = 0, res = 0, w = p;

    while(right < n) {
        while(right < n - 1 && date[right + 1] - (date[right] + 1) <= w) {
            w -= date[right + 1] - (date[right] + 1);
            right++;
        }

        int day = date[right] - date[left] + 1 + w;
        if(day > res) res = day;
        
        if(left == right) right++;
        else w += date[left + 1] - (date[left] + 1);
        
        left++;
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