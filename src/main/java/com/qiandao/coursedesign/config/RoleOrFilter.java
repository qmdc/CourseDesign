package com.qiandao.coursedesign.config;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;


public class RoleOrFilter extends AuthorizationFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        Subject subject = this.getSubject(request, response);
        String[] perms = (String[]) mappedValue;
        boolean isPermitted = true;
        if (perms != null && perms.length > 0) {
            if (perms.length == 1) {
                if (isOneOfPermitted(perms[0], subject)) {
                    isPermitted = false;
                }
            } else if (!isAllPermitted(perms,subject)) {
                isPermitted = false;
            }
        }
        return isPermitted;
    }

    /**
     * 以“，”分割的权限为并列关系的权限控制，分别对每个权限字符串进行“|”分割解析
     * 若并列关系的权限有一个不满足则返回false
     *
     * @param permStrArray 以","分割的权限集合
     * @param subject      当前用户的登录信息
     * @return 是否拥有该权限
     */
    private boolean isAllPermitted(String[] permStrArray, Subject subject) {
        boolean isPermitted = true;
        for (String s : permStrArray) {
            if (isOneOfPermitted(s, subject)) {
                isPermitted = false;
            }
        }
        return isPermitted;
    }

    /**
     * 判断以“|”分割的权限有一个满足的就返回true，表示权限的或者关系
     *
     * @param permStr 权限数组种中的一个字符串
     * @param subject 当前用户信息
     * @return 是否有权限
     */
    private boolean isOneOfPermitted(String permStr, Subject subject) {
        boolean isPermitted = false;
        String[] permArr = permStr.split("\\|");
        if (permArr.length > 0) {
            for (String s : permArr) {
                if (subject.isPermitted(s)) {
                    isPermitted = true;
                }
            }
        }
        return !isPermitted;
    }

}






