package pagerank;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class PageRankReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

  public static enum ReduceCounters {
    PAGE_RANK, // nombre de mots qui ont été indexés
  };

    public void reduce(Text key, Iterable<IntWritable> values, Context context)
      throws IOException, InterruptedException{

      int sum = 0;
      for (IntWritable value : values) {
        sum += value.get();
      }
      context.write(key, new IntWritable(sum));
      context.getCounter(ReduceCounters.PAGE_RANK).increment(1);
    }
  }
