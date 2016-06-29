package com.springapp.config;

import com.springapp.security.DefaultLogoutSuccessHandler;
import com.springapp.security.DefaultUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth.common.signature.SharedConsumerSecretImpl;
import org.springframework.security.oauth.consumer.BaseProtectedResourceDetails;
import org.springframework.security.oauth.consumer.ProtectedResourceDetails;
import org.springframework.security.oauth.provider.*;
import org.springframework.security.oauth.provider.filter.OAuthProviderProcessingFilter;
import org.springframework.security.oauth.provider.filter.ProtectedResourceProcessingFilter;
import org.springframework.security.oauth.provider.token.InMemoryProviderTokenServices;
import org.springframework.security.openid.OpenIDAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

/**
 * Created by Mathieu on 6/25/2016.
 */
@Configuration
@EnableWebSecurity
@ComponentScan("com.springapp.security")
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DefaultLogoutSuccessHandler defaultLogoutSuccessHandler;

    @Autowired
    private DefaultUserDetailsService userDetailsService;

    private String consumerKey = "appdirectchallenge-122459";
    private String consumerSecret = "PTgZETLcganA";

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/*.{jsp}", "/resources/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();

        http.authorizeRequests().antMatchers("/**").permitAll().anyRequest().authenticated();

        http.openidLogin()
                .authenticationUserDetailsService(userDetailsService)
                .loginProcessingUrl("/login/openid")
                .permitAll()
                .defaultSuccessUrl("/");

        http.logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler(defaultLogoutSuccessHandler);

        http.addFilterBefore(oAuthProviderProcessingFilter(), OpenIDAuthenticationFilter.class);
    }

    @Bean
    public OAuthProviderProcessingFilter oAuthProviderProcessingFilter() {

        final ProtectedResourceProcessingFilter filter = new ProtectedResourceProcessingFilter() {

            @Override
            protected boolean requiresAuthentication(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain) {

                if (new AntPathRequestMatcher("/user/**").matches(request) ||
                        new AntPathRequestMatcher("/subscription/**").matches(request)) {
                    OAuthProcessingFilterEntryPoint authenticationEntryPoint = new OAuthProcessingFilterEntryPoint();
                    setAuthenticationEntryPoint(authenticationEntryPoint);
                    String realmName = request.getRequestURL().toString();
                    authenticationEntryPoint.setRealmName(realmName);
                    return true;
                }
                return false;
            }
        };
        filter.setConsumerDetailsService(consumerDetailsService());
        filter.setTokenServices(inMemoryProviderTokenServices());

        return filter;
    }

    @Bean
    public ConsumerDetailsService consumerDetailsService() {
        final BaseConsumerDetails consumerDetails = new BaseConsumerDetails();
        consumerDetails.setConsumerKey(consumerKey);
        consumerDetails.setSignatureSecret(new SharedConsumerSecretImpl(consumerSecret));
        consumerDetails.setRequiredToObtainAuthenticatedToken(false);

        final InMemoryConsumerDetailsService consumerDetailsService = new InMemoryConsumerDetailsService();
        consumerDetailsService.setConsumerDetailsStore(new HashMap<String, ConsumerDetails>() {{
            put(consumerKey, consumerDetails);
        }});
        return consumerDetailsService;
    }

    @Bean
    public InMemoryProviderTokenServices inMemoryProviderTokenServices() {
        return new InMemoryProviderTokenServices();
    }

    @Bean
    public ProtectedResourceDetails protectedResourceDetails() {
        final BaseProtectedResourceDetails resource = new BaseProtectedResourceDetails();
        resource.setConsumerKey(consumerKey);
        resource.setSharedSecret(new SharedConsumerSecretImpl(consumerSecret));
        return resource;
    }

    @Bean(name="myAuthenticationManager")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user").password("password").authorities("ROLE_USER");
    }

}