package accepted;

import java.io.IOException;
import java.util.TreeMap;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.io.IntWritable;

public class AcceptedTopTenReducer extends Reducer<IntWritable, Text, IntWritable, Text> {

    private TreeMap<Integer, Text> map = new TreeMap<Integer, Text>();

    public void reduce(IntWritable inputKey, Iterable<Text> inputValues, Context context) throws IOException, InterruptedException {

        for (Text t : inputValues) {
            String[] in = t.toString().split(" ");

            this.map.put(Integer.parseInt(in[1]), new Text(t));

            if (this.map.size() > 10) {
                this.map.remove(this.map.firstKey());
            }
        }

        for (Integer i : this.map.descendingMap().keySet()){
            context.write(new IntWritable(i), this.map.get(i));
        }
    }
}