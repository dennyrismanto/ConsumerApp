package com.example.consumerapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import static com.example.consumerapp.DatabaseMovie.NoteColumns.CONTENT_URI;
import static com.example.consumerapp.DatabaseMovie.NoteColumns.ORIGINAL_TITLE;
import static com.example.consumerapp.DatabaseMovie.NoteColumns.OVERVIEW;
import static com.example.consumerapp.DatabaseMovie.NoteColumns.POSTERPATH;
import static com.example.consumerapp.DatabaseMovie.NoteColumns.RELEASEDATE;

public class DetailActivity extends AppCompatActivity {
    private Button btnFav1, btnFav2;
    public static final String EXTRA_NOTE = "extra_note";
    public static final String EXTRA_POSITION = "extra_position";
    private boolean isEdit = false;
    private Movie movieItemDatabase, movie;
    private int position;
//    private MovieHelper movieHelper;

    private ArrayList<Movie> movies = new ArrayList<>();
    private Context context;
    ImageView imgPhoto;
    TextView tvName, tvRemarks, tvRemarks2, tvDescription;
    private ProgressBar loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        loading = findViewById(R.id.progressBar);
        btnFav1 = findViewById(R.id.btn_item_favorite1);
        btnFav2 = findViewById(R.id.btn_item_favorite2);
        tvName = findViewById(R.id.tv_item_name);
        imgPhoto = findViewById(R.id.img_item_photo);
        tvRemarks = findViewById(R.id.tv_item_remarks);
        tvRemarks2 = findViewById(R.id.tv_item_remarks2);
        tvDescription = findViewById(R.id.tv_item_description);
        loading.setVisibility(View.VISIBLE);
        imgPhoto.setVisibility(View.GONE);
        btnFav1.setVisibility(View.GONE);
        btnFav2.setVisibility(View.GONE);
        tvName.setVisibility(View.GONE);
        tvRemarks.setVisibility(View.GONE);
        tvRemarks2.setVisibility(View.GONE);
        tvDescription.setVisibility(View.GONE);

        if (savedInstanceState == null){
            loading.setVisibility(View.GONE);
            imgPhoto.setVisibility(View.VISIBLE);
            btnFav1.setVisibility(View.VISIBLE);
            btnFav2.setVisibility(View.GONE);
            tvName.setVisibility(View.VISIBLE);
            tvRemarks.setVisibility(View.VISIBLE);
            tvRemarks2.setVisibility(View.VISIBLE);
            tvDescription.setVisibility(View.VISIBLE);
        } else {
            loading.setVisibility(View.GONE);
            imgPhoto.setVisibility(View.VISIBLE);
            btnFav1.setVisibility(View.VISIBLE);
            btnFav2.setVisibility(View.GONE);
            tvName.setVisibility(View.VISIBLE);
            tvRemarks.setVisibility(View.VISIBLE);
            tvRemarks2.setVisibility(View.VISIBLE);
            tvDescription.setVisibility(View.VISIBLE);
        }

        movie = getIntent().getParcelableExtra("KEY_EXTRA");
        String title = movie.getTitle();
        tvName.setText(title);
        String name = movie.getOriginalName();
        tvName.setText(name);
        String release_date = movie.getReleaseDate();
        tvRemarks2.setText(String.valueOf(release_date));
        String first_date = movie.getFirstAirDate();
        tvRemarks.setText(first_date);
        String overview = movie.getOverview();
        tvDescription.setText(overview);
//        btnFav1.setOnClickListener(this);
//        btnFav2.setOnClickListener(this);

        String poster_path = "https://image.tmdb.org/t/p/w154" + movie.getPosterPath();
        Glide.with(this).load(poster_path).apply(new RequestOptions().error(R.drawable.ic_menu_camera)).into(imgPhoto);

//Kirim data
//        movieHelper = MovieHelper.getInstance(getApplicationContext());
//        movieHelper.open();

//        Uri uri = getIntent().getData();
//        if (uri != null) {
//            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
//            if (cursor != null) {
//                if (cursor.moveToFirst()) movie = new Movie(cursor);
//                cursor.close();
//            }
//        }
        saveFavoriteMovie();
    }

    private void getDataIntent(){
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveFavoriteMovie() {
        ContentValues values = new ContentValues();
        values.put(ORIGINAL_TITLE, movie.getOriginalTitle());
        values.put(OVERVIEW, movie.getOverview());
        values.put(RELEASEDATE, movie.getReleaseDate());
        values.put(POSTERPATH, movie.getPosterPath());
        getContentResolver().insert(CONTENT_URI, values);
    }

//    @Override
//    public void onClick(View view) {
//        if (view.getId() == R.id.btn_item_favorite1) {
//            movieItemDatabase = new Movie();
//            movieItemDatabase.setTitle(movie.getTitle());
//            movieItemDatabase.setOriginalName(movie.getOriginalName());
//            movieItemDatabase.setOverview(movie.getOverview());
//            movieItemDatabase.setPosterPath(movie.getPosterPath());
//            movieItemDatabase.setReleaseDate(movie.getReleaseDate());
//            movieItemDatabase.setFirstAirDate(movie.getFirstAirDate());
//        }
////        else if (view.getId()==R.id.btn_item_favorite1){
////            movieItemDatabase = new Movie();
////            movieItemDatabase.setTitle(movie.getTitle());
////            movieItemDatabase.setOriginalName(movie.getOriginalName());
////            movieItemDatabase.setOverview(movie.getOverview());
////            movieItemDatabase.setPosterPath(movie.getPosterPath());
////            movieItemDatabase.setReleaseDate(movie.getReleaseDate());
////            movieItemDatabase.setFirstAirDate(movie.getFirstAirDate());
////        }
////
////        Cursor cek = movieHelper.cekData(movie.getOriginalTitle());
////        if (cek.getCount()>0){
////            cek.moveToFirst();
////            btnFav1.setVisibility(View.VISIBLE);
////            btnFav2.setVisibility(View.GONE);
////            movieHelper.deleteNote(cek.getInt(cek.getColumnIndex(DatabaseMovie.NoteColumns._ID)));
//////            tvMovieHelper.deleteNote(cek.getInt(cek.getColumnIndex(DatabaseMovie.NoteColumns._ID)));
////            Toast.makeText(DetailActivity.this, "Data berhasil dihapus dari favorite", Toast.LENGTH_SHORT).show();
////        }
////        else {
////            btnFav1.setVisibility(View.GONE);
////            btnFav2.setVisibility(View.VISIBLE);
////            Long insert = movieHelper.insertNote(movieItemDatabase);
//////            Long insert2 = tvMovieHelper.insertNote(movieItemDatabase);
//////            movieHelper.insertNote(movieItemDatabase);
////            if (insert>0){
////                Toast.makeText(DetailActivity.this, "Data berhasil ditambahan ke favorite", Toast.LENGTH_SHORT).show();
////            }
////        }
//    }
}
