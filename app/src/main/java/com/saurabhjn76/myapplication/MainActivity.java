package com.saurabhjn76.myapplication;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    public  ArrayList<Salon> salons= new ArrayList<Salon>();

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private VerticalViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        readData();
       /* Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (VerticalViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);


      /*  FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private static  final String ARG_SALON_NAME="salon_name";
        private static  final String ARG_SALON_DISTANCE="salon_distance";
        private static  final String ARG_SALON_ADDRESS="salon_address";


        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber,Salon salon) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            args.putDouble(ARG_SALON_DISTANCE,salon.getDistance());
            args.putString(ARG_SALON_NAME,salon.getSalonName());
            args.putString(ARG_SALON_ADDRESS,salon.getAddressLine1() + " " + salon.getAddressLine2() );

            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            //TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            //textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
           CardView cv = (CardView)rootView.findViewById(R.id.cv);
            TextView salonName = (TextView)rootView.findViewById(R.id.salon_name);
            salonName.setText(ARG_SALON_NAME);
           // salonName.setText(salons.get(ARG_SECTION_NUMBER).getSalonName());
            TextView salonDistance = (TextView)rootView.findViewById(R.id.salon_distance);
            salonDistance.setText(ARG_SALON_DISTANCE);
            TextView salonAddress = (TextView)rootView.findViewById(R.id.AddressTextView);
            salonAddress.setText(ARG_SALON_ADDRESS);
            ImageView salonPhoto = (ImageView)rootView.findViewById(R.id.salon_photo);
            switch (getArguments().getInt(ARG_SECTION_NUMBER) % 3) {
                case 0:
                    salonPhoto.setImageResource(R.drawable.hair_inside_salon);
                    break;
                case 1:
                   salonPhoto.setImageResource(R.drawable.salon_4_full);
                    break;
                case 2:
                   salonPhoto.setImageResource(R.drawable.slider_newton_highlands);
                    break;
            }
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private FragmentManager mFragmentManager;
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            this.mFragmentManager = fm;
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1,salons.get(position));
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return salons.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
           return "Section" +position;
        }
    }
    void writeData()
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("salon-app-dad97/data");
        //  Firebase ref = new Firebase("https://salon-app-dad97.firebaseio.com/data");
        Salon[] s= new Salon[10];
        s[0]=new Salon("Affinity Salon","1st Floor, Global Foyer Mall","Golf Course Road","Unisex",4.2,true,true,4);
        s[1]=new Salon("Neu Salonz","4th Floor","South Point Mall","Unisex",4.1,true,true,8);
        s[2]=new Salon("Bella Madonna","123 1st Floor","South Point Mall","Unisex",4.2,true,true,0.6);
        s[3]=new Salon("Levo Saplon","Ibis Hotel","Golf Course Road","Unisex",5,false,true,0.3);
        s[4]=new Salon("ISAAC Wellness","319, 3rd Floor","South Point Mall","Unisex",-1,true,true,1.4);
        s[5]=new Salon("Kosh Salon","DSF Phase 2 , Behind Supermart-2","Supermart 2","Unisex",-1,true,false,3.6);
        s[6]=new Salon("Makeup by Vidya Tikari","GF, Peagasu One Ibis Hotel DLF","Golf Course Road","Women",-1,false,true,4.8);
        s[7]=new Salon("Cut & Style","Shop No A-241","Supermart 1","Unisex",4.4,false,false,0.39);
        s[8]=new Salon("Tangles Salon","6005, Opposite Supermart 1","DLF Phase 4","Unisex",4,true,true,5);
        s[9]=new Salon("Amaaya Spa","6302 , Opp. Supemart 1","DLF Phase 4","Unisex",-1,true,true,3.8);

        for(int i=0;i<10;i++)
            ref.push().setValue(s[i]);

       /* ref.setValue(s[0], new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if (firebaseError != null) {
                    System.out.println("Data could not be saved. " + firebaseError.getMessage());
                } else {
                    System.out.println("Data saved successfully.");
                }
            }
        });*/

    }

    ArrayList<Salon> readData() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("salon-app-dad97/data");

        // Attach an listener to read the data at our posts reference
        ref.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                //  Log.i(this, "Called getTestData()");
                System.out.println("There are " + snapshot.getChildrenCount() + " salons");
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Salon salon = postSnapshot.getValue(Salon.class);
                    // System.out.println(salon.getSalonName());
                    Log.e("data", "" + salon);
                    salons.add(salon);
                    mSectionsPagerAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: "+ databaseError.getMessage());
            }
        });
        return salons;
    }

}
