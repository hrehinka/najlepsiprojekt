package algoritmy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

public class algoritmy {

	public static Map<String, ArrayList<String>> generator(int n) {
		Map<String, ArrayList<String>> map = new HashMap<>();
		for (int i = 1; i < n + 1; i++) {
			int numberOfvalues = (int) ((Math.random() * (n - 1)) + 1);
			ArrayList<String> values = new ArrayList<>();
			while (values.size() != numberOfvalues) {
				int value = (int) ((Math.random() * (n - 1)) + 1);
				if (!values.contains(Integer.toString(value))) {
					values.add(Integer.toString(value));
				}

			}
			map.put(Integer.toString(i), values);
		}

		return map;

	}

	public static double intersection(ArrayList<String> a, ArrayList<String> b) {
		double size = 0;
		for (String s : a) {
			if (b.contains(s)) {
				size++;
			}
		}
		return size;

	}

	public static ArrayList<String> intersection2(ArrayList<String> a, ArrayList<String> b) {
		ArrayList<String> list = new ArrayList<>();
		for (String s : a) {
			if (b.contains(s)) {
				list.add(s);
			}
		}
		return list;

	}

	public static double union(ArrayList<String> a, ArrayList<String> b) {
		Set<String> set = new HashSet<>();
		for (String sa : a) {
			set.add(sa);
		}
		for (String sb : b) {
			set.add(sb);
		}

		double size = set.size();

		return size;

	}

	public static double JaggardMetric(ArrayList<String> list1, ArrayList<String> list2) {
		double ro = 0;

		double intersection = intersection(list1, list2);
		double union = union(list1, list2);
		double division = intersection / union;

		ro = 1 - division;

		return ro;

	}

	public static Set<ArrayList<String>> rice_siff(Map<String, ArrayList<String>> table) {
		CopyOnWriteArrayList<ArrayList<String>> C = new CopyOnWriteArrayList<>();
		for (ArrayList<String> line : table.values()) {
			C.add(line);
		}

		CopyOnWriteArrayList<ArrayList<String>> D = new CopyOnWriteArrayList<>();
		D = (CopyOnWriteArrayList<ArrayList<String>>) C.clone();
		double min = Double.MAX_VALUE;
		ArrayList<String> index1 = new ArrayList<>();
		ArrayList<String> index2 = new ArrayList<>();

		// ma sa posledny prvok ktory ostane v D pridat do C?
		while (D.size() > 1) {
			for (ArrayList<String> l : D) {
				for (ArrayList<String> k : D) {
					if (!l.equals(k)) {
						double distance = JaggardMetric(l, k);
						if (min > distance) {
							min = distance;
							index1 = l;
							index2 = k;
						}
					}

				}
			}

			D.remove(index1);
			D.remove(index2);
			min = Double.MAX_VALUE;
			// ked je prienik prazna mnozina ma sa pridat do C ?
			ArrayList<String> pom = intersection2(index1, index2);
			if (!pom.isEmpty()) {
				D.add(pom);
			}
			C.add(pom);

		}

		Set<ArrayList<String>> output = new HashSet<>();
		output.addAll(C);
		return output;

	}

	public static CopyOnWriteArraySet<ArrayList<String>> objectIntersection(Map<String, ArrayList<String>> table) {
		CopyOnWriteArraySet<ArrayList<String>> C = new CopyOnWriteArraySet<ArrayList<String>>();
		ArrayList<String> all = new ArrayList<>();
		for (int i = 1; i < table.size() + 1; i++) {
			all.add(Integer.toString(i));
		}
		C.add(all);
		for (ArrayList<String> value : table.values()) {
			for (ArrayList<String> c : C) {
				ArrayList<String> intersection = new ArrayList<>();
				for (String s : value) {
					if (c.contains(s)) {
						intersection.add(s);
					}
				}
				C.add(intersection);
			}

		}

		return C;

	}

	public static void main(String[] args) {
		// input generation
		Map<String, ArrayList<String>> table = new HashMap<>();
		table = generator(3);
		System.out.println(table);

		
		System.out.println(objectIntersection(table));
		System.out.println(rice_siff(table));

	}

}
