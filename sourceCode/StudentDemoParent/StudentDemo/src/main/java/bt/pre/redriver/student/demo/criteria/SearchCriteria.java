package bt.pre.redriver.student.demo.criteria;

abstract class SearchCriteria {

	private int pageSize;
	private int currentPage;

	public int getPageSize() {
		return pageSize;
	}

	public SearchCriteria(int pageSize, int currentPage) {
		super();
		this.pageSize = pageSize;
		this.currentPage = currentPage;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

}
