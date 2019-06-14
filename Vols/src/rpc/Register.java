package rpc;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import db.DBConnection;
import db.DBConnectionFactory;

/**
 * Servlet implementation class Register
 */
@WebServlet("/register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DBConnection conn = DBConnectionFactory.getConnection();
		
		try {
			JSONObject inputObj = RpcHelper.readJSONObject(request);
			
			String firstName = inputObj.getString("first_name");
			String lastName = inputObj.getString("last_name");
			String password = inputObj.getString("password");
			String userId = inputObj.getString("user_id");
			
			JSONObject obj = new JSONObject();
			if(conn.registerUser(userId, password, firstName, lastName)) {
				obj.put("status", "OK");
			} else {
				obj.put("status", "User Already Exist");
			}
			
			RpcHelper.writeJsonObject(response, obj);			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}

}
