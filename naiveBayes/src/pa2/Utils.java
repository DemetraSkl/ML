package pa2;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;
import java.util.Iterator;


public class Utils {

	// Find the number of occurrences a string element in a String array
	public static HashMap<String, Integer> countStringOccurences(String[] strArray) {
		HashMap<String, Integer> countMap = new HashMap<String, Integer>();
		for (String string : strArray) {
			if (!countMap.containsKey(string)) {
				countMap.put(string, 1);
			} else {
				Integer count = countMap.get(string);
				count = count + 1;
				countMap.put(string, count);
			}
		}
		return countMap;
	}

	public static void info(Object str) {
		System.out.println(str);
	}

	public static void error(Object str) {
		System.err.println(str);
	}


	public static void printHashSetContents(Set<String> set) {
		Iterator iterator = set.iterator();
		Utils.info("============");

		while (iterator.hasNext()) {
			Object val = iterator.next();
			Utils.info(val);
		}
	}

	public static void printSetContents(Set<Document> set) {
		Iterator iterator = set.iterator();
		Utils.info("============");

		while (iterator.hasNext()) {
			Object val = iterator.next();
			Utils.info(val);
		}
	}





}
