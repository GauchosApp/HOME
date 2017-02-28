package m2wapps.ar.ifitweremyhome;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;



/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CompareFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class CompareFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private String url = "https://www.ifitweremyhome.com/compare/", text;
    private TextView texto ;
    private ArrayList<Datos> datos;
    private ExpandableListView lista;
    private ExpandableListAdapter listAdapter;
    private ArrayList<String> listDataHeader;
    private HashMap<String, String> listDataChild;
    private Button again;
    private Button share;
    private View view;
    private LinearLayout botones;

    public CompareFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_compare, container, false);
        botones = (LinearLayout) view.findViewById(R.id.botones);
        texto = (TextView) view.findViewById(R.id.texto);
        text = "If "+ MainActivity.countriesCache.get(1) +" were your home instead of "+ MainActivity.countriesCache.get(0) + " you would...";
        texto.setTextColor(getResources().getColor(R.color.texto));
        texto.setText(text);
        lista = (ExpandableListView) view.findViewById(R.id.expandable);
        again = (Button) view.findViewById(R.id.again);
        again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onFragmentInteraction(null);
            }
        });
        share = (Button) view.findViewById(R.id.share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (shouldAskPermission()) {
                   System.out.println("ask");
                 //  String[] perms = {"android.permission. WRITE_EXTERNAL_STORAGE"};

                //   int permsRequestCode = 200;
                   ActivityCompat.requestPermissions(CompareFragment.this.getActivity(),
                           new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"},
                           1);
               //    requestPermissions(perms, permsRequestCode);
                //   takeScreenshot();

               }else
               {
                   System.out.println("share");
                   takeScreenshot();
               }
            }
        });
        // Inflate the layout for this fragment
        hiloInfo();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    private void getInfo(){
        try {
            datos = new ArrayList<>();
            String urlAux = url + MainActivity.digitsCache.get(0)+"/"+MainActivity.digitsCache.get(1);
            System.out.println(urlAux);
            Document doc = Jsoup.connect(urlAux).get();
            Elements negative = doc.select("div.band3 div.content div#comparisons div.comparison.negative");
            Elements positive = doc.select("div.band3 div.content div#comparisons div.comparison.positive");
            Elements neutral = doc.select("div.band3 div.content div#comparisons div.comparison.neutral");
            Element detail;
            Element explanation;

            for (Element e: negative){
                detail = e.getElementsByClass("detail").first();
                explanation = e.getElementsByClass("explanation").first();
               // System.out.println("negative: "+explanation.text());
                datos.add(new Datos(0,detail.text(),explanation.text()));
            }
            for (Element e: positive){
                detail = e.getElementsByClass("detail").first();
                explanation = e.getElementsByClass("explanation").first();
             //   System.out.println("positive: "+explanation.text());
                datos.add(new Datos(1,detail.text(),explanation.text()));
            }
            for (Element e: neutral){
                detail = e.getElementsByClass("detail").first();
                explanation = e.getElementsByClass("explanation").first();
             //   System.out.println("neutral: "+explanation.text());
                datos.add(new Datos(2,detail.text(),explanation.text()));
            }
            Collections.shuffle(datos);
           cargarAdapter();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void hiloInfo(){
        class GetPartidos extends AsyncTask<Void,Void,String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
             //   showLoading();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

              //  hideLoading();
                lista.setAdapter(listAdapter);
            }

            @Override
            protected String doInBackground(Void... params) {
                getInfo();
                return "";
            }
        }
        GetPartidos  gi = new GetPartidos ();
        gi.execute();
    }
    private void cargarAdapter(){
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();
        for (Datos d : datos){
            listDataHeader.add(d.getTitulo());
            listDataChild.put(d.getTitulo(), d.getDetalle());
        }
        listAdapter = new ExpandableListAdapter(this.getContext(), listDataHeader, listDataChild, datos);
    }
  /*  protected void takeScreenshot() {
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        try {
            // image naming and path  to include sd card  appending name you choose for file
            String mPath = Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpg";

            // create bitmap screen capture
         //   View v1 = getActivity().getWindow().getDecorView().getRootView().findViewById(R.id.principal);
            View v1 = this.getView();
            again.setVisibility(View.GONE);
            share.setVisibility(View.GONE);
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);
            again.setVisibility(View.VISIBLE);
            share.setVisibility(View.VISIBLE);
            File imageFile = new File(mPath);

            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();
            System.out.println(mPath);
            shareImage(mPath);
        } catch (Throwable e) {
            // Several error may come out with file handling or OOM
            e.printStackTrace();
        }
    }
    private void shareImage(String dir){
        Uri uri = Uri.parse(dir);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/jpg");

        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
        intent.putExtra(android.content.Intent.EXTRA_TEXT, "");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        try {
            startActivity(Intent.createChooser(intent, "Share to: "));
        } catch (ActivityNotFoundException e) {
            System.out.println("error");
        }
    }*/
    protected void takeScreenshot(){
        String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() +
                "/ifitweremyhome/";
        View v1 = this.getView();
        botones.setVisibility(View.GONE);
        v1.setDrawingCacheEnabled(true);
        Bitmap screenshot = Bitmap.createBitmap(v1.getDrawingCache());
        v1.setDrawingCacheEnabled(false);
        botones.setVisibility(View.VISIBLE);
        File dir = new File(file_path);
        dir.mkdirs();
        File file = new File(dir, "screen.png");
        FileOutputStream fOut;
        try {
            fOut = new FileOutputStream(file);

            screenshot.compress(Bitmap.CompressFormat.PNG, 100, fOut);

            fOut.flush();
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


// Share
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/png");

        share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
        share.putExtra(Intent.EXTRA_TEXT, "IFITWEREMYHOME - "+text);

        startActivity(Intent.createChooser(share, "Share To:"));
    }
    private boolean shouldAskPermission(){

        return(Build.VERSION.SDK_INT>Build.VERSION_CODES.LOLLIPOP_MR1);

    }
}
