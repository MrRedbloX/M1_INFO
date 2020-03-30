package minmaxcount;

import java.io.IOException;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class MinMaxCountReducer extends Reducer<Text, MinMaxCountWrapper, Text, MinMaxCountWrapper> {
	private MinMaxCountWrapper res = new MinMaxCountWrapper();
	public void reduce(Text key, Iterable<MinMaxCountWrapper> values, Context context) throws IOException, InterruptedException {
		long sum = 0;
		for(MinMaxCountWrapper mmxw : values) {
			if(this.res.getFirst() == null || mmxw.getFirst().compareTo(this.res.getFirst()) < 0) this.res.setFirst(mmxw.getFirst());
			if(this.res.getLast() == null || mmxw.getLast().compareTo(this.res.getLast()) > 0) this.res.setLast(mmxw.getLast());
			sum += mmxw.getCount();
		}
		this.res.setCount(sum);
		context.write(key, this.res);
	}
 }
