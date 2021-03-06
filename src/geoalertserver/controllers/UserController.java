package geoalertserver.controllers;

import java.io.InputStream;

import javax.servlet.annotation.WebServlet;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

import geoalertserver.entities.User;
import geoalertserver.entities.User;
import geoalertserver.services.UserService;

@Path("/v1/user")
@WebServlet("/v1/user/*")
public class UserController {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response denyGetRequest() {
		return Response.status(403).entity("unauthorised GET request").build();
	}

	/**
	 * @param username
	 *            username to authenticate
	 * @param password
	 *            encrypted password to authenticate
	 * @return HTTP response code
	 */
	@Path("/authenticate")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response AuthenticateUser(@FormParam("username") String username, @FormParam("password") String password) {

		User user = new User(username, password);
		return new UserService(user).authenticateUser();
	}

	/**
	 *
	 * @param username
	 *            username
	 * @param password
	 *            password
	 * @param email
	 *            contact email
	 * @param lang
	 *            users preferred language
	 * @return HTTP response code
	 */
	@Path("/register")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response registerUser(@FormParam("username") String username, @FormParam("password") String password,
			@FormParam("contactNumber") String contactNumber, @FormParam("email") String email,
			@FormParam("lang") String lang, @FormParam("securityQuestion") String securityQuestion,
			@FormParam("securityAnswer") String securityAnswer) {

		User user = new User(username, password, email, lang, contactNumber, securityQuestion, securityAnswer);
		return new UserService(user).registerUser();

	}

	@Path("/confirm/email")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response confirmEmail(@FormParam("email") String email) {

		User user = new User();
		user.setEmail(email);
		return new UserService(user).confirmEmail();

	}

	@Path("/save/new/password")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveNewPassword(@FormParam("newPassword") String newPassword, @FormParam("email") String email,
			@FormParam("securityQuestion") String securityQuestion,
			@FormParam("securityAnswer") String securityAnswer) {

		User user = new User();
		user.setPassword(newPassword);
		user.setEmail(email);
		user.setSecurityQuestion(securityQuestion);
		user.setSecurityAnswer(securityAnswer);
		return new UserService(user).saveNewPassword();

	}

	@Path("/confirm/security/answer")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response confirmSecurityAnswer(@FormParam("email") String email, @FormParam("answer") String answer,
			@FormParam("question") String question) {

		User user = new User();
		user.setEmail(email);
		user.setSecurityAnswer(answer);
		user.setSecurityQuestion(question);
		return new UserService(user).confirmSecurityAnswer();

	}

	@Path("/retrieve/security/question")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response retrieveSecurityQuestion(@FormParam("email") String email) {

		User user = new User();
		user.setEmail(email);
		return new UserService(user).retrieveSecurityQuestion();

	}

	@Path("/retrieve/profile/information")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response retrieveProfileInformation(@FormParam("username") String username) {

		User user = new User();
		user.setUsername(username);
		return new UserService(user).retrieveProfileInformation();

	}
	
	@Path("/upload/profile/image")
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadFile(@FormDataParam("username") String username, @FormDataParam("image") InputStream uploadedStream,
			@FormDataParam("image") FormDataContentDisposition fileDetail) {
		
		User user = new User();
		user.setUsername(username);
		return new UserService(user).uploadProfileImage(uploadedStream);
	}
	
	@Path("/retrieve/profile/image")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces("image/png")
	public Response retrieveProfileImage(@FormParam("username") String username) {

		User user = new User();
		user.setUsername(username);
		return new UserService(user).retrieveProfileImage();

	}
	
	@Path("/update/profile/information")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateProfileInformation(@FormParam("username") String username, @FormParam("profileInfo") String profileInfo) {

		String[] array = profileInfo.split(",");
		User user = new User();
		user.setUsername(username);
		user.setFullName(array[0]);
		user.setGender(array[1]);
		user.setDob(array[2]);
		user.setBloodType(array[3]);
		user.setHeight(array[4]);
		user.setWeight(array[5]);
		user.setClothingTop(array[6]);
		user.setClothingBottom(array[7]);
		user.setClothingShoes(array[8]);
		user.setNextOfKinFullName(array[9]);
		user.setNextOfKinRelationship(array[10]);
		user.setNextOfKinContactNumber(array[11]);
		return new UserService(user).updateProfileInformation();

	}
	
	@Path("/update/status")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateStatus(@FormParam("username") String username, @FormParam("status") String status) {
		
		User user = new User();
		user.setUsername(username);
		user.setStatus(status);
		return new UserService(user).updateStatus();

	}
	
	@Path("/retrieve/user/contacts")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response retrieveUserContacts(@FormParam("username") String username) {

		User user = new User();
		user.setUsername(username);
		return new UserService(user).retrieveUserContacts();

	}
	
	@Path("/delete/contact")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteContact(@FormParam("username") String username, @FormParam("contactId") int contactId) {

		User user = new User();
		user.setUsername(username);
		return new UserService(user).deleteContact(contactId);

	}
	
	@Path("/retrieve/pending/contact/requests")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response retrievePendingContactRequests(@FormParam("username") String username) {

		User user = new User();
		user.setUsername(username);
		return new UserService(user).retrievePendingContactRequests();

	}
	
	@Path("/accept/contact/request")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response acceptContactRequest(@FormParam("username") String username, @FormParam("userId") int userId) {

		User user = new User();
		user.setUsername(username);
		return new UserService(user).acceptContactRequest(userId);

	}
	
	@Path("/decline/contact/request")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response declinetContactRequest(@FormParam("username") String username, @FormParam("userId") int userId) {

		User user = new User();
		user.setUsername(username);
		return new UserService(user).declineContactRequest(userId);

	}
	
	@Path("/add/contact")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addContact(@FormParam("username") String username, @FormParam("contactUsername") String contactUsername) {

		User user = new User();
		user.setUsername(username);
		return new UserService(user).addContact(contactUsername);

	}
	
	@Path("/add/notification")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addNotification(@FormParam("username") String username, @FormParam("contactUsername") String contactUsername) {

		User user = new User();
		user.setUsername(username);
		return new UserService(user).addNotification(contactUsername);

	}
	
	@Path("/retreive/notifications")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response retrieveNotifications(@FormParam("username") String username) {

		User user = new User();
		user.setUsername(username);
		return new UserService(user).retrieveNotifications();

	}
	
	@Path("/delete/notification")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteNotification(@FormParam("username") String username, @FormParam("contactUsername") String contactUsername) {

		User user = new User();
		user.setUsername(username);
		return new UserService(user).deleteNotification(contactUsername);

	}
	
	@Path("/retreive/status")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response retrieveProfileStatus(@FormParam("username") String username) {

		User user = new User();
		user.setUsername(username);
		return new UserService(user).retrieveProfileStatus();

	}
	
	@Path("/update/map/view")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateMapView(@FormParam("username") String username, @FormParam("showMap") boolean showMap) {
		
		User user = new User();
		user.setUsername(username);
		user.setShowMap(showMap);
		return new UserService(user).updateMapView();

	}
}
