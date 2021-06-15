package WeatherAnalysis;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class MapReduceMain {

    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        List<String> fileMap = new LinkedList<>();

        Configuration configuration = new Configuration();

        // 得到执行的任务
        Job job = Job.getInstance(configuration);
        // 入口类
        job.setJarByClass(MapReduceMain.class);

        // job名字
        job.setJobName("weather");

        // job执行是map执行的类
        job.setMapperClass(WeatherMapper.class);
        job.setReducerClass(WeatherReduce.class);

        job.setMapOutputKeyClass(DoubleWritable.class);
        job.setMapOutputValueClass(DoubleWritable.class);

        job.setOutputKeyClass(DoubleWritable.class);
        job.setOutputValueClass(DoubleWritable.class);


        // 使用自定义的排序, 分组
        job.setPartitionerClass(MyPartition.class);
//        job.setSortComparatorClass(MySort.class);
//        job.setGroupingComparatorClass(MyGroup.class);
//        job.setJar("E:\\sxt\\target\\weather.jar");

        //设置 分区数量
        job.setNumReduceTasks(4);
        // **** 使用插件上传data.txt到hdfs/root/usr/data.txt

        //****使得左边为key, 右边为value, 此类默认为  "\t" 可以自定义
        // 或者  config.set("mapreduce.input.keyvaluelinerecordreader.key.value.separator", "\t");
        job.setInputFormatClass(KeyValueTextInputFormat.class);

        // 使用文件
        FileInputFormat.addInputPath(job, new Path("/root/weatherData/"));
//        FileInputFormat.addInputPath(job, new Path("/root/usr/weather.txt"));

        // 使用一个不存在的目录进行
        Path path = new Path("/root/weather");
        // 如果存在删除
        FileSystem fs = FileSystem.get(configuration);
        if (fs.exists(path)) {
            fs.delete(path, true);
        }

        // 输出
        FileOutputFormat.setOutputPath(job, path);

        boolean forCompletion = job.waitForCompletion(true);

        if (forCompletion) {
            System.out.println("success");
        }

    }
}

class WeatherMapper extends Mapper<Text, Text, DoubleWritable, DoubleWritable> {

    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 将键值取出来, 封装为key 每行第一个分隔符"\t"左侧为key, 右侧有value, 传递过来的数据已经切割好了
     */
    @Override
    protected void map(Text key, Text value, Mapper<Text, Text, DoubleWritable, DoubleWritable>.Context context)
            throws IOException, InterruptedException {


        String line = key.toString();
//        System.out.println("key: "+key.toString()+"-------------------------------------");
//        System.out.println("value: "+value.toString()+"-------------------------------------");

        if(line.contains("Time")) return;

        String[] words = line.split(",");
//            String [] pf = words[2].split("\n");
        double temperature = Double.parseDouble(words[2]);
//
//        String str = words[1].replace("\"", "");
////        System.out.println("date: " + str + "------------------------------------------------------");
//        String mon = "";
//        for(int i = 0; i <str.length();i ++){
//            if(str.charAt(i) == '-'){
//                for(int j = i+1;j<str.length();j++){
//                    if(str.charAt(j) != '-') {
//                        mon += str.charAt(j);
//                    }else{
//                        break;
//                    }
//                }
//                break;
//            }
//        }
////        System.out.println("string mon: " + mon + "------------------------------------------------------");
//
////        System.out.println("tem: "+temperature+"-------------------------------------");
////        System.out.println("date: "+words[1].substring(1, words[1].length() - 1)+"-------------------------------------");
////        System.out.println();
////        System.out.println();
////
////        Date date = null;
////        try {
////            String str= words[1].replace("\"", "");
////            date = formatter.parse(str);
////        } catch (ParseException e) {
////            e.printStackTrace();
////        }
////        System.out.println("dateToString: " + date.toString() + " ---------------------------------------------- ");
////        Calendar calendar = Calendar.getInstance();
////        calendar.setTime(date);
//        int month = 1;
//        if(mon != "")
//             month = Integer.parseInt(mon);
////        System.out.println("month: " + month + "  ------------------------------------------------------");
//
//        MyKey mykey = new MyKey();
//        mykey.setId(id);
//        mykey.setMonth(month);
//        mykey.setTemperature(temperature);
//
        context.write(new DoubleWritable(temperature),new DoubleWritable(1L));

    }



}


class WeatherReduce extends Reducer<DoubleWritable, DoubleWritable, DoubleWritable, DoubleWritable> {
    NullWritable nullWritable = NullWritable.get();
    private double max = Double.MIN_VALUE;
    private double min = Double.MAX_VALUE;
    @Override
    protected void reduce(DoubleWritable arg0, Iterable<DoubleWritable> arg1,
                          Reducer<DoubleWritable, DoubleWritable, DoubleWritable, DoubleWritable>.Context arg2)
            throws IOException, InterruptedException {
//        arg2.write(arg0, NullWritable.get());


        if(max < arg0.get() ){
            max = arg0.get();
        }
        if(min > arg0.get()) {
            min = arg0.get();
        }
//        int i = 0;
//        Iterator<DoubleWritable> it = arg1.iterator();
//        while (it.hasNext()) {
//            DoubleWritable doubleWritable = it.next();
////            System.out.println("doubleWritable:  "+doubleWritable.get()+"----------------------");
//            i++;
//            String msg = arg0.getId() + " " + arg0.getMonth() + " " + doubleWritable.get();
//            // key中已经包含需要的结果了
//            if (i == 1) {
////                System.out.println(msg);
////                arg2.write(new Text(msg), NullWritable.get());
//                arg2.write(new Text(msg),new DoubleWritable(doubleWritable.get()));
//                System.out.println(doubleWritable.get());
//            }else if(it.hasNext() == false){
////                System.out.println(msg);
////                arg2.write(new Text(msg), NullWritable.get());
//                arg2.write(new Text(msg),new DoubleWritable(doubleWritable.get()));
//                System.out.println(doubleWritable.get());
//            }
//        }
    }

    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {
        context.write(new DoubleWritable(max), new DoubleWritable(min));
    }


    }
