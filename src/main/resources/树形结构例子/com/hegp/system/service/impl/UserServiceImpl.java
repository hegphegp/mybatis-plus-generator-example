package com.hegp.system.service.impl;

import com.hegp.system.mapper.SysUserMapper;
import com.hegp.system.model.SysUser;
import com.hegp.system.service.UserService;
import com.hegp.system.service.core.impl.TreeEntityServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends TreeEntityServiceImpl<SysUserMapper, SysUser> implements UserService {
}
