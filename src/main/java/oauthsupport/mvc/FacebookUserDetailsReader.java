package oauthsupport.mvc;

import org.codehaus.jackson.node.ObjectNode;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestOperations;

/**
 * @author Ryan Heaton
 * @author Dave Syer
 */
@Controller
public class FacebookUserDetailsReader implements OAuthUserDetailsReader
{
	@Override
	public UserDetails loadUserFromOauth(RestOperations restTemplate)
	{
		ObjectNode me = restTemplate.getForObject("https://graph.facebook.com/me?fields=name,email,picture",
			ObjectNode.class);
		String fullName = me.get("name").getTextValue();
		String email = me.get("email").getTextValue();
		String picture = me.get("picture").get("data").get("url").getTextValue();

		UserDetails myUserDetails = new UserDetails(email, fullName, false);
		myUserDetails.setPicture(picture);
		return myUserDetails;
	}
}
