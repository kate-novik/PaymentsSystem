package by.it.novik.services;

import by.it.novik.dao.RoleDao;
import by.it.novik.pojos.Role;

/**
 * Created by Kate Novik.
 */
public class RoleService extends BaseService<Role> implements IRoleService {

    private RoleDao roleDao;

    public RoleService(RoleDao roleDao) {
        super(roleDao);
        this.roleDao = roleDao;
    }
}
