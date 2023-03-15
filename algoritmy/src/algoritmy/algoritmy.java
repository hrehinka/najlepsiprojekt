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

	public static Set<ArrayList<String>> rice_siff(CopyOnWriteArrayList<ArrayList<String>> C) {
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

	public static CopyOnWriteArraySet<ArrayList<String>> objectIntersection(Map<String, ArrayList<String>> table,
			CopyOnWriteArraySet<ArrayList<String>> C) {
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
		// object intersection inputs
		Map<String, ArrayList<String>> table = new HashMap<>();
		table.put("a", new ArrayList<String>(Arrays.asList("1", "4", "5")));
		table.put("b", new ArrayList<String>(Arrays.asList("2", "5", "6")));
		table.put("c", new ArrayList<String>(Arrays.asList("2", "3", "4")));
		table.put("d", new ArrayList<String>(Arrays.asList("1", "2", "4", "6")));
		table.put("e", new ArrayList<String>(Arrays.asList("5", "6")));
		CopyOnWriteArraySet<ArrayList<String>> C = new CopyOnWriteArraySet<>();
		ArrayList<String> c = new ArrayList<String>(Arrays.asList("1", "2", "3", "4", "5", "6"));
		C.add(c);
		System.out.println(objectIntersection(table, C));

		Map<String, ArrayList<String>> table2 = new HashMap<>();
		table2.put("x", new ArrayList<String>(Arrays.asList("1", "2")));
		table2.put("y", new ArrayList<String>(Arrays.asList("2", "4")));
		table2.put("z", new ArrayList<String>(Arrays.asList("1", "3")));
		table2.put("w", new ArrayList<String>(Arrays.asList("2", "3", "4")));
		CopyOnWriteArraySet<ArrayList<String>> C2 = new CopyOnWriteArraySet<>();
		ArrayList<String> c2 = new ArrayList<String>(Arrays.asList("1", "2", "3", "4"));

		C2.add(c2);
		System.out.println(objectIntersection(table2, C2));

		Map<String, ArrayList<String>> table3 = new HashMap<>();
		table3.put("a", new ArrayList<String>(Arrays.asList("M", "F", "AJ")));
		table3.put("b", new ArrayList<String>(Arrays.asList("F", "CH", "B", "SJ")));
		table3.put("c", new ArrayList<String>(Arrays.asList("AJ", "SJ")));
		table3.put("d", new ArrayList<String>(Arrays.asList("F", "CH", "B", "AJ", "SJ")));
		table3.put("e", new ArrayList<String>(Arrays.asList("CH", "B", "AJ")));
		CopyOnWriteArraySet<ArrayList<String>> C3 = new CopyOnWriteArraySet<>();
		ArrayList<String> c3 = new ArrayList<String>(Arrays.asList("M", "F", "CH", "B", "AJ", "SJ"));
		C3.add(c3);
		System.out.println(objectIntersection(table3, C3));

		
		// rice-siff algorithm input
		ArrayList<String> lista = new ArrayList<String>(Arrays.asList("1", "4", "5"));
		ArrayList<String> listb = new ArrayList<String>(Arrays.asList("2", "5", "6"));
		ArrayList<String> listc = new ArrayList<String>(Arrays.asList("2", "3", "4"));
		ArrayList<String> listd = new ArrayList<String>(Arrays.asList("1", "2", "4", "6"));
		ArrayList<String> liste = new ArrayList<String>(Arrays.asList("5", "6"));
		CopyOnWriteArrayList<ArrayList<String>> list = new CopyOnWriteArrayList<>();
		list.add(lista);
		list.add(listb);
		list.add(listc);
		list.add(listd);
		list.add(liste);

		System.out.println(rice_siff(list));

	}

}
