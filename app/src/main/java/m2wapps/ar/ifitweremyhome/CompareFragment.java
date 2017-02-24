package m2wapps.ar.ifitweremyhome;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CompareFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class CompareFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private Pais pais1, pais2;
    private String url = "https://www.ifitweremyhome.com/compare/";
    private TextView texto ;
    public CompareFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.

     * @return A new instance of fragment CompareFragment.
     */


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
        // Inflate the layout for this fragment
      //  getInfo();
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
            String urlAux = url + MainActivity.digitsCache.get(0)+"/"+MainActivity.digitsCache.get(1);
            System.out.println(urlAux);
            Document doc = Jsoup.connect(urlAux).get();
            /*Elements aux = doc.getElementsByClass("band3");
            Elements aux2 = aux.select("div.content");
            Elements aux3 = aux2.select("div#comparisons");
            Elements countries = aux3.select("div.comparison_negative");*/
            Elements countries = doc.select("div.band3 div.content div#comparisons div.comparison.negative");
            for (Element e: countries){
              //  paises.add(new Pais(e.text(),e.attr("data-iso2")));
                System.out.println(e.html());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
