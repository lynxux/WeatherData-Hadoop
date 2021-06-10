package WeatherAnalysis;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class MySort extends WritableComparator {

    public MySort() {
        super(MyKey.class,true);
    }

    @Override
    public int compare(WritableComparable a, WritableComparable b) {
        MyKey key1 = (MyKey) a;
        MyKey key2 = (MyKey) b;

        int r1 = Integer.compare(key1.getId(), key2.getId());

        if(r1 == 0){
//            int r2 = Integer.compare(key1.getMonth(), key2.getMonth());

//            if (r2 == 0) {
                // 温度降序
                return - Double.compare(key1.getTemperature(), key2.getTemperature());
//            }else {
//                return r2;
//            }
        }
        return r1;
    }
}
