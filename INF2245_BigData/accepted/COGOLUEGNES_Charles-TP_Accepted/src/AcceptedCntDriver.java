package accepted;

import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;

public class AcceptedCntDriver {

  public static void main(String[] args) throws Exception {
    Configuration conf = new Configuration();
    if (args.length != 2) {
      System.err.println(
          "Usage: AcceptedCntDriver <accepted> <out> ");
      System.exit(1);
    }
    FileSystem hdfs= FileSystem.get(new URI("hdfs://hnn:9000"),conf);
    Path output_path= new Path(args[1]);
    hdfs.delete(output_path, true); // supprime le répertoire de sortie
    
    Job job = Job.getInstance(conf, "Accepted Cnt Driver");
    job.setJarByClass(AcceptedCntDriver.class);
    
    // identity mapper
    job.setInputFormatClass(SequenceFileInputFormat.class);
    FileInputFormat.addInputPath(job, new Path(args[0]));
    
    //job.setCombinerClass(AcceptedCntReducer.class);
    job.setReducerClass(AcceptedCntReducer.class);
    FileOutputFormat.setOutputPath(job, new Path(args[1]));
    job.setOutputFormatClass(SequenceFileOutputFormat.class);
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(IntWritable.class);
    
    job.waitForCompletion(true);
  }
}
