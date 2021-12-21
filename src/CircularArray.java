package remme;

import java.util.ArrayList;

public class CircularArray<T> {
	private ArrayList<T> arr = new ArrayList<>();
	private int start = 0;
	private int limit;

	CircularArray(int limit) {
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

	public static void main(String args[]) {
		CircularArray<Integer> c1 = new CircularArray<>(10);

		for (int i = 0; i < 10; i++)
			c1.add(i);

		for (int i = 0; i < c1.size(); i++)
			System.out.print(c1.get(i) + " ");
		System.out.println();
		System.out.println(c1.start);
		System.out.println();

		for (int i = 10; i < 15; i++)
			c1.add(i);

		for (int i = 0; i < c1.size(); i++)
			System.out.print(c1.get(i) + " ");
		System.out.println();
		System.out.println(c1.start);
		System.out.println();

		c1.limit(7);

		for (int i = 0; i < c1.size(); i++)
			System.out.print(c1.get(i) + " ");
		System.out.println();

		c1.limit(12);

		for (int i = 0; i < c1.size(); i++)
			System.out.print(c1.get(i) + " ");
		System.out.println();

		for (int i = 15; i < 20; i++)
			c1.add(i);

		for (int i = 0; i < c1.size(); i++)
			System.out.print(c1.get(i) + " ");
		System.out.println();
		System.out.println(c1.start);
		System.out.println();

		for (int i = 20; i < 25; i++)
			c1.add(i);

		for (int i = 0; i < c1.size(); i++)
			System.out.print(c1.get(i) + " ");
		System.out.println();
		System.out.println(c1.start);
		System.out.println();
	}

}
