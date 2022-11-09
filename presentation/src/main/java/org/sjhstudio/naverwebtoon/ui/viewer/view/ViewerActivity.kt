package org.sjhstudio.naverwebtoon.ui.viewer.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import org.sjhstudio.naverwebtoon.R
import org.sjhstudio.naverwebtoon.base.BaseActivity
import org.sjhstudio.naverwebtoon.databinding.ActivityViewerBinding
import org.sjhstudio.naverwebtoon.ui.viewer.adapter.ViewerAdapter
import org.sjhstudio.naverwebtoon.ui.viewer.viewmodel.ViewerViewModel

@AndroidEntryPoint
class ViewerActivity : BaseActivity<ActivityViewerBinding>(R.layout.activity_viewer) {

    private val viewerViewModel: ViewerViewModel by viewModels()
    private val viewerAdapter: ViewerAdapter by lazy { ViewerAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind()
        observeData()
    }

    private fun bind() {
        with(binding) {
            adapter = viewerAdapter
        }
    }

    private fun observeData() {
        with(viewerViewModel) {
            lifecycleScope.launchWhenStarted {
                viewerList.collectLatest { pagingData ->
                    viewerAdapter.submitData(pagingData)
                }
            }
        }
    }
}