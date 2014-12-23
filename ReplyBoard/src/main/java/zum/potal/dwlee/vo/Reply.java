package zum.potal.dwlee.vo;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.web.multipart.MultipartFile;

@Entity
public class Reply extends Common{

	@Id
	private int no;//글번호
	private String content;//글내용
	private String writeDate;
	private String writer;
	
	private String imageName;
	//private MultipartFile image;
	
	public Reply() {
		super();
		// TODO Auto-generated constructor stub
	}
	
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

//	public MultipartFile getImage() {
//		return image;
//	}
//
//	public void setImage(MultipartFile image) {
//		this.image = image;
//	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	
	
	
}
