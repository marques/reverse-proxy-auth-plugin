# Jenkins Reverse Proxy Authentication and Authorisation Plugin

The Reverse Proxy Plugin providers developers the ability to have easy and simple Authentication and Authorisation using SSO techniques. The plugin expects that the user to have Jenkins authenticated agains will be informed via a HHTP header field.

When it comes to Authorisation, the plugin has been extended in order to offer three flavours to developers: HTTP header containing LDAP groups, LDAP discovery or Crowd discovery. When one of the mentioned favlours is used, the developer can have Jenkins configured to use Role Based Matrix Authorisation, that will read the groups that were fed to the Reverse Proxy plugin.

The default values for the HTTP header fields are:

1. Header User Name: X-Forwarded-User
2. Header Groups Name: X-Forwarded-Groups
3. Header Groups Delimiter: |
 

The Authorisation options can be displayed via the Advanced... button, located on the right side of the security settings. The authorization type is selected using the select "Authorization type" with three possible values:
1. Header groups (default)
2. Crowd
3. LDAP

The default used will be Header groups. 
If Crowd is selected then the "Crowd authorization properties" should be provided. Crowd server, application name and application password are mandatory to properly configure Crowd access.
If LDAP is selected, then the "LDAP authorization properties" should be provided. 

If the username is not forwaded to Jenkins, the user will be authenticated as ANONYMOUS. When LDAP groups are sent via the HTTP header, there is no check if the username exists in the LDAP directory, so protect your proxy in order to avoid HTTP Header injection. Once an username is informed, the user will be authenticated. If no groups are returned from the LDAP search, the user will still be authenticated, but no other grants will be given.

If LDAP authorization type is selected and the LDAP properties are properly configured instead of groups on the HTTP header, there is guarantee that only the LDAP groups of a given user will be returned. 

If Crowd authorization type is selected and the Crowd properties are properly configured then the groups of the given user in Crowd server will be returned. If no groups are found for the user in Crowd, the user will still be authenticated but no other grants will be given.

## Running in docker

Use this command for run jenkins and crowd behind a proxy in containers.

    docker-compose up -d

For loggin:

    docker-compose logs -f <service>

    docker-compose logs -f crowd

Jenkins endpoint http://localhost

Crowd endpoint http://localhost/crowd
