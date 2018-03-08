package com.plan.utils;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.plan.bll.wf.AddUserProfileBll;
import com.plan.dal.dao.Status;
import com.plan.dal.dao.UserProfile;

public class JsonParserServlet extends HttpServlet {
	 
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	System.out.println("JsonParserServlet invoked");
        response.setContentType("application/json");       
        Gson gson = new Gson();
 
        try {
            StringBuilder sb = new StringBuilder();
            String s;
            while ((s = request.getReader().readLine()) != null) {
                sb.append(s);
            }
            System.out.println("Received Json " + sb.toString());
            
            UserProfile student = (UserProfile) gson.fromJson(sb.toString(), UserProfile.class);
 
            Status status = new Status();
            if (new AddUserProfileBll().addMobileUserProfile(student)) {
                status.setSuccess(true);
                status.setDescription("success");
            } else {
                status.setSuccess(false);
                status.setDescription("failure");
            }
            response.getOutputStream().print(gson.toJson(status));
            response.getOutputStream().flush();
        } catch (Exception ex) {
            ex.printStackTrace();
            Status status = new Status();
            status.setSuccess(false);
            status.setDescription(ex.getMessage());
            response.getOutputStream().print(gson.toJson(status));
            response.getOutputStream().flush();
        }
    }
 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
