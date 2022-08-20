package remme;

import java.util.ArrayList;

public class CircularArray<T> {
	private ArrayList<T> arr = new ArrayList<>();
	private int start = 0;
	private int limit;

	public CircularArray(int limit) {
		this.limit = limit;
	}

	void add(T t) {
		if (arr.size() < limit) {
			arr.add(t);
		} else {
			arr.set(start, t);
			start = (start + 1) % limit;
		}
	}

	T get(int i) {
		return arr.get((start + i) % limit);
	}

	int size() {
		return arr.size();
	}

	int start() {
		return start;
	}

	int limit() {
		return limit;
	}

	void clear() {
		arr.clear();
		start = 0;
	}

	void limit(int newLimit) {
		if (limit == newLimit)
			return;
		ArrayList<T> newArr = new ArrayList<>();
		limit = arr.size();

		if (newLimit > limit) {
			for (int i = 0; i < limit; i++)
				newArr.add(get(i));
		} else {
			for (int i = 0; i < newLimit; i++)
				newArr.add(get(limit - newLimit + i));
		}

		limit = newLimit;
		arr = newArr;
		start = 0;
	}

}
