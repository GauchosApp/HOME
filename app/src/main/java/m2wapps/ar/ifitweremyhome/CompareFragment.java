package m2wapps.ar.ifitweremyhome;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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
    private String url = "https://www.ifitweremyhome.com/compare/";
    private TextView texto ;
    private ArrayList<Datos> datos;
    private ExpandableListView lista;
    private ExpandableListAdapter listAdapter;
    private ArrayList<String> listDataHeader;
    private HashMap<String, String> listDataChild;
    private Button again;

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
        View view = inflater.inflate(R.layout.fragment_compare, container, false);
        texto = (TextView) view.findViewById(R.id.texto);
        texto.setText("If "+ MainActivity.countriesCache.get(0) +" were your home instead of "+ MainActivity.countriesCache.get(1) + " you would...");
        lista = (ExpandableListView) view.findViewById(R.id.expandable);
        again = (Button) view.findViewById(R.id.again);
        again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onFragmentInteraction(null);
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
        listAdapter = new ExpandableListAdapter(this.getContext(), listDataHeader, listDataChild);
    }

}
