package svetozarradic.ferit.hr.rma_project.models;

public class FitbitParameters {
    public static int STEPS=0;
    public static int CALORIES=0;

    private Summary summary;

    public void FitbitParameters(Summary summary) {
        this.summary = summary;
    }

    public Summary getSummary() {
        return summary;
    }
}
