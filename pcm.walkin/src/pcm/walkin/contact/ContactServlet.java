package pcm.walkin.contact;


import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/contact/*")
public class ContactServlet extends HttpServlet {
	public static final String PCM_CLUSTER = "pcm.walkin";
	public static final String PCM_INDEX = "walkin_store";
	public static final String PCM_CONTACT_TYPE = "contact";

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/plain;charset=UTF-8");
		
		PrintWriter out = resp.getWriter();
		out.println("Basic Post 요청에 대한 응답입니다.");
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/plain;charset=UTF-8");
		
		// ElasticSearch Client 객체 생성
		TransportClient esclient = null;
		
		// 클러스터 이름을 직접 지정하도록 설정.
		Settings settings = Settings.settingsBuilder().put("cluster.name", PCM_CLUSTER).build();
		
		// 직접 지정한 클러스터 셋팅을 적용하고 설치된 ElasticSearch에 접속.
		// 주소는 로컬호스트, 포트는 9200
		esclient = TransportClient.builder().settings(settings).build()
				.addTransportAddress(
						new InetSocketTransportAddress(
								InetAddress.getLocalHost(),9200));
		
		// walkin_store 인덱스의 contact 타입에서 id가 1인 document를 가져옵니다.
		GetResponse response = esclient.prepareGet(PCM_INDEX, PCM_CONTACT_TYPE, "1").get();
		
		Map<String, Object> resultMap = response.getSource();
		Gson gsonObj = new Gson();
		String resultStr = gsonObj.toJson(resultMap);
		
		PrintWriter out = resp.getWriter();
		out.println(resultStr);
	}
	
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/plain;charset=UTF-8");
		
		PrintWriter out = resp.getWriter();
		out.println("Basic Put 요청에 대한 응답입니다.");
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/plain;charset=UTF-8");
		
		PrintWriter out = resp.getWriter();
		out.println("Basic Delete 요청에 대한 응답입니다.");
	}

}
