package sec.project.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sec.project.domain.Signup;
import sec.project.repository.SignupRepository;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SignupController {
	

    @Autowired
    private SignupRepository signupRepository;
	private Connection connection;

    @RequestMapping("/form")
    public String defaultMapping() {
        return "redirect:/login";
    }
    
    @RequestMapping("/login")
    public String create(Model model) {
	    return "redirect:/login";
    }
    
    @RequestMapping("/greeting")
    public ModelAndView hi(ModelAndView modelAndView, @RequestParam String name) {
	modelAndView.addObject("name", name);
	modelAndView.setViewName("greeting");
	return modelAndView;
    }
    
    @RequestMapping("/logged")
    public ModelAndView lolol(ModelAndView modelAndView, @RequestParam String username, @RequestParam String password) throws SQLException{
		    Connection con = DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "");
		    String sql = "SELECT * FROM users WHERE lower(USERNAME) = lower('" + username + "') AND PASSWORD = '" + password + "' ;";
		    ResultSet resultSet = con.createStatement().executeQuery(sql);
		    List lista = new ArrayList<String>();
		    while (resultSet.next()){
			//String name = resultSet.getString("username");
			lista.add("Your username is '" + resultSet.getString("username") + "' and your password is '" + resultSet.getString("password")+ "'";
			//String password = resultSet.getString("email");
		    }
		    
	
	modelAndView.addObject("lista", lista);
	modelAndView.setViewName("logged");
	
		    
		  
		    
	return modelAndView;
		}
    

    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String loadForm() {
        return "form";
    }

    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String submitForm(@RequestParam String name, @RequestParam String address) {
        signupRepository.save(new Signup(name, address));
        return "done";
    }
    
 @RequestMapping(value= "/admin")
 public String admin() {
	return "admin"
		}
    
     
}
