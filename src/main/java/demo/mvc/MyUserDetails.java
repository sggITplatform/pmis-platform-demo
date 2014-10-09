package demo.mvc;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;

public class MyUserDetails implements UserDetails
{
	private static final String ROLE_REGISTERED = "ROLE_REGISTERED";

	private Collection<GrantedAuthority> _authorities;

	private Date _creationDate;

	private String _email;

	private boolean _enabled;

	private String _fullName;

	private String _picture;

	private String _uid;

	public MyUserDetails(String email, String fullName, boolean enabled)
	{
		_email = email;
		_fullName = fullName;
		_enabled = enabled;
		_authorities = new ArrayList<GrantedAuthority>();
		_authorities.add(new GrantedAuthorityImpl(ROLE_REGISTERED));
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities()
	{
		return _authorities;
	}

	public Date getCreationDate()
	{
		return _creationDate;
	}

	public String getEmail()
	{
		return _email;
	}

	public String getFullName()
	{
		return _fullName;
	}

	@Override
	public String getPassword()
	{
		return null;
	}

	public String getPicture()
	{
		return _picture;
	}

	public String getUid()
	{
		return _uid;
	}

	@Override
	public String getUsername()
	{
		return _email;
	}

	@Override
	public boolean isAccountNonExpired()
	{
		return false;
	}

	@Override
	public boolean isAccountNonLocked()
	{
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired()
	{
		return false;
	}

	@Override
	public boolean isEnabled()
	{
		return _enabled;
	}

	public void setCreationDate(Date creationDate)
	{
		_creationDate = creationDate;
	}

	public void setEmail(String email)
	{
		_email = email;
	}

	public void setEnabled(boolean enabled)
	{
		_enabled = enabled;
	}

	public void setFullName(String fullName)
	{
		_fullName = fullName;
	}

	public void setPicture(String picture)
	{
		_picture = picture;
	}

	public void setUid(String uid)
	{
		_uid = uid;
	}
}
