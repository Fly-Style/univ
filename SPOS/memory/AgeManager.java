import javafx.util.Pair;

import java.util.*;
import java.util.BitSet;
import java.util.stream.Collectors;

public class AgeManager {
    private List<Pair<BitSet,Page>> pageList;

    public AgeManager(Vector pages) {
        Vector<Page> vector = new Vector<>(pages);
        pageList = vector.stream().map(o -> new Pair<>(new BitSet(8), o))
                .collect(Collectors.toList());

        pageList.forEach(bitSetPagePair ->{
            for(int i = 0;i < 8; i++)
                bitSetPagePair.getKey().set(0,i);
        });
    }

    public void nextStep() {
        pageList.forEach(bitSetPagePair -> {
            BitSet tempBitSet = bitSetPagePair.getKey().get(1, Math.max(1, bitSetPagePair.getKey().length()));
            tempBitSet.set(0,bitSetPagePair.getValue().R);
            bitSetPagePair = new Pair<>(tempBitSet, bitSetPagePair.getValue());
        });
    }

    public int getPageIndexToDelete() {
        int index = 0;
        boolean isInit = false;
        BitSet minimum = new BitSet(8);

        for (Pair<BitSet,Page> iterator : pageList) {

            if(!isInit && iterator.getValue().inMemTime > 0) {
                index = iterator.getValue().id;
                minimum = iterator.getKey();
                isInit = !isInit;
            }

            if(Util.compare(iterator.getKey(),minimum) == -1 && iterator.getValue().inMemTime > 0) {
                minimum = iterator.getKey();
                index = iterator.getValue().id;
                System.out.println(index);
            }
        }
        System.out.print(index);
        return index;
    }
}
