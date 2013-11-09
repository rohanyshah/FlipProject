package com.AutoFlip.flip;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.Map;
import java.text.SimpleDateFormat;

import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.pdfbox.pdmodel.*;

import java.io.File;

import android.os.Build;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //SEE WHAT HAPPENS WHEN WE WRITE STUFF IN THE SAME PLACE.
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

    public void importCards(View view)
    {
        File pdf = new File();
    }

    private String processPDF(File file, String outputDateMask, Map<String, String> metadata) throws Throwable {
        PDFTextStripper pdfS = new PDFTextStripper();
        PDDocument pdoc = new PDDocument();
        pdoc = PDDocument.load(file);
        PDDocumentInformation info = pdoc.getDocumentInformation();
        if (info.getTitle() != null) {
            metadata.put("title",info.getTitle());
        }
        if (info.getAuthor() != null) {
            metadata.put("author",info.getAuthor());
        }
        if (info.getSubject() != null) {
            metadata.put("subject",info.getSubject());
        }
        if (info.getKeywords() != null) {
            metadata.put("keywords",info.getKeywords());
        }
        if (info.getCreator() != null) {
            metadata.put("creator",info.getCreator());
        }
        if (info.getProducer() != null) {
            metadata.put("producer",info.getProducer());
        }
        SimpleDateFormat sdf = new SimpleDateFormat(outputDateMask);
        if (info.getModificationDate() != null) {
            metadata.put("published",sdf.format(info.getModificationDate().getTime()));
        } else if (info.getCreationDate() != null) {
            metadata.put("published",sdf.format(info.getCreationDate().getTime()));
        }
        return pdfS.getText(pdoc);
    }
}
