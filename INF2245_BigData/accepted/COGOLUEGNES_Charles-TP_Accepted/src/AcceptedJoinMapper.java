package accepted;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import xmlutils.XmlUtils;

public class AcceptedJoinMapper extends Mapper<Object, Text, IntWritable, Text> {
    public static enum MapCounters {
        /* v a l e u r s o b t e n u e s s u r Pos ts −100K. xml / s u r Pos ts −1M. xml */
        QUESTIONS_NB, /* v a l e u r = / */
        RESPONSES_NB, /* v a l e u r = / */
        UNKNOW_POST_TYPE, /* v a l e u r = / */
        INVALID_POST_TYPE, /* v a l e u r = / */
        UNKNOW_ANSWER_ID, /* v a l e u r = / */
        UNKNOW_ID, /* v a l e u r = / */
        UNKNOWOWNER_ID, /* v a l e u r = / */
    }

    private IntWritable key;
    private Text out;

    public void map(Object key, Text xmlString, Context context) throws IOException, InterruptedException, NumberFormatException {
		
		Map<String, String> xmlMap = XmlUtils.transformXmlToMap(xmlString.toString());
		
		int postType = Integer.parseInt(xmlMap.get("PostTypeId"));
		
		this.key = new IntWritable(Integer.parseInt(xmlMap.get("Id")));
        if(postType == 1) { //Question
            this.out = new Text("Q "+xmlMap.get("AcceptedAnswerId"));
			context.write(this.key, this.out);
        }
        else if (postType == 2){ //Réponse
            this.out = new Text("R "+this.key+" "+xmlMap.get("OwnerUserId"));
            context.write(this.key, this.out);
        }
	}
}