package pcm.walkin.file;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

@WebServlet("/file/*")
public class FileServlet extends HttpServlet {

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/plain;charset=UTF-8");
		
		process(req, resp);
		
		//PrintWriter out = resp.getWriter();
		//out.println("File Post 요청에 대한 응답입니다.");
	}
	
	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/plain;charset=UTF-8");
		
		process(req, resp);
		
		//PrintWriter out = resp.getWriter();
		//out.println("File Get 요청에 대한 응답입니다.");
	}
	
	protected void process(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String root = req.getSession().getServletContext().getRealPath("/");
        String pathname = root + "uploadDirectory";
 
        File f = new File(pathname);
        if (!f.exists()) {
            // 폴더가 존재하지 않으면 폴더 생성
            f.mkdirs();
        }
 
        String encType = "UTF-8";
        int maxFilesize = 5 * 1024 * 1024;
 
        // MultipartRequest(request, 저장경로[, 최대허용크기, 인코딩케릭터셋, 동일한 파일명 보호 여부])
        MultipartRequest mr = new MultipartRequest(req, pathname, maxFilesize,
                encType, new DefaultFileRenamePolicy());
 
        File file1 = mr.getFile("filename1");
        File file2 = mr.getFile("filename2");
        //System.out.println(file1); // 첨부된 파일의 전체경로
        //System.out.println(file2);
 
        //System.out.println(req.getParameter("title")); // null
        //System.out.println(mr.getParameter("title")); // 입력된 문자
    }
}
