package by.it.novik.services;

import by.it.novik.dao.UserDao;
import by.it.novik.entities.Role;
import by.it.novik.util.DaoException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kate Novik.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static Logger log = Logger.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        by.it.novik.entities.User user;
        try {
            user = userDao.findByLogin(username);
        } catch (DaoException e) {
            log.error("Error findByLogin() user in MyUserDetailsService." + e);
            throw new UsernameNotFoundException("Error findByLogin() user in MyUserDetailsService.");
        }
        List<GrantedAuthority> authorities = buildUserAuthority(user.getRole());

        return buildUserForAuthentication(user, authorities);
    }

    // Converts by.it.novik.entities.User user to org.springframework.security.core.userdetails.User
    private User buildUserForAuthentication(by.it.novik.entities.User user,
                                            List<GrantedAuthority> authorities) {
        return new User(user.getLogin(), user.getPassword(), authorities);
    }


    private List<GrantedAuthority> buildUserAuthority(Role userRole) {
        GrantedAuthority authority = new SimpleGrantedAuthority(userRole.getRole());
        // consider using Arrays.asList()
        List<GrantedAuthority> listAuthority = new ArrayList<>();
        listAuthority.add(authority);
        return listAuthority;
    }
}
