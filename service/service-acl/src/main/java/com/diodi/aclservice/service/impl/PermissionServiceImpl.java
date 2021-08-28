package com.diodi.aclservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.diodi.aclservice.entity.Permission;
import com.diodi.aclservice.entity.RolePermission;
import com.diodi.aclservice.entity.User;
import com.diodi.aclservice.helper.MemuHelper;
import com.diodi.aclservice.helper.PermissionHelper;
import com.diodi.aclservice.mapper.PermissionMapper;
import com.diodi.aclservice.service.PermissionService;
import com.diodi.aclservice.service.RolePermissionService;
import com.diodi.aclservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 权限 服务实现类
 * </p>
 * @author testjava
 * @since 2020-01-12
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Autowired
    private RolePermissionService rolePermissionService;

    @Autowired
    private UserService userService;

    //获取全部菜单
    @Override
    public List<Permission> queryAllMenu() {

        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        List<Permission> permissionList = baseMapper.selectList(wrapper);

        List<Permission> result = bulid(permissionList);

        return result;
    }

    //根据角色获取菜单
    @Override
    public List<Permission> selectAllMenu(String roleId) {
        List<Permission> allPermissionList = baseMapper.selectList(new QueryWrapper<Permission>().orderByAsc("CAST(id AS SIGNED)"));

        //根据角色id获取角色权限
        List<RolePermission> rolePermissionList = rolePermissionService.list(new QueryWrapper<RolePermission>().eq(
                "role_id", roleId));
        //转换给角色id与角色权限对应Map对象
//        List<String> permissionIdList = rolePermissionList.stream().map(e -> e.getPermissionId()).collect
//        (Collectors.toList());
//        allPermissionList.forEach(permission -> {
//            if(permissionIdList.contains(permission.getId())) {
//                permission.setSelect(true);
//            } else {
//                permission.setSelect(false);
//            }
//        });
        for (int i = 0; i < allPermissionList.size(); i++) {
            Permission permission = allPermissionList.get(i);
            for (int m = 0; m < rolePermissionList.size(); m++) {
                RolePermission rolePermission = rolePermissionList.get(m);
                if (rolePermission.getPermissionId().equals(permission.getId())) {
                    permission.setSelect(true);
                }
            }
        }

        List<Permission> permissionList = bulid(allPermissionList);
        return permissionList;
    }

    //给角色分配权限
    @Override
    public void saveRolePermissionRealtionShip(String roleId,
                                               String[] permissionIds) {

        rolePermissionService.remove(new QueryWrapper<RolePermission>().eq("role_id", roleId));

        List<RolePermission> rolePermissionList = new ArrayList<>();
        for (String permissionId : permissionIds) {
            if (StringUtils.isEmpty(permissionId)) {
                continue;
            }

            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(permissionId);
            rolePermissionList.add(rolePermission);
        }
        rolePermissionService.saveBatch(rolePermissionList);
    }

    //递归删除菜单
    @Override
    public void removeChildById(String id) {
        List<String> idList = new ArrayList<>();
        this.selectChildListById(id, idList);

        idList.add(id);
        baseMapper.deleteBatchIds(idList);
    }

    //根据用户id获取用户菜单
    @Override
    public List<String> selectPermissionValueByUserId(String id) {

        List<String> selectPermissionValueList = null;
        if (this.isSysAdmin(id)) {
            //如果是系统管理员，获取所有权限
            selectPermissionValueList = baseMapper.selectAllPermissionValue();
        } else {
            selectPermissionValueList = baseMapper.selectPermissionValueByUserId(id);
        }
        return selectPermissionValueList;
    }

    @Override
    public List<JSONObject> selectPermissionByUserId(String userId) {
        List<Permission> selectPermissionList = null;
        if (this.isSysAdmin(userId)) {
            //如果是超级管理员，获取所有菜单
            selectPermissionList = baseMapper.selectList(null);
        } else {
            selectPermissionList = baseMapper.selectPermissionByUserId(userId);
        }

        List<Permission> permissionList = PermissionHelper.bulid(selectPermissionList);
        List<JSONObject> result = MemuHelper.bulid(permissionList);
        return result;
    }

    /**
     * 判断用户是否系统管理员
     * @param userId
     * @return
     */
    private boolean isSysAdmin(String userId) {
        User user = userService.getById(userId);

        if (null != user && "admin".equals(user.getUsername())) {
            return true;
        }
        return false;
    }

    /**
     * 递归获取子节点
     * @param id
     * @param idList
     */
    private void selectChildListById(String id,
                                     List<String> idList) {
        List<Permission> childList = baseMapper.selectList(new QueryWrapper<Permission>().eq("pid", id).select("id"));
        childList.stream().forEach(item -> {
            idList.add(item.getId());
            this.selectChildListById(item.getId(), idList);
        });
    }

    /**
     * 使用递归方法建菜单
     * @param treeNodes
     * @return
     */
    private static List<Permission> bulid(List<Permission> treeNodes) {
        List<Permission> trees = new ArrayList<>();
        for (Permission treeNode : treeNodes) {
            if ("0".equals(treeNode.getPid())) {
                treeNode.setLevel(1);
                trees.add(findChildren(treeNode, treeNodes));
            }
        }
        return trees;
    }

    /**
     * 递归查找子节点
     * @param treeNodes
     * @return
     */
    private static Permission findChildren(Permission treeNode,
                                           List<Permission> treeNodes) {
        treeNode.setChildren(new ArrayList<Permission>());

        for (Permission it : treeNodes) {
            if (treeNode.getId().equals(it.getPid())) {
                int level = treeNode.getLevel() + 1;
                it.setLevel(level);
                if (treeNode.getChildren() == null) {
                    treeNode.setChildren(new ArrayList<>());
                }
                treeNode.getChildren().add(findChildren(it, treeNodes));
            }
        }
        return treeNode;
    }

    //========================递归查询所有菜单================================================
    //获取全部菜单
    @Override
    public List<Permission> queryAllMenuGuli() {
        //查询菜单表的所有数据
        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        List<Permission> permissions = baseMapper.selectList(wrapper);
        List<Permission> permissionList = bulidPermission(permissions);
        return permissionList;
    }

    /**
     * 把返回所有菜单list集合进行封装的方法
     * @param permissionList 包含所有数据的list
     * @return 最终返回的 树形的最终返回类
     */
    public static List<Permission> bulidPermission(List<Permission> permissionList) {
        //定义最终返回类
        ArrayList<Permission> nodeFinal = new ArrayList<>();
        //吧所有菜单List集合遍历 得到顶层菜单 pid=0 设置level为1
        for (Permission permission : permissionList) {
            //得到父节点的pid
            String pid = permission.getPid();
            if ("0".equals(pid)) {
                //设置父节点的level
                permission.setLevel(1);
                //将父节点及子节点放到最终返回类中返回 使用了递归的方法 递归方法中返回的是父节点 父节点包含子节点
                nodeFinal.add(selectChildren(permission, permissionList));
            }
        }
        return nodeFinal;
    }

    /**
     * 递归调用反回所有子节点
     * @param permissionNode 父节点
     * @param permissionList 包含所有数据的list
     * @return 父节点及所包含的子节点
     */
    private static Permission selectChildren(Permission permissionNode,
                                             List<Permission> permissionList) {
        //对象初始化
        permissionNode.setChildren(new ArrayList<Permission>());
        //循环所有列表
        for (Permission permission : permissionList) {
            //判断父节点的id=子节点的pid
            if (permissionNode.getId().equals(permission.getPid())) {
                //设置子节点的等级 来自父节点的等级加一
                permission.setLevel(permissionNode.getLevel()+1);
                //这个不懂有什么用
                if (permissionNode.getChildren() == null) {
                    permissionNode.setChildren(new ArrayList<Permission>());
                }
                //父节点的子节点中添加数据 而添加的数据是递归调用 如果还有子子节点 返回含有子子节点的子节点以此类推
                //如果没有子子节点了 返回的就是当前的子节点
                permissionNode.getChildren().add(selectChildren(permission, permissionList));
            }

        }
        return permissionNode;
    }

    //============递归删除菜单==================================
    @Override
    public void removeChildByIdGuli(String id) {
        //1 创建list集合，用于封装所有删除菜单id值
        ArrayList<String> idList = new ArrayList<>();
        //封装当前ID
        idList.add(id);
        //2 向idList集合设置删除菜单id
        this.selectPermissionChildById(id, idList);
        //把当前id封装到list里面
        baseMapper.deleteBatchIds(idList);
    }

    //2 根据当前菜单id，查询菜单里面子菜单id，封装到list集合
    private void selectPermissionChildById(String id,
                                           List<String> idList) {
        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        //查询菜单里面子菜单id
        wrapper.eq("pid", id);
        wrapper.select("id");
        List<Permission> permissions = baseMapper.selectList(wrapper);
        //把childIdList里面菜单id值获取出来，封装idList里面，做递归查询
        permissions.forEach(resp->{
            //封装idList里面
            idList.add(resp.getId());
            //递归查询
            this.selectPermissionChildById(resp.getId(), idList);
        });
    }

    //=========================给角色分配菜单=======================
    @Override
    public void saveRolePermissionRealtionShipGuli(String roleId,
                                                   String[] permissionIds) {
        //roleId角色id
        //permissionId菜单id 数组形式
        //1 创建list集合，用于封装添加数据
        List<RolePermission> rolePermissionList = new ArrayList<>();
        //遍历所有菜单数组
        for (String perId : permissionIds) {
            //RolePermission对象
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(perId);
            //封装到list集合
            rolePermissionList.add(rolePermission);
        }
        //添加到角色菜单关系表
        rolePermissionService.saveBatch(rolePermissionList);
    }
}
