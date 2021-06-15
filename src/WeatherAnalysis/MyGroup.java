package WeatherAnalysis;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class MyGroup extends WritableComparator {

    public MyGroup() {
        super(MaxMin.class, true);
    }

    /**
     * 年, 月相同, 则为一组
     */
    @Override
    public int compare(WritableComparable a, WritableComparable b) {
//        MaxMin key1 = (MaxMin) a;
//        MaxMin key2 = (MaxMin) b;
//
//        int r1 = Integer.compare(key1.getId(), key2.getId());
////        if (r1 == 0) {
////            return Integer.compare(key1.getMonth(), key2.getMonth());
////        }
//        return r1;
        return 1;
    }
}
