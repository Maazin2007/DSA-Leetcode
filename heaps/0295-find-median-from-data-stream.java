import java.util.*;

class MedianFinder {
    private PriorityQueue<Integer> minHeap;
    private PriorityQueue<Integer> maxHeap;

    public MedianFinder() {
        this.minHeap = new PriorityQueue<>();
        this.maxHeap = new PriorityQueue<>(Collections.reverseOrder());
    }

    public void addNum(int num) {
        maxHeap.offer(num);

        // fix ordering: max of lower half should never exceed min of upper half
        if (minHeap.size() != 0 && maxHeap.size() != 0 && (maxHeap.peek() > minHeap.peek())) {
            int val = maxHeap.poll();
            minHeap.offer(val);
        }

        // fix size balance: keep the two heaps within 1 of each other
        if (maxHeap.size() > minHeap.size() + 1) {
            int val = maxHeap.poll();
            minHeap.offer(val);
        }
        if (minHeap.size() > maxHeap.size() + 1) {
            int val = minHeap.poll();
            maxHeap.offer(val);
        }
    }

    public double findMedian() {
        if (maxHeap.size() > minHeap.size()) {
            return maxHeap.peek();
        }
        if (maxHeap.size() < minHeap.size()) {
            return minHeap.peek();
        }
        return (maxHeap.peek() + minHeap.peek()) / 2.0;
    }
}
