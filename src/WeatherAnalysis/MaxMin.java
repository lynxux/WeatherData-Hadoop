package WeatherAnalysis;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class MaxMin  implements WritableComparable<MaxMin > {

//    private int id;
//    private int month;
//    private double temperature;
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public int getMonth() {
//        return month;
//    }
//
//    public void setMonth(int month) {
//        this.month = month;
//    }
//
//    public double getTemperature() {
//        return temperature;
//    }
//
//    public void setTemperature(double temperature) {
//        this.temperature = temperature;
//    }
//
//    @Override
//    public void write(DataOutput dataOutput) throws IOException {
//        dataOutput.writeInt(id);
//        dataOutput.writeInt(month);
//        dataOutput.writeDouble(temperature);
//    }
//
//    @Override
//    public void readFields(DataInput dataInput) throws IOException {
//        this.id = dataInput.readInt();
//        this.month = dataInput.readInt();
//        this.temperature = dataInput.readDouble();
//    }
//
//    @Override
//    public int compareTo(MyKey o) {
//        int c1 = Integer.compare(this.id, o.getId());
////        int c1 = this.id.compareTo(o.getId());
//        if (c1 == 0) {
//            int c2 = Integer.compare(this.month, o.getMonth());
//            if (c2 == 0) {
//                return Double.compare(this.temperature, o.getTemperature());
//            }
//        }
//        return 1;
//    }

    private double max;
    private double min;

    public void readFields(DataInput in) throws IOException {
        max = in.readDouble();
        min = in.readDouble();
    }

    public void write(DataOutput out) throws IOException {
        out.writeDouble(max);
        out.writeDouble(min);
    }

    public int compareTo(MaxMin o) {
        return 0;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    @Override
    public String toString() {
        return "MaxMin [max=" + max + ", min=" + min + "]";
    }

    public MaxMin() {
        super();
    }

    public MaxMin(double max, double min) {
        //super();
        this.max = max;
        this.min = min;
    }
}
