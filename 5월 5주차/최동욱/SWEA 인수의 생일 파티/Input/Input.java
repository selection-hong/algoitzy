package Input;

import java.io.IOException;

/**
 * 커스텀 빠른 입출력 클래스
 * System.in.read()를 직접 사용하여 파싱 속도를 극대화함
 */
public class Input {
    public int readInt() throws IOException {
        int c = System.in.read();
        // 공백 및 줄바꿈 스킵
        while(c <= ' ') c = System.in.read();
        
        int n = 0;
        // 숫자 ASCII 값을 이용한 정수 파싱
        while(c >= '0' && c <= '9') {
            // n * 10 대신 비트 연산 사용 (n << 3 + n << 1)
            n = (n << 3) + (n << 1) + (c & 15);
            c = System.in.read();
        }
        return n;
    }
}
