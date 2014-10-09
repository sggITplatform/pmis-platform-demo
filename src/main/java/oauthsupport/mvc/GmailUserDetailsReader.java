package oauthsupport.mvc;

import org.codehaus.jackson.node.ObjectNode;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestOperations;

/**
 * @author Ryan Heaton
 * @author Dave Syer
 */
@Controller
public class GmailUserDetailsReader implements OAuthUserDetailsReader
{
	@Override
	public UserDetails loadUserFromOauth(RestOperations restTemplate)
	{
		ObjectNode me = restTemplate.getForObject("https://www.googleapis.com/oauth2/v1/userinfo", ObjectNode.class);
		String fullName = me.get("name").getTextValue();
		String email = me.get("email").getTextValue();
		String picture = me.get("picture").getTextValue();

		UserDetails myUserDetails = new UserDetails(email, fullName, false);
		myUserDetails.setPicture(picture);
		return myUserDetails;
	}
}
