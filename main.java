import java.util.*;

class Box {
    int length;
    int width;
    int height;
    int volume;

    public Box(int length, int width, int height) {
        this.length = length;
        this.width = width;
        this.height = height;
        this.volume = length * width * height;
    }
}

class Pallet {
    int length;
    int width;
    int height;
    Map<Box, Integer> boxes = new HashMap<>();
    int numBoxesFit = 0;

    public Pallet(int length, int width, int height) {
        this.length = length;
        this.width = width;
        this.height = height;
    }

    public boolean canFit(Box box) {
        return box.length <= length && box.width <= width && box.height <= height;
    }

    public void placeBox(Box box) {
        if (boxes.containsKey(box)) {
            boxes.put(box, boxes.get(box) + 1);
        } else {
            boxes.put(box, 1);
        }
        length -= box.length;
        width -= box.width;
        height -= box.height;
        numBoxesFit++;
    }

    public void reset() {
        length = 120;
        width = 80;
        height = 120;
        boxes.clear();
        numBoxesFit = 0;
    }

    public void printPallet() {
        System.out.println("Kutular palete yerleştirildi:");
        for (Map.Entry<Box, Integer> entry : boxes.entrySet()) {
            Box box = entry.getKey();
            int count = entry.getValue();
            System.out.println("(" + box.length + "x" + box.width + "x" + box.height + ") boyutlarındaki kutudan: "  + count + " tane");
        }
        System.out.println("Toplam yerleştirilen kutu sayısı: " + numBoxesFit);
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        List<Box> boxes = new ArrayList<>();

        while (true) {
            System.out.println("Kutunun boyutlarını (Uzunluk Genişlik Yükselik) cinsinden giriniz. Bitirmek için q giriniz.");
            String input = scanner.nextLine();

            if (input.equals("q")) {
                break;
            }

            String[] dimensions = input.split(" ");
            int length = Integer.parseInt(dimensions[0]);
            int width = Integer.parseInt(dimensions[1]);
            int height = Integer.parseInt(dimensions[2]);

            boxes.add(new Box(length, width, height));
        }

        Pallet pallet = new Pallet(120, 80, 120);



        for (Box box : boxes) {
            if (!pallet.canFit(box)) {
                System.out.println("(" + box.length + "x" + box.width + "x" + box.height + ")" + " boyutlarındaki kutu palet için çok büyük." );
            }
            else {
                pallet.placeBox(box);

            }
        }
        
        boxes.sort((b1, b2) -> b2.volume - b1.volume);

        for (Box box : boxes) {
            while (pallet.canFit(box)) {
                pallet.placeBox(box);
            }
        }

        pallet.printPallet();
        scanner.close();
    }
}
