package com.codeoftheweb.salvo;

import com.codeoftheweb.salvo.models.*;
import com.codeoftheweb.salvo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class SalvoApplication {

	@Autowired
	PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(SalvoApplication.class, args);
	}

	@Bean
	public PasswordEncoder passwordEncoder(){
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Bean
	public CommandLineRunner initData(PlayerRepository playerRepository,
									  GameRepository gameRepository,
									  GamePlayerRepository gamePlayerRepository,
									  ShipRepository shipRepository,
									  SalvoRepository salvoRepository,
									  ScoreRepository scoreRepository) {
		return (args) -> {

			//Player: Email of the player
			Player admin = new Player("admin@admin",passwordEncoder.encode("admin"));
			Player player1 = new Player("player1@gmail.com",passwordEncoder.encode("24"));
			Player player2 = new Player("player2@gmail.com", passwordEncoder.encode("123"));
			Player player3 = new Player("player3@gmail.com", passwordEncoder.encode("hola"));
			Player player4 = new Player("player4@gmail.com", passwordEncoder.encode("kb"));
			//Player Repository save
			/*playerRepository.save(admin);
			playerRepository.save(player1);
			playerRepository.save(player2);
			playerRepository.save(player3);
			playerRepository.save(player4);*/

			//Game:  Time of creation.
			Game game1 = new Game(LocalDateTime.now());
			Game game2 = new Game(LocalDateTime.now().plusHours(1));
			Game game3 = new Game(LocalDateTime.now().plusHours(3));
			//Game Repository save
			/*gameRepository.save(game1);
			gameRepository.save(game2);*/

			//GamePlayer: Player, Game, Join Date.
			GamePlayer gamePlayer1 = new GamePlayer(player1, game1, LocalDateTime.now());
			GamePlayer gamePlayer2 = new GamePlayer(player2, game1, LocalDateTime.now());
			GamePlayer gamePlayer3 = new GamePlayer(player3, game2, LocalDateTime.now());
			GamePlayer gamePlayer4 = new GamePlayer(player4, game2, LocalDateTime.now());
			//GamePlayer Repository save
			/*gamePlayerRepository.save(gamePlayer1);
			gamePlayerRepository.save(gamePlayer2);
			gamePlayerRepository.save(gamePlayer3);
			gamePlayerRepository.save(gamePlayer4);*/

			//Ships: GamePlayer, Type of ship, Locations of the ship in the board.
			Ship ship1 = new Ship(gamePlayer1, "carrier", List.of("I4", "I5", "I6", "I7", "I8"));
			Ship ship2 = new Ship(gamePlayer1, "battleship", List.of("B6", "B7", "B8", "B9"));
			Ship ship3 = new Ship(gamePlayer1, "patrolboat", List.of("B2", "C2"));
			Ship ship4 = new Ship(gamePlayer1, "destroyer", List.of("D8", "E8", "F8"));
			Ship ship5 = new Ship(gamePlayer1, "submarine", List.of("F2", "F3", "F4"));

			Ship ship6 = new Ship(gamePlayer2, "carrier", List.of("I4", "I5", "I6", "I7", "I8"));
			Ship ship7 = new Ship(gamePlayer2, "battleship", List.of("B6", "B7", "B8", "B9"));
			Ship ship8 = new Ship(gamePlayer2, "patrolboat", List.of("B2", "C2"));
			Ship ship9 = new Ship(gamePlayer2, "destroyer", List.of("D8", "E8", "F8"));
			Ship ship10 = new Ship(gamePlayer2, "submarine", List.of("F2", "F3", "F4"));

			//Ship Repository save
			/*shipRepository.save(ship1);
			shipRepository.save(ship2);
			shipRepository.save(ship3);
			shipRepository.save(ship4);
			shipRepository.save(ship5);
			shipRepository.save(ship6);
			shipRepository.save(ship7);
			shipRepository.save(ship8);
			shipRepository.save(ship9);
			shipRepository.save(ship10);*/

			//Salvoes: GamePlayer, Location of de Salvo, turn.
			Salvo salvo1 = new Salvo(gamePlayer1, List.of("B2", "C2", "D8","E8", "F8"), 1); //PATROLBOAT - DESTROYER
			Salvo salvo2 = new Salvo(gamePlayer1, List.of("I4", "I5", "I6", "I7", "I8"), 2); // CARRIER
			Salvo salvo3 = new Salvo(gamePlayer1, List.of("F2","F3","F4","B6" ,"B7"), 3); //SUBMARINE - BATTLESHIP
			Salvo salvo4 = new Salvo(gamePlayer1, List.of("B8"), 4); //BATTLESHIP A 1 HIT
			Salvo salvo5 = new Salvo(gamePlayer2, List.of("B2", "C2", "D8","E8", "F8"), 1); //Hunde destroyer del gameplayer 1
			Salvo salvo6 = new Salvo(gamePlayer2, List.of("I4", "I5", "I6", "I7", "I8"), 2); //2 Hits en battleship gameplayer 1
			Salvo salvo7 = new Salvo(gamePlayer2, List.of("F2","F3","F4","B6" ,"B7"), 3);
			Salvo salvo8 = new Salvo(gamePlayer2, List.of("B8"), 4);
			Salvo salvo9 = new Salvo(gamePlayer3, List.of("B5", "B6", "B7"), 2);
			Salvo salvo0 = new Salvo(gamePlayer4, List.of("E9", "C2", "E1"), 1);
			Salvo salvo11 = new Salvo(gamePlayer4, List.of("B5", "B6", "B7"), 2);
			//Salvoes Repository save
			/*salvoRepository.save(salvo1);
			salvoRepository.save(salvo2);
			salvoRepository.save(salvo3);
			salvoRepository.save(salvo4);
			salvoRepository.save(salvo5);
			salvoRepository.save(salvo6);
			salvoRepository.save(salvo7);
			salvoRepository.save(salvo8);
			salvoRepository.save(salvo9);
			salvoRepository.save(salvo0);
			salvoRepository.save(salvo11);*/

			//Scores:
			Score score1 = new Score(player1, game1, 1f, LocalDateTime.now().plusMinutes(30L));
			Score score2 = new Score(player2, game1, 0f, LocalDateTime.now().plusMinutes(30L));
			Score score3 = new Score(player3, game2, .5f, LocalDateTime.now().plusMinutes(30L));
			Score score4 = new Score(player4, game2, .5f, LocalDateTime.now().plusMinutes(30L));
			//Scores Repository save
			/*scoreRepository.save(score1);
			scoreRepository.save(score2);
			scoreRepository.save(score3);
			scoreRepository.save(score4);*/
		};
	}
}


