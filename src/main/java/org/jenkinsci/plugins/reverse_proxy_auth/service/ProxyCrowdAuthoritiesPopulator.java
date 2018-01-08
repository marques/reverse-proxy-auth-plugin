package org.jenkinsci.plugins.reverse_proxy_auth.service;

import com.atlassian.crowd.service.client.CrowdClient;
import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.GrantedAuthorityImpl;
import org.acegisecurity.providers.ldap.LdapAuthoritiesPopulator;
import org.jenkinsci.plugins.reverse_proxy_auth.auth.ReverseProxyAuthoritiesPopulator;
import org.jenkinsci.plugins.reverse_proxy_auth.model.ReverseProxyUserDetails;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * {@link LdapAuthoritiesPopulator} that adds the automatic 'authenticated' role.
 */
public class ProxyCrowdAuthoritiesPopulator implements ReverseProxyAuthoritiesPopulator {

    private static final Logger LOGGER = Logger.getLogger(ProxyCrowdAuthoritiesPopulator.class.getName());

    private String ROLE_PREFIX = "ROLE_";

    private Integer MAX_GROUPS = 1000;

    private CrowdClient crowdClient;

    public ProxyCrowdAuthoritiesPopulator(CrowdClient crowdClient){
        this.crowdClient = crowdClient;
    }

    /**
     * Retrieves the group membership in two ways.
     *
     * We'd like to retain the original name, but we historically used to do "ROLE_GROUPNAME".
     * So this method return both, "ROLE_GROUPNAME" and "groupName".
     */
    @Override
    public GrantedAuthority[] getGrantedAuthorities(ReverseProxyUserDetails userDetails) {

        Set<GrantedAuthority> grantedAuthoritySet = new HashSet<>();
        List<String> namesOfGroupsForUser = new ArrayList<>();

        try {
            namesOfGroupsForUser = crowdClient.getNamesOfGroupsForUser(userDetails.getUsername(), 0, MAX_GROUPS);
        } catch (Exception e) {
            String msg = String.format("Failed to search Crowd groups for username %s", userDetails.getUsername());
            LOGGER.log(Level.SEVERE, msg, e);
        }

        for (final String groupName : namesOfGroupsForUser) {
            grantedAuthoritySet.add(new GrantedAuthorityImpl(groupName));
            grantedAuthoritySet.add(new GrantedAuthorityImpl(ROLE_PREFIX + groupName.toUpperCase()));
        }


        return grantedAuthoritySet.toArray(new GrantedAuthority[0]);
    }
}