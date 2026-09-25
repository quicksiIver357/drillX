package main.java.io.quicksiiver.drillx.utility;

import java.util.ArrayList;

public class DoubleList<T1, T2> {
    // this class is like a normal list, except that it takes in two
    // types and both of those types can be stored in the ordered list.

    // instance variables
    // these just hold the values for l1 and l2
    private ArrayList<T1> L1;
    private ArrayList<T2> L2;

    // this boolean ArrayList holds the which list has what value,
    // for example, if the boolean at index 1 of this ArrayList is false, 
    // it would be the first one that has the value.
    private ArrayList<Boolean> keys;

    public DoubleList() {
        L1 = new ArrayList<>();
        L2 = new ArrayList<>();
        keys = new ArrayList<>();
    }

    public void addT1(T1 e) { addT1(e, keys.size()); }
    public void addT1(T1 e, int index) {
        keys.add(index, false);
        L1.add(index, e);
        L2.add(index, null); // this value will never be used, just to keep the sizes nice
    }
    public void addT2(T2 e) { addT2(e, keys.size()); }
    public void addT2(T2 e, int index) {
        keys.add(index, true);
        L1.add(index, null);
        L2.add(index, e);
    }

    public void removeT1(T1 e) {
        int index = L1.indexOf(e);

        keys.remove(index);
        L1.remove(index);
        L2.remove(index);
    }
    public void removeT2(T2 e) {
        int index = L2.indexOf(e);

        keys.remove(index);
        L1.remove(index);
        L2.remove(index);
    }

    // TODO: add getter methods
}
