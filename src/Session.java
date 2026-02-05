import java.util.*;

public class Session {
    private String date;
    private String venue;
    private String sessionType; // Oral / Poster
    private List<Student> presenters = new ArrayList<>();
    private List<Evaluator> evaluators = new ArrayList<>();

    public Session(String date, String venue, String sessionType) {
        this.date = date;
        this.venue = venue;
        this.sessionType = sessionType;
    }

    public String getDate() { return date; }
    public String getVenue() { return venue; }
    public String getSessionType() { return sessionType; }
    public List<Student> getPresenters() { return presenters; }
    public List<Evaluator> getEvaluators() { return evaluators; }
}
