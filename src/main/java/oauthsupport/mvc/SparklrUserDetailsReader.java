package oauthsupport.mvc;

import org.codehaus.jackson.node.ObjectNode;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestOperations;

/**
 * @author Ryan Heaton
 * @author Dave Syer
 */
@Controller
public class SparklrUserDetailsReader implements OAuthUserDetailsReader
{
	String _userInfoURL;

	public String getUserInfoURL()
	{
		return _userInfoURL;
	}

	@Override
	public UserDetails loadUserFromOauth(RestOperations restTemplate)
	{
		ObjectNode me = restTemplate.getForObject(_userInfoURL, ObjectNode.class);
		String email = me.get("name").getTextValue();

		UserDetails myUserDetails = new UserDetails(email, "", false);
		return myUserDetails;
	}

	public void setUserInfoURL(String userInfoURL)
	{
		_userInfoURL = userInfoURL;
	}
}
