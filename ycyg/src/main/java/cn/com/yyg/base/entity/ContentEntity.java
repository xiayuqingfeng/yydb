package cn.com.yyg.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import cn.com.easy.persistence.BaseEntity;
/**
 * 大字段内容
 * @author lvzf
 *
 */
@Entity
@Table(name = "yyg_content")
@DynamicInsert
@DynamicUpdate
public class ContentEntity  extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6672378006862115038L;
 

	/**正文 */
	@Column(columnDefinition = "longtext COMMENT '正文'")
	private String content;	
	
 

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	
	
}
