package WeatherAnalysis;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class MyKey implements WritableComparable<MyKey> {

    private int id;
    private int month;
    private double temperature;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(id);
        dataOutput.writeInt(month);
        dataOutput.writeDouble(temperature);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.id = dataInput.readInt();
        this.month = dataInput.readInt();
        this.temperature = dataInput.readDouble();
    }

    @Override
    public int compareTo(MyKey o) {
        int c1 = Integer.compare(this.id, o.getId());
//        int c1 = this.id.compareTo(o.getId());
        if (c1 == 0) {
            int c2 = Integer.compare(this.month, o.getMonth());
            if (c2 == 0) {
                return Double.compare(this.temperature, o.getTemperature());
            }
        }
        return 1;
    }
}
