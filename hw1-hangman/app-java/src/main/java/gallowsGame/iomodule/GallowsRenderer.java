package src.main.java.gallowsGame.iomodule;

import src.main.java.gallowsGame.Utils.GameDifficulty;
import src.main.java.gallowsGame.Utils.ConstantsGallows;
import java.util.ArrayList;

public class GallowsRenderer {
    private ArrayList<String> arrayListGallows;

    private void initStandartGallows() {
        arrayListGallows.add("   +---+");
        arrayListGallows.add("   |   |");
        arrayListGallows.add("   o   |");
        arrayListGallows.add("       |");
        arrayListGallows.add("  \\|/  |");
        arrayListGallows.add("  \\|   |");
        arrayListGallows.add("   |   |");
        arrayListGallows.add("  /    |");
        arrayListGallows.add("  / \\  |");
        arrayListGallows.add("========");
    }


    public GallowsRenderer() {
        arrayListGallows = new ArrayList<>();
        initStandartGallows();
    }

    public void renderGallows(int count, int maxAttempts) {
        for (int i = 0; i < 8; ++i) {
            handlerGallows(i, count, maxAttempts);
        }
    }

    private void handlerGallows(int i, int count, int maxAttempts) {
        switch (i) {
            case 0:
                System.out.println(arrayListGallows.get(0));
                break;
            case 1:
                System.out.println(arrayListGallows.get(1));
                break;
            case 2:
                if (count > 0) {
                    System.out.println(arrayListGallows.get(2));
                } else {
                    System.out.println(arrayListGallows.get(3));
                }
                break;
            case 3:
                if ((maxAttempts == ConstantsGallows.HARD_GAME && count > 1) || count > 3) {
                    System.out.println(arrayListGallows.get(4));
                } else if (count == 2) {
                    System.out.println(arrayListGallows.get(6));
                } else if (count == 3) {
                    System.out.println(arrayListGallows.get(5));
                } else {
                    System.out.println(arrayListGallows.get(3));
                }
                break;
            case 4:
                if (count > 1) {
                    System.out.println(arrayListGallows.get(6));
                } else {
                    System.out.println(arrayListGallows.get(3));
                }
                break;
            case 5:
                if ((maxAttempts == ConstantsGallows.HARD_GAME && count > 2) || count > 5) {
                    System.out.println(arrayListGallows.get(8));
                } else if (count == 5) {
                    System.out.println(arrayListGallows.get(7));
                } else {
                    System.out.println(arrayListGallows.get(3));
                }
                break;
            case 6:
                System.out.println(arrayListGallows.get(3));
                break;
            case 7:
                System.out.println(arrayListGallows.get(9));
                break;
            default:
                System.out.println("none");
                break;
        }
    }
}
