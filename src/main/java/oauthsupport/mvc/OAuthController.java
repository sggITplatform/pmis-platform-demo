package oauthsupport.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestOperations;

@Controller
public class OAuthController
{
	private OAuthAuthenticationSuccessHandler _authenticationSuccessHandler;

	public OAuthAuthenticationSuccessHandler getAuthenticationSuccessHandler()
	{
		return _authenticationSuccessHandler;
	}

	public void setAuthenticationSuccessHandler(OAuthAuthenticationSuccessHandler authenticationSuccessHandler)
	{
		_authenticationSuccessHandler = authenticationSuccessHandler;
	}

	public OAuthUserDetailsReader getUserDetailsReader()
	{
		return _userDetailsReader;
	}

	public void setUserDetailsReader(OAuthUserDetailsReader userDetailsReader)
	{
		_userDetailsReader = userDetailsReader;
	}

	private OAuthUserDetailsReader _userDetailsReader;

	private RestOperations _restTemplate;

	public OAuthController()
	{
		super();
	}

	public RestOperations getRestTemplate()
	{
		return _restTemplate;
	}

	public void setRestTemplate(RestOperations restTemplate)
	{
		_restTemplate = restTemplate;
	}

	@RequestMapping
	public void handleRequest(HttpServletRequest arg0, HttpServletResponse arg1) throws Exception
	{
		SecurityContextHolder.getContext().setAuthentication(new PreAuthenticatedAuthenticationToken("none", ""));
		UserDetails ud = _userDetailsReader.loadUserFromOauth(getRestTemplate());
		getAuthenticationSuccessHandler().handle(arg0, arg1, ud);
	}

}