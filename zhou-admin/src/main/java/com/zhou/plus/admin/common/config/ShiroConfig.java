package com.zhou.plus.admin.common.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.zhou.plus.admin.common.authc.FormAuthenticationFilter;
import com.zhou.plus.admin.common.authc.LogoutFilter;
import com.zhou.plus.admin.common.realm.SystemAuthorizingRealm;
import com.zhou.plus.framework.security.CustomCredentialsMatcher;
import com.zhou.plus.framework.security.session.CacheSessionDAO;
import com.zhou.plus.framework.security.session.SessionDao;
import com.zhou.plus.framework.security.session.SessionManager;
import com.zhou.plus.framework.utils.IdGen;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.ClassPathResource;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
@EnableCaching
public class ShiroConfig {

    /**
     * 自定义密码匹配器
     * @return
     */
    @Bean
    public CustomCredentialsMatcher customCredentialsMatcher(){
        return new CustomCredentialsMatcher();
    }

    /**
     * 自定义realm
     * @return
     */
    @Bean
    public SystemAuthorizingRealm systemAuthorizingRealm(){
        SystemAuthorizingRealm systemAuthorizingRealm=new SystemAuthorizingRealm();
        systemAuthorizingRealm.setCredentialsMatcher(customCredentialsMatcher());
        return systemAuthorizingRealm;
    }


    /**
     * 自定义缓存
     * @return
     */
    @Bean
    public EhCacheManagerFactoryBean cacheManager(){
        EhCacheManagerFactoryBean cacheManagerFactoryBean = new EhCacheManagerFactoryBean ();
        cacheManagerFactoryBean.setConfigLocation (new ClassPathResource("cache/ehcache-local.xml"));
        cacheManagerFactoryBean.setShared(true);
        return cacheManagerFactoryBean;
    }

    /**
     * 会话Id生成
     * @return
     */
    @Bean
    @Lazy(value = false)
    public IdGen idGen(){
        return new IdGen();
    }


    /**
     * 自定义系统缓存
     * @return
     */
    @Bean
    public EhCacheManager ehCacheManager(){
        EhCacheManager ehCacheManager=new EhCacheManager();
        ehCacheManager.setCacheManager(cacheManager().getObject());
        return ehCacheManager;
    }


    /**
     * 自定义SessionDao
     */
    @Bean
    public CacheSessionDAO sessionDAO(){
        CacheSessionDAO sessionDAO=new CacheSessionDAO();
        sessionDAO.setSessionIdGenerator(idGen());
        sessionDAO.setActiveSessionsCacheName("activeSessionsCacheName");
        sessionDAO.setCacheManager(ehCacheManager());
        return sessionDAO;
    }


    /**
     *  指定本系统SESSIONID, 默认为: JSESSIONID
     *  问题: 与SERVLET容器名冲突, 如JETTY, TOMCAT 等默认JSESSIONID,
     *       当跳出SHIRO SERVLET时如ERROR-PAGE容器会为JSESSIONID重新分配值导致登录会话丢失
     */
    @Bean
    public SimpleCookie simpleCookie(){
        SimpleCookie simpleCookie=new SimpleCookie();
        simpleCookie.setName("zhou.session.id");
        simpleCookie.setHttpOnly(true);
        simpleCookie.setMaxAge(-1);
        return simpleCookie;
    }

    /**
     * 自定义会话管理
     */
    public SessionManager sessionManager(){
        SessionManager sessionManager=new SessionManager();
        sessionManager.setSessionDAO(sessionDAO());
        sessionManager.setGlobalSessionTimeout(180000);
        sessionManager.setSessionValidationInterval(120000);
        sessionManager.setSessionValidationSchedulerEnabled(true);
        sessionManager.setDeleteInvalidSessions(true);
        sessionManager.setSessionIdCookie(simpleCookie());
        sessionManager.setSessionIdCookieEnabled(true);
        return sessionManager;
    }

    /**
     * shiro 管理器
     * @return
     */
    @Bean
    public SecurityManager securityManager(){
        DefaultWebSecurityManager securityManager=new DefaultWebSecurityManager();
        securityManager.setRealm(systemAuthorizingRealm());
        securityManager.setCacheManager(ehCacheManager());
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }

    /**
     * 自定义表单过滤器
     * @return
     */
    @Bean
    public FormAuthenticationFilter formAuthenticationFilter(){
        return  new FormAuthenticationFilter();
    }


    /**
     * shiro退出过滤器
     * @return
     */
    @Bean
    public LogoutFilter logoutFilter(){
        return  new LogoutFilter();
    }
    /**
     * Filter工厂，设置对应的过滤条件和跳转条件
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();

        factoryBean.setSecurityManager(securityManager);
        factoryBean.setLoginUrl("/login");
        factoryBean.setSuccessUrl("/index");


        // 自定义过滤器
        Map<String, Filter> filterMap = factoryBean.getFilters();
        filterMap.put("authc", formAuthenticationFilter());
        filterMap.put("logout", logoutFilter());
        factoryBean.setFilters(filterMap);

        Map<String,String> map = new LinkedHashMap<>();
        //登出
        map.put("/api/**","anon");
        map.put("/static/**","anon");
        map.put("/webjars/**","anon");
        map.put("/swagger-ui.html","anon");
        //对所有用户认证
        map.put("/login","authc");
        map.put("/logout","logout");
        map.put("/login","user");
        factoryBean.setFilterChainDefinitionMap(map);

        return factoryBean;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }


    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }


    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }


    /**
     * thymeleaf shiro 模板解析
     * @return
     */
    @Bean
    public ShiroDialect shiroDialect(){
        return new ShiroDialect();
    }
}
