package com.demo.config;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.mgt.SubjectFactory;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.AbstractShiroWebFilterConfiguration;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.session.SingleSignOutHttpSessionListener;
import org.pac4j.cas.client.CasClient;
import org.pac4j.cas.client.rest.CasRestFormClient;
import org.pac4j.cas.config.CasConfiguration;
import org.pac4j.cas.config.CasProtocol;
import org.pac4j.core.client.Clients;
import org.pac4j.core.config.Config;
import org.pac4j.cas.client.CasProxyReceptor;
import org.jasig.cas.client.validation.Cas20ProxyTicketValidator;

import org.pac4j.core.matching.PathMatcher;
import org.pac4j.http.client.direct.ParameterClient;
import org.pac4j.jwt.config.encryption.SecretEncryptionConfiguration;
import org.pac4j.jwt.config.signature.SecretSignatureConfiguration;
import org.pac4j.jwt.credentials.authenticator.JwtAuthenticator;
import org.pac4j.jwt.profile.JwtGenerator;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.DelegatingFilterProxy;

import org.pac4j.cas.client.direct.DirectCasProxyClient;

import io.buji.pac4j.filter.CallbackFilter;
import io.buji.pac4j.filter.SecurityFilter;
import io.buji.pac4j.subject.Pac4jSubjectFactory;


@Configuration
@Order(2)
public class ShiroConfiguration extends AbstractShiroWebFilterConfiguration {

    @Value("${cas-server-url-prefix}")
    private String casServerUrlPrefix;
    @Value("${cas-server-login-url}")
    private String casServerLoginUrl;
    @Value("${cas-logout-redirectUrl}")
    private String casLogoutUrl;

    @Value("${cas-clients-callbackUrl}")
    private String casClientCallbackUrl;
    @Value("${cas-proxyReceptor-callbackUrl}")
    private String casproxyReceptorCallbackUrl;

    @Value("${cas.serviceUrl}")
	private String casClientPrefixUrl;



    @Bean(name = "casProxyReceptor")
    public CasProxyReceptor casProxyReceptor(){
        CasProxyReceptor casProxyReceptor = new CasProxyReceptor();
        casProxyReceptor.setCallbackUrl(casproxyReceptorCallbackUrl);
        casProxyReceptor.setName("rec");
        return casProxyReceptor;
    }
    
    /**
     * cas的基本设置，包括或url等等，rest调用协议等
     * @return
     */
    @Bean
    public CasConfiguration casConfiguration(CasProxyReceptor casProxyReceptor) {
        CasConfiguration casConfiguration = new CasConfiguration(casServerLoginUrl);
        casConfiguration.setProtocol(CasProtocol.CAS20_PROXY);
        casConfiguration.setPrefixUrl(casServerUrlPrefix);
        casConfiguration.setAcceptAnyProxy(true);
        casConfiguration.setProxyReceptor(casProxyReceptor);
        return casConfiguration;
    }
    
    /**
     * 不拦截的路径
     * @return
     */
    @Bean
    public PathMatcher pathMatcher(){
    	PathMatcher pathMatcher= new PathMatcher();
        pathMatcher.excludePath("/html/**");
        return pathMatcher;
    }
    
    /**
     * pac4jRealm
     * @return
     */
    @Bean(name = "pac4jRealm")
    public Realm pac4jRealm() {
        return new CustomPac4jRealm();
    }
    
    /**
     * 通过rest接口可以获取tgt，获取service ticket，甚至可以获取CasProfile
     * @return
     */
    @Bean
    protected CasRestFormClient casRestFormClient(CasConfiguration casConfiguration) {
        CasRestFormClient casRestFormClient = new CasRestFormClient();
        casRestFormClient.setConfiguration(casConfiguration);
        casRestFormClient.setName("rest");
        return casRestFormClient;
    } 

    
    /**
     * casClient
     * @return
     */
    @Bean
    public CasClient casClient(CasConfiguration casConfiguration) {
        CasClient casClient = new CasClient();
        casClient.setConfiguration(casConfiguration);
        casClient.setCallbackUrl(casClientCallbackUrl);
        casClient.setName("cas");
        return casClient;
    }

    /**
     * token校验相关
     * @return
     */
    @Bean
    protected Clients clients(CasClient casClient, CasProxyReceptor casProxyReceptor) {
        //可以设置默认client
        Clients clients = new Clients();
        clients.setCallbackUrl(casClientCallbackUrl);
        clients.setClients(casClient, casProxyReceptor );
        return clients;
    }    
    
