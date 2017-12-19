package pa1;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

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

  // Find the number of occurrences a class element in a Data Point array
	public static HashMap<String, Integer> countStringOccurences(DataPoint[] strArray) {
		HashMap<String, Integer> countMap = new HashMap<String, Integer>();
		for (DataPoint fdt : strArray) {
			if (!countMap.containsKey(fdt.klass)) {
				countMap.put(fdt.klass, 1);
			} else {
				Integer count = countMap.get(fdt.klass);
				count = count + 1;
				countMap.put(fdt.klass, count);
			}
		}
		return countMap;
	}
}
