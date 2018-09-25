package com.cas.handler;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.authentication.HandlerResult;
import org.apereo.cas.authentication.PreventedException;
import org.apereo.cas.authentication.exceptions.AccountDisabledException;
import org.apereo.cas.authentication.exceptions.AccountPasswordMustChangeException;
import org.apereo.cas.authentication.principal.PrincipalFactory;
import org.apereo.cas.services.ServicesManager;
import org.apereo.cas.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.apereo.cas.adaptors.jdbc.AbstractJdbcUsernamePasswordAuthenticationHandler;
import org.apereo.cas.authentication.handler.support.AbstractUsernamePasswordAuthenticationHandler;
import org.apereo.cas.authentication.UsernamePasswordCredential;

import javax.security.auth.login.AccountNotFoundException;
import javax.security.auth.login.FailedLoginException;
import javax.sql.DataSource;
import java.security.GeneralSecurityException;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import com.cas.utils.PasswordHelper;

/**
 * Created by lep on 18-9-2.
 */
public class MyAuthenticationHandler extends AbstractJdbcUsernamePasswordAuthenticationHandler{
    private static final Logger LOGGER = LoggerFactory.getLogger(MyAuthenticationHandler.class);

    private final String sql;
    private final String fieldPassword;
    private final String fieldExpired;
    private final String fieldDisabled;
    private final Map<String, Collection<String>> principalAttributeMap;

    public MyAuthenticationHandler(final String name, final ServicesManager servicesManager,
                                   final PrincipalFactory principalFactory,
                                   final Integer order, final DataSource dataSource, final String sql,
                                   final String fieldPassword, final String fieldExpired, final String fieldDisabled,
                                   final Map<String, Collection<String>> attributes) {
        super(name, servicesManager, principalFactory, order, dataSource);
        this.sql = sql;
        this.fieldPassword = fieldPassword;
        this.fieldExpired = fieldExpired;
        this.fieldDisabled = fieldDisabled;
        this.principalAttributeMap = attributes;
    }

    @Override
    protected HandlerResult authenticateUsernamePasswordInternal(final UsernamePasswordCredential credential, final String originalPassword)
            throws GeneralSecurityException, PreventedException {

        if (StringUtils.isBlank(this.sql) || getJdbcTemplate() == null) {
            throw new GeneralSecurityException("Authentication handler is not configured correctly. "
                    + "No SQL statement or JDBC template is found.");
        }

        final Map<String, Object> attributes = new LinkedHashMap<>(this.principalAttributeMap.size());
        final String username = credential.getUsername();
        final String password = credential.getPassword();

        try {
            final Map<String, Object> dbFields = getJdbcTemplate().queryForMap(this.sql, username);
            final String dbPassword = (String) dbFields.get(this.fieldPassword);
            final String salt = (String) dbFields.get("salt");

            String encPwd = PasswordHelper.encryptPassword(password, salt);
            if(StringUtils.isBlank(originalPassword) && !StringUtils.equals(encPwd, dbPassword)) {
                throw new FailedLoginException("Password does not match value on record.");
            }

        } catch (final IncorrectResultSizeDataAccessException e) {
            if (e.getActualSize() == 0) {
                throw new AccountNotFoundException(username + " not found with SQL query");
            }
            throw new FailedLoginException("Multiple records found for " + username);
        } catch (final DataAccessException e) {
            throw new PreventedException("SQL exception while executing query for " + username, e);
        }

        return createHandlerResult(credential, this.principalFactory.createPrincipal(username, attributes), null);
    }
}
