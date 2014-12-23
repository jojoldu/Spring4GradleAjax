package zum.potal.dwlee.vo;

import javax.persistence.Entity;


public class Common {

	private int pageIndex=0;//현재 페이지 
	private int pageSize=10;//한 페이지당 출력된 글 개수
	private int pageScope=10; // 한번에 출력될 페이지수  
	private int totalPageCount=0;//총 페이지 개수
	private int firstRow=0;
	
	private int family; //원글번호
	private int parent; //부모글번호
	private int depth; //깊이
	
	public Common() {
		super();
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

	public int getTotalPageCount() {
		return totalPageCount;
	}

	public void setTotalPageCount(int totalPageCount) {
		this.totalPageCount = totalPageCount;
	}

	public void setPageScope(int pageScope) {
		this.pageScope = pageScope;
	}

	public int getPageScope() {
		return pageScope;
	}

	public int getFirstRow() {
		return firstRow;
	}

	public void setFirstRow(int firstRow) {
		this.firstRow = firstRow;
	}

	
}
