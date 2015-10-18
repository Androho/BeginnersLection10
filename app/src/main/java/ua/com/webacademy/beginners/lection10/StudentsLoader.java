package ua.com.webacademy.beginners.lection10;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class StudentsLoader extends AsyncTaskLoader<List<Student>> {

    private Context Context;
    private List<Student> Students;

    public StudentsLoader(Context context) {
        super(context);

        this.Context = context;
    }

    @Override
    public List<Student> loadInBackground() {
        Students = new ArrayList<Student>();

        Students.add(new Student());
        Students.add(new Student());

        return Students;
    }

    @Override
    public void deliverResult(List<Student> data) {
        if (isReset()) {
            return;
        }

        Students = data;

        if (isStarted()) {
            super.deliverResult(data);
        }
    }

    @Override
    protected void onStartLoading() {
        if (Students != null) {
            deliverResult(Students);
        }

        if (takeContentChanged() || Students == null) {
            forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override
    protected void onReset() {
        onStopLoading();

        if (Students != null) {
            Students = null;
        }
    }
}
