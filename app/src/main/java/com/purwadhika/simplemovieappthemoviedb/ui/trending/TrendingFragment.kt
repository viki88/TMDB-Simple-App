package com.purwadhika.simplemovieappthemoviedb.ui.trending

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.purwadhika.simplemovieappthemoviedb.Constant
import com.purwadhika.simplemovieappthemoviedb.databinding.FragmentTrendingBinding
import com.purwadhika.simplemovieappthemoviedb.network.ApiService
import com.purwadhika.simplemovieappthemoviedb.network.response.Genre
import com.purwadhika.simplemovieappthemoviedb.network.response.GenreResponse
import com.purwadhika.simplemovieappthemoviedb.network.response.Movie
import com.purwadhika.simplemovieappthemoviedb.network.response.TrendingMovieResponse
import com.purwadhika.simplemovieappthemoviedb.ui.detailmovie.DetailMovieActivity
import com.purwadhika.simplemovieappthemoviedb.ui.home.TrendingMovieListAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TrendingFragment : Fragment(), TrendingMovieListAdapter.OnClickMovieListListerner{

    private lateinit var binding :FragmentTrendingBinding
    private lateinit var adapter: TrendingMovieListAdapter
    private var genreList = listOf<Genre>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentTrendingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListView()
        loadGenreList()
    }

    private fun setupListView(){
        adapter = TrendingMovieListAdapter(requireContext(), this)
        binding.rvTrendingList.layoutManager = LinearLayoutManager(requireContext())
        binding.rvTrendingList.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
        binding.rvTrendingList.adapter = adapter
    }

    private fun loadGenreList(){
        binding.progressBar.visibility = View.VISIBLE
        ApiService().getTheMovieDbApiService().getAllGenres(Constant.API_KEY)
                .enqueue(object : Callback<GenreResponse> {
                    override fun onFailure(call: Call<GenreResponse>, t: Throwable) {
                        binding.progressBar.visibility = View.GONE
                        Log.i("TMDB", "onFailure: ${t.message}")
                        Toast.makeText(requireContext(), "error response : ${t.message}", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(
                            call: Call<GenreResponse>,
                            response: Response<GenreResponse>
                    ) {
                        binding.progressBar.visibility = View.GONE
                        val responseData = response.body()
                        responseData?.let {
                            genreList = it.genres
                            loadTrendingMovie()
                        }
                    }

                })
    }

    private fun loadTrendingMovie(){
        binding.progressBar.visibility = View.VISIBLE
        ApiService().getTheMovieDbApiService().getAllTrending(Constant.API_KEY)
                .enqueue(object : Callback<TrendingMovieResponse> {
                    override fun onFailure(call: Call<TrendingMovieResponse>, t: Throwable) {
                        binding.progressBar.visibility = View.GONE
                        Log.i("TMDB", "onFailure: ${t.message}")
                        Toast.makeText(requireContext(), "error response : ${t.message}", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<TrendingMovieResponse>, response: Response<TrendingMovieResponse>) {
                        binding.progressBar.visibility = View.GONE

                        val responseData = response.body()

                        responseData?.let {
                            adapter.updateGenreList(genreList)
                            adapter.updateList(it.results)
                        }
                    }

                })
    }

    override fun onClickMovieList(movie: Movie) {
        val intent = Intent(requireContext(), DetailMovieActivity::class.java)
        intent.putExtra(DetailMovieActivity.MOVIE_DATA, movie)
        startActivity(intent)
    }
}