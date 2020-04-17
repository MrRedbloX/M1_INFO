package pagerank;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class PageRankMapper extends Mapper<Text, TupleWritable, Text, TupleWritable> {
	public static enum MapCounters {
		PAGE_CNT
	}

	public void map(Text key, TupleWritable value, Context context) throws IOException, InterruptedException {
		context.getCounter(MapCounters.PAGE_CNT).increment(1);
		String[] val = value.get();
		String[] valTmp = new String[val.length-1];
		System.arraycopy(val, 1, valTmp, 0, valTmp.length);
		ArrayList<String> links = new ArrayList<String>(Arrays.asList(valTmp));
		int totalLinks = links.size();
		for(String link : links) {
			context.write(new Text(link), new TupleWritable(key.toString(), "1.0", String.valueOf(totalLinks)));
		}
		context.write(key, value);
	}
}
