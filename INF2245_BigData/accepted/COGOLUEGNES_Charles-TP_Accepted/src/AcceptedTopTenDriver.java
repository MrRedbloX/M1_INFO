package accepted;

import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class AcceptedTopTenDriver {

  public static void main(String[] args) throws Exception {
    Configuration conf = new Configuration();
    if (args.length != 2) {
      System.err.println(
          "Usage: AcceptedTopTenDriver <accepted cnt> <out> ");
      System.exit(1);
    }
    FileSystem hdfs= FileSystem.get(new URI("hdfs://hnn:9000"),conf);
    Path output_path= new Path(args[1]);
    hdfs.delete(output_path, true); // supprime le répertoire de sortie
    
    Job job = Job.getInstance(conf, "Accepted Top Ten Driver");
    job.setJarByClass(AcceptedTopTenDriver.class);
    
    job.setInputFormatClass(SequenceFileInputFormat.class);
    FileInputFormat.addInputPath(job, new Path(args[0]));
    job.setMapperClass(AcceptedTopTenMapper.class);
    job.setMapOutputKeyClass(NullWritable.class);
    job.setMapOutputValueClass(Text.class);
    
    job.setNumReduceTasks(1);
    
    job.setReducerClass(AcceptedTopTenReducer.class);
    FileOutputFormat.setOutputPath(job, new Path(args[1]));
    job.setOutputFormatClass(TextOutputFormat.class);
    
    job.waitForCompletion(true);
  }
}
