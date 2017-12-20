package org.jenkinsci.plugins.reverse_proxy_auth.service;

import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.userdetails.UserDetails;
import org.acegisecurity.userdetails.UserDetailsService;
import org.acegisecurity.userdetails.UsernameNotFoundException;
import org.jenkinsci.plugins.reverse_proxy_auth.ReverseProxySecurityRealm;
import org.springframework.dao.DataAccessException;
import org.springframework.web.context.WebApplicationContext;

import java.util.logging.Logger;

public class ProxyCrowdUserDetailsService implements UserDetailsService {

	private static final Logger LOGGER = Logger.getLogger(ProxyCrowdUserDetailsService.class.getName());

	public ProxyCrowdUserDetailsService(ReverseProxySecurityRealm securityRealm, WebApplicationContext appContext) {
	}


	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException, DataAccessException {
		return new UserDetails() {
			@Override
			public GrantedAuthority[] getAuthorities() {
				GrantedAuthority g1 = new GrantedAuthority() {
					@Override
					public String getAuthority() {
						return "hola1";
					}
				};

				GrantedAuthority g2 = new GrantedAuthority() {
					@Override
					public String getAuthority() {
						return "hola2";
					}
				};

				GrantedAuthority g3 = new GrantedAuthority() {
					@Override
					public String getAuthority() {
						return "hola3";
					}
				};

				return new GrantedAuthority[]{g1, g2, g3};
			}

			@Override
			public String getPassword() {
				return null;
			}

			@Override
			public String getUsername() {
				return username;
			}

			@Override
			public boolean isAccountNonExpired() {
				return false;
			}

			@Override
			public boolean isAccountNonLocked() {
				return false;
			}

			@Override
			public boolean isCredentialsNonExpired() {
				return false;
			}

			@Override
			public boolean isEnabled() {
				return true;
			}
		};
	}
}