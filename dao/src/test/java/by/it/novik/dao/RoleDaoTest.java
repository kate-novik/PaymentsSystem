package by.it.novik.dao;

import by.it.novik.entities.Role;
import by.it.novik.util.DaoException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import resources.HibernateTestConfig;

/**
 * Created by Kate Novik.
 */
@ContextConfiguration(classes = HibernateTestConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@Rollback(true)
public class RoleDaoTest {
    private static Role role;

    @Autowired
    private RoleDao roleDao;

    @Before
    public void initializeEntity () {
        role = BuilderEntity.buildRole();
    }

    @Test
    public void saveOrUpdateRoleTest () throws DaoException {
        roleDao.saveOrUpdate(role);
        roleDao.getSession().flush();
        roleDao.getSession().evict(role);
        Assert.assertNotNull("Role didn't save!", roleDao.get(role.getIdRole()));
    }

    @Test
    public void getRoleByNameTest () throws DaoException {
        roleDao.saveOrUpdate(role);
        roleDao.getSession().flush();
        roleDao.getSession().evict(role);
        Role roleExp = roleDao.getRoleByName("user");
        Assert.assertEquals("Role didn't get!", roleExp, role);
    }
}
