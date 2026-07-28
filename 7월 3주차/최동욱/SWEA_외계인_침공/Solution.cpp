#include <iostream>

using namespace std;

int main() {
    ios::sync_with_stdio(false);
    cin.tie(NULL);

    int T, n, val;
    cin >> T;
    
    long long pre1 = 0, pre2 = 0, target1, target2, res;
    for(int t = 1; t <= T; t++) {
        cin >> n;
        
        cin >> pre1;
        pre2 = 0;
        for(int i = 1; i < n; i++) {
            cin >> val;
            target1 = pre2 + val;
            target2 = pre1 > pre2 ? pre1 : pre2;

            pre1 = target1;
            pre2 = target2;
        }

        res = pre1 > pre2 ? pre1 : pre2;
        cout << '#' << t << ' ' << res << '\n';
    }
    
    return 0;
}