package zum.potal.dwlee.vo;

public class ResponseObject {

	private boolean result=false;

	
	public ResponseObject() {
	}
	
	public ResponseObject(boolean result) {
		this.result = result;
	}


	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}
	
	
}
