package rank;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class PageRankReducer  extends Reducer<Text, TupleWritable, Text, TupleWritable> {
	public enum ReduceCounters {
		PAGE_RANK,
	}

	public void reduce(Text key, Iterable<TupleWritable> values, Context context) throws IOException, InterruptedException{
		context.getCounter(ReduceCounters.PAGE_RANK).increment(1);
		ArrayList<String> links = null;
		double sum = 0;
		String page = "";
		for(TupleWritable value : values){
			if(value.get(0).compareTo("1.0") == 0){
				String[] val = value.get();
				String[] valTmp = new String[val.length-1];
				System.arraycopy(val, 1, valTmp, 0, valTmp.length);
				links = new ArrayList<String>(Arrays.asList(valTmp));
			}
			else{
				try{
					page = value.get(0);
					sum += Double.valueOf(value.get(1))/Integer.valueOf(value.get(2));
				}
				catch(NumberFormatException nfe) {
					nfe.printStackTrace();
				}
			}
		}
		double rank = sum * PageRankMR.D_FACTOR + (1 - PageRankMR.D_FACTOR);
		if(links != null && sum != 0.0) {
			links.add(0, String.valueOf(rank));
			context.write(new Text(page), new TupleWritable(links.toArray(new String[0])));
		}
  }
}
