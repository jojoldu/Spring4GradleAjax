package zum.potal.dwlee.vo;

public class CommonVO {

	private int pageIndex=0;//현재 페이지 
	private int pageSize=10;//한 화면당 출력될 페이지 숫자
	private int pageLength=10; // 
	
	private int family; //원글번호
	private int parent; //부모글번호
	private int depth; //깊이
	private int indent; //들여쓰기
	
	public CommonVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public int getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPageLength() {
		return pageLength;
	}
	public void setPageLength(int pageLength) {
		this.pageLength = pageLength;
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
	public int getIndent() {
		return indent;
	}
	public void setIndent(int indent) {
		this.indent = indent;
	} 
	
	
}
