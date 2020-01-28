package btsdigital.kz.ui.login

import android.app.SearchManager
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.database.Cursor
import android.database.MatrixCursor
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.provider.BaseColumns
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.SimpleCursorAdapter
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import btsdigital.kz.R
import btsdigital.kz.data.network.model.PhotoSearchResultItem
import btsdigital.kz.databinding.FragmentSearchBinding
import btsdigital.kz.ui.ImageAdapter
import btsdigital.kz.utils.KeyboardUtils.hideKeyboard


class SearchFragment : Fragment() {

    private lateinit var viewModel: SearchViewModel
    private lateinit var binding: FragmentSearchBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        return binding.root
    }

    private fun initialiseSearchView() {
        val from = arrayOf(SearchManager.SUGGEST_COLUMN_TEXT_1)
        val to = intArrayOf(android.R.id.text1)
        val suggestions: List<String> = viewModel.loadSuggestions()
        val cursorAdapter = SimpleCursorAdapter(context, R.layout.item_search,
                null, from, to, 0)

        binding.searchView.suggestionsAdapter = cursorAdapter
        binding.searchView.setOnSuggestionListener(object : SearchView.OnSuggestionListener {
            override fun onSuggestionSelect(position: Int): Boolean {
                return false
            }

            override fun onSuggestionClick(position: Int): Boolean {
                val cursor = binding.searchView.suggestionsAdapter.getItem(position) as Cursor
                val selection = cursor.getString(cursor.getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_1))
                binding.searchView.setQuery(selection, false)

                // Do something with selection
                performSearch(selection)
                return true
            }
        })


        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(text: String): Boolean {
                performSearch(text)
                return true
            }

            override fun onQueryTextChange(text: String): Boolean {
                val cursor = MatrixCursor(arrayOf(BaseColumns._ID, SearchManager.SUGGEST_COLUMN_TEXT_1))
                suggestions.forEachIndexed { index, suggestion ->
                    if (suggestion.contains(text, true)) {
                        cursor.addRow(arrayOf(index, suggestion))
                    }
                }

                cursorAdapter.changeCursor(cursor)
                return true
            }
        })
    }

    private fun performSearch(text: String) {
        hideKeyboard(activity)
        viewModel.requestImages(text)
        viewModel.saveSearch(text)
    }

    private fun showImages(images: List<PhotoSearchResultItem>) {
        val columns = 3

        val adapter = ImageAdapter(images, columns)
        adapter.setOnImageClickListener(object : ImageAdapter.OnPhotoSearchResultClickListener {
            override fun onImageClicked(image: PhotoSearchResultItem) {

                val bundle = Bundle()
                bundle.putString(IMAGE_URL_EXTRA, image.url)
                bundle.putString(IMAGE_TITLE_EXTRA, image.title)

                findNavController().navigate(R.id.action_searchFragment_to_imageFragment, bundle)
            }
        })

        binding.rvImages.adapter = adapter
        binding.rvImages.layoutManager = GridLayoutManager(context, columns)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)
        binding.vm = viewModel

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbarSearch)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)

        initialiseSearchView()

        subscribeToImageLoads()

        val isImagesExist = viewModel.imagesLoaded.value == null

        if (savedInstanceState == null && isImagesExist) {
            viewModel.requestImages()
        }

        viewModel.showErrorDialog.observe(this, Observer { msg ->
            msg?.getContentIfNotHandled()?.let {
                // Only proceed if the event has never been handled
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun subscribeToImageLoads() {
        viewModel.imagesLoaded.observe(this, Observer {
            if (it != null && it.isNotEmpty()) {
                showImages(it)
            }
        })
    }
}
