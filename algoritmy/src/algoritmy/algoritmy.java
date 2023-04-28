package algoritmy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;

public class algoritmy {

	private static Map<ArrayList<String>, ArrayList<Double>> jaggard_matrix = new HashMap<ArrayList<String>, ArrayList<Double>>();

	public static Map<String, ArrayList<String>> generator(int n) {
		int longest = 0;
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
			Collections.sort(values);
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

	public static ArrayList<String> union1(ArrayList<String> a, ArrayList<String> b) {
		Set<String> set = new HashSet<>();
		for (String sa : a) {
			set.add(sa);
		}
		for (String sb : b) {
			set.add(sb);
		}

		ArrayList<String> setlist = new ArrayList<String>(set);
		return setlist;

	}

	public static double JaggardMetric(ArrayList<String> list1, ArrayList<String> list2) {
		double ro = 0;

		double intersection = intersection(list1, list2);
		double union = union(list1, list2);
		double division = intersection / union;

		ro = 1 - division;

		return ro;

	}

	public static double Metric(ArrayList<Double> row1, ArrayList<Double> row2) {

		double suma_min = 0;
		double suma_max = 0;

//		if (row1.size() != row2.size()) {
//			return 0;
//		}

		for (int i = 0; i < row1.size(); i++) {
			if (row1.get(i) < row2.get(i)) {
				suma_min = suma_min + row1.get(i);
			} else {
				suma_min = suma_min + row2.get(i);
			}
			if (row1.get(i) > row2.get(i)) {
				suma_max = suma_max + row1.get(i);
			} else {
				suma_max = suma_max + row2.get(i);
			}

		}

		double ro = 0;

		double division = suma_min / suma_max;

		ro = 1 - division;

		return ro;

	}

	public static Set<ArrayList<String>> rice_siff_fuzzy(Map<ArrayList<String>, ArrayList<Double>> table) {
		ArrayList<ArrayList<Double>> D = new ArrayList<>();
		for (ArrayList<Double> line : table.values()) {
			D.add(line);
		}

		CopyOnWriteArrayList<ArrayList<String>> keys = new CopyOnWriteArrayList<>();
		CopyOnWriteArrayList<ArrayList<String>> C = new CopyOnWriteArrayList<>();

		for (ArrayList<String> line : table.keySet()) {
			keys.add(line);
			C.add(line);
		}

		double min = Double.MAX_VALUE;
		ArrayList<Double> index1 = new ArrayList<>();
		ArrayList<Double> index2 = new ArrayList<>();

		ArrayList<String> indexkeys1 = new ArrayList<>();
		ArrayList<String> indexkeys2 = new ArrayList<>();

		System.out.println("JAGGARD " + jaggard_matrix);

		// ma sa posledny prvok ktory ostane v D pridat do C?
		while (D.size() > 1) {
			int ikeys1 = 0;
			int min1 = 0;
			int min2 = 0;
			System.out.println("While D " + D);
			for (ArrayList<Double> l : D) {
				int ikeys2 = 0;
				for (ArrayList<Double> k : D) {
					if (!l.equals(k)) {
						double distance = Metric(l, k);
						if (min > distance) {
							min = distance;
							index1 = l;
							index2 = k;
							indexkeys1 = keys.get(ikeys1);
							indexkeys2 = keys.get(ikeys2);
							min1 = ikeys1;
							min2 = ikeys2;

						}
					}
					ikeys2 = ikeys2 + 1;

				}
				ikeys1 = ikeys1 + 1;

			}

			keys.remove(indexkeys1);
			keys.remove(indexkeys2);

			D.remove(index1);
			D.remove(index2);
			min = Double.MAX_VALUE;
			// ked je prienik prazna mnozina ma sa pridat do C ?

			System.out.println("D after removed :" + D);
			System.out.println("Keys after removed :" + keys);

			jaggard_matrix.remove(indexkeys1);
			jaggard_matrix.remove(indexkeys2);

			System.out.println("Remove keys " + indexkeys1 + " " + indexkeys2);

			ArrayList<String> pom = union1(indexkeys1, indexkeys2);
			ArrayList<Double> pom2 = computeJaggard(pom, keys);

			System.out.println("POM " + pom);
			System.out.println("POM2" + pom2);

			System.out.println("Jaggard matrix after removed :" + jaggard_matrix);
			System.out.println("MIN1 " + min1);
			System.out.println("MIN2 " + min2);

			for (ArrayList<String> key : jaggard_matrix.keySet()) {
				ArrayList<Double> values = jaggard_matrix.get(key);
				values.remove(min2);
				values.remove(min1);

				jaggard_matrix.put(key, values);

			}

			if (!jaggard_matrix.containsKey(pom)) {
				System.out.println("pom2" + pom2);
				if (!pom.isEmpty()) {
					keys.add(pom);
					jaggard_matrix.put(pom, pom2);
				}
				int k = 0;
				for (ArrayList<String> key : jaggard_matrix.keySet()) {
					ArrayList<Double> values = jaggard_matrix.get(key);
					System.out.println("Matrix in for :" + jaggard_matrix);
					if (key.equals(pom)) {
						values.add((double) 1);
						jaggard_matrix.put(key, values);
					} else {
						if (k < pom2.size()) {
							values.add(pom2.get(k));
							k++;
							jaggard_matrix.put(key, values);
						}
					}

				}

				System.out.println("Matrix :" + jaggard_matrix);
			}


			D = new ArrayList<>();
			for (ArrayList<Double> line : jaggard_matrix.values()) {
				D.add(line);
			}

			System.out.println("D :" + jaggard_matrix);

			C.add(pom);

		}

		Set<ArrayList<String>> output = new HashSet<>();
		output.addAll(C);
		return output;

	}

	private static ArrayList<Double> computeJaggard(ArrayList<String> pom,
			CopyOnWriteArrayList<ArrayList<String>> keys) {

		ArrayList<Double> newDistances = new ArrayList<Double>();
		for (ArrayList<String> key : keys) {
			double ro = 0;

			double intersection = intersection(pom, key);
			double union = union(pom, key);
			double division = intersection / union;

			ro = 1 - division;
			newDistances.add(ro);
		}

		return newDistances;
	}

//	public static Set<ArrayList<String>> rice_siff(Map<String, ArrayList<String>> table) {
//		CopyOnWriteArrayList<ArrayList<String>> C = new CopyOnWriteArrayList<>();
//		for (ArrayList<String> line : table.values()) {
//			C.add(line);
//		}
//
//		System.out.println("RICE SIFF C " + C);
//
//		CopyOnWriteArrayList<ArrayList<String>> D = new CopyOnWriteArrayList<>();
//		D = (CopyOnWriteArrayList<ArrayList<String>>) C.clone();
//		System.out.println("RICE SIFF D " + D);
//		double min = Double.MAX_VALUE;
//		ArrayList<String> index1 = new ArrayList<>();
//		ArrayList<String> index2 = new ArrayList<>();
//		boolean firstIter = true;
//		// ma sa posledny prvok ktory ostane v D pridat do C?
//		while (D.size() > 1) {
//			for (ArrayList<String> l : D) {
//				for (ArrayList<String> k : D) {
//					if (!l.equals(k)) {
//						double distance = JaggardMetric(l, k);
//						if (min > distance) {
//							min = distance;
//							index1 = l;
//							index2 = k;
//						}
//
//					}
//				}
//
//			}
//
//		}
//
//		D.remove(index1);
//		D.remove(index2);
//		min = Double.MAX_VALUE;
//		// ked je prienik prazna mnozina ma sa pridat do C ?
//		ArrayList<String> pom = intersection2(index1, index2);
//		if (!pom.isEmpty()) {
//			D.add(pom);
//		}
//		C.add(pom);
//
//		Set<ArrayList<String>> output = new HashSet<>();
//		output.addAll(C);
//		return output;
//
//	}

	public static Set<ArrayList<String>> rice_siff_without_duplicates(Map<String, ArrayList<String>> table) {
		CopyOnWriteArrayList<ArrayList<String>> C = new CopyOnWriteArrayList<>();
		for (ArrayList<String> line : table.values()) {
			C.add(line);
		}

		CopyOnWriteArrayList<ArrayList<String>> D = new CopyOnWriteArrayList<>();
		D = (CopyOnWriteArrayList<ArrayList<String>>) C.clone();
//		System.out.println("RICE SIFF D " + D);
		D = removeDuplicates(D);
//		System.out.println("RICE SIFF without duplicates D " + D);
		double min = Double.MAX_VALUE;
		ArrayList<String> index1 = new ArrayList<>();
		ArrayList<String> index2 = new ArrayList<>();
		boolean firstIter = true;
		// ma sa posledny prvok ktory ostane v D pridat do C?
		while (D.size() > 1) {
			for (ArrayList<String> l : D) {
				ArrayList<Double> distances = new ArrayList<>();
				for (ArrayList<String> k : D) {
					if (!l.equals(k)) {
						double distance = JaggardMetric(l, k);
						distances.add(distance);
						if (min >= distance) {
							min = distance;
							index1 = l;
							index2 = k;
						}

					} else {
						distances.add((double) 1);
					}

				}

				if (firstIter) {
					jaggard_matrix.put(l, distances);
				}

			}
			firstIter = false;
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

	private static CopyOnWriteArrayList<ArrayList<String>> removeDuplicates(CopyOnWriteArrayList<ArrayList<String>> d) {
		CopyOnWriteArrayList<ArrayList<String>> new_D = new CopyOnWriteArrayList<>();
		for (ArrayList<String> element : d) {
			if (!new_D.contains(element)) {

				new_D.add(element);
			}
		}
		return new_D;
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
		table = generator(4);
		System.out.println("KONTEXT " + table);

//		System.out.println(objectIntersection(table));
//		System.out.println("RICE - SIFF RESULT WITH DUPLICATES " + rice_siff_without_duplicates(table));

		System.out.println("JAGGARD MATRIX " + rice_siff_without_duplicates(table));

		System.out.println(jaggard_matrix);

		System.out.println("RICE - SIFF FUZZY RESULT " + rice_siff_fuzzy(jaggard_matrix));

//		ArrayList<String> a = new ArrayList<String>(Arrays.asList("1", "3"));
//		ArrayList<String> b = new ArrayList<String>(Arrays.asList("3"));
//		ArrayList<String> c = new ArrayList<String>(Arrays.asList("2"));
//		ArrayList<Double> a1 = new ArrayList<Double>(Arrays.asList(1.0, 1.0, 0.5));
//		ArrayList<Double> b2 = new ArrayList<Double>(Arrays.asList(1.0, 0.5, 1.0));
//		ArrayList<Double> c3 = new ArrayList<Double>(Arrays.asList(1.0, 1.0, 1.0));
//		Map<ArrayList<String>, ArrayList<Double>> map = new HashMap<>();
//		map.put(c, c3);
//		map.put(b, b2);
//		map.put(a, a1);

//		System.out.println(map);

	}

}