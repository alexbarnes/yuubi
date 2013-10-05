package com.yubi.core.messaging;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name= "INT_GROUP_TO_MESSAGE")
public class GroupToMessage implements Serializable {
	
	private static final long serialVersionUID = 136165864785867951L;

	@Id
	@Column(name = "MESSAGE_ID", unique = true, nullable = false, length = 36)
	private String messageId;
	
	@Id
	@Column(length = 36, nullable = false, name = "GROUP_KEY")
	private String groupKey;

}
