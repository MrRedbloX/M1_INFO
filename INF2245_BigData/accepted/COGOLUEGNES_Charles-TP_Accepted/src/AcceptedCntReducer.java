package accepted;

import java.io.IOException;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.io.IntWritable;

public class AcceptedCntReducer extends Reducer<Text, Text, Text, IntWritable> {
	public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
		int count = 0;
		for(Text t : values) {
			count += Integer.parseInt(t.toString().split(" ")[1]);
		}
		context.write(key, new IntWritable(count));
	}
 }
