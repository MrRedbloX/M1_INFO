package accepted;

import java.io.IOException;
import java.util.ArrayList;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class AcceptedJoinReducer extends Reducer<IntWritable, Text, IntWritable, Text> {

    private ArrayList<Text> A;
    private ArrayList<Text> B;

    public void reduce(IntWritable inputKey, Iterable<Text> inputValues, Context context) throws IOException, InterruptedException {
        this.A = new ArrayList<Text>();
        this.B = new ArrayList<Text>();

        for (Text in : inputValues) {
            if (in.charAt(0) == 'Q') {
                this.A.add(new Text("0"));
            } else if (in.charAt(0) == 'R') {
                this.B.add(new Text(in.toString().substring(2)));
            }
        }
        if (!this.A.isEmpty() && !this.B.isEmpty()) {
            for (Text a : this.A){
                for (Text b : this.B){
                    String[] tuple = b.toString().split("\t");
                    context.write(new IntWritable(Integer.parseInt(tuple[0])), new Text(tuple[1]+" 1"));
                }
            }
        }
    }
}