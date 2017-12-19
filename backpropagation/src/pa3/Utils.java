package pa3;
import java.util.*;

public class Utils {
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
}
