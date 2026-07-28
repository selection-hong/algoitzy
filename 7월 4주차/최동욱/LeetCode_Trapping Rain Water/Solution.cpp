class Solution {
public:
    int trap(vector<int>& height) {
        
        int n = height.size();
        int stack[n][2];

        int top = -1, maxHeight = 0, result = 0;
        for(int i = 0; i < n; i++) {

            int currentHeight = height[i];
            if(top == -1 && currentHeight == 0) continue;

            int fillHeight = maxHeight > currentHeight ? currentHeight : maxHeight;

            int width = 1;
            while(top > -1 && stack[top][0] < fillHeight) {
                result += (fillHeight - stack[top][0]) * stack[top][1];
                width += stack[top][1];
                top--;
            }

            top++;
            stack[top][0] = currentHeight;
            if(maxHeight > currentHeight) {    
                stack[top][1] = width;    
            } else {
                stack[top][1] = 1;    
                maxHeight = currentHeight;
            }
        }

        return result;
    }
};