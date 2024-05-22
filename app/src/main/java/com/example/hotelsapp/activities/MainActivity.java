//package com.example.hotelsapp.activities;
//
//import android.os.Bundle;
//
//import androidx.appcompat.app.ActionBarDrawerToggle;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.Toolbar;
//import androidx.drawerlayout.widget.DrawerLayout;
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentManager;
//import androidx.fragment.app.FragmentTransaction;
//
//import com.example.hotelsapp.FavoriteFragment;
//import com.example.hotelsapp.R;
//import com.example.hotelsapp.RecommendationsFragment;
//import com.example.hotelsapp.SearchFragment;
//import com.google.android.material.bottomnavigation.BottomNavigationView;
//import com.google.android.material.floatingactionbutton.FloatingActionButton;
//import com.google.android.material.navigation.NavigationView;
//
//public class MainActivity extends AppCompatActivity {
//
//    FloatingActionButton fab;
//    DrawerLayout drawerLayout;
//    NavigationView navigationView;
//    BottomNavigationView bottomNavigationView;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        bottomNavigationView = findViewById(R.id.bottomNavigationView);
//        drawerLayout = findViewById(R.id.drawer_layout);
//
//        NavigationView navigationView = findViewById(R.id.nav_view);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//
//        setSupportActionBar(toolbar);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);
//        drawerLayout.addDrawerListener(toggle);
//        toggle.syncState();
//
//        //navigation Activity //
//
//
//        if (savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction().replace(R.id.fragme_layout, new SearchFragment()).commit();
//            navigationView.setCheckedItem(R.id.nav_view);
//        }
//
//        replaceFragment(new Fragment());
//
//        bottomNavigationView.setBackground(null);
//        bottomNavigationView.setOnItemSelectedListener(item -> {
//
//            switch (item.getItemId()) {
//                case R.id.bottomMenu_favorite:
//                    replaceFragment(new FavoriteFragment());
//                    break;
//
//                case R.id.bottomMenu_refresh:
//                    replaceFragment(new SearchFragment());
//                    break;
//
//                case R.id.bottomMenu_recommendations:
//                    replaceFragment(new RecommendationsFragment());
//                    break;
//            }
//
//            return true;
//
//        });
//
//    }
//
//    private void replaceFragment(Fragment fragment) {
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.fragme_layout, fragment);
//        fragmentTransaction.commit();
//
//    }
//}
