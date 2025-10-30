package autotest.page.Rise;

public class ProjectData {

	public ProjectData() {
		// TODO Auto-generated constructor stub
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	 public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}


	private String title;
	private String projectType;
	private String client;
	private String description;
	private String startDate;
	private String deadline;
	private String price;
	private String label;
   
	private String status;

	public ProjectData(String title, String projectType, String client, 
			String description, String startDate,
			String deadline, String price, String label ) {
		this.title = title;
		this.projectType = projectType;
		this.client = client;
		this.description = description;
		this.startDate = startDate;
		this.deadline = deadline;
		this.price = price;
		this.label = label;
	}
    private ProjectData(Builder builder) {
        this.title = builder.title;
        this.projectType = builder.projectType;
        this.client = builder.client;
        this.description = builder.description;
        this.startDate = builder.startDate;
        this.deadline = builder.deadline;
        this.price = builder.price;
        this.label = builder.label;
        this.status = builder.status;
    }
    public static class Builder {
        private final String title;
        private final String projectType;
        
        private String client;
        private String description;
        private String startDate;
        private String deadline;
        private String price;
        private String label;
        private String status;

        public Builder(String title, String projectType) {
            this.title = title;
            this.projectType = projectType;
        }
        public Builder withClient(String client) {
            this.client = client;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withStartDate(String startDate) {
            this.startDate = startDate;
            return this;
        }

        public Builder withDeadline(String deadline) {
            this.deadline = deadline;
            return this;
        }
        public Builder withPrice(String price) {
            this.price = price;
            return this;
        }

        public Builder withLabel(String label) {
            this.label = label;
            return this;
        }

        public Builder withStatus(String status) {
            this.status = status;
            return this;
        }

        public ProjectData build() {
            return new ProjectData(this);
        }
       
    }
	public static ProjectData of(String title, String type, String client, String description,
                String startDate, String deadline, String price, String label) {
		return null;
	}
}
