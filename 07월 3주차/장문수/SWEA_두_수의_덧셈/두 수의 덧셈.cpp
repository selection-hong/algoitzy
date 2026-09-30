#include <iostream>
#include <string>
#include <algorithm>

using namespace std;


/* 문자 -> 정수 변환 */
int char_to_int(char a, char b, int up) {

    return (a -'0') + (b - '0') + up;
}


/* 남은 구간 계산 */
string final_calc(string a, string b, int up) {
    string result = "";
    for (int i = b.size(); i < a.size(); i++) {
        result += to_string(char_to_int(a[i], '0', up) % 10);

        if (char_to_int(a[i], '0', up) >= 10) {
            up = 1;
        } else {
            up = 0;
        }
    }

    if (up) {
        result += "1";
    }

    return result;
}


/* string 계산 */
string calc(string a, string b) {
    string result = "";
    int up = 0;
    for (int i = 0; i < b.size(); i++) {
        result += to_string(char_to_int(a[i], b[i], up) % 10);
        
        if (char_to_int(a[i], b[i], up) >= 10) {
            up = 1;
        } else {
            up = 0;
        }
    }

    result += final_calc(a, b, up);
    reverse(result.begin(), result.end());
    
    return result;
}

int main() {

    int tc;
    cin >> tc;

    for (int t = 1; t <= tc; t++) {
        string a, b;
        cin >> a >> b;

        reverse(a.begin(), a.end());
        reverse(b.begin(), b.end());

        string answer = a.size() > b.size() ? calc(a, b) : calc(b, a);
        cout << "#" << t << " " << answer << endl;
    }
}