package com.example.post.model.users;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor //생성자의 매게변수로 넣어주는 것
/*위의 어노테이션은 
 * GenderType(String description) {
this.description = description

}
와 같은 의미
 */
@Getter
public enum GenderType {
	MALE("남성"),
	FEMALE("여성");
	
	private final String description;
}
