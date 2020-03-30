package minmaxcount;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class MinMaxCountMapper extends Mapper<Object, Text, Text, MinMaxCountWrapper> {

	private Text outUserId = new Text();
	private MinMaxCountWrapper outWrapper = new MinMaxCountWrapper();
	private SimpleDateFormat sdf = outWrapper.getDateFormat();

	public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
		Map<String, String> parsed = xmlutils.XmlUtils.transformXmlToMap(value.toString());
		String creaDate = parsed.get("CreationDate");
		String userId = parsed.get("UserId");
		if(creaDate != null && userId != null){
			Date creationDate = null;
			try {
				creationDate = this.sdf.parse(creaDate);
			}
      catch (ParseException e){
				e.printStackTrace();
			}
			this.outWrapper.setFirst(creationDate);
			this.outWrapper.setLast(creationDate);
			this.outWrapper.setCount(1);
			this.outUserId.set(userId);
			context.write(outUserId, outWrapper);
		}
	}
}
