package WeatherAnalysis;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class MySort extends WritableComparator {

    public MySort() {
        super(MaxMin.class,true);
    }

    @Override
    public int compare(WritableComparable a, WritableComparable b) {
//        MaxMin key1 = (MaxMin) a;
//        MaxMin key2 = (MaxMin) b;
//
//        int r1 = Integer.compare(key1.get(), key2.getId());
//
//        if(r1 == 0){
////            int r2 = Integer.compare(key1.getMonth(), key2.getMonth());
//
////            if (r2 == 0) {
//                // 温度降序
//                return - Double.compare(key1.getTemperature(), key2.getTemperature());
////            }else {
////                return r2;
////            }
//        }
//        return r1;
        return  0;
    }
}
