public class Submission {

    private String title;
    private String abstractText;
    private String supervisor;
    private PresentationType presentationType;
    private String filePath;

    public Submission(String title, String abstractText,
                      String supervisor,
                      PresentationType presentationType,
                      String filePath) {

        this.title = title;
        this.abstractText = abstractText;
        this.supervisor = supervisor;
        this.presentationType = presentationType;
        this.filePath = filePath;
    }

    public String getTitle() { return title; }
    public String getAbstractText() { return abstractText; }
    public String getSupervisor() { return supervisor; }
    public PresentationType getPresentationType() { return presentationType; }
    public String getFilePath() { return filePath; }
}
