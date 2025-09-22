package org.noexcs.basic.algo;

public class TwoPointer {
    public static void main(String[] args) {
        int[] nums = new int[20];

    }

    public void twoPointer(int[] nums) {
        int length = nums.length;

        int left = 0, right = length - 1;
        int ans = 0;
        for (; left < right; ) {
            String b = predict1(nums, left, right);
            ans = recordAns(nums, left, right, ans);
            if ("left".equals(b)) {
                left++;
            }
            if ("right".equals(b)) {
                right--;
            }
        }
    }

    private int recordAns(int[] nums, int left, int right, int ans) {
        return 0;
    }


    // 盛水最多的容器
    private String predict1(int[] nums, int left, int right) {
        if (nums[left] < nums[right]) {
            return "left";
        }
        return "right";
    }

    private String predict2(int[] nums, int left, int right) {
        
        return "true";
    }
}
