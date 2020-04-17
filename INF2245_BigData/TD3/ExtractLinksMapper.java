package pagerank;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class ExtractLinksMapper extends Mapper<Text, Text, Text, TupleWritable> {
	private static Pattern pattern = Pattern.compile("\\[\\[[^\\]]+\\]\\]");

	public static enum MapCounters {
		PAGE_CNT,
		LINK_CNT,
	}
	
	public void map(Text key, Text value, Context context) throws IOException, InterruptedException {
		context.getCounter(MapCounters.PAGE_CNT).increment(1);
		ArrayList<String> list = new ArrayList<String>();
		list.add("1.0");
		Matcher matcher = pattern.matcher(value.toString());
		while(matcher.find()) {
			String link = matcher.group().toLowerCase();
			if (pattern.matcher(link).matches()) {
				list.add(link);
				context.getCounter(MapCounters.LINK_CNT).increment(1);
			}
		}
		context.write(key, new TupleWritable(list.toArray(new String[0])));
	}
}
