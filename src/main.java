
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class main{
	
	/**
	 * 测试地址 mifanblog.cn:1031
	 * @param args
	 * @throws UnknownHostException
	 * @throws IOException
	 */
    public static void main( String[] args ) throws UnknownHostException, IOException{
    	System.out.println(main.set("czf", "mifan"));
		
    }
    
    /** redis协议的一般形式
     * 
     *  <参数数量> CR LF
		$<参数 1 的字节数量> CR LF
		<参数 1 的数据> CR LF
		...
		$<参数 N 的字节数量> CR LF
		<参数 N 的数据> CR LF
     * @param name 键名
     * @param value 键值
     * @return 内容
     */
    
    public static String set(String name,String value) {
    	try {
			return post("*3\r\n$3\r\nSET\r\n$"+
					name.length()+
					"\r\n"+
					name+
					"\r\n$"+
					value.length()+
					"\r\n"+
					value+
					"\r\n",
					"mifanblog.cn:1031");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
    }
    
    /**
     * 用于建立握手并发送数据包和返回接收的数据包
     * @param raw
     * @param host
     * @return
     * @throws IOException
     */
    
	public static String post(String raw,String host) throws IOException {
		Socket soc=new Socket();
		InetSocketAddress inetSocketAddress = new InetSocketAddress(host.split(":")[0],!host.contains(":")?80:Integer.parseInt(host.split(":")[1]));
		soc.connect(inetSocketAddress,1000);
		PrintWriter pw=new PrintWriter(soc.getOutputStream());
		pw.println(raw);
		pw.flush();
		soc.shutdownOutput();
		BufferedReader br=new BufferedReader(new InputStreamReader(soc.getInputStream()));
		String line=null;
		String toret="";
		while((line=br.readLine())!=null) {
			toret+=line+"\r\n";
		}
		soc.close();
		return toret;
	}
}
