import java.io.IOException;


public class ReverseOctDec {
    public static void main(String[] args) {
        IntList arrInt = new IntList();
        IntList lineNo = new IntList();
        try {
            Scanner sc = new Scanner(System.in);
            while (sc.hasNextInt()) {
                lineNo.add(sc.curLineNo());
                arrInt.add(sc.nextInt());
            }
            lineNo.add(sc.curLineNo() - 1);
            for (int pointer = arrInt.size() - 1; pointer > -1; pointer--) {
                if (lineNo.get(pointer) != lineNo.get(pointer + 1)) {
                    for (int i = 0; i < lineNo.get(pointer + 1) - lineNo.get(pointer); i++) {
                        System.out.println();
                    }
                }
                System.out.print(arrInt.get(pointer) + " ");
            }
            for (int i = 0; i < lineNo.get(0) + 1; i++) {
                System.out.println();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}