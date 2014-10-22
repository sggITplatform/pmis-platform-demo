package oauthsupport.mvc;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

public class OAuthAuthenticationSuccessHandler
{
	String _registeredView;

	String _unregisteredView;

	public String getRegisteredView()
	{
		return _registeredView;
	}

	public String getUnregisteredView()
	{
		return _unregisteredView;
	}

	public void handle(HttpServletRequest arg0, HttpServletResponse arg1, UserDetails oauthUser) throws IOException,
			ServletException
	{
		UserDetails localUser = new UserDetails(oauthUser.getUsername(), "", true);
		PreAuthenticatedAuthenticationToken authentication = new PreAuthenticatedAuthenticationToken(localUser, "",
				localUser.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		arg1.sendRedirect(String.format("%s%s", arg0.getContextPath(), _registeredView));
	}

	public void setRegisteredView(String registeredView)
	{
		_registeredView = registeredView;
	}

	public void setUnregisteredView(String unregisteredView)
	{
		_unregisteredView = unregisteredView;
	}
}
