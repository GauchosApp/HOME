package m2wapps.ar.ifitweremyhome;


import android.net.Uri;
import android.os.StrictMode;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;


public class MainActivity extends AppCompatActivity implements MainFragment.OnFragmentInteractionListener, CompareFragment.OnFragmentInteractionListener {
    private MainFragment mainFragment;
    private CompareFragment compareFragment;
    public static SparseArray<String> digitsCache = new SparseArray<>(2);
    public static SparseArray<String> countriesCache = new SparseArray<>(2);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        setFragment(0);
    }

    public void setFragment(int position) {
        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;
        switch (position) {
            case 0:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                if (mainFragment == null) {
                    mainFragment = new MainFragment();
                }
                fragmentTransaction.replace(R.id.fragment, mainFragment);
                fragmentTransaction.commit();
                break;
            case 1:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                if (compareFragment == null) {
                    compareFragment = new CompareFragment();
                }
                fragmentTransaction.replace(R.id.fragment, compareFragment);
                fragmentTransaction.commit();
                break;
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        setFragment(1);
    }
}
