package citrusdemo;

import java.sql.SQLException;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.wait.strategy.LogMessageWaitStrategy;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.example.citrusdemo.CitrusdemoApplication;
import static org.hamcrest.Matchers.*;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.qameta.allure.junit5.AllureJunit5;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = CitrusdemoApplication.class)
@ActiveProfiles("integration")
@Testcontainers
@ExtendWith(AllureJunit5.class)
@Epic("REST API Regression Testing using Junit5")
class DemoIT {

	@Container
	private static final MySQLContainer<?> MY_SQL_CONTAINER = new MySQLContainer<>("mysql:5.7.34")
			.withExposedPorts(3306).withDatabaseName("testDb").withUsername("root").withPassword("root")
			.waitingFor(new LogMessageWaitStrategy().withRegEx(".*database system is ready to accept connections.*\\s")
					.withTimes(2).withStartupTimeout(Duration.of(60, ChronoUnit.SECONDS)));

	@BeforeAll
	static void startDb() throws SQLException {
		MY_SQL_CONTAINER.start();
		RestAssured.baseURI = "http://localhost:8080/user";
		RestAssured.filters(new AllureRestAssured());
	}

	@DynamicPropertySource
	static void registerPgProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url",
				() -> String.format("jdbc:mysql://localhost:%d/testDb", MY_SQL_CONTAINER.getFirstMappedPort()));
	}

	@AfterAll
	static void closeDb() {
		MY_SQL_CONTAINER.stop();
	}

	String bodyString = "{\r\n" + "    \"id\" : 1,\r\n" + "    \"userName\" : \"Aniket Sha\"\r\n" + "}";

	@Test
	@Story("POST Request with id")
	@Severity(SeverityLevel.NORMAL)
	@Description("Test Description : Verify that the POST API returns correctly")
	void apiTest() throws SQLException {
		RestAssured.defaultParser = Parser.JSON;
		RestAssured.given().urlEncodingEnabled(true).contentType("application/json").body(bodyString)
				.post("http://localhost:8080/user/add").then().statusCode(200)
				.body(equalTo("User saved successfully with name : Aniket Sha"));

	}
}
