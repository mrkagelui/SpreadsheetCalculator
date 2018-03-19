import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class SpreadsheetTest {
    private int id;
    private String fileName;
    private static final Map<Integer, String> inputMap = createMap();
    private static Map<Integer, String> createMap() {
        Map<Integer, String> aMap = new HashMap<>();
        aMap.put(0, "0.txt");
//        aMap.put(1, "2.txt");
//        aMap.put(2, "3.txt");
        return aMap;
    }

    public SpreadsheetTest(int i, String filename) {
        id = i;
        fileName = filename;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> prepare(){
        Collection<Object[]> params = new LinkedList<>();
        for (Map.Entry e : inputMap.entrySet()) {
            Object[] oneArr = new Object[] {e.getKey(), e.getValue()};
            params.add(oneArr);
        }
        return params;
    }

    @Before
    public void setUp() {
        try {
            FileInputStream is = new FileInputStream(new File(this.fileName));
            System.setIn(is);
            String outFileName = "out" + File.separator + this.id + ".out.txt";
            PrintStream printStream = new PrintStream(new BufferedOutputStream(
                    new FileOutputStream(outFileName)));
            System.setOut(printStream);

            Spreadsheet.main(new String[0]);
            printStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test(){
        try {
            BufferedReader reader = new BufferedReader(new FileReader("out" +
                    File.separator + this.id + ".out.txt"));
            String oneLine;
            switch (this.id) {
                case 0:
                    oneLine = reader.readLine();
                    assertTrue("Testcase " + id + ": first line should be dimension",
                            oneLine.equalsIgnoreCase("3 2"));
                    for (int i = 0; i < 2; i++) {
                        oneLine = reader.readLine();
                    }
                    assertTrue("Testcase " + id + ": line 3",
                            oneLine.equalsIgnoreCase("20.00000"));
                    break;
//                case 1:
//                    break;
//                case 2:
//                    break;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
