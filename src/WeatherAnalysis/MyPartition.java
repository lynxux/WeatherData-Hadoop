package WeatherAnalysis;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.mapreduce.lib.partition.HashPartitioner;


public class MyPartition extends HashPartitioner<MyKey, DoubleWritable> {

    @Override
    public int getPartition(MyKey key, DoubleWritable value, int numReduceTasks) {
        // 减去最小的, 更精确
        return ((key.getId()) % numReduceTasks +numReduceTasks) % numReduceTasks;
    }
}
