
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class main{
	
	/**
	 * ���Ե�ַ mifanblog.cn:1031
	 * @param args
	 * @throws UnknownHostException
	 * @throws IOException
	 */
    public static void main( String[] args ) throws UnknownHostException, IOException{
    	System.out.println(main.set("czf", "mifan"));
		
    }
    
    /** redisЭ���һ����ʽ
     * 
     *  <��������> CR LF
		$<���� 1 ���ֽ�����> CR LF
		<���� 1 ������> CR LF
		...
		$<���� N ���ֽ�����> CR LF
		<���� N ������> CR LF
     * @param name ����
     * @param value ��ֵ
     * @return ����
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
     * ���ڽ������ֲ��������ݰ��ͷ��ؽ��յ����ݰ�
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
