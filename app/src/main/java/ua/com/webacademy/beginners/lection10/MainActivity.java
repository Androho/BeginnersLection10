package ua.com.webacademy.beginners.lection10;

import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Student>> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                getLoaderManager().initLoader(0, null, this);
                break;
            case R.id.button2:
                getLoaderManager().restartLoader(0, null, this);
                break;
            case R.id.button3:
                getContacts();
                break;
            case R.id.button4:
                getStudents();
                break;
        }
    }

    @Override
    public Loader<List<Student>> onCreateLoader(int i, Bundle bundle) {
        return new StudentsLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<List<Student>> loader, List<Student> students) {
        Toast.makeText(this, String.valueOf(students.size()), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoaderReset(Loader<List<Student>> loader) {

    }

    private List<Student> getStudents() {
        List<Student> students = new ArrayList<>();
        Cursor cursor = null;

        try {
            Uri uri = Uri.parse("content://ua.com.webacademy.lection11/students");

            cursor = getContentResolver().query(uri, null, null, null, null);

            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    Student student = new Student();

                    student.id = cursor.getLong(cursor.getColumnIndex("_id"));
                    student.FirstName = cursor.getString(cursor.getColumnIndex("FirstName"));
                    student.LastName = cursor.getString(cursor.getColumnIndex("LastName"));
                    student.Age = cursor.getLong(cursor.getColumnIndex("Age"));

                    students.add(student);

                    cursor.moveToNext();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return students;
    }

    private List<String> getContacts() {
        List<String> names = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME}, null, null, null);

            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    names.add(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)));

                    cursor.moveToNext();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return names;
    }
}
