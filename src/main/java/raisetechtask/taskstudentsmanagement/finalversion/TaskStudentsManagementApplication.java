package raisetechtask.taskstudentsmanagement.finalversion;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(info = @Info(title = "受講生管理システム"))
@SpringBootApplication
public class TaskStudentsManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskStudentsManagementApplication.class, args);
	}

}
