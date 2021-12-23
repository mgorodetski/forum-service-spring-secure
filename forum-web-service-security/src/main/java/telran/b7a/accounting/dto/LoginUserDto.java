package telran.b7a.accounting.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@NonNull
public class LoginUserDto { // after we made Authorization header we don't actually need LoginUserDto class!!! 
	String login;
	String password;

}
