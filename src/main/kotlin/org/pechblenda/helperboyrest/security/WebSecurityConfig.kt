package org.pechblenda.helperboyrest.security

import org.pechblenda.auth.AuthController
import org.pechblenda.auth.service.AuthService
import org.pechblenda.auth.util.ContextApp
import org.pechblenda.helperboyrest.entity.User
import org.pechblenda.security.JwtAuthEntryPoint
import org.pechblenda.security.JwtAuthTokenFilter
import org.pechblenda.security.JwtProvider

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class WebSecurityConfig {

	@Autowired
	private lateinit var userDetailsService: UserDetailsServiceImpl

	@Autowired
	private lateinit var jwtProvider: JwtProvider

	@Autowired
	private lateinit var authRepository: IAuthRepository

	@Bean
	fun authenticationJwtTokenFilter(): JwtAuthTokenFilter {
		return JwtAuthTokenFilter(jwtProvider, userDetailsService)
	}

	@Bean
	fun jwtAuthEntryPoint(): JwtAuthEntryPoint {
		return JwtAuthEntryPoint()
	}

	@Bean
	fun authenticationProvider(): DaoAuthenticationProvider {
		val authProvider = DaoAuthenticationProvider()
		authProvider.setUserDetailsService(userDetailsService)
		authProvider.setPasswordEncoder(passwordEncoder())
		return authProvider
	}

	@Bean
	fun authenticationManager(authConfig: AuthenticationConfiguration): AuthenticationManager {
		return authConfig.authenticationManager
	}

	@Bean
	fun passwordEncoder(): PasswordEncoder {
		return BCryptPasswordEncoder()
	}

	@Bean
	fun filterChain(http: HttpSecurity): SecurityFilterChain? {
		http.cors()
			.and()
			.csrf()
			.disable()
			.exceptionHandling()
			.authenticationEntryPoint(jwtAuthEntryPoint())
			.and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.authorizeRequests()
			.antMatchers(
				"/rest/auth/**",
				"/api/**",
				"/**" //todo remove that
			).permitAll()
			.anyRequest()
			.authenticated()

		http.authenticationProvider(authenticationProvider())
		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter::class.java)
		return http.build()
	}

	@Bean
	fun authService(userRepository: IAuthRepository): AuthService {
		return AuthService(userRepository, User::class)
	}

	@Bean
	fun authController(): AuthController {
		return AuthController()
	}

	@Bean
	fun contextApp(): ContextApp {
		return ContextApp(authRepository)
	}

}
