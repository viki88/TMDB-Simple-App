package com.purwadhika.simplemovieappthemoviedb.ui.favorite

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.purwadhika.simplemovieappthemoviedb.database.table.FavMovie
import com.purwadhika.simplemovieappthemoviedb.databinding.FragmentFavoriteListBinding
import com.purwadhika.simplemovieappthemoviedb.ui.detailfavmovie.DetailFavMovieActivity
import com.purwadhika.simplemovieappthemoviedb.ui.detailmovie.DetailMovieViewModel

class FavoriteListFragment : Fragment(), OnClickFavoriteMovieListener{

    lateinit var binding: FragmentFavoriteListBinding
    private val viewModel : DetailMovieViewModel by viewModels()
    lateinit var adapter :FavoriteListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentFavoriteListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListFavTrending()

        viewModel.favMovieLiveData.observe(viewLifecycleOwner, Observer {
            if (it.isEmpty()){
                binding.rvFavList.visibility = View.GONE
                binding.emptyText.visibility = View.VISIBLE
            }else{
                binding.rvFavList.visibility = View.VISIBLE
                binding.emptyText.visibility = View.GONE
            }
            adapter.updateList(it)
        })

        viewModel.loadAllFavMovie()
    }

    private fun setupListFavTrending(){
        adapter = FavoriteListAdapter( this)
        binding.rvFavList.adapter = adapter
        binding.rvFavList.layoutManager = LinearLayoutManager(requireContext())
        binding.rvFavList.addItemDecoration(DividerItemDecoration(requireContext(),DividerItemDecoration.VERTICAL))
    }

    override fun onClicked(favMovie: FavMovie) {
        val intent = Intent(requireContext(), DetailFavMovieActivity::class.java)
        intent.putExtra(DetailFavMovieActivity.FAV_DATA, favMovie)
        startActivity(intent)
    }

    override fun onLongClicked(favMovie: FavMovie) {
        AlertDialog.Builder(requireContext())
            .setTitle("Delete Favorite Movie")
            .setMessage("Are you sure want to delete ${favMovie.title} ?")
            .setPositiveButton("Delete") { dialogInterface, _ ->
                dialogInterface.dismiss()
                viewModel.deleteFavMovie(favMovie.idmovie)
            }
            .setNegativeButton("Cancel"){dialogInterface, _ ->
                dialogInterface.dismiss()
            }.create().show()
    }
}