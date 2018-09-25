package com.cas.configuration;

/**
 * Created by lep on 18-9-2.
 */

import org.apereo.cas.authentication.AuthenticationEventExecutionPlan;
import org.apereo.cas.authentication.AuthenticationEventExecutionPlanConfigurer;
import org.apereo.cas.authentication.AuthenticationHandler;

import org.apereo.cas.CipherExecutor;
import org.apereo.cas.authentication.AuthenticationEventExecutionPlan;
import org.apereo.cas.authentication.AuthenticationEventExecutionPlanConfigurer;
import org.apereo.cas.authentication.AuthenticationHandler;
import org.apereo.cas.authentication.CoreAuthenticationUtils;
import org.apereo.cas.authentication.principal.DefaultPrincipalFactory;
import org.apereo.cas.authentication.principal.PrincipalNameTransformerUtils;
import org.apereo.cas.authentication.support.password.PasswordEncoderUtils;
import org.apereo.cas.authentication.support.password.PasswordPolicyConfiguration;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.configuration.model.support.jdbc.JdbcAuthenticationProperties;
import org.apereo.cas.configuration.model.support.jdbc.QueryJdbcAuthenticationProperties;
import org.apereo.cas.configuration.support.JpaBeans;
import org.apereo.cas.services.ServicesManager;
import org.apereo.cas.util.CollectionUtils;

import org.apereo.cas.services.ServicesManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.collect.Multimap;
import com.cas.handler.MyAuthenticationHandler;

@Configuration("MyAuthenticationEventExecutionPlanConfiguration")
@EnableConfigurationProperties(CasConfigurationProperties.class)
public class MyAuthenticationEventExecutionPlanConfiguration  implements AuthenticationEventExecutionPlanConfigurer{

        @Autowired
        private CasConfigurationProperties casProperties;

        @Autowired
        @Qualifier("servicesManager")
        private ServicesManager servicesManager;

        @Bean
        public AuthenticationHandler myAuthenticationHandler() {
            final JdbcAuthenticationProperties jdbc = casProperties.getAuthn().getJdbc();
            QueryJdbcAuthenticationProperties b = jdbc.getQuery().get(0);
            final Multimap<String, String> attributes = CoreAuthenticationUtils.transformPrincipalAttributesListIntoMultiMap(b.getPrincipalAttributeList());

            final MyAuthenticationHandler handler = new MyAuthenticationHandler(b.getName()+"_demo",
                    servicesManager,
                    new DefaultPrincipalFactory(), b.getOrder(),
                    JpaBeans.newDataSource(b), b.getSql(), b.getFieldPassword(),
                    b.getFieldExpired(), b.getFieldDisabled(),
                    CollectionUtils.wrap(attributes));
            return handler;
        }

        @Override
        public void configureAuthenticationExecutionPlan(AuthenticationEventExecutionPlan plan) {
            plan.registerAuthenticationHandler(myAuthenticationHandler());
        }
    }
