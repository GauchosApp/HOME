package m2wapps.ar.ifitweremyhome;

import android.content.Context;
import android.database.MatrixCursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;



public class MainFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private ArrayList<Pais> paises;
    private ArrayList<String> auxNombres, auxNombres2, auxDigits, auxDigits2;
    private String digit1, digit2, name1, name2;
    private SearchView pais1, pais2;
    private SimpleCursorAdapter mAdapter, mAdapter2;
    // TODO: Rename and change types of parameters


    private OnFragmentInteractionListener mListener;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        pais1.setQuery(name1,true);
        pais2.setQuery(name2,true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        // Inflate the layout for this fragment
        pais1 = (SearchView) view.findViewById(R.id.pais1);
        pais2 = (SearchView) view.findViewById(R.id.pais2);
        int id;
         id = pais1.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
     /*   TextView textView ;
        textView = (TextView) pais1.findViewById(id);
        textView.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        id = pais2.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        textView = (TextView) pais2.findViewById(id);
        textView.setTextColor(getResources().getColor(R.color.colorPrimaryDark));*/
        pais1.setIconifiedByDefault(false);
        pais2.setIconifiedByDefault(false);
        Button compareBtn = (Button) view.findViewById(R.id.compareBtn);
        compareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    if(digit1 != null && digit2 != null) {
                        MainActivity.digitsCache.put(0,digit1);
                        MainActivity.digitsCache.put(1,digit2);
                        MainActivity.countriesCache.put(0,name1);
                        MainActivity.countriesCache.put(1,name2);
                        mListener.onFragmentInteraction(null);
                    }
                }
            }
        });
        final String[] from = new String[] {"countryName"};
        final String[] from2 = new String[] {"countryName2"};
        final int[] to = new int[] {android.R.id.text1};
        mAdapter = new SimpleCursorAdapter(getActivity(),
                android.R.layout.simple_list_item_1,
                null,
                from,
                to,
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        mAdapter2 = new SimpleCursorAdapter(getActivity(),
                android.R.layout.simple_list_item_1,
                null,
                from2,
                to,
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        pais1.setSuggestionsAdapter(mAdapter);
        pais2.setSuggestionsAdapter(mAdapter2);


        listeners();
        getPaises();
        return view;
    }
    private void getPaises(){
        paises = new ArrayList<>();
        try {
            String url = "https://www.ifitweremyhome.com/";
            Document doc = Jsoup.connect(url).get();
            Elements aux = doc.getElementsByClass("country_cloud");
            Elements countries = aux.select("a");
            for (Element e: countries){
                paises.add(new Pais(e.text(),e.attr("data-iso2")));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void populateAdapter(String query, boolean esPrimerSearch) {
        final MatrixCursor c;
        if (esPrimerSearch) {
            c = new MatrixCursor(new String[]{BaseColumns._ID, "countryName"});
            auxNombres = new ArrayList<>();
            auxDigits = new ArrayList<>();
            for (int i=0; i < paises.size(); i++) {
                if (paises.get(i).getName().toLowerCase().startsWith(query.toLowerCase())) {
                    c.addRow(new Object[]{i, paises.get(i).getName()});
                    auxNombres.add(paises.get(i).getName());
                    auxDigits.add(paises.get(i).getDigit());
                }
            }
            mAdapter.changeCursor(c);
        }else{
            c = new MatrixCursor(new String[]{BaseColumns._ID, "countryName2"});
            auxNombres2 = new ArrayList<>();
            auxDigits2 = new ArrayList<>();
            for (int i=0; i < paises.size(); i++) {
                if (paises.get(i).getName().toLowerCase().startsWith(query.toLowerCase())) {
                    c.addRow(new Object[]{i, paises.get(i).getName()});
                    auxNombres2.add(paises.get(i).getName());
                    auxDigits2.add(paises.get(i).getDigit());
                }
            }
            mAdapter2.changeCursor(c);
        }
    }
    private void listeners(){
        pais1.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                populateAdapter(newText, true);
                return false;
            }
        });
        pais1.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(int position) {
                return true;
            }

            @Override
            public boolean onSuggestionClick(int position) {
                pais1.setQuery(auxNombres.get(position),true);
                digit1 = auxDigits.get(0);
                name1 = auxNombres.get(0);
                return true;
            }
        });
        pais2.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                populateAdapter(newText, false);
                return false;
            }
        });
        pais2.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(int position) {
                return true;
            }

            @Override
            public boolean onSuggestionClick(int position) {
                pais2.setQuery(auxNombres2.get(position),true);
                digit2 = auxDigits2.get(0);
                name2 = auxNombres2.get(0);
                return true;
            }
        });
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
}
