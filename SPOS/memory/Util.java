/**
 * Created by proskur on 25.03.16.
 */
import java.util.BitSet;

public final class Util {
    private Util() {
    }

    public static BitSet increment(BitSet set, int totalBits) {
        boolean carry = true;
        int place = totalBits - 1;
        while (carry && place >= 0) {
            carry = set.get(place);
            set.set(place, !set.get(place));
            place--;
        }
        return set;
    }

    public static int toInteger(BitSet set, int totalBits) {
        int acc = 0;
        for (int i = 0; i < totalBits; i++) {
            acc += ((set.get(totalBits - i - 1)) ? 1 : 0) << i;
        }
        return acc;
    }

    public static BitSet toBitSet(int value, int totalBits) {
        int idx = totalBits - 1;
        BitSet result = new BitSet();
        while (value > 0) {
            result.set(idx--, value % 2 == 1);
            value /= 2;
        }
        return result;
    }

    public static boolean equalsBitSet(BitSet a, BitSet b, int totalBits) {
        for (int i = 0; i < totalBits; i++) {
            boolean ca = a.get(i);
            boolean cb = b.get(i);
            if (ca != cb)
                return false;
        }
        return true;
    }

    public static BitSet copyBitSet(BitSet set, int totalBits) {
        BitSet newset = new BitSet();
        for (int i = 0; i < totalBits; i++) {
            newset.set(i, set.get(i));
        }
        return newset;

    }

    public static int compare(BitSet lhs, BitSet rhs) {
        if (lhs.equals(rhs)) return 0;
        BitSet xor = (BitSet)lhs.clone();
        xor.xor(rhs);
        int firstDifferent = xor.length()-1;
        return rhs.get(firstDifferent) ? 1 : -1;
    }
}
