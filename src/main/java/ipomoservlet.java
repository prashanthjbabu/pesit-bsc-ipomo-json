import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.*;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.*;
import java.util.*;
import java.net.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.w3c.dom.*;
public class ipomoservlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
      //  resp.getWriter().print("Hello from Java!\n");
      try{
                      resp.setContentType("text/xml");
    PrintWriter out=resp.getWriter();
   String usn=req.getParameter("usn");
   Date d=new Date();
       System.out.println("Request for USN "+usn+" at time "+d);
  	//String usn="1pe09cs059";
		//String cpass=req.getParameter("cpass");
		ipomo2 ip=new ipomo2();
		ip.getattn(usn);
		//out.println("TEST="+ip.subj[0]);
		//start tree build
		JSONObject obj = new JSONObject();
	obj.put("college",ip.college);
	obj.put("name",ip.name);
	obj.put("course",ip.course);
	obj.put("usn",ip.rollno);
	obj.put("college",ip.college);
	obj.put("semester",ip.semester);
	obj.put("section",ip.section);
	out.println(obj.toJSONString());

    }catch(Exception e)
    {
    System.out.println("some gudbad happened.. "+e);
    }
    
    }

    public static void main(String[] args) throws Exception{
        Server server = new Server(Integer.valueOf(System.getenv("PORT")));
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);
        context.addServlet(new ServletHolder(new ipomoservlet()),"/*");
        server.start();
        server.join();   
    }
}
class ipomo2
{
static String subj[]=new String[20];
static String attn[]=new String[20];
static String total[]=new String[20];
static String percent[]=new String[20];
static String line;
static String college;
static String name;
static String course;
static String rollno;
static String semester;
static String section;

static int ctr;
public static void getattn(String usn)
{
try {
CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
    // Construct data
    String data = URLEncoder.encode("userid", "UTF-8") + "=" + URLEncoder.encode(usn, "UTF-8");
    data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode("003", "UTF-8");
    // Send data
    URL url = new URL("http://ipomo.in/IpomoStudentLoginServer/IpomoStudentServlet");
    URLConnection conn = url.openConnection();
    conn.setDoOutput(true);
    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
    wr.write(data);
    wr.flush();
    // Get the response
    BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
    String line;
//System.out.println("*******************************");
//send stuff
url = new URL("http://ipomo.in/IpomoStudentLoginServer/studentInfo.jsp");
  conn = url.openConnection();
    conn.setDoOutput(true);
 rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
     
    while ((line = rd.readLine()) != null) {
        // Process line...

if(line.contains("temp ="))
{
//System.out.println("INHERE");
wr.close();
    rd.close();
parse(line);
//return parse(line);
}
if(line.contains("var nicename"))
{
line=line.substring(line.indexOf('"')+1,line.lastIndexOf('"'));
//System.out.println("LINE="+line);
college=line;
}
if(line.contains("var name"))
{
line=line.substring(line.indexOf('"')+1,line.lastIndexOf('"'));
//System.out.println("LINE="+line);
name=line;
}mport org.json.simple.JSONArray;
import org.json.simple.JSONObject;
if(line.contains("var course"))
{
line=line.substring(line.indexOf('"')+1,line.lastIndexOf('"'));
//System.out.println("LINE="+line);
course=line;
}
if(line.contains("var rollno"))
{
line=line.substring(line.indexOf('"')+1,line.lastIndexOf('"'));
//System.out.println("LINE="+line);
rollno=line;
}
if(line.contains("var semester"))
{
line=line.substring(line.indexOf('"')+1,line.lastIndexOf('"'));
//System.out.println("LINE="+line);
semester=line;
}
if(line.contains("var section"))
{
line=line.substring(line.indexOf('"')+1,line.lastIndexOf('"'));
//System.out.println("LINE="+line);
section=line;
} 
   }
//send stuff ends

    


} catch (Exception e) {
}
    
}
public static void parse(String data) throws IOException
{
int firstbox=data.indexOf('[');
data=data.substring(++firstbox);
int lastbox=data.lastIndexOf(']');
data=data.substring(0,lastbox);
/*System.out.println(data);
data=data.replace("[","");
data=data.replace("]","");
System.out.println(data);*/
StringTokenizer token=new StringTokenizer(data,"],[");
//System.out.println(data);
ctr=0;
while(token.hasMoreTokens())
{
subj[ctr]=token.nextToken();
subj[ctr]=subj[ctr].substring(1,subj[ctr].length()-1);
total[ctr]=token.nextToken();
total[ctr]=total[ctr].substring(1,totalmport org.json.simple.JSONArray;
import org.json.simple.JSONObject;[ctr].length()-1);
attn[ctr]=token.nextToken();
attn[ctr]=attn[ctr].substring(1,attn[ctr].length()-1);
percent[ctr]=token.nextToken();
percent[ctr]=percent[ctr].substring(1,percent[ctr].length()-1);
//System.out.println(percent[ctr]);
ctr++;
}
}
/*public static void main(String args[]) throws IOException
{
getattn(args[0]);
for(int i=0;i<ctr;i++)
{
System.out.println(subj[i]+"-"+attn[i]+"-"+total[i]+"-"+percent[i]);
}

//System.out.println(getattn("1pe09cs061"));
}
*/

}
