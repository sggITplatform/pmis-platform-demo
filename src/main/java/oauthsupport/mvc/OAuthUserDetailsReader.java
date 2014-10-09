package oauthsupport.mvc;

import org.springframework.web.client.RestOperations;

public interface OAuthUserDetailsReader
{
	UserDetails loadUserFromOauth(RestOperations restTemplate);
}