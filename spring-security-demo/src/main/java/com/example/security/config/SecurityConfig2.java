package com.example.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//@EnableWebSecurity
public class SecurityConfig2 extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/static/**", "/index").permitAll()
				// 参看: https://docs.spring.io/spring-security/site/docs/5.0.3.RELEASE/reference/htmlsingle/#el-access
				.antMatchers("/user/**").hasAnyRole("USER", "ADMIN")
				.and()
			.formLogin()
				.loginPage("/login")
                .failureUrl("/login-error")
				.and()
			// 自定义注销配置
			.logout()
				// 详细说明: https://docs.spring.io/spring-security/site/docs/5.0.3.RELEASE/reference/htmlsingle/#jc-logout
				// 配置 logout 支持, 该配置由于 WebSecurityConfigurerAdapter 自动配置
				.logoutUrl("/logout")
				 // 配置注销成功之后跳转到的地址
				.logoutSuccessUrl("/logout-success")
				// 配置注销成功之后的处理器, 如果配置了, 则上一个配置不会生效
//				.logoutSuccessHandler((request, response, authentication) -> {
//				})
				// 注销用户成功之后使用户失效
				.invalidateHttpSession(true)
				// 添加注销处理类.
//				.addLogoutHandler()
				.deleteCookies("jsessionid");
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
				.passwordEncoder(new PasswordEncoder() {
					public String encode(CharSequence charSequence) {
						return charSequence.toString();
					}
					public boolean matches(CharSequence charSequence, String s) {
						return s.equals(charSequence.toString());
					}
				})
				.withUser("zhangsan").password("123456").roles("USER")
				.and()
				.withUser("lisi").password("123456").roles("ADMIN");
	}

}