//

@Configuration
class WebSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {
	@Autowired
	PlayerRepository playerRepository;

	@Override
	public void init(AuthenticationManagerBuilder auth) throws Exception {
		//User Authentication
		auth.userDetailsService(inputUserName -> {
			Player player = playerRepository.findByUserName(inputUserName);
			if (player != null) {
				return new User(player.getUserName(), player.getPassword(),
						AuthorityUtils.createAuthorityList("USER"));
			} else {
				throw new UsernameNotFoundException("Unknown user: " + inputUserName);
			}
		});
	}
}

@EnableWebSecurity
@Configuration
class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/web/**").permitAll()
				.antMatchers("/api/game_view/**").hasAuthority("USER")
				.antMatchers("/api/games").permitAll()
				.antMatchers(HttpMethod.POST, "/api/players").permitAll();

		http.formLogin()
				.usernameParameter("name")
				.passwordParameter("pwd")
				.loginPage("/api/login");

		http.logout().logoutUrl("/api/logout");

		// turn off checking for CSRF tokens
		http.csrf().disable();

		// if user is not authenticated, just send an authentication failure response
		http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

		// if login is successful, just clear the flags asking for authentication
		http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

		// if login fails, just send an authentication failure response
		http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

		// if logout is successful, just send a success response
		http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
	}

	private void clearAuthenticationAttributes(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		}
	}
}
