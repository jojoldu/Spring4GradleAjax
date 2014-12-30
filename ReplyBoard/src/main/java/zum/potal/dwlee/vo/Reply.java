package zum.potal.dwlee.vo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Size;


@Entity
public class Reply{

	@Id
	private int no;//글번호
	
	@Size(min=0, max=20000)
	private String content;//글내용
	
	private String writeDate;
	
	private String modifyDate;
	
	private String writer;
	
	private String imageName;
	//private MultipartFile image;
	
	private int family; //원글번호
	private int parent; //부모글번호
	private int depth; //깊이
	private String path;
	
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getWriteDate() {
		return writeDate;
	}
	public void setWriteDate(String writeDate) {
		this.writeDate = writeDate;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}

	public int getFamily() {
		return family;
	}

	public void setFamily(int family) {
		this.family = family;
	}

	public int getParent() {
		return parent;
	}

	public void setParent(int parent) {
		this.parent = parent;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
}
