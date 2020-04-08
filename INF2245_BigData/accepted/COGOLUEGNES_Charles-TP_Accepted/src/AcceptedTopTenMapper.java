package accepted;

import java.io.IOException;
import java.util.Calendar;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.IntWritable;

public class AcceptedTopTenMapper extends Mapper<IntWritable, Text, IntWritable, Text> {

    private TreeMap<Integer, Text> map = new TreeMap<Integer, Text>();

    public void map(Text inputKey, Text inputValue, Context context) throws IOException, InterruptedException, IllegalStateException {

        if (inputKey.toString().length() > 0 && inputValue.toString().length() > 0) {
            this.map.put(Integer.parseInt(inputValue.toString()), new Text(inputKey + " " + inputValue));

            if (this.map.size() > 10) {
                this.map.remove(this.map.firstKey());
            }
        }

    }

    protected void cleanup(Context context) throws IOException, InterruptedException {
        for (Integer i : this.map.keySet()) {
            context.write(new IntWritable(i), this.map.get(i));
        }
    }
}