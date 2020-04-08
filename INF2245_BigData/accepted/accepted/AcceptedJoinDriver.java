package accepted;

import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;

public class AcceptedJoinDriver {

  public static void main(String[] args) throws Exception {
    Configuration conf = new Configuration();
    if (args.length != 2) {
      System.err.println(
          "Usage: AcceptedJoinDriver <posts> <out> ");
      System.exit(1);
    }
    FileSystem hdfs= FileSystem.get(new URI("hdfs://hnn:9000"),conf);
    Path output_path= new Path(args[1]);
    hdfs.delete(output_path, true); // supprime le répertoire de sortie
    
    Job job = Job.getInstance(conf, "Accepted Join Driver");
    job.setJarByClass(AcceptedJoinDriver.class);
    
    job.setMapperClass(AcceptedJoinMapper.class);
    job.setInputFormatClass(TextInputFormat.class);
    FileInputFormat.addInputPath(job, new Path(args[0]));
    job.setMapOutputKeyClass(Text.class);
    job.setMapOutputValueClass(Text.class);
    
    job.setReducerClass(AcceptedJoinReducer.class);
    FileOutputFormat.setOutputPath(job, new Path(args[1]));
    job.setOutputFormatClass(SequenceFileOutputFormat.class);
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(IntWritable.class);
    
    job.waitForCompletion(true);
    
  }
}
