package SwordToOfferSolution._40_KLeastNumbers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

/*
 * 最小的 K 个数
 *
 * 题目描述：
 * 输入 n 个整数，找出其中最小的 k 个数。
 * 例如输入 4、5、1、6、2、7、3、8 这 8 个数字，则最小的 4 个数字是 1、2、3、4。
 *
 * 思路 1：可以修改原数组的情况
 * 1. 利用快速排序中的获取分割（中轴）点位置函数 partition；
 * 2. 基于数组的第 k 个数字来调整，使得比第 k 个数字小的所有数字都位于数组的左边，比第 k 个数字大的所有数字都位于数组的右边；
 * 3. 调整之后，位于数组左边的 k 个数字就是最小的 k 个数字（这 k 个数字不一定是排序的）。
 *
 * 思路 2：不可修改原数组
 * 1. 通过设置比较器的不同来实现大顶堆或小顶堆；
 * 2. 使用优先队列（大顶堆）存储最小的 k 个数；
 * 3. 如果容器中已有的数字小于 k 个的话，则直接将当前数字放入到容器中；
 * 4. 如果当前数字比容器中的最大值要小的话，则用该数字替换已有的最大值；
 * 5. 如果当前数字比容器中的最大值要大的话，则该数字不可能是最小 k 个整数之一，则将其舍去；
 * 6. 时间复杂度：插入删除操作 O(log k)，最坏情况是 n 次，则总的时间复杂度是 O(nlog k)。
 */
public class Solution {
    // 方法一
    public ArrayList<Integer> getLeastNumbers(int[] nums, int k) {
        ArrayList<Integer> res = new ArrayList<>();
        if (nums == null || k > nums.length || k <= 0) {
            return res;
        }

        findKthSmallest(nums, k - 1);
        for (int i = 0; i < k; i++) {
            res.add(nums[i]);
        }
        return res;
    }

    // 将数组 nums 进行按照 k 进行划分，小于 k 的都放在其左边，大于 k 的都放在 k 的右边
    // 这样的话，前 k 个数都会在 k 的左边
    private void findKthSmallest(int[] nums, int k) {
        int low = 0, height = nums.length - 1;
        while (low < height) {
            // partition() 方法返回一个 index，该 index 满足 nums[low, index-1] <= nums[index] <= nums[index+1, height]
            int index = partition(nums, low, height);
            if (index == k) {
                break;
            }
            // k 在 index 的左侧，则调整 height
            if (index > k) {
                height = index - 1;
            }
            // k 在 index 的右侧，则调整 low
            if (index < k) {
                low = index + 1;
            }
        }
    }

    private int partition(int[] nums, int low, int height) {
        int pivot = nums[low];
        int i = low, j = height + 1;
        while (true) {
            // 保持头尾两个指针向中间扫描，每次从左侧开始找到小于 pivot 的值，同时在右侧开始找到大于于 pivot 的值，
            // 然后将它们做一个交换，就可以一次把这两个数字放到最终的位置
            // 从左到右，不断找到小于 pivot 的数
            while (i != height && nums[++i] < pivot) ;
            // 从右到左，不断找到大于 pivot 的数
            while (j != low && nums[--j] > pivot) ;
            if (i >= j) {
                break;
            }
            swap(nums, i, j);
        }
        swap(nums, low, j);
        return j;
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    // 方法二
    public int[] getLeastNumbers2(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return new int[0];
        }

        PriorityQueue<Integer> heapMax = new PriorityQueue<>((o1, o2) -> (o2 - o1));
        for (int num : nums) {
            if (heapMax.size() < k) {
                heapMax.offer(num);
                continue;
            }

            if (!heapMax.isEmpty() && num < heapMax.peek()) {
                heapMax.poll();
                heapMax.offer(num);
            }
        }

        int[] ans = new int[heapMax.size()];
        int i = 0;
        while (!heapMax.isEmpty()) {
            ans[i++] = heapMax.poll();
        }

        return ans;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        int[] nums1 = {3, 2, 1};
        int[] nums2 = {0, 1, 2, 1};

        System.out.println(solution.getLeastNumbers(nums1, 2));
        System.out.println(solution.getLeastNumbers(nums2, 1));

        System.out.println(Arrays.toString(solution.getLeastNumbers2(nums1, 2)));
        System.out.println(Arrays.toString(solution.getLeastNumbers2(nums2, 1)));
    }
}