    @Bean
    protected Config casConfig(Clients clients) {
        Config config = new Config();
        config.setClients(clients);
        return config;
    }

    /**
     * 由于cas代理了用户，所以必须通过cas进行创建对象
     *
     * @return
     */
    @Bean(name = "subjectFactory")
    protected SubjectFactory subjectFactory() {
        return new Pac4jSubjectFactory();
    }
    
    /**
	 * 注册单点登出的listener
	 * 
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
	public ServletListenerRegistrationBean<?> singleSignOutHttpSessionListener() {
		ServletListenerRegistrationBean bean = new ServletListenerRegistrationBean();
		bean.setListener(new SingleSignOutHttpSessionListener());
		bean.setEnabled(true);
		return bean;
	}
	
	/**
	 * 注册单点登出filter
	 * 
	 * @return
	 */
	@Bean
	@Order(Ordered.HIGHEST_PRECEDENCE)
	public FilterRegistrationBean singleSignOutFilter() {
		FilterRegistrationBean bean = new FilterRegistrationBean();
		bean.setName("singleSignOutFilter");
		SingleSignOutFilter singleSignOutFilter = new SingleSignOutFilter();
		singleSignOutFilter.setCasServerUrlPrefix(casServerUrlPrefix);
		singleSignOutFilter.setIgnoreInitConfiguration(true);
		bean.setFilter(singleSignOutFilter);
		bean.addUrlPatterns("/*");
		bean.setEnabled(true);
		return bean;
	}
    //-------------------------------------------shiro 相关---------------------------
    /**
     * shiro管理器
     * @Description:TODO
     * @param pac4jRealm
     * @param subjectFactory
     * @return
     */
    @Bean(name = "securityManager")
	public DefaultWebSecurityManager securityManager(Realm pac4jRealm, SubjectFactory subjectFactory) {
		DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
		defaultWebSecurityManager.setRealm(pac4jRealm);
		defaultWebSecurityManager.setSubjectFactory(subjectFactory);
		return defaultWebSecurityManager;
	}
    

	
	@Bean
	public FilterRegistrationBean filterRegistrationBean() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		filterRegistrationBean.setFilter(new DelegatingFilterProxy("shiroFilter"));
		filterRegistrationBean.addInitParameter("targetFilterLifecycle", "true");
		filterRegistrationBean.setEnabled(true);
		filterRegistrationBean.addUrlPatterns("/*");
		return filterRegistrationBean;
	}

    /**
     * 对过滤器进行调整
     *
     * @param securityManager
     * @return
     */
    @Bean(name = "shiroFilter")
    protected ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager, Config config) {
        ShiroFilterFactoryBean filterFactoryBean = super.shiroFilterFactoryBean();
        filterFactoryBean.setSecurityManager(securityManager);
        
        //过滤器设置
        Map<String, Filter> filters = new HashMap<>();
        SecurityFilter securityFilter = new SecurityFilter();
        securityFilter.setClients("cas, rec");
        securityFilter.setConfig(config);
        securityFilter.setMultiProfile(true);
        filters.put("casSecurityFilter", securityFilter);
        
        CallbackFilter callbackFilter = new CallbackFilter();
        callbackFilter.setConfig(config);
        callbackFilter.setMultiProfile(true);
        filters.put("callbackFilter", callbackFilter);
        
        filterFactoryBean.setFilters(filters);
        

        return filterFactoryBean;
    }

    /**
     * shiro路径过滤设置
     * @return
     */
   @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition definition = new DefaultShiroFilterChainDefinition();
        definition.addPathDefinition("/callback", "callbackFilter");
       definition.addPathDefinition("/proxy", "callbackFilter");
        definition.addPathDefinition("/user/**", "casSecurityFilter");
        definition.addPathDefinition("/**", "casSecurityFilter");

        return definition;
    }
   /**
    * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
    * 配置以下两个bean(DefaultAdvisorAutoProxyCreator(可选)和AuthorizationAttributeSourceAdvisor)即可实现此功能
    * @return
    */
   @Bean
   @DependsOn({"lifecycleBeanPostProcessor"})
   public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator(){
       DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
       advisorAutoProxyCreator.setProxyTargetClass(true);
       return advisorAutoProxyCreator;
   }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor aasa = new AuthorizationAttributeSourceAdvisor();
        aasa.setSecurityManager(securityManager);
        return aasa;
    }
}
