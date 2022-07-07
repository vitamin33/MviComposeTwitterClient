package com.serbyn.mvicomposetwitterclient.ui.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.serbyn.mvicomposetwitterclient.ui.theme.MviComposeTwitterClientTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FeedFragment : Fragment() {

    private val viewModel: FeedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(inflater.context).apply {
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        setContent {
            MviComposeTwitterClientTheme {
                //MoviesScreen(state = viewModel.state, onMovieClick = ::onMovieClicked)
            }
        }
    }

//    private fun onMovieClicked(movie: Movie) {
//        activity?.let {
//            findNavController().navigate(
//                R.id.details,
//                Bundle().apply { putParcelable(AppConstants.MOVIE_BUNDLE_KEY, movie) })
//        }
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sendIntent(FeedContract.ViewIntent.Initial)
    }

    private fun sendIntent(intent: FeedContract.ViewIntent) {
        lifecycleScope.launch { viewModel.processIntents(intent) }
    }

}
