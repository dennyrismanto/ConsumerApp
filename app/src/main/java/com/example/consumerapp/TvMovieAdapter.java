package com.example.consumerapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TvMovieAdapter extends RecyclerView.Adapter<TvMovieAdapter.MovieViewHolder> {
    private ArrayList<Movie> movies = new ArrayList<>();
    private Context context;



    @NonNull
    @Override
    public TvMovieAdapter.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        return new TvMovieAdapter.MovieViewHolder(inflater.inflate(R.layout.itemrow_tvmovie, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TvMovieAdapter.MovieViewHolder categoryViewHolder, final int position) {
        Movie movie = movies.get(position);
        if(movie !=null){
            categoryViewHolder.bind(movie);
        }
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void setMovies(List<Movie>movies){
        this.movies.clear();
        this.movies.addAll(movies);
        notifyDataSetChanged();
    }
    public ArrayList<Movie> getMovies() {
        return movies;
    }

    class MovieViewHolder extends RecyclerView.ViewHolder{
        TextView tvName, tvRemarks, tvDescription;
        ImageView imgPhoto;
        public MovieViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_item_name);
            tvRemarks = itemView.findViewById(R.id.tv_item_remarks);
            tvDescription = itemView.findViewById(R.id.tv_item_description);
            imgPhoto = itemView.findViewById(R.id.img_item_photo);

        }
        void bind(Movie movie){
            Log.d(MovieAdapter.class.getSimpleName(), "bind: " + movie.getOriginalName());
            tvName.setText(movie.getOriginalName());
            tvRemarks.setText(String.valueOf(movie.getFirstAirDate()));
            tvDescription.setText(movie.getOverview());
            MovieClient.LoadImage(context, imgPhoto, movie.getPosterPath());

            imgPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    Movie clickDataItem = movies.get(pos);
                    Intent detailIntent = new Intent(context, TvMovieDetail.class);
                    detailIntent.putExtra("KEY_EXTRA", clickDataItem);
                    context.startActivity(detailIntent);
                }
            });
        }
    }
}
