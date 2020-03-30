package minmaxcount;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.hadoop.io.Writable;

public class MinMaxCountWrapper implements Writable{
  private Date first = null;
  private Date last = null;
  private long count = 0;
  private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");

  public Date getFirst(){
    return this.first;
  }

  public Date getLast(){
    return this.last;
  }

  public long getCount(){
    return this.count;
  }

  public SimpleDateFormat getDateFormat(){
    return this.sdf;
  }

  public void setFirst(Date newFirst){
    this.first = newFirst;
  }

  public void setLast(Date newLast){
    this.last = newLast;
  }

  public void setCount(long newCount){
    this.count = newCount;
  }

  public void readFields(DataInput in) throws IOException {
    	this.first = new Date(in.readLong());
    	this.last = new Date(in.readLong());
    	this.count = in.readLong();
    }

  public void write(DataOutput out) throws IOException {
  	out.writeLong(this.first.getTime());
  	out.writeLong(this.last.getTime());
  	out.writeLong(this.count);
  }

  public String toString() {
  	return sdf.format(this.first) + "\t" + sdf.format(this.last) + "\t" + this.count;
  }
}
