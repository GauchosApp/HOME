package m2wapps.ar.ifitweremyhome;


import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.StrictMode;


import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
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
    private int position;

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
                this.position = 0;
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
                this.position = 1;
                fragmentTransaction.replace(R.id.fragment, compareFragment);
                fragmentTransaction.commit();
                break;
        }
    }
    @Override
    public void onBackPressed() {
        if (position == 1){
            setFragment(0);
         //   getFragmentManager().popBackStack();

        }
        else {
            super.onBackPressed();
        }

    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        if(position == 0) {
            setFragment(1);
        }else{
            setFragment(0);
        }
    }
    @Override
    public void onRequestPermissionsResult(int permsRequestCode, @NonNull String[] permissions, @NonNull int[] grantResults){

        System.out.println("entra: "+permsRequestCode);
        boolean writeAccepted;
        switch(permsRequestCode){
            case 1:
               writeAccepted = grantResults[0]== PackageManager.PERMISSION_GRANTED;
                if (writeAccepted){
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{"android.permission.READ_EXTERNAL_STORAGE"},
                        2);
                }
                break;
            case 2:
                writeAccepted = grantResults[0]== PackageManager.PERMISSION_GRANTED;
                if (writeAccepted) {
                    compareFragment.takeScreenshot();
                }
                break;

        }

    }
